
SCENARIO 2 — COMPLEX ENUM
🏥 DHS Service Priority Engine (Smart Behavior System)

You are building a DHS system that prioritizes requests.

Each request has a priority level, and each priority:

Has a processing time
Has a cost
Has different behavior
Must be type-safe
Must support polymorphism

👉 This is where enums become powerful (like mini-classes)



// ======================================================
// 🔹 INTERFACE (ENUM IMPLEMENTS THIS)
// ======================================================
interface PriorityAction {
    void executeAction();
}


// ======================================================
// 🔹 COMPLEX ENUM
// ======================================================
enum PriorityLevel implements PriorityAction {

    // ==================================================
    // 🔥 ENUM VALUES WITH CUSTOM BEHAVIOR
    // ==================================================
    LOW(5, 10) {
        public void executeAction() {
            System.out.println("🟢 LOW: Queue for later processing");
        }
    },

    MEDIUM(3, 20) {
        public void executeAction() {
            System.out.println("🟡 MEDIUM: Process within standard time");
        }
    },

    HIGH(1, 50) {
        public void executeAction() {
            System.out.println("🔴 HIGH: Immediate attention required!");
        }
    };

    // ==================================================
    // 🔹 FIELDS (IMMUTABLE)
    // ==================================================
    private final int processingDays;
    private final double cost;

    // ==================================================
    // 🔹 STATIC CONSTANT
    // ==================================================
    public static final String SYSTEM = "DHS Priority Engine";

    // ==================================================
    // 🔹 CONSTRUCTOR (IMPLICITLY PRIVATE)
    // ==================================================
    PriorityLevel(int processingDays, double cost) {
        System.out.println("⚙️ Initializing: " + this.name());
        this.processingDays = processingDays;
        this.cost = cost;
    }

    // ==================================================
    // 🔹 INSTANCE METHODS
    // ==================================================
    public int getProcessingDays() {
        return processingDays;
    }

    public double getCost() {
        return cost;
    }

    // ==================================================
    // 🔹 STATIC METHOD
    // ==================================================
    public static PriorityLevel getDefault() {
        return MEDIUM;
    }
}


// ======================================================
// 🔹 SERVICE CLASS USING ENUM
// ======================================================
class DHSRequest {

    private String id;
    private PriorityLevel priority;

    public DHSRequest(String id, PriorityLevel priority) {
        this.id = id;
        this.priority = priority;
    }

    public void process() {

        System.out.println("\n📄 Processing request: " + id);
        System.out.println("Priority: " + priority);

        // 🔥 ENUM METHOD
        priority.executeAction();

        // 🔥 ACCESS ENUM FIELDS
        System.out.println("⏱ Processing days: " + priority.getProcessingDays());
        System.out.println("💰 Cost: $" + priority.getCost());
    }
}


// ======================================================
// 🔹 MAIN SYSTEM
// ======================================================
public class ComplexEnumScenario {

    public static void main(String[] args) {

        System.out.println("🚀 System starting...\n");

        // =========================================
        // 🔹 ENUM INITIALIZATION (HAPPENS ONCE)
        // =========================================
        DHSRequest r1 = new DHSRequest("REQ-1", PriorityLevel.LOW);
        DHSRequest r2 = new DHSRequest("REQ-2", PriorityLevel.HIGH);

        r1.process();
        r2.process();

        // =========================================
        // 🔹 ENUM METHODS
        // =========================================
        System.out.println("\nDefault priority: " +
                PriorityLevel.getDefault());

        // =========================================
        // 🔹 LOOP ENUM VALUES
        // =========================================
        System.out.println("\nAll priorities:");
        for (PriorityLevel p : PriorityLevel.values()) {
            System.out.println(p.name() + " → " +
                    p.getProcessingDays() + " days");
        }

        // =========================================
        // 🔹 valueOf()
        // =========================================
        PriorityLevel parsed = PriorityLevel.valueOf("HIGH");
        System.out.println("\nParsed: " + parsed);

        // =========================================
        // 🔹 SWITCH WITH ENUM
        // =========================================
        switch (parsed) {
            case LOW -> System.out.println("Low urgency");
            case MEDIUM -> System.out.println("Medium urgency");
            case HIGH -> System.out.println("High urgency");
        }
    }
}

// ======================================================

🚀 System starting...

⚙️ Initializing: LOW
⚙️ Initializing: MEDIUM
⚙️ Initializing: HIGH

📄 Processing request: REQ-1
Priority: LOW
🟢 LOW: Queue for later processing
⏱ Processing days: 5
💰 Cost: $10.0

📄 Processing request: REQ-2
Priority: HIGH
🔴 HIGH: Immediate attention required!
⏱ Processing days: 1
💰 Cost: $50.0

Default priority: MEDIUM

All priorities:
LOW → 5 days
MEDIUM → 3 days
HIGH → 1 days

Parsed: HIGH
High urgency