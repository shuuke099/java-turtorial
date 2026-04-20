public class Phase1_Base {

    public static void serveCustomer(String name) {
        System.out.println("➡️ Serving " + name + " on " + Thread.currentThread().getName());
        work();
        System.out.println("✅ Done with " + name);
    }

    public static void work() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("⚠️ Interrupted");
        }
    }
}

// STAGE 1 — EXTENDING THREAD (VERY OLD STYLE)

// 👉 java.lang.Thread 

class CustomerThread extends Thread {

    private String name;

    public CustomerThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Phase1_Base.serveCustomer(name);
    }
}

public class Stage1_ExtendThread {

    public static void main(String[] args) {

        new CustomerThread("Ali").start();
        new CustomerThread("Fatima").start();
        new CustomerThread("Hassan").start();

        System.out.println("Office running (Thread subclass)...");
    }
}


// Still First stage

class CustomerThread extends Thread {

    private String name;

    public CustomerThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Phase1_Base.serveCustomer(name);
    }
}

public class Stage1_ExtendThread {

    public static void main(String[] args) {

        Thread t1 = new CustomerThread("Ali");
        Thread t2 = new CustomerThread("Fatima");
        Thread t3 = new CustomerThread("Hassan");

        t1.start();
        t2.start();
        t3.start();

        System.out.println("Office running (Thread subclass)...");
    }
}


// STAGE 2 — RUNNABLE (SEPARATE CLASS) ✅ BEST FOUNDATION

// 👉 java.lang.Runnable

class CustomerTask implements Runnable {

    private String name;

    public CustomerTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Phase1_Base.serveCustomer(name);
    }
}

public class Stage2_Runnable {

    public static void main(String[] args) {

        Thread t1 = new Thread(new CustomerTask("Ali"));
        Thread t2 = new Thread(new CustomerTask("Fatima"));
        Thread t3 = new Thread(new CustomerTask("Hassan"));

        t1.start();
        t2.start();
        t3.start();

        System.out.println("Office running (Runnable)...");
    }
}

🔵 STAGE 3 — ANONYMOUS CLASS (INLINE OLD STYLE)


public class Stage3_Anonymous {

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Phase1_Base.serveCustomer("Ali");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Phase1_Base.serveCustomer("Fatima");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Phase1_Base.serveCustomer("Hassan");
            }
        }).start();

        System.out.println("Office running (Anonymous)...");
    }
}

🟣 STAGE 4 — LAMBDA (MODERN JAVA) ✅🔥
public class Stage4_Lambda {

    public static void main(String[] args) {

        new Thread(() -> Phase1_Base.serveCustomer("Ali")).start();
        new Thread(() -> Phase1_Base.serveCustomer("Fatima")).start();
        new Thread(() -> Phase1_Base.serveCustomer("Hassan")).start();

        System.out.println("Office running (Lambda)...");
    }
}

public class Stage5_MethodReference {

    public static void main(String[] args) {

        Runnable r1 = () -> Phase1_Base.serveCustomer("Ali");
        Runnable r2 = () -> Phase1_Base.serveCustomer("Fatima");

        new Thread(r1).start();
        new Thread(r2).start();

        System.out.println("Office running (Method ref style)...");
    }
}

