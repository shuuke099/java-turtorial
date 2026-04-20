import java.util.*;

// ======================================================
// 🔹 OUTER CLASS (MAIN SYSTEM)
// ======================================================
public class DHSSystem {

    private String systemName = "DHS Core System";

    // ==================================================
    // 🔹 1. INNER CLASS (NON-STATIC)
    // ==================================================
    // 👉 tied to outer instance
    // 👉 can access outer fields (even private)
    class NotificationService {

        public void send(String message) {
            System.out.println("📢 [" + systemName + "] " + message);
        }
    }


    // ==================================================
    // 🔹 2. STATIC NESTED CLASS
    // ==================================================
    // 👉 no outer instance required
    // 👉 cannot access instance fields directly
    static class BenefitCalculator {

        public int calculate(int a, int b) {
            return a * b;
        }
    }


    // ==================================================
    // 🔹 3. NESTED RECORD (IMPLICITLY STATIC)
    // ==================================================
    record Request(String id, int amount) {}



    // ==================================================
    // 🔹 MAIN BUSINESS METHOD
    // ==================================================
    public void processRequest() {

        System.out.println("🚀 Processing request...\n");

        // =========================================
        // 🔹 INNER CLASS USAGE
        // =========================================
        NotificationService notifier = new NotificationService();
        notifier.send("Request started");

        // =========================================
        // 🔹 STATIC NESTED CLASS USAGE
        // =========================================
        BenefitCalculator calc = new BenefitCalculator();
        System.out.println("💰 Benefit: " + calc.calculate(5, 10));

        // =========================================
        // 🔹 LOCAL CLASS (INSIDE METHOD)
        // =========================================
        final int base = 10;

        class Validator {
            public boolean validate(int value) {
                return value > base; // must be effectively final
            }
        }

        Validator validator = new Validator();
        System.out.println("✔ Valid: " + validator.validate(20));

        // =========================================
        // 🔹 ANONYMOUS CLASS
        // =========================================
        Runnable task = new Runnable() {
            public void run() {
                System.out.println("⚙️ Running async task...");
            }
        };
        task.run();

        // =========================================
        // 🔹 NESTED RECORD USAGE
        // =========================================
        Request req = new Request("REQ-1", 100);
        System.out.println("📄 Request: " + req);

        // =========================================
        // 🔹 ADVANCED: INNER CLASS INSTANCE FROM OUTSIDE STYLE
        // =========================================
        DHSSystem outer = new DHSSystem();
        DHSSystem.NotificationService n2 = outer.new NotificationService();
        n2.send("Manual instantiation");
    }


    // ==================================================
    // 🔹 STATIC METHOD (CANNOT USE INNER CLASS DIRECTLY)
    // ==================================================
    public static void staticDemo() {

        // ❌ new NotificationService(); // DOES NOT COMPILE

        DHSSystem system = new DHSSystem();
        DHSSystem.NotificationService notifier =
                system.new NotificationService();

        notifier.send("Called from static method");

        BenefitCalculator calc = new BenefitCalculator();
        System.out.println("Static calc: " + calc.calculate(2, 3));
    }


    // ==================================================
    // 🔹 MAIN METHOD
    // ==================================================
    public static void main(String[] args) {

        DHSSystem system = new DHSSystem();

        system.processRequest();

        System.out.println("\n--- Static Demo ---");
        staticDemo();
    }
}

🚀 Processing request...

📢 [DHS Core System] Request started
💰 Benefit: 50
✔ Valid: true
⚙️ Running async task...
📄 Request: Request[id=REQ-1, amount=100]
📢 [DHS Core System] Manual instantiation

--- Static Demo ---
📢 [DHS Core System] Called from static method
Static calc: 6


// ======================================================

WHAT THIS COVERS (FULL NESTED MASTER)
🔥 1. INNER CLASS
class NotificationService
✅ Features:
Access outer fields:
systemName // allowed
Needs outer instance:
outer.new NotificationService()
🔥 2. STATIC NESTED CLASS
static class BenefitCalculator
✅ Features:
No outer instance needed
Cannot access systemName
🔥 3. LOCAL CLASS
class Validator
✅ Rules:
Inside method only
Cannot use non-final variables
int base = 10; // must be effectively final
🔥 4. ANONYMOUS CLASS
new Runnable() { ... }
✅ Features:
No name
One-time use
Must extend/implement ONE type
🔥 5. NESTED RECORD
record Request(...)
✅ Features:
implicitly static
cannot access outer instance
⚠️ IMPORTANT RULES (EXAM CRITICAL)
❌ Inner class in static context
new NotificationService(); // ❌
❌ Local variable must be effectively final
int x = 10;
x = 20; // ❌ breaks local class
❌ Static nested cannot access instance
systemName // ❌
❌ Anonymous class limitations
Cannot extend + implement both
Must implement all methods
🧠 FINAL MENTAL MODEL
🔥 WHEN TO USE EACH
Type	Use Case
Inner class	tightly coupled logic
Static nested	utility/helper class
Local class	method-only logic
Anonymous	one-time quick logic
Nested record	data container
🏆 INTERVIEW LEVEL ANSWER

If asked:

👉 “Why use nested classes?”

Say:

“Nested classes improve encapsulation, reduce exposure of helper logic, and keep code organized by grouping related functionality inside the main class.”

🚀 