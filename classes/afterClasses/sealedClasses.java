PROFESSIONAL SCENARIO
🏥 DHS Benefit Eligibility Engine (STRICT DOMAIN MODEL)

You are building a DHS eligibility system.

There are only specific allowed request types:

🧍 IndividualRequest
👨‍👩‍👧 FamilyRequest
🏢 BusinessRequest

👉 You MUST:

Restrict subclasses (no unknown types)
Guarantee compile-time safety
Use pattern matching safely

👉 This is EXACTLY why sealed classes exist

// ======================================================
// ======================================================
// 🔹 SEALED INTERFACE (ROOT CONTRACT)
// ======================================================
sealed interface Request permits IndividualRequest, FamilyRequest, BusinessRequest {

    String getId();
}


// ======================================================
// 🔹 SEALED ABSTRACT CLASS
// ======================================================
abstract sealed class BaseRequest implements Request
        permits IndividualRequest, FamilyRequest, BusinessRequest {

    protected String id;

    public BaseRequest(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public abstract double calculateBenefit();
}


// ======================================================
// 🔹 FINAL SUBCLASS (CLOSED)
// ======================================================
final class IndividualRequest extends BaseRequest {

    public IndividualRequest(String id) {
        super(id);
    }

    @Override
    public double calculateBenefit() {
        return 1000;
    }
}


// ======================================================
// 🔹 SEALED SUBCLASS (RESTRICTED FURTHER)
// ======================================================
sealed class FamilyRequest extends BaseRequest
        permits SmallFamily, LargeFamily {

    public FamilyRequest(String id) {
        super(id);
    }

    @Override
    public double calculateBenefit() {
        return 1500;
    }
}

// 🔸 Allowed subclass
final class SmallFamily extends FamilyRequest {
    public SmallFamily(String id) {
        super(id);
    }
}

// 🔸 Another allowed subclass
final class LargeFamily extends FamilyRequest {
    public LargeFamily(String id) {
        super(id);
    }

    @Override
    public double calculateBenefit() {
        return 2000;
    }
}


// ======================================================
// 🔹 NON-SEALED SUBCLASS (OPEN)
// ======================================================
non-sealed class BusinessRequest extends BaseRequest {

    public BusinessRequest(String id) {
        super(id);
    }

    @Override
    public double calculateBenefit() {
        return 3000;
    }
}

// 🔸 Allowed because BusinessRequest is non-sealed
class StartupRequest extends BusinessRequest {
    public StartupRequest(String id) {
        super(id);
    }
}


// ======================================================
// 🔹 SERVICE USING SEALED CLASS
// ======================================================
class DHSProcessor {

    public static void process(Request request) {

        System.out.println("\n📄 Processing: " + request.getId());

        // ==================================================
        // 🔥 PATTERN MATCHING (SEALED CLASS POWER)
        // ==================================================
        String result = switch (request) {

            case IndividualRequest i ->
                    "🧍 Individual → $" + i.calculateBenefit();

            case SmallFamily sf ->
                    "👨‍👩‍👧 Small Family → $" + sf.calculateBenefit();

            case LargeFamily lf ->
                    "👨‍👩‍👧‍👦 Large Family → $" + lf.calculateBenefit();

            case BusinessRequest b ->
                    "🏢 Business → $" + b.calculateBenefit();
        };

        System.out.println(result);
    }
}


// ======================================================
// 🔹 MAIN SYSTEM
// ======================================================
public class SealedClassScenario {

    public static void main(String[] args) {

        System.out.println("🚀 DHS Sealed System Starting...\n");

        Request r1 = new IndividualRequest("REQ-1");
        Request r2 = new SmallFamily("REQ-2");
        Request r3 = new LargeFamily("REQ-3");
        Request r4 = new StartupRequest("REQ-4");

        DHSProcessor.process(r1);
        DHSProcessor.process(r2);
        DHSProcessor.process(r3);
        DHSProcessor.process(r4);
    }
}

🚀 DHS Sealed System Starting...

📄 Processing: REQ-1
🧍 Individual → $1000.0

📄 Processing: REQ-2
👨‍👩‍👧 Small Family → $1500.0

📄 Processing: REQ-3
👨‍👩‍👧‍👦 Large Family → $2000.0

📄 Processing: REQ-4
🏢 Business → $3000.0