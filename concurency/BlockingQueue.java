import java.util.concurrent.*;

public class ServiceCenter {

    // ==============================
    // 🔐 STATE
    // ==============================
    private int ticket = 0;

    // Thread-safe queue (handles waiting automatically)
    private final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    // ==============================
    // 🎟️ PRODUCER
    // ==============================
    public void assignTicket() {
        try {
            int newTicket = ++ticket;

            queue.put(newTicket); // 🔒 blocks if needed

            System.out.println("🎟️ Assigned: " + newTicket +
                    " by " + Thread.currentThread().getName());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ==============================
    // 👨‍💼 CONSUMER
    // ==============================
    public void serveCustomer() {
        try {
            int servingTicket = queue.take(); // 🔄 waits automatically

            System.out.println("👨‍💼 Serving: " + servingTicket +
                    " by " + Thread.currentThread().getName());

            doWork();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ==============================
    // ⚙️ WORK
    // ==============================
    private void doWork() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
    }
}