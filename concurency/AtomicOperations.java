BANK ACCOUNT CONCURRENCY — FULL STORY

We will model ONE bank account accessed by multiple ATMs (threads).

🟢 CASE 1 — SIMPLE BALANCE COUNTER
(Atomic is STRONG here)
🏦 Business Requirement

Track account balance

Only deposits

No overdraft rules

Just increment safely

import java.util.concurrent.*;

public class Bank_Bug_Race {

    static int balance = 0;

    public static void main(String[] args) {
        ExecutorService atmPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            atmPool.submit(() -> {
                balance += 100; // NOT ATOMIC
                System.out.println("Balance: " + balance);
            });
        }

        atmPool.shutdown();
    }
}


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank_Fix_Atomic {

    static AtomicInteger balance = new AtomicInteger(0);

    public static void main(String[] args) {
        ExecutorService atmPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            atmPool.submit(() -> {
                int newBalance = balance.addAndGet(100);
                System.out.println("Balance: " + newBalance);
            });
        }

        atmPool.shutdown();
    }
}



CASE 2 — WITHDRAW WITH RULES
(Atomic FAILS)
🏦 New Business Rule

❗ Balance must NEVER go below zero



import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank_Bug_AtomicWithdraw {

    static AtomicInteger balance = new AtomicInteger(100);

    public static void main(String[] args) {
        ExecutorService atmPool = Executors.newFixedThreadPool(2);

        Runnable withdraw = () -> {
            if (balance.get() >= 100) {
                balance.addAndGet(-100); // NOT SAFE AS A RULE
                System.out.println("Withdraw successful");
            }
        };

        atmPool.submit(withdraw);
        atmPool.submit(withdraw);

        atmPool.shutdown();
    }
}


import java.util.concurrent.*;

public class Bank_Fix_Synchronized {

    static class Account {
        private int balance = 100;

        synchronized boolean withdraw(int amount) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdraw approved. Balance: " + balance);
                return true;
            }
            System.out.println("Withdraw denied");
            return false;
        }
    }

    public static void main(String[] args) {
        Account account = new Account();
        ExecutorService atmPool = Executors.newFixedThreadPool(2);

        atmPool.submit(() -> account.withdraw(100));
        atmPool.submit(() -> account.withdraw(100));

        atmPool.shutdown();
    }
}


CASE 3 — ATM TIMEOUT / CANCEL
(synchronized FAILS)
🏦 New Real-World Requirement

ATM should wait only 3 seconds

If account is locked → cancel

Customer should NOT wait forever

synchronized cannot timeout.



PROBLEM WITH synchronized
synchronized void withdraw() {
    // waits forever if locked
}

// FIX — Lock (Full Control)

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Bank_Fix_Lock {

    static class Account {
        private int balance = 100;
        private final Lock lock = new ReentrantLock(true); // fair lock

        boolean withdraw(int amount) throws InterruptedException {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    if (balance >= amount) {
                        balance -= amount;
                        System.out.println(
                            Thread.currentThread().getName()
                            + " withdrew. Balance: " + balance
                        );
                        return true;
                    }
                    System.out.println("Insufficient funds");
                    return false;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(
                    Thread.currentThread().getName()
                    + " timed out"
                );
                return false;
            }
        }
    }

    public static void main(String[] args) {
        Account account = new Account();
        ExecutorService atmPool = Executors.newFixedThreadPool(2);

        atmPool.submit(() -> {
            try { account.withdraw(100); } catch (Exception e) {}
        });
        atmPool.submit(() -> {
            try { account.withdraw(100); } catch (Exception e) {}
        });

        atmPool.shutdown();
    }
}
