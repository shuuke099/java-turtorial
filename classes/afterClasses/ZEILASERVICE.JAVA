// =============================================
// 🚀 ZEILA HUB - FULL PROFESSIONAL ARCHITECTURE
// Covers:
// ✅ Interface (abstract, default, static, private)
// ✅ Multiple interfaces
// ✅ Abstract class
// ✅ Functional interface (Strategy)
// ✅ Polymorphism
// =============================================

import java.util.*;

// =============================================
// 1️⃣ CORE INTERFACE
// =============================================
public interface Service {

    // 🔴 ABSTRACT METHODS
    void executeService();
    double calculatePrice();

    // 🟡 DEFAULT METHOD
    default void printReceipt() {
        double price = calculatePrice();
        System.out.println("💵 Total Price: $" + price);
        applyDiscount(price);
    }

    // 🔵 STATIC METHOD
    static boolean isValidCustomer(String name) {
        return name != null && !name.isEmpty();
    }

    // 🟣 PRIVATE METHOD (Java 9+)
    private void applyDiscount(double price) {
        if (price > 100) {
            System.out.println("🎉 Discount Applied!");
        }
    }
}

// =============================================
// 2️⃣ ADDITIONAL INTERFACES
// =============================================

interface Trackable {
    void trackStatus();

    default void sendNotification() {
        System.out.println("📩 Notification sent to customer");
    }
}

interface Rateable {
    void rateService(int rating);
}

// =============================================
// 3️⃣ FUNCTIONAL INTERFACE (STRATEGY)
// =============================================

@FunctionalInterface
interface PricingStrategy {
    double apply(double basePrice);
}

// =============================================
// 4️⃣ ABSTRACT BASE CLASS
// =============================================

abstract class BaseService implements Service, Trackable {

    protected String serviceId;
    protected String customerName;

    public BaseService(String serviceId, String customerName) {
        if (!Service.isValidCustomer(customerName)) {
            throw new IllegalArgumentException("Invalid customer");
        }
        this.serviceId = serviceId;
        this.customerName = customerName;
    }

    public void logService() {
        System.out.println("🧾 Service ID: " + serviceId +
                " | Customer: " + customerName);
    }

    @Override
    public void trackStatus() {
        System.out.println("📍 Tracking service: " + serviceId);
    }
}

// =============================================
// 5️⃣ CONCRETE SERVICES
// =============================================

class ResumeService extends BaseService implements Rateable {

    public ResumeService(String id, String name) {
        super(id, name);
    }

    @Override
    public void executeService() {
        logService();
        System.out.println("📄 Building professional resume...");
    }

    @Override
    public double calculatePrice() {
        return 50;
    }

    @Override
    public void rateService(int rating) {
        System.out.println("⭐ Resume Service rated: " + rating);
    }
}

class CleaningService extends BaseService {

    public CleaningService(String id, String name) {
        super(id, name);
    }

    @Override
    public void executeService() {
        logService();
        System.out.println("🧹 Cleaning in progress...");
    }

    @Override
    public double calculatePrice() {
        return 120;
    }
}

class MechanicService extends BaseService {

    public MechanicService(String id, String name) {
        super(id, name);
    }

    @Override
    public void executeService() {
        logService();
        System.out.println("🔧 Fixing car...");
    }

    @Override
    public double calculatePrice() {
        return 200;
    }
}

// =============================================
// 6️⃣ PRICING ENGINE (STRATEGY PATTERN)
// =============================================

class PricingEngine {

    public static double calculate(double base, PricingStrategy strategy) {
        return strategy.apply(base);
    }
}

// =============================================
// 7️⃣ MAIN SYSTEM (POLYMORPHISM)
// =============================================

public class ZeilaHubSystem {

    public static void main(String[] args) {

        List<Service> services = new ArrayList<>();

        services.add(new ResumeService("S1", "Ali"));
        services.add(new CleaningService("S2", "Fatima"));
        services.add(new MechanicService("S3", "Omar"));

        // 🔁 PROCESS ALL SERVICES
        for (Service service : services) {
            service.executeService();
            service.printReceipt();

            // Pattern matching instanceof (Java 17+)
            if (service instanceof Trackable t) {
                t.trackStatus();
                t.sendNotification();
            }

            System.out.println("----------------------");
        }

        // =============================================
        // 🎯 STRATEGY EXAMPLE
        // =============================================

        PricingStrategy discount10 = price -> price * 0.9;
        PricingStrategy premium = price -> price * 1.2;

        double basePrice = 100;

        System.out.println("Discounted Price: " +
                PricingEngine.calculate(basePrice, discount10));

        System.out.println("Premium Price: " +
                PricingEngine.calculate(basePrice, premium));
    }
}

// =============================================
// ✅ OUTPUT SAMPLE
// =============================================
/*
🧾 Service ID: S1 | Customer: Ali
📄 Building professional resume...
💵 Total Price: $50.0
📍 Tracking service: S1
📩 Notification sent to customer
----------------------

🧾 Service ID: S2 | Customer: Fatima
🧹 Cleaning in progress...
💵 Total Price: $120.0
🎉 Discount Applied!
📍 Tracking service: S2
📩 Notification sent to customer
----------------------

🧾 Service ID: S3 | Customer: Omar
🔧 Fixing car...
💵 Total Price: $200.0
🎉 Discount Applied!
📍 Tracking service: S3
📩 Notification sent to customer
----------------------

Discounted Price: 90.0
Premium Price: 120.0
*/

// =============================================
// 🎯 FINAL NOTES
// =============================================
// ✔ Interface → contract (WHAT)
// ✔ Abstract class → shared logic (HOW partially)
// ✔ Concrete classes → real services
// ✔ Strategy → dynamic pricing
// ✔ Polymorphism → scalable marketplace system
// =============================================
