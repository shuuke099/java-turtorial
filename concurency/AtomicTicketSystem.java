// ✔ No duplicates
// ✔ No missing values
// ✔ Correct updates (thread-safe)




import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class AtomicTicketSystem {

    // Core atomic variables
    private AtomicInteger ticketCounter = new AtomicInteger(0);
    private AtomicBoolean systemOpen = new AtomicBoolean(true);
    private AtomicLong totalServed = new AtomicLong(0);

    // 🎟️ Assign ticket (incrementAndGet)
    public int assignTicket() {
        if (!systemOpen.get()) {
            System.out.println("❌ System closed");
            return -1;
        }

        int ticket = ticketCounter.incrementAndGet(); // atomic increment
        totalServed.incrementAndGet();

        System.out.println("🎟️ Ticket " + ticket + " assigned by " +
                Thread.currentThread().getName());

        return ticket;
    }

    // ❌ Cancel ticket (decrementAndGet)
    public void cancelTicket() {
        int value = ticketCounter.decrementAndGet();
        System.out.println("❌ Ticket canceled → now: " + value);
    }

 
    // 📊 Read current ticket (get)
    public void printCurrentTicket() {
        System.out.println("📊 Current ticket: " + ticketCounter.get());
    }

    // 🔧 Force set value (set)
    public void forceSetTicket(int value) {
        ticketCounter.set(value);
        System.out.println("⚙️ Forced ticket to: " + value);
    }

    // ➕ Batch add tickets (addAndGet)
    public void addBulkTickets(int n) {
        int result = ticketCounter.addAndGet(n);
        System.out.println("➕ Bulk added → now: " + result);
    }

    // 🔁 Update using function (updateAndGet)
    public void doubleTickets() {
        int result = ticketCounter.updateAndGet(x -> x * 2);
        System.out.println("🔁 Doubled tickets → now: " + result);
    }

    // 🔐 Compare and set (CAS)
    public void safeUpdate(int expected, int newValue) {
        boolean success = ticketCounter.compareAndSet(expected, newValue);

        System.out.println("🔐 CAS update: " + success +
                " → current: " + ticketCounter.get());
    }

    // 🔁 accumulateAndGet (NEW VALUE)
    public void accumulateExample() {
        int result = ticketCounter.accumulateAndGet(5, (current, x) -> current + x);

        System.out.println("🔁 accumulateAndGet (+5) → new: " + result);
    }

    // 🔁 getAndAccumulate (OLD VALUE)
    public void getAndAccumulateExample() {
        int old = ticketCounter.getAndAccumulate(3, (current, x) -> current * x);

        System.out.println("🔁 getAndAccumulate (*3) → old: " + old +
                " | now: " + ticketCounter.get());
    }



   // 🔄 Reset system (getAndSet)
    public void resetSystem() {
        int old = ticketCounter.getAndSet(0);

        System.out.println("🔄 System reset. Previous tickets: " + old);
    }

    // 🚪 Open / Close system
    public void toggleSystem(boolean state) {
        systemOpen.set(state);
        System.out.println("🚪 System open: " + state);
    }

    // 📈 Total served (AtomicLong)
    public void printTotalServed() {
        System.out.println("📈 Total served: " + totalServed.get());
    }

    // 🎯 MAIN TEST
    public static void main(String[] args) throws Exception {

        AtomicTicketSystem system = new AtomicTicketSystem();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Assign tickets concurrently
        for (int i = 0; i < 5; i++) {
            executor.submit(system::assignTicket);
        }

        Thread.sleep(1000);

        // Cancel a ticket
        system.cancelTicket();

        // Bulk add
        system.addBulkTickets(3);

        // Double tickets
        system.doubleTickets();

        // CAS attempt
        system.safeUpdate(10, 100);

        // 🔧 Force set value
        system.forceSetTicket(50);

        // 🔁 accumulate
        system.accumulateExample();

        // 🔁 getAndAccumulate
        system.getAndAccumulateExample();

        // Print current
        system.printCurrentTicket();

        // Reset system
        system.resetSystem();

        // Close system
        system.toggleSystem(false);

        // Try assigning when closed
        system.assignTicket();

        // Total served
        system.printTotalServed();

        executor.shutdown();
    }
}
Possible Output #1
🎟️ Ticket 1 assigned by pool-1-thread-3
🎟️ Ticket 2 assigned by pool-1-thread-1
🎟️ Ticket 3 assigned by pool-1-thread-5
🎟️ Ticket 4 assigned by pool-1-thread-2
🎟️ Ticket 5 assigned by pool-1-thread-4
✅ Possible Output #2
🎟️ Ticket 1 assigned by pool-1-thread-1
🎟️ Ticket 2 assigned by pool-1-thread-2
🎟️ Ticket 3 assigned by pool-1-thread-3
🎟️ Ticket 4 assigned by pool-1-thread-4
🎟️ Ticket 5 assigned by pool-1-thread-5
✅ Possible Output #3 (mixed)
🎟️ Ticket 2 assigned by pool-1-thread-2
🎟️ Ticket 1 assigned by pool-1-thread-1
🎟️ Ticket 4 assigned by pool-1-thread-4
🎟️ Ticket 3 assigned by pool-1-thread-3
🎟️ Ticket 5 assigned by pool-1-thread-5


❌ Ticket canceled → now: 4
➕ Bulk added → now: 7
🔁 Doubled tickets → now: 14
🔐 CAS update: true → current: 14
⚙️ Forced ticket to: 50
🔁 accumulateAndGet (+5) → new: 55
🔁 getAndAccumulate (*3) → old: 55 | now: 165
📊 Current ticket: 1165
🔄 System reset. Previous tickets: 165
🚪 System open: false
❌ System closed
📈 Total served: 5