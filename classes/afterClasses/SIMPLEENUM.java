// ======================================================
// 🔹 SIMPLE ENUM
// ======================================================
java.lang.Enum
enum RequestStatus {

    CREATED,
    IN_PROGRESS,
    APPROVED,
    REJECTED
}


// ======================================================
// 🔹 SERVICE CLASS USING ENUM
// ======================================================
class DHSRequest {

    private String requestId;
    private RequestStatus status;

    public DHSRequest(String requestId, RequestStatus status) {
        this.requestId = requestId;
        this.status = status;
    }

    public void printStatus() {
        // 🔥 toString() (implicitly called)
        System.out.println("Request " + requestId + " status: " + status.toString());
    }

    public void processStatus() {

        // 🔥 SWITCH WITH ENUM
        switch (status) {
            case CREATED:
                System.out.println("📄 Request created");
                break;

            case IN_PROGRESS:
                System.out.println("⏳ Processing request...");
                break;

            case APPROVED:
                System.out.println("✅ Request approved");
                break;

            case REJECTED:
                System.out.println("❌ Request rejected");
                break;
        }
    }

    public RequestStatus getStatus() {
        return status;
    }
}


// ======================================================
// 🔹 MAIN SYSTEM
// ======================================================
public class SimpleEnumScenario {

    public static void main(String[] args) {

        // =========================================
        // 🔹 CREATE ENUM VALUE
        // =========================================
        DHSRequest request =
                new DHSRequest("REQ-101", RequestStatus.CREATED);

        request.printStatus();
        request.processStatus();



        // =========================================
        // 🔹 MY PRACTICE
        // =========================================
        String input = "CREATED";
        RequestStatus status = RequestStatus.valueOf(input);

        DHSRequest request = new DHSRequest("REQ-101", status);

        // =========================================
        // 🔥 1. == (BEST PRACTICE)
        // =========================================
        if (request.getStatus() == RequestStatus.CREATED) {
            System.out.println("👉 Still new request (== comparison)");
        }

        // =========================================
        // 🔥 2. equals()
        // =========================================
        boolean isSame = request.getStatus().equals(RequestStatus.CREATED);
        System.out.println("Equals check: " + isSame);

        // =========================================
        // 🔥 3. values(). #Returns all enum constants as an array --Status.values();
        // =========================================
        System.out.println("\nAll statuses (values):");
        for (RequestStatus s : RequestStatus.values()) {

            // 🔥 name() + ordinal()
            System.out.println(
                "Name: " + s.name() +
                " | Ordinal: " + s.ordinal()
            );
        }
        RequestStatus.APPROVED.name(); // "APPROVED"
        RequestStatus.APPROVED.ordinal(); // 2

        // =========================================
        // 🔥 4. valueOf(String name) #Converts String → Enum
        // =========================================
        RequestStatus parsed = RequestStatus.valueOf("APPROVED");
        System.out.println("\nParsed status (valueOf): " + parsed);
        // Must match exactly or it throws exception

        // =========================================
        // 🔥 5. toString()
        // =========================================
        System.out.println("toString(): " + parsed.toString());

          // =========================================
        // 🔹 MY PRACTICE
        // =========================================

        System.out.println(Status.APPROVED.toString()); // "APPROVED"
        System.out.println(Status.APPROVED.name()); // "APPROVED"

        // =========================================
        // 🔥 6. compareTo()
        // =========================================
        int comparison = RequestStatus.CREATED.compareTo(RequestStatus.APPROVED);
        System.out.println("compareTo (CREATED vs APPROVED): " + comparison);
RequestStatus.APPROVED.compareTo(RequestStatus.CREATED); // 1
RequestStatus.CREATED.compareTo(RequestStatus.CREATED); // 0
        // =========================================
        // 🔥 7. hashCode()
        // =========================================
        System.out.println("HashCode of CREATED: " +
                RequestStatus.CREATED.hashCode());

        // =========================================
        // 🔥 8. getDeclaringClass()
        // =========================================
        System.out.println("Declaring class: " +
                RequestStatus.CREATED.getDeclaringClass());

        // =========================================
        // 🔥 9. valueOf() INVALID (EXCEPTION)
        // =========================================
        try {
            RequestStatus.valueOf("approved"); // ❌ case-sensitive
        } catch (Exception e) {
            System.out.println("⚠️ Invalid enum value!");
        }
    }
}

Request REQ-101 status: CREATED
📄 Request created
👉 Still new request (== comparison)
Equals check: true

All statuses (values):
Name: CREATED | Ordinal: 0
Name: IN_PROGRESS | Ordinal: 1
Name: APPROVED | Ordinal: 2
Name: REJECTED | Ordinal: 3

Parsed status (valueOf): APPROVED
toString(): APPROVED
compareTo (CREATED vs APPROVED): -2
HashCode of CREATED: 12345678   // (example)
Declaring class: class RequestStatus

⚠️ Invalid enum value!


// COMPLETE                   BUILT-IN METHODS COVERED
// Method	                  What it does
// values()	                  All enum constants
// valueOf(String)	          String → Enum
// name()	                  Exact constant name
// ordinal()	              Position index
// toString()	              String representation
// compareTo()	              Compare order
// equals()	                  Equality check
// hashCode()	              Unique hash
// getDeclaringClass()	      Returns enum class