package marketplace.service;

import marketplace.dto.*;
import marketplace.domain.*;
import marketplace.pricing.*;

import java.util.*;

public class OrderProcessor {

    private final String systemName = "Zeila Marketplace";

    // =========================================
    // 🔹 INNER CLASS (has access to outer state)
    // =========================================
    class NotificationService {
        void notifyUser(String msg) {
            System.out.println("📢 [" + systemName + "] " + msg);
        }
    }

    // =========================================
    // 🔹 STATIC NESTED CLASS (utility)
    // =========================================
    static class AuditService {
        static void log(String msg) {
            System.out.println("🔐 AUDIT: " + msg);
        }
    }

    // =========================================
    // 🔹 MAIN PROCESS METHOD
    // =========================================
    public void process(Order order, PricingStrategy strategy) {

        NotificationService notifier = new NotificationService();
        notifier.notifyUser("Processing Order: " + order.orderId());

        double total = 0;

        for (OrderItem item : order.items()) {

            Product product = map(item.product());

            // 🔥 Sealed + polymorphism
            String type = switch (product) {
                case PhysicalProduct p -> "📦 Physical";
                case DigitalProduct d -> "💻 Digital";
                case ServiceProduct s -> "🛠️ Service";
            };

            double raw = item.product().price() * item.quantity();
            double finalPrice = strategy.apply(raw);

            System.out.printf(
                    "Item: %-20s | Type: %-10s | Final: $%.2f%n",
                    item.product().name(), type, finalPrice
            );

            total += finalPrice;
        }

        System.out.println("💰 TOTAL: $" + total);
        AuditService.log("Order " + order.orderId() + " processed");

        // =========================================
        // 🔹 LOCAL CLASS (validation)
        // =========================================
        final double threshold = 100;

        class FraudChecker {
            boolean suspicious(double amount) {
                return amount > threshold;
            }
        }

        FraudChecker checker = new FraudChecker();
        System.out.println("⚠️ Fraud check: " + checker.suspicious(total));

        // =========================================
        // 🔹 ANONYMOUS CLASS
        // =========================================
        Runnable task = new Runnable() {
            public void run() {
                System.out.println("⚙️ Sending receipt...");
            }
        };
        task.run();
    }

    // =========================================
    // 🔹 MAPPING (factory-like)
    // =========================================
    private Product map(ProductData data) {

        if (data.name().toLowerCase().contains("laptop"))
            return new PhysicalProduct(data);

        if (data.name().toLowerCase().contains("ebook"))
            return new DigitalProduct(data);

        return new ServiceProduct(data);
    }
}