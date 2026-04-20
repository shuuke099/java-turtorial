import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Phase2_AtomicFix {

    // ✅ Thread-safe counter
    private AtomicInteger ticketNumber = new AtomicInteger(0);

    // ✅ Safe method
    private void assignTicket() {
        int newNumber = ticketNumber.incrementAndGet(); // atomic

        System.out.println(
            "🎟️ Ticket " + newNumber +
            " assigned by " + Thread.currentThread().getName()
        );
    }

    public static void main(String[] args) {

        Phase2_AtomicFix system = new Phase2_AtomicFix();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 10 customers
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> system.assignTicket());
        }

        executor.shutdown();
    }
}

// 🎟️ Ticket 1 assigned by pool-1-thread-1
// 🎟️ Ticket 2 assigned by pool-1-thread-3
// 🎟️ Ticket 3 assigned by pool-1-thread-2
// 🎟️ Ticket 4 assigned by pool-1-thread-5
// 🎟️ Ticket 5 assigned by pool-1-thread-4
// 🎟️ Ticket 6 assigned by pool-1-thread-1
// 🎟️ Ticket 7 assigned by pool-1-thread-3
// 🎟️ Ticket 8 assigned by pool-1-thread-2
// 🎟️ Ticket 9 assigned by pool-1-thread-5
// 🎟️ Ticket 10 assigned by pool-1-thread-4