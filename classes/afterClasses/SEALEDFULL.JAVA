// ======================================================
// 🔹 1. STRATEGY INTERFACE
// ======================================================
interface BenefitStrategy {
    double calculate(double baseAmount);
    String getName();
}


// ======================================================
// 🔹 2. ENUM (STRATEGY IMPLEMENTATION)
// ======================================================
enum BenefitPlan implements BenefitStrategy {

    BASIC {
        public double calculate(double baseAmount) {
            return baseAmount;
        }

        public String getName() {
            return "Basic Plan";
        }
    },

    PREMIUM {
        public double calculate(double baseAmount) {
            return baseAmount * 1.5;
        }

        public String getName() {
            return "Premium Plan";
        }
    },

    EMERGENCY {
        public double calculate(double baseAmount) {
            return baseAmount * 2 + 500;
        }

        public String getName() {
            return "Emergency Plan";
        }
    };

    // 🔥 Shared validation logic
    protected void validate(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("❌ Invalid amount");
        }
    }

    public static BenefitPlan getDefault() {
        return BASIC;
    }
}


// ======================================================
// 🔹 3. SEALED INTERFACE (DOMAIN CONTRACT)
// ======================================================
sealed interface Request permits IndividualRequest, FamilyRequest, BusinessRequest {
    String getId();
    double baseAmount();
}


// ======================================================
// 🔹 4. SEALED ABSTRACT CLASS
// ======================================================
abstract sealed class BaseRequest implements Request
        permits IndividualRequest, FamilyRequest, BusinessRequest {

    protected String id;
    protected double baseAmount;

    public BaseRequest(String id, double baseAmount) {
        this.id = id;
        this.baseAmount = baseAmount;
    }

    public String getId() {
        return id;
    }

    public double baseAmount() {
        return baseAmount;
    }
}


// ======================================================
// 🔹 5. FINAL CLASS (CLOSED)
// ======================================================
final class IndividualRequest extends BaseRequest {

    public IndividualRequest(String id, double baseAmount) {
        super(id, baseAmount);
    }
}


// ======================================================
// 🔹 6. SEALED CLASS (CONTROLLED)
// ======================================================
sealed class FamilyRequest extends BaseRequest
        permits SmallFamily, LargeFamily {

    public FamilyRequest(String id, double baseAmount) {
        super(id, baseAmount);
    }
}

final class SmallFamily extends FamilyRequest {
    public SmallFamily(String id, double baseAmount) {
        super(id, baseAmount);
    }
}

final class LargeFamily extends FamilyRequest {
    public LargeFamily(String id, double baseAmount) {
        super(id, baseAmount);
    }
}


// ======================================================
// 🔹 7. NON-SEALED CLASS (OPEN)
// ======================================================
non-sealed class BusinessRequest extends BaseRequest {

    public BusinessRequest(String id, double baseAmount) {
        super(id, baseAmount);
    }
}

// allowed because non-sealed
class StartupRequest extends BusinessRequest {
    public StartupRequest(String id, double baseAmount) {
        super(id, baseAmount);
    }
}


// ======================================================
// 🔹 8. SERVICE (USES EVERYTHING)
// ======================================================
class DHSProcessor {

    public static void process(Request request, BenefitStrategy strategy) {

        System.out.println("\n📄 Request: " + request.getId());

        // 🔥 Pattern matching with sealed classes
        String type = switch (request) {

            case IndividualRequest i -> "🧍 Individual";

            case SmallFamily sf -> "👨‍👩‍👧 Small Family";

            case LargeFamily lf -> "👨‍👩‍👧‍👦 Large Family";

            case BusinessRequest b -> "🏢 Business";
        };

        System.out.println("Type: " + type);
        System.out.println("Strategy: " + strategy.getName());

        // 🔥 Strategy execution (ENUM)
        double result = strategy.calculate(request.baseAmount());

        System.out.println("💰 Final Benefit: $" + result);
    }
}


// ======================================================
// 🔹 9. MAIN SYSTEM
// ======================================================
public class FullArchitectureSystem {

    public static void main(String[] args) {

        System.out.println("🚀 DHS Unified System Starting...\n");

        Request r1 = new IndividualRequest("REQ-1", 1000);
        Request r2 = new SmallFamily("REQ-2", 1000);
        Request r3 = new LargeFamily("REQ-3", 1000);
        Request r4 = new StartupRequest("REQ-4", 1000);

        DHSProcessor.process(r1, BenefitPlan.BASIC);
        DHSProcessor.process(r2, BenefitPlan.PREMIUM);
        DHSProcessor.process(r3, BenefitPlan.EMERGENCY);
        DHSProcessor.process(r4, BenefitPlan.PREMIUM);

        // 🔁 Polymorphism
        BenefitStrategy strategy = BenefitPlan.EMERGENCY;
        System.out.println("\n🔁 Polymorphism Test: " +
                strategy.calculate(500));
    }
}

// ======================================================

🚀 DHS Unified System Starting...

📄 Request: REQ-1
Type: 🧍 Individual
Strategy: Basic Plan
💰 Final Benefit: $1000.0

📄 Request: REQ-2
Type: 👨‍👩‍👧 Small Family
Strategy: Premium Plan
💰 Final Benefit: $1500.0

📄 Request: REQ-3
Type: 👨‍👩‍👧‍👦 Large Family
Strategy: Emergency Plan
💰 Final Benefit: $2500.0

📄 Request: REQ-4
Type: 🏢 Business
Strategy: Premium Plan
💰 Final Benefit: $1500.0

🔁 Polymorphism Test: 1500.0