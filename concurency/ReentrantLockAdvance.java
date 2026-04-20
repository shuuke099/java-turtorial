import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class ServiceCenter {

    // ==============================
    // 🔐 STATE
    // ==============================
    private int ticket = 0;

    private final ReentrantLock lock = new ReentrantLock(); // 🔒 main lock
    private final Condition notEmpty = lock.newCondition();  // 🔔 replaces wait/notify

    private final Queue<Integer> queue = new LinkedList<>();

    // ==============================
    // 🎟️ PRODUCER (assign ticket)
    // ==============================
    public void assignTicket() {

        lock.lock(); // 🔒 acquire lock
        try {
            int newTicket = ++ticket;
            queue.add(newTicket);

            System.out.println("🎟️ Assigned: " + newTicket +
                    " by " + Thread.currentThread().getName());

            notEmpty.signalAll(); // 🔔 wake up waiting consumers
        } finally {
            lock.unlock(); // 🔓 always release
        }
    }

    // ==============================
    // 👨‍💼 CONSUMER (serve ticket)
    // ==============================
    public void serveCustomer() {

        int servingTicket;

        lock.lock();
        try {

            while (queue.isEmpty()) {
                try {
                    System.out.println("⏳ Waiting for customers...");
                    notEmpty.await(); // 🔄 wait (releases lock)
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            servingTicket = queue.poll();

            System.out.println("👨‍💼 Serving: " + servingTicket +
                    " by " + Thread.currentThread().getName());

        } finally {
            lock.unlock();
        }

        doWork(); // outside lock
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
        lock.lock();
        try {
            ticket = 0;
            queue.clear();
            System.out.println("🔄 System reset");
        } finally {
            lock.unlock();
        }
    }

    // ==============================
    // 📊 READ
    // ==============================
    public int getCurrentTicket() {
        lock.lock();
        try {
            return ticket;
        } finally {
            lock.unlock();
        }
    }

    // ==============================
    // 🎯 MAIN
    // ==============================
    public static void main(String[] args) throws Exception {

        ServiceCenter center = new ServiceCenter();

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
    }
}



// synchronized(lock) {
//     queue.wait();
//     queue.notifyAll();
// }


lock.lock();
notEmpty.await();
notEmpty.signalAll();
lock.unlock();

Condition notEmpty;
Condition notFull;