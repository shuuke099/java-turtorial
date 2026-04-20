import java.util.*;
import java.util.concurrent.*;


// Guarantee	Yes/No
// No race condition	✅
// One thread at a time	✅
// Correct values	✅


public class ServiceCenter {

    // ==============================
    // 🔐 STATE
    // ==============================
    private int ticket = 0;
    private final Object lock = new Object(); // custom monitor

    // Queue for wait/notify demo
    private final Queue<Integer> queue = new LinkedList<>();

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    // ==============================
    // 🚀 START SYSTEM
    // ==============================
    public void start() {

        // Producers (assign tickets)
        for (int i = 0; i < 5; i++) {
            executor.submit(this::assignTicket);
        }

        // Consumers (serve tickets)
        for (int i = 0; i < 5; i++) {
            executor.submit(this::serveCustomer);
        }

        executor.shutdown();
    }

    // ==============================
    // 🎟️ PRODUCER (adds tickets)
    // ==============================
    public void assignTicket() {
        synchronized (lock) {

            int newTicket = ++ticket;
            queue.add(newTicket);

            System.out.println("🎟️ Assigned: " + newTicket +
                    " by " + Thread.currentThread().getName());

            lock.notifyAll(); // 🔔 wake up waiting consumers
        }
    }

    // ==============================
    // 👨‍💼 CONSUMER (serves tickets)
    // ==============================
    public void serveCustomer() {

        int servingTicket;

        synchronized (lock) {

            // 🔄 wait until ticket available
            while (queue.isEmpty()) {
                try {
                    System.out.println("⏳ Waiting for customers...");
                    lock.wait(); // 🔴 releases lock + waits
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            servingTicket = queue.poll();

            System.out.println("👨‍💼 Serving: " + servingTicket +
                    " by " + Thread.currentThread().getName());
        }

        // outside lock
        doWork();
    }

    // ==============================
    // ⚙️ NON-CRITICAL WORK
    // ==============================
    private void doWork() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ==============================
    // 🔄 RESET (BLOCK SYNC)
    // ==============================
    public void reset() {
        synchronized (lock) {
            ticket = 0;
            queue.clear();
            System.out.println("🔄 System reset");
        }
    }

    // ==============================
    // 📊 READ (BLOCK SYNC)
    // ==============================
    public int getCurrentTicket() {
        synchronized (lock) {
            return ticket;
        }
    }

    // ==============================
    // 🎤 SYNCHRONIZED METHOD (this)
    // ==============================
    public synchronized void announce() {
        System.out.println("📢 System running...");
    }

    // ==============================
    // 🌍 STATIC SYNCHRONIZATION
    // ==============================
    private static int totalGlobalServed = 0;

    public static synchronized void globalCount() {
        totalGlobalServed++;
        System.out.println("🌍 Total served globally: " + totalGlobalServed);
    }

    // ==============================
    // 🎯 MAIN
    // ==============================
    public static void main(String[] args) throws Exception {

        ServiceCenter center = new ServiceCenter();

        center.announce(); // instance synchronized

        center.start();

        Thread.sleep(2000);

        center.reset();

        System.out.println("📊 Final ticket: " + center.getCurrentTicket());

        // static sync demo
        ServiceCenter.globalCount();
    }
}




// ==============================

import java.util.*;
import java.util.concurrent.*;

public class ServiceCenter {

    // ==============================
    // 🔐 STATE
    // ==============================
    private int ticket = 0;
    private final Object lock = new Object();

    private final Queue<Integer> queue = new LinkedList<>();

    // ==============================
    // 🎟️ PRODUCER
    // ==============================
    public void assignTicket() {
        synchronized (lock) {

            int newTicket = ++ticket;
            queue.add(newTicket);

            System.out.println("🎟️ Assigned: " + newTicket +
                    " by " + Thread.currentThread().getName());

            lock.notifyAll(); // wake up consumers
        }
    }

    // ==============================
    // 👨‍💼 CONSUMER
    // ==============================
    public void serveCustomer() {

        int servingTicket;

        synchronized (lock) {

            while (queue.isEmpty()) {
                try {
                    System.out.println("⏳ Waiting for customers...");
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            servingTicket = queue.poll();

            System.out.println("👨‍💼 Serving: " + servingTicket +
                    " by " + Thread.currentThread().getName());
        }

        doWork();
    }

    // ==============================
    // ⚙️ NON-CRITICAL WORK
    // ==============================
    private void doWork() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ==============================
    // 🔄 RESET
    // ==============================
    public void reset() {
        synchronized (lock) {
            ticket = 0;
            queue.clear();
            System.out.println("🔄 System reset");
        }
    }

    // ==============================
    // 📊 READ
    // ==============================
    public int getCurrentTicket() {
        synchronized (lock) {
            return ticket;
        }
    }

    // ==============================
    // 🎤 INSTANCE SYNC
    // ==============================
    public synchronized void announce() {
        System.out.println("📢 System running...");
    }

    // ==============================
    // 🌍 STATIC SYNC
    // ==============================
    private static int totalGlobalServed = 0;

    public static synchronized void globalCount() {
        totalGlobalServed++;
        System.out.println("🌍 Total served globally: " + totalGlobalServed);
    }

    // ==============================
    // 🎯 MAIN (CONTROL CENTER)
    // ==============================
    public static void main(String[] args) throws Exception {

        ServiceCenter center = new ServiceCenter();

        center.announce();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 🚀 PRODUCERS
        for (int i = 0; i < 5; i++) {
            executor.submit(center::assignTicket);
        }

        // 🚀 CONSUMERS
        for (int i = 0; i < 5; i++) {
            executor.submit(center::serveCustomer);
        }

        executor.shutdown();

        Thread.sleep(2000);

        center.reset();

        System.out.println("📊 Final ticket: " + center.getCurrentTicket());

        ServiceCenter.globalCount();
    }
}



🎯 PART 3 — Final section (ALWAYS same)
🔄 System reset
📊 Final ticket: 0
🌍 Total served globally: 1

// ==============================
🎯 PART 2 — Consumers (serve tickets)

✔ All 5 tickets will be served
❌ Order may vary

✅ Possible Output #1 (clean flow)
🎟️ Assigned: 1 by pool-1-thread-1
🎟️ Assigned: 2 by pool-1-thread-2
🎟️ Assigned: 3 by pool-1-thread-3
🎟️ Assigned: 4 by pool-1-thread-4
🎟️ Assigned: 5 by pool-1-thread-5

👨‍💼 Serving: 1 by pool-1-thread-2
👨‍💼 Serving: 2 by pool-1-thread-3
👨‍💼 Serving: 3 by pool-1-thread-4
👨‍💼 Serving: 4 by pool-1-thread-1
👨‍💼 Serving: 5 by pool-1-thread-5
✅ Possible Output #2 (mixed timing)
🎟️ Assigned: 1 by pool-1-thread-3
👨‍💼 Serving: 1 by pool-1-thread-1
🎟️ Assigned: 2 by pool-1-thread-2
🎟️ Assigned: 3 by pool-1-thread-5
👨‍💼 Serving: 2 by pool-1-thread-4
👨‍💼 Serving: 3 by pool-1-thread-2
🎟️ Assigned: 4 by pool-1-thread-1
🎟️ Assigned: 5 by pool-1-thread-3
👨‍💼 Serving: 4 by pool-1-thread-5
👨‍💼 Serving: 5 by pool-1-thread-1
⚠️ Possible Output #3 (waiting happens)
⏳ Waiting for customers...
⏳ Waiting for customers...

🎟️ Assigned: 1 by pool-1-thread-2
🎟️ Assigned: 2 by pool-1-thread-3

👨‍💼 Serving: 1 by pool-1-thread-1
👨‍💼 Serving: 2 by pool-1-thread-4

🎟️ Assigned: 3 by pool-1-thread-5
🎟️ Assigned: 4 by pool-1-thread-2
🎟️ Assigned: 5 by pool-1-thread-3

