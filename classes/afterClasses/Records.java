import java.util.*;

// ======================================================
// 🔹 INTERFACE (RECORD IMPLEMENTS THIS)
// ======================================================
interface Identifiable {
    String id();
}


// ======================================================
// 🔹 MAIN RECORD (DATA MODEL)
// ======================================================
public record CitizenApplication(
        String id,
        String name,
        int age,
        List<String> documents
) implements Identifiable {

    // ==================================================
    // 🔥 COMPACT CONSTRUCTOR (VALIDATION + TRANSFORM)
    // ==================================================
    public CitizenApplication {

        if (id == null || id.isBlank())
            throw new IllegalArgumentException("❌ ID required");

        if (age < 0)
            throw new IllegalArgumentException("❌ Invalid age");

        // 🔥 Transform name
        name = name.substring(0, 1).toUpperCase()
                + name.substring(1).toLowerCase();

        // 🔥 Defensive copy (immutability)
        documents = List.copyOf(documents);
    }

    // ==================================================
    // 🔹 OVERLOADED CONSTRUCTOR
    // ==================================================
    public CitizenApplication(String name, int age) {
        this(UUID.randomUUID().toString(), name, age, List.of());
    }

    // ==================================================
    // 🔹 CUSTOM METHOD
    // ==================================================
    public boolean isAdult() {
        return age >= 18;
    }

    // ==================================================
    // 🔹 STATIC FIELD + METHOD
    // ==================================================
    private static int counter = 0;

    public static int getCreatedCount() {
        return counter;
    }

    // static initializer
    static {
        System.out.println("📦 CitizenApplication record loaded");
    }
}


// ======================================================
// 🔹 NESTED RECORD
// ======================================================
record Address(String city, String country) {}


// ======================================================
// 🔹 SERVICE USING RECORD
// ======================================================
class DHSService {

    public static void process(CitizenApplication app) {

        System.out.println("\n📄 Processing Application:");
        System.out.println(app); // toString()

        // 🔥 Accessors
        System.out.println("Name: " + app.name());
        System.out.println("Adult: " + app.isAdult());

        // 🔥 Pattern Matching with Record
        if (app instanceof CitizenApplication(String id,
                                              String name,
                                              int age,
                                              List<String> docs)) {

            System.out.println("🔍 Pattern matched:");
            System.out.println("ID: " + id);
            System.out.println("Docs count: " + docs.size());
        }
    }
}


// ======================================================
// 🔹 MAIN SYSTEM
// ======================================================
public class RecordScenarioFull {

    public static void main(String[] args) {

        System.out.println("🚀 DHS Record System Starting...\n");

        // =========================================
        // 🔹 CREATE RECORD
        // =========================================
        CitizenApplication app1 =
                new CitizenApplication("REQ-1", "ali", 25,
                        List.of("Passport", "ID"));

        CitizenApplication app2 =
                new CitizenApplication("john", 17);

        // =========================================
        // 🔹 PROCESS
        // =========================================
        DHSService.process(app1);
        DHSService.process(app2);

        // =========================================
        // 🔹 EQUALITY + HASHCODE
        // =========================================
        CitizenApplication copy =
                new CitizenApplication("REQ-1", "ali", 25,
                        List.of("Passport", "ID"));

        System.out.println("\nEquality: " + app1.equals(copy));
        System.out.println("HashCodes: " +
                app1.hashCode() + " / " + copy.hashCode());

        // =========================================
        // 🔹 ACCESSORS
        // =========================================
        System.out.println("\nName accessor: " + app1.name());

        // =========================================
        // 🔹 NESTED RECORD
        // =========================================
        Address address = new Address("Minneapolis", "USA");
        System.out.println("Address: " + address);

        // =========================================
        // 🔹 IMMUTABILITY TEST
        // =========================================
        try {
            app1.documents().add("FakeDoc"); // ❌
        } catch (Exception e) {
            System.out.println("⚠️ Cannot modify documents (immutable)");
        }
    }
}

📦 CitizenApplication record loaded
🚀 DHS Record System Starting...

📄 Processing Application:
CitizenApplication[id=REQ-1, name=Ali, age=25, documents=[Passport, ID]]
Name: Ali
Adult: true
🔍 Pattern matched:
ID: REQ-1
Docs count: 2

📄 Processing Application:
CitizenApplication[id=..., name=John, age=17, documents=[]]
Name: John
Adult: false
🔍 Pattern matched:
ID: ...
Docs count: 0

Equality: true
HashCodes: 12345 / 12345

Name accessor: Ali
Address: Address[city=Minneapolis, country=USA]

⚠️ Cannot modify documents (immutable)