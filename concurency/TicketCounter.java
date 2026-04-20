SCENARIO 1 — Ticket Number Generator
Atomic is STRONG


// ❌ BUG — Duplicate Ticket Numbers (Race Condition)

import java.util.concurrent.*;

public class HelpDesk_Bug_TicketCounter {

    static int ticket = 0;

    public static void main(String[] args) {
        ExecutorService agents = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            agents.submit(() -> {
                int issued = ++ticket; // NOT ATOMIC
                System.out.println(
                    Thread.currentThread().getName()
                    + " issued ticket " + issued
                );
            });
        }

        agents.shutdown();
    }
}

// pool-1-thread-1 issued ticket 1
// pool-1-thread-2 issued ticket 1


FIX — AtomicInteger

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HelpDesk_Fix_TicketCounter {

    static AtomicInteger ticket = new AtomicInteger(0);

    public static void main(String[] args) {
        ExecutorService agents = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            agents.submit(() -> {
                int issued = ticket.incrementAndGet();
                System.out.println(
                    Thread.currentThread().getName()
                    + " issued ticket " + issued
                );
            });
        }

        agents.shutdown();
    }
}

Tickets always unique.



🟢 SCENARIO 2 — Help Desk Open / Closed Flag
AtomicBoolean is STRONG


public class HelpDesk_Bug_Visibility {

    static boolean open = true;

    public static void main(String[] args) throws Exception {

        new Thread(() -> {
            while (open) {
                // agent keeps working
            }
            System.out.println("Agent stopped");
        }).start();

        Thread.sleep(2000);
        open = false; // may never be seen
    }
}
// ❌ May run forever.


import java.util.concurrent.atomic.AtomicBoolean;

public class HelpDesk_Fix_Visibility {

    static AtomicBoolean open = new AtomicBoolean(true);

    public static void main(String[] args) throws Exception {

        new Thread(() -> {
            while (open.get()) {
            }
            System.out.println("Agent stopped");
        }).start();

        Thread.sleep(2000);
        open.set(false);
    }
}
// ✅ All agents see update.


SCENARIO 3 — Atomic FAILS
Check-Then-Assign Logic



BUG — Atomic Used Incorrectly

import java.util.concurrent.atomic.AtomicBoolean;

public class HelpDesk_Bug_CheckThenAct {

    static AtomicBoolean agentFree = new AtomicBoolean(true);

    public static void main(String[] args) {

        Runnable assign = () -> {
            if (agentFree.get()) {
                agentFree.set(false);
                System.out.println("Customer assigned");
            }
        };

        new Thread(assign).start();
        new Thread(assign).start();
    }
}

// ❌ Two customers can be assigned.

✅ FIX — synchronized

public class HelpDesk_Fix_Assignment {

    static class AssignmentBoard {
        private boolean agentFree = true;

        synchronized void assignCustomer() {
            if (agentFree) {
                agentFree = false;
                System.out.println(
                    Thread.currentThread().getName()
                    + " assigned customer"
                );
            }
        }
    }

    public static void main(String[] args) {
        AssignmentBoard board = new AssignmentBoard();

        new Thread(board::assignCustomer).start();
        new Thread(board::assignCustomer).start();
    }
}


Only one assignment happens.

🟡 SCENARIO 4 — synchronized for Business Rules
Customer Assignment System

❌ BUG — No Synchronization

import java.util.*;

public class HelpDesk_Bug_AssignmentBoard {

    static Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {
        queue.add(1);

        Runnable assign = () -> {
            Integer ticket = queue.poll();
            System.out.println(
                Thread.currentThread().getName()
                + " took ticket " + ticket
            );
        };

        new Thread(assign).start();
        new Thread(assign).start();
    }
}
❌ Same ticket may be taken twice.



✅ FIX — synchronized

import java.util.*;

public class HelpDesk_Fix_AssignmentBoard {

    static class Board {
        Queue<Integer> queue = new LinkedList<>();

        synchronized Integer nextTicket() {
            return queue.poll();
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.queue.add(1);

        Runnable assign = () -> {
            Integer ticket = board.nextTicket();
            System.out.println(
                Thread.currentThread().getName()
                + " took ticket " + ticket
            );
        };

        new Thread(assign).start();
        new Thread(assign).start();
    }
}

Queue remains consistent.


SCENARIO 5 — synchronized FAILS
Agent Should Not Wait Forever


PROBLEM WITH synchronized
synchronized void accessSystem() {
    // waits forever if locked
}


❌ No timeout
❌ No cancellation



SCENARIO 6 — Lock (Advanced Control)
Admin Console Access
✅ FIX — ReentrantLock
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class HelpDesk_Fix_Lock {

    static class AdminConsole {
        private final Lock lock = new ReentrantLock(true);

        void access() {
            try {
                if (lock.tryLock(3, TimeUnit.SECONDS)) {
                    try {
                        System.out.println(
                            Thread.currentThread().getName()
                            + " accessing admin console"
                        );
                        Thread.sleep(2000);
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println(
                        Thread.currentThread().getName()
                        + " timed out"
                    );
                }
            } catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args) {
        AdminConsole console = new AdminConsole();

        new Thread(console::access).start();
        new Thread(console::access).start();
    }
}


✅ Timeout
✅ Fairness
✅ No dead waiting



FINAL HELP-DESK DECISION MAP
Help Desk Problem	Tool
Ticket numbers	AtomicInteger
Open / closed flag	AtomicBoolean
Simple counters	Atomic
Assigning customers	synchronized
Business rules	synchronized
Timeout / cancel	Lock
Fair access	Lock
🧠 ONE FINAL LINE (LOCK THIS IN)

Atomic protects simple state, synchronized protects rules, Lock protects behavior and control.

You now have real, production-level intuition for concurrency.