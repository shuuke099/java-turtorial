import java.util.concurrent.*;

public class Phase1_RaceCondition {

    // ❌ Shared variable (NOT thread-safe)
    private int ticketNumber = 0;

    // ❌ Problem method
    private void assignTicket() {
        int newNumber = ++ticketNumber; // NOT atomic

        System.out.println(
            "🎟️ Ticket " + newNumber +
            " assigned by " + Thread.currentThread().getName()
        );
    }

    public static void main(String[] args) {

        Phase1_RaceCondition system = new Phase1_RaceCondition();

        // Thread pool (agents)
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Simulate 10 customers arriving
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> system.assignTicket());
        }

        executor.shutdown();
    }
}

🎟️ Ticket 1 assigned by pool-1-thread-1
🎟️ Ticket 2 assigned by pool-1-thread-2
🎟️ Ticket 2 assigned by pool-1-thread-3  ❌ duplicate
🎟️ Ticket 4 assigned by pool-1-thread-4
🎟️ Ticket 5 assigned by pool-1-thread-5
🎟️ Ticket 6 assigned by pool-1-thread-1
🎟️ Ticket 7 assigned by pool-1-thread-2
🎟️ Ticket 7 assigned by pool-1-thread-3  ❌ duplicate
🎟️ Ticket 9 assigned by pool-1-thread-4  ❌ missing 8
🎟️ Ticket 10 assigned by pool-1-thread-5

🎟️ Ticket 2 assigned by pool-1-thread-1
🎟️ Ticket 1 assigned by pool-1-thread-2
🎟️ Ticket 3 assigned by pool-1-thread-3
🎟️ Ticket 5 assigned by pool-1-thread-4
🎟️ Ticket 5 assigned by pool-1-thread-5  ❌ duplicate
🎟️ Ticket 6 assigned by pool-1-thread-1
🎟️ Ticket 8 assigned by pool-1-thread-2  ❌ missing 7
🎟️ Ticket 9 assigned by pool-1-thread-3
🎟️ Ticket 10 assigned by pool-1-thread-4
🎟️ Ticket 10 assigned by pool-1-thread-5 ❌ duplicate