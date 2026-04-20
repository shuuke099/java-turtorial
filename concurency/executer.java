public class Phase0_SingleAgent {

    public static void main(String[] args) {
        serveCustomer("Ali");
        serveCustomer("Fatima");
        serveCustomer("Hassan");
    }

    static void serveCustomer(String name) {
        System.out.println("➡️ Serving " + name);
        work();
        System.out.println("✅ Done with " + name);
    }

    static void work() {
        try {
            Thread.sleep(2000); // simulate work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Phase1_RawThreads {

    public static void main(String[] args) {

        new Thread(() -> serveCustomer("Ali")).start();
        new Thread(() -> serveCustomer("Fatima")).start();
        new Thread(() -> serveCustomer("Hassan")).start();

        System.out.println("Office is running...");
    }

    static void serveCustomer(String name) {
        System.out.println("➡️ Serving " + name + " on " + Thread.currentThread().getName());
        work();
        System.out.println("✅ Done with " + name);
    }

    static void work() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
    }
}


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Phase2_ExecutorService {

    public static void main(String[] args) {

        ExecutorService office = Executors.newFixedThreadPool(2);

        office.submit(() -> serveCustomer("Ali"));
        office.submit(() -> serveCustomer("Fatima"));
        office.submit(() -> serveCustomer("Hassan"));
        office.submit(() -> serveCustomer("Amina"));

        office.shutdown();
    }

    static void serveCustomer(String name) {
        System.out.println("➡️ Serving " + name + " by " + Thread.currentThread().getName());
        work();
        System.out.println("✅ Done with " + name);
    }

    static void work() {
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }
}