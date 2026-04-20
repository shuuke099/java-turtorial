public class DeadlockExample {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void task1() {
        synchronized (lock1) {
            System.out.println("Thread 1: Holding lock1...");

            sleep(100); // simulate delay

            System.out.println("Thread 1: Waiting for lock2...");
            synchronized (lock2) {
                System.out.println("Thread 1: Acquired lock2");
            }
        }
    }

    public void task2() {
        synchronized (lock2) {
            System.out.println("Thread 2: Holding lock2...");

            sleep(100);

            System.out.println("Thread 2: Waiting for lock1...");
            synchronized (lock1) {
                System.out.println("Thread 2: Acquired lock1");
            }
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {

        DeadlockExample example = new DeadlockExample();

        Thread t1 = new Thread(example::task1);
        Thread t2 = new Thread(example::task2);

        t1.start();
        t2.start();
    }
}


// =============================


public void task1() {
    synchronized (lock1) {
        synchronized (lock2) {
            System.out.println("Task1 done");
        }
    }
}

public void task2() {
    synchronized (lock1) { // ✅ SAME ORDER
        synchronized (lock2) {
            System.out.println("Task2 done");
        }
    }
}