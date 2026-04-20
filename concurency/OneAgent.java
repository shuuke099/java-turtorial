public class Step1_OneAgent {

    public static void main(String[] args) {
        serveCustomer("Customer A");
        serveCustomer("Customer B");
    }

    static void serveCustomer(String customer) {
        System.out.println("Serving " + customer);
        sleep(2000);
        System.out.println("Finished " + customer);
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {}
    }
}


public class Step2_Chaos {

    public static void main(String[] args) {
        new Thread(() -> serve("Customer A")).start();
        new Thread(() -> serve("Customer B")).start();
        new Thread(() -> serve("Customer C")).start();
    }

    static void serve(String customer) {
        System.out.println("Serving " + customer);
        sleep(2000);
        System.out.println("Finished " + customer);
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {}
    }
}






import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DHS_File1_OneAgent {

    public static void main(String[] args) {

        ExecutorService office = Executors.newSingleThreadExecutor();

        for (int i = 1; i <= 5; i++) {
            int customer = i;
            office.submit(() -> {
                System.out.println("Agent serving customer " + customer);
                sleep(2000);
                System.out.println("Customer " + customer + " done");
            });
        }

        office.shutdown();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DHS_File2_MultipleAgents {

    public static void main(String[] args) {

        ExecutorService office = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 6; i++) {
            int customer = i;
            office.submit(() -> {
                System.out.println(
                    Thread.currentThread().getName()
                    + " helping customer " + customer
                );
                sleep(2000);
                System.out.println("Customer " + customer + " finished");
            });
        }

        office.shutdown();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DHS_File3_InstantFix {

    public static void main(String[] args) {

        ExecutorService office = Executors.newSingleThreadExecutor();

        office.submit(() -> {
            System.out.println("Password reset completed instantly");
        });

        office.shutdown();
    }
}


import java.util.concurrent.*;

public class DHS_File4_WaitForResult {

    public static void main(String[] args) throws Exception {

        ExecutorService office = Executors.newSingleThreadExecutor();

        Future<String> receipt = office.submit(() -> {
            System.out.println("Background check running...");
            Thread.sleep(3000);
            return "APPROVED";
        });

        System.out.println("Customer waiting...");
        System.out.println("Result: " + receipt.get());

        office.shutdown();
    }
}


import java.util.concurrent.*;

public class DHS_File5_CheckStatus {

    public static void main(String[] args) throws Exception {

        ExecutorService office = Executors.newSingleThreadExecutor();

        Future<String> future = office.submit(() -> {
            Thread.sleep(4000);
            return "DOCUMENT VERIFIED";
        });

        while (!future.isDone()) {
            System.out.println("Still waiting...");
            Thread.sleep(1000);
        }

        System.out.println("Done: " + future.get());
        office.shutdown();
    }
}


import java.util.concurrent.*;

public class DHS_File6_LicenseWorkflow {

    public static void main(String[] args) throws Exception {

        ExecutorService office = Executors.newFixedThreadPool(2);

        // Step 1: Intake
        office.submit(() ->
            System.out.println("Payment & documents accepted")
        );

        // Step 2: Approval
        Future<Boolean> approval = office.submit(() -> {
            Thread.sleep(3000);
            System.out.println("Senior agent approved license");
            return true;
        });

        // Step 3: Photo station
        if (approval.get()) {
            office.submit(() ->
                System.out.println("Photo & biometrics collected")
            );
        }

        office.shutdown();
    }
}


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DHS_File7_CachedPool {

    public static void main(String[] args) {

        ExecutorService office = Executors.newCachedThreadPool();

        for (int i = 1; i <= 10; i++) {
            int customer = i;
            office.submit(() -> {
                System.out.println(
                    Thread.currentThread().getName()
                    + " helping customer " + customer
                );
                sleep(1000);
            });
        }

        office.shutdown();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}


import java.util.concurrent.*;

public class DHS_File8_SingleScheduled {

    public static void main(String[] args) {

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(() -> {
            System.out.println(
                "Reminder sent: Your DHS appointment is tomorrow."
            );
        }, 5, TimeUnit.SECONDS);

        sleep(7000);
        scheduler.shutdown();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}


import java.util.concurrent.*;

public class DHS_File9_ScheduledPool {

    public static void main(String[] args) {

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(3);

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Sending appointment reminders...");
            sleep(2000);
        }, 0, 10, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Cleaning expired sessions...");
            sleep(3000);
        }, 0, 15, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Syncing federal systems...");
            sleep(4000);
        }, 0, 20, TimeUnit.SECONDS);

        sleep(35000);
        scheduler.shutdown();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DHS_File10_VirtualThreads {

    public static void main(String[] args) {

        ExecutorService office =
                Executors.newVirtualThreadPerTaskExecutor();

        for (int i = 1; i <= 8; i++) {
            int customer = i;
            office.submit(() -> {
                System.out.println(
                    "Virtual agent handling customer " + customer
                );
                sleep(1000);
            });
        }

        office.shutdown();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}


