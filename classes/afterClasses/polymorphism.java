Perfect — now we’ll build a 🔥 full professional polymorphism scenario that covers EVERY concept from your notes in ONE system.

I’ll include:

✅ interface + class polymorphism
✅ object vs reference
✅ method overriding (runtime polymorphism)
✅ static method hiding
✅ variable hiding
✅ casting (implicit + explicit)
✅ invalid cast (ClassCastException)
✅ instanceof (safe casting)
✅ real-world design

🏢 PROFESSIONAL SCENARIO
🏥 DHS Multi-Service Processing System

You are building a DHS system with:

🧍 Citizens
🏠 Housing services
💼 Job services

👉 All services share behavior but differ internally.




// ======================================================
// 🔹 INTERFACE
// ======================================================
interface Service {
    void process();
}


// ======================================================
// 🔹 BASE CLASS
// ======================================================
class DHSService {

    protected int baseAmount = 100;

    public String getServiceName() {
        return "Generic DHS Service";
    }

    public static String systemType() {
        return "BASE SYSTEM";
    }

    public void printInfo() {
        // 🔥 runtime polymorphism
        System.out.println("Service: " + getServiceName());
    }
}


// ======================================================
// 🔹 SUBCLASS 1
// ======================================================
class HousingService extends DHSService implements Service {

    protected int baseAmount = 500; // 🔥 variable hiding

    @Override
    public String getServiceName() {
        return "Housing Service";
    }

    @Override
    public void process() {
        System.out.println("🏠 Processing housing...");
    }

    // 🔥 static method hiding
    public static String systemType() {
        return "HOUSING SYSTEM";
    }
}


// ======================================================
// 🔹 SUBCLASS 2
// ======================================================
class JobService extends DHSService implements Service {

    @Override
    public String getServiceName() {
        return "Job Service";
    }

    @Override
    public void process() {
        System.out.println("💼 Processing job...");
    }
}


// ======================================================
// 🔹 MAIN SYSTEM
// ======================================================
public class PolymorphismScenario {

    public static void main(String[] args) {

        System.out.println("🚀 DHS Polymorphism System\n");

        // =========================================
        // 🔹 OBJECT vs REFERENCE
        // =========================================
        HousingService housing = new HousingService();

        DHSService serviceRef = housing; // 🔥 polymorphism
        Service interfaceRef = housing;

        // SAME OBJECT → DIFFERENT REFERENCES
        serviceRef.printInfo();          // uses overridden method
        interfaceRef.process();          // interface method

        // =========================================
        // 🔹 ACCESS LIMITATION (REFERENCE TYPE)
        // =========================================
        // System.out.println(interfaceRef.baseAmount); ❌
        // System.out.println(serviceRef.process()); ❌

        // =========================================
        // 🔹 VARIABLE HIDING
        // =========================================
        System.out.println("\nVariable Hiding:");
        System.out.println("Child reference: " + housing.baseAmount);
        System.out.println("Parent reference: " + serviceRef.baseAmount);

        // =========================================
        // 🔹 STATIC METHOD HIDING
        // =========================================
        System.out.println("\nStatic Method Hiding:");
        System.out.println(HousingService.systemType());
        System.out.println(DHSService.systemType());

        // =========================================
        // 🔹 CASTING (IMPLICIT + EXPLICIT)
        // =========================================
        DHSService upcast = new HousingService(); // implicit

        HousingService downcast = (HousingService) upcast; // explicit
        downcast.process();

        // =========================================
        // 🔹 INVALID CAST (RUNTIME ERROR)
        // =========================================
        DHSService wrong = new JobService();

        try {
            HousingService badCast = (HousingService) wrong; // ❌
            badCast.process();
        } catch (ClassCastException e) {
            System.out.println("⚠️ Invalid cast detected!");
        }

        // =========================================
        // 🔹 INSTANCEOF (SAFE CAST)
        // =========================================
        DHSService maybeHousing = new HousingService();

        if (maybeHousing instanceof HousingService hs) {
            hs.process(); // safe
        }

        // =========================================
        // 🔹 POLYMORPHIC COLLECTION (REAL WORLD)
        // =========================================
        System.out.println("\nProcessing all services:");

        List<Service> services = List.of(
                new HousingService(),
                new JobService()
        );

        for (Service s : services) {
            s.process(); // polymorphism
        }
    }
}

// ======================================================
🚀 DHS Polymorphism System

Service: Housing Service
🏠 Processing housing...

Variable Hiding:
Child reference: 500
Parent reference: 100

Static Method Hiding:
HOUSING SYSTEM
BASE SYSTEM

🏠 Processing housing...
⚠️ Invalid cast detected!
🏠 Processing housing...

Processing all services:
🏠 Processing housing...
💼 Processing job...



Polymorphism allows one object to be referenced in multiple forms. The object determines behavior at runtime, while the reference controls access at compile time.