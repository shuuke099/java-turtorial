import java.util.concurrent.locks.ReentrantLock;

public class LockExample {

    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {

        lock.lock(); // 🔒 acquire

        try {
            count++;
            System.out.println("Count: " + count +
                    " by " + Thread.currentThread().getName());
        } finally {
            lock.unlock(); // 🔓 ALWAYS release
        }
    }
}


// =============================
lock.lock();
try {
    // critical section
} finally {
    lock.unlock();
}
// ==============================

if (lock.tryLock()) {
    try {
        // do work
    } finally {
        lock.unlock();
    }
} else {
    System.out.println("Could not get lock");
}

if (lock.tryLock(2, TimeUnit.SECONDS)) {
    try {
        // safe work
    } finally {
        lock.unlock();
    }
}