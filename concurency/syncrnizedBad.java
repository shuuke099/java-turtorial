import java.util.concurrent.*;

public class BadServiceCenter {

    private int ticket = 0;

    private ExecutorService executor = Executors.newFixedThreadPool(5);

    public void start() {

        // ❌ Mistake 1: Synchronizing submission instead of execution
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                executor.submit(() -> processCustomer());
            }
        }

        executor.shutdown();
    }

    public void processCustomer() {

        // ❌ Mistake 2: Using different lock every time
        synchronized (new Object()) {

            // ❌ Mistake 3: Non-atomic operation (race condition)
            int current = ticket;
            current = current + 1;

            // ❌ Artificial delay to increase race condition chance
            try { Thread.sleep(50); } catch (InterruptedException e) {}

            ticket = current;

            System.out.println("Serving ticket: " + ticket +
                    " by " + Thread.currentThread().getName());
        }
    }

    // ❌ Mistake 4: Mixing locks (instance vs class)
    public void reset() {
        synchronized (BadServiceCenter.class) {
            ticket = 0;
        }
    }

    // ❌ Mistake 5: Over-synchronization (huge block)
    public void heavyOperation() {
        synchronized (this) {
            System.out.println("Starting heavy task...");

            for (int i = 0; i < 5; i++) {
                try { Thread.sleep(100); } catch (Exception e) {}
                System.out.println("Processing step " + i);
            }

            System.out.println("Finished heavy task.");
        }
    }

    public static void main(String[] args) {
        BadServiceCenter center = new BadServiceCenter();

        center.start();

        // ❌ Race with reset
        center.reset();

        center.heavyOperation();
    }
}

// Serving ticket: 1
// Serving ticket: 2
// Serving ticket: 2   ❌ duplicate
// Serving ticket: 4   ❌ skipped 3
// Serving ticket: 5

// This bad code demonstrates:

// ❌ Race conditions
// ❌ Incorrect locking
// ❌ False sense of safety
// ❌ Poor performance
// ❌ Unpredictable results