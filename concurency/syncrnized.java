import java.util.concurrent.*;

public class ServiceCenter {

    private int ticket = 0;

    // ✅ Single shared lock (correct)
    private final Object lock = new Object();

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    // 🚀 Start system
    public void start() {

        for (int i = 0; i < 5; i++) {
            executor.submit(this::processCustomer); // ✅ no fake synchronization here
        }

        executor.shutdown();
    }

    // 🎯 Critical section properly protected
    public void processCustomer() {

        int assignedTicket;

        // ✅ ONLY protect critical logic (small block)
        synchronized (lock) {
            assignedTicket = ++ticket;
            System.out.println("Serving ticket: " + assignedTicket +
                    " by " + Thread.currentThread().getName());
        }

        // ✅ Non-critical work OUTSIDE lock (important)
        doWork();
    }

    // ⚙️ Simulate processing (not synchronized)
    private void doWork() {
        try {
            Thread.sleep(100); // simulate real work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // 🔄 Reset safely (same lock!)
    public void reset() {
        synchronized (lock) {
            ticket = 0;
            System.out.println("🔄 System reset");
        }
    }

    // 📊 Read safely (optional but consistent)
    public int getCurrentTicket() {
        synchronized (lock) {
            return ticket;
        }
    }

    public static void main(String[] args) throws Exception {

        ServiceCenter center = new ServiceCenter();

        center.start();

        Thread.sleep(1000);

        center.reset();

        System.out.println("📊 Final ticket: " + center.getCurrentTicket());
    }
}




// ==============================

import java.util.concurrent.*;

public class ServiceCenter {

    private int ticket = 0;

    // ✅ Single shared lock (correct)
    private final Object lock = new Object();

    // 🎯 Critical section properly protected
    public void processCustomer() {

        int assignedTicket;

        synchronized (lock) {
            assignedTicket = ++ticket;
            System.out.println("Serving ticket: " + assignedTicket +
                    " by " + Thread.currentThread().getName());
        }

        doWork();
    }

    // ⚙️ Simulate processing (not synchronized)
    private void doWork() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // 🔄 Reset safely (same lock!)
    public void reset() {
        synchronized (lock) {
            ticket = 0;
            System.out.println("🔄 System reset");
        }
    }

    // 📊 Read safely
    public int getCurrentTicket() {
        synchronized (lock) {
            return ticket;
        }
    }

    // 🎯 MAIN (everything here now)
    public static void main(String[] args) throws Exception {

        ServiceCenter center = new ServiceCenter();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 🚀 Start system (moved here)
        for (int i = 0; i < 5; i++) {
            executor.submit(center::processCustomer); // ✅ correct (instance reference)
        }

        executor.shutdown();

        Thread.sleep(1000);

        center.reset();

        System.out.println("📊 Final ticket: " + center.getCurrentTicket());
    }
}
FULL SAMPLE RUN
Serving ticket: 1 by pool-1-thread-3
Serving ticket: 2 by pool-1-thread-1
Serving ticket: 3 by pool-1-thread-5
Serving ticket: 4 by pool-1-thread-2
Serving ticket: 5 by pool-1-thread-4
🔄 System reset
📊 Final ticket: 0

// ==============================

✅ Possible Output #1
Serving ticket: 1 by pool-1-thread-1
Serving ticket: 2 by pool-1-thread-2
Serving ticket: 3 by pool-1-thread-3
Serving ticket: 4 by pool-1-thread-4
Serving ticket: 5 by pool-1-thread-5
✅ Possible Output #2 (mixed threads)
Serving ticket: 1 by pool-1-thread-3
Serving ticket: 2 by pool-1-thread-1
Serving ticket: 3 by pool-1-thread-5
Serving ticket: 4 by pool-1-thread-2
Serving ticket: 5 by pool-1-thread-4
✅ Possible Output #3 (another variation)
Serving ticket: 2 by pool-1-thread-2
Serving ticket: 1 by pool-1-thread-1
Serving ticket: 4 by pool-1-thread-4
Serving ticket: 3 by pool-1-thread-3
Serving ticket: 5 by pool-1-thread-5
