PROFESSIONAL SCENARIO
🏥 DHS Benefit Calculation Engine

You are building a system that calculates monthly benefits based on:

🧍 Individual
👨‍👩‍👧 Family
🏢 Business

Each type has:

Different rules
Different formulas
Same interface

👉 This is a Strategy Pattern

🧠 DESIGN IDEA

👉 Instead of:

if(type == INDIVIDUAL) { ... }
else if(type == FAMILY) { ... }

👉 You use:

🔥 Enum = Strategy holder
🔥 Interface = contract








// ======================================================
// 🔹 STRATEGY INTERFACE
// ======================================================
interface BenefitStrategy {

    double calculateBenefit(double baseAmount);

    String getDescription();
}


// ======================================================
// 🔹 ENUM (STRATEGY IMPLEMENTATION)
// ======================================================
enum BenefitType implements BenefitStrategy {

    // ==================================================
    // 🧍 INDIVIDUAL STRATEGY
    // ==================================================
    INDIVIDUAL {
        @Override
        public double calculateBenefit(double baseAmount) {
            return baseAmount * 1.0;
        }

        @Override
        public String getDescription() {
            return "Individual standard benefit";
        }
    },

    // ==================================================
    // 👨‍👩‍👧 FAMILY STRATEGY
    // ==================================================
    FAMILY {
        @Override
        public double calculateBenefit(double baseAmount) {
            return baseAmount * 1.5 + 100;
        }

        @Override
        public String getDescription() {
            return "Family benefit with bonus";
        }
    },

    // ==================================================
    // 🏢 BUSINESS STRATEGY
    // ==================================================
    BUSINESS {
        @Override
        public double calculateBenefit(double baseAmount) {
            return baseAmount * 2.0;
        }

        @Override
        public String getDescription() {
            return "Business support benefit";
        }
    };

    // ==================================================
    // 🔹 COMMON METHOD (SHARED LOGIC)
    // ==================================================
    protected void validate(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("❌ Invalid amount");
        }
    }

    // ==================================================
    // 🔹 STATIC HELPER
    // ==================================================
    public static BenefitType getDefault() {
        return INDIVIDUAL;
    }
}


// ======================================================
// 🔹 SERVICE CLASS (USES STRATEGY)
// ======================================================
class BenefitService {

    private String citizenId;
    private BenefitType type;

    public BenefitService(String citizenId, BenefitType type) {
        this.citizenId = citizenId;
        this.type = type;
    }

    public void process(double baseAmount) {

        System.out.println("\n📄 Citizen: " + citizenId);
        System.out.println("Type: " + type);

        // 🔥 STRATEGY EXECUTION
        double result = type.calculateBenefit(baseAmount);

        System.out.println("💰 Benefit: $" + result);
        System.out.println("📘 " + type.getDescription());
    }
}


// ======================================================
// 🔹 MAIN SYSTEM
// ======================================================
public class StrategyEnumSystem {

    public static void main(String[] args) {

        System.out.println("🚀 DHS Benefit System Starting...\n");

        // =========================================
        // 🔹 CREATE SERVICES
        // =========================================
        BenefitService s1 =
                new BenefitService("CIT-1", BenefitType.INDIVIDUAL);

        BenefitService s2 =
                new BenefitService("CIT-2", BenefitType.FAMILY);

        BenefitService s3 =
                new BenefitService("CIT-3", BenefitType.BUSINESS);

        s1.process(1000);
        s2.process(1000);
        s3.process(1000);

        // =========================================
        // 🔹 POLYMORPHISM (INTERFACE)
        // =========================================
        BenefitStrategy strategy = BenefitType.FAMILY;

        System.out.println("\n🔁 Using interface reference:");
        System.out.println("Benefit: " +
                strategy.calculateBenefit(500));

        // =========================================
        // 🔹 LOOP ALL STRATEGIES
        // =========================================
        System.out.println("\nAll benefit types:");
        for (BenefitType t : BenefitType.values()) {
            System.out.println(t + " → " +
                    t.calculateBenefit(100));
        }

        // =========================================
        // 🔹 valueOf()
        // =========================================
        BenefitType parsed = BenefitType.valueOf("BUSINESS");
        System.out.println("\nParsed: " + parsed);

        // =========================================
        // 🔹 SWITCH (OPTIONAL)
        // =========================================
        switch (parsed) {
            case INDIVIDUAL -> System.out.println("Basic case");
            case FAMILY -> System.out.println("Family case");
            case BUSINESS -> System.out.println("Business case");
        }
    }
}


// ======================================================
🚀 DHS Benefit System Starting...

📄 Citizen: CIT-1
Type: INDIVIDUAL
💰 Benefit: $1000.0
📘 Individual standard benefit

📄 Citizen: CIT-2
Type: FAMILY
💰 Benefit: $1600.0
📘 Family benefit with bonus

📄 Citizen: CIT-3
Type: BUSINESS
💰 Benefit: $2000.0
📘 Business support benefit

🔁 Using interface reference:
Benefit: 850.0

All benefit types:
INDIVIDUAL → 100.0
FAMILY → 250.0
BUSINESS → 200.0

Parsed: BUSINESS
Business case



“I use an interface for the contract and an enum for concrete strategies. It keeps the code clean, avoids class explosion, and supports polymorphism.