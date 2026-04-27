public class ICUWithoutVolatile {

    static class ICUController {

        private boolean shutdown = false; // ❌ NOT volatile

        void stopSystem() {
            System.out.println("Admin: Stopping system...");
            shutdown = true;
        }

        void monitorPatient() {

            System.out.println("Monitor: Started monitoring...");

            while (!shutdown) {
                // Simulate monitoring work
            }

            System.out.println("Monitor: System stopped");
        }
    }

    public static void main(String[] args) throws Exception {

        ICUController icu = new ICUController();

        // 🧵 Thread A — Monitoring system
        Thread monitorThread = new Thread(() -> icu.monitorPatient());

        // 🧵 Thread B — Admin stops system
        Thread stopThread = new Thread(() -> {
            try {
                Thread.sleep(2000); // wait 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            icu.stopSystem();
        });

        monitorThread.start();
        stopThread.start();
    }
}






public class ICUWithVolatile {

    static class ICUController {

        private volatile boolean shutdown = false; // ✅ FIX

        void stopSystem() {
            System.out.println("Admin: Stopping system...");
            shutdown = true;
        }

        void monitorPatient() {

            System.out.println("Monitor: Started monitoring...");

            while (!shutdown) {
                // Simulate monitoring work
            }

            System.out.println("Monitor: System stopped");
        }
    }

    public static void main(String[] args) throws Exception {

        ICUController icu = new ICUController();

        // 🧵 Thread A — Monitoring
        Thread monitorThread = new Thread(() -> icu.monitorPatient());

        // 🧵 Thread B — Stop signal
        Thread stopThread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            icu.stopSystem();
        });

        monitorThread.start();
        stopThread.start();
    }
}