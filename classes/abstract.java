import java.util.*;

// ==============================
// 🧩 ABSTRACT BASE CLASS
// ==============================
abstract class ServiceRequest {

    protected String requestId;
    protected String applicantName;

    public ServiceRequest(String requestId, String applicantName) {
        this.requestId = requestId;
        this.applicantName = applicantName;
    }

    // 🔥 Abstract method (MUST be implemented)
    public abstract void processRequest();

    // ✅ Concrete method (shared logic)
    public void logRequest() {
        System.out.println("📋 Processing request ID: " + requestId +
                " for " + applicantName);
    }
}


// ==============================
// 🏠 HOUSING SERVICE
// ==============================
class HousingRequest extends ServiceRequest {

    public HousingRequest(String requestId, String applicantName) {
        super(requestId, applicantName);
    }

    @Override
    public void processRequest() {
        logRequest();
        System.out.println("🏠 Checking housing eligibility...");
        System.out.println("🏠 Housing Approved ✅\n");
    }
}


// ==============================
// 💼 JOB TRAINING SERVICE
// ==============================
class JobTrainingRequest extends ServiceRequest {

    public JobTrainingRequest(String requestId, String applicantName) {
        super(requestId, applicantName);
    }

    @Override
    public void processRequest() {
        logRequest();
        System.out.println("💼 Matching applicant with training programs...");
        System.out.println("💼 Training Assigned ✅\n");
    }
}


// ==============================
// 📄 LICENSE RENEWAL SERVICE
// ==============================
class LicenseRequest extends ServiceRequest {

    public LicenseRequest(String requestId, String applicantName) {
        super(requestId, applicantName);
    }

    @Override
    public void processRequest() {
        logRequest();
        System.out.println("📄 Validating documents...");
        System.out.println("📄 License Renewed ✅\n");
    }
}


// ==============================
// 🚀 MAIN SYSTEM (POLYMORPHISM)
// ==============================
public class DHSSystem {

    public static void main(String[] args) {

        // 🧠 Polymorphic collection
        List<ServiceRequest> requests = new ArrayList<>();

        requests.add(new HousingRequest("REQ-101", "Ali"));
        requests.add(new JobTrainingRequest("REQ-102", "Fatima"));
        requests.add(new LicenseRequest("REQ-103", "Omar"));

        // 🔁 Process all requests
        for (ServiceRequest request : requests) {
            request.processRequest(); // 🔥 Runtime polymorphism
        }
    }
}
// =================================

📋 Processing request ID: REQ-101 for Ali
🏠 Checking housing eligibility...
🏠 Housing Approved ✅

📋 Processing request ID: REQ-102 for Fatima
💼 Matching applicant with training programs...
💼 Training Assigned ✅

📋 Processing request ID: REQ-103 for Omar
📄 Validating documents...
📄 License Renewed ✅


// ======================================================

public class Main {
    public static void main(String[] args) {

        // ✅ Step 1: Create objects normally
        ServiceRequest r1 = new HousingRequest("REQ-101", "Ali");
        ServiceRequest r2 = new JobTrainingRequest("REQ-102", "Fatima");
        ServiceRequest r3 = new LicenseRequest("REQ-103", "Omar");

        // ✅ Step 2: Put them into a collection
        List<ServiceRequest> requests = new ArrayList<>();
        requests.add(r1);
        requests.add(r2);
        requests.add(r3);

        // ✅ Step 3: Process them
        for (ServiceRequest request : requests) {
            request.processRequest(); // 🔥 polymorphism
        }
    }
}