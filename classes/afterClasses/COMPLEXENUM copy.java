SCENARIO — DHS Payment Method System
💳 “Smart Payment Engine with Behavior + Rules”

You are building a DHS system where users can pay using:

💵 CASH
💳 CARD
📱 MOBILE

Each payment method:

Has different fees
Has different validation rules
Has different processing logic
Shares a common contract

👉 This is a perfect use case for complex enums (strategy pattern)


import java.util.*;

// ======================================================
// 🔹 INTERFACE (CONTRACT)
// ======================================================
interface PaymentAction {
    void process(double amount);
}


// ======================================================
// 🔹 COMPLEX ENUM (STRATEGY + DATA + BEHAVIOR)
// ======================================================
enum PaymentMethod implements PaymentAction {

    // ==================================================
    // 💵 CASH
    // ==================================================
    CASH(0.0) {
        @Override
        public void process(double amount) {
            validate(amount);
            System.out.println("💵 Paying $" + amount + " in CASH");
        }
    },

    // ==================================================
    // 💳 CARD
    // ==================================================
    CARD(2.5) {
        @Override
        public void process(double amount) {
            validate(amount);
            double total = amount + calculateFee(amount);
            System.out.println("💳 CARD payment: $" + total + " (includes fee)");
        }
    },

    // ==================================================
    // 📱 MOBILE
    // ==================================================
    MOBILE(1.0) {
        @Override
        public void process(double amount) {
            validate(amount);
            double total = amount + calculateFee(amount);
            System.out.println("📱 MOBILE payment: $" + total);
        }
    };

    // ==================================================
    // 🔹 FIELD
    // ==================================================
    private final double feePercentage;

    // ==================================================
    // 🔹 CONSTRUCTOR (IMPLICITLY PRIVATE)
    // ==================================================
    PaymentMethod(double feePercentage) {
        System.out.println("⚙️ Initializing PaymentMethod: " + this.name());
        this.feePercentage = feePercentage;
    }

    // ==================================================
    // 🔹 INSTANCE METHOD
    // ==================================================
    public double calculateFee(double amount) {
        return amount * (feePercentage / 100);
    }

    public double getFeePercentage() {
        return feePercentage;
    }

    // ==================================================
    // 🔹 STATIC METHOD
    // ==================================================
    public static PaymentMethod getRecommended() {
        return MOBILE;
    }

    // ==================================================
    // 🔹 PRIVATE METHOD (INTERNAL LOGIC)
    // ==================================================
    private void validate(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("❌ Invalid amount");
        }
    }
}


// ======================================================
// 🔹 SERVICE CLASS
// ======================================================
class PaymentService {

    private String requestId;
    private PaymentMethod method;

    public PaymentService(String requestId, PaymentMethod method) {
        this.requestId = requestId;
        this.method = method;
    }

    public void processPayment(double amount) {

        System.out.println("\n📄 Request: " + requestId);
        System.out.println("Method: " + method);

        // 🔥 POLYMORPHISM
        method.process(amount);

        System.out.println("Fee %: " + method.getFeePercentage());
    }
}


// ======================================================
// 🔹 MAIN SYSTEM
// ======================================================
public class EnumPaymentScenario {

    public static void main(String[] args) {

        System.out.println("🚀 DHS Payment System Starting...\n");

        // =========================================
        // 🔹 CREATE REQUESTS
        // =========================================
        PaymentService p1 = new PaymentService("REQ-1", PaymentMethod.CASH);
        PaymentService p2 = new PaymentService("REQ-2", PaymentMethod.CARD);
        PaymentService p3 = new PaymentService("REQ-3", PaymentMethod.MOBILE);

        p1.processPayment(100);
        p2.processPayment(200);
        p3.processPayment(300);

        // =========================================
        // 🔹 LOOP THROUGH ENUM VALUES
        // =========================================
        System.out.println("\nAvailable Methods:");
        for (PaymentMethod m : PaymentMethod.values()) {
            System.out.println(m.name() + " → Fee: " + m.getFeePercentage() + "%");
        }

        // =========================================
        // 🔹 STATIC METHOD
        // =========================================
        System.out.println("\nRecommended: " +
                PaymentMethod.getRecommended());

        // =========================================
        // 🔹 valueOf()
        // =========================================
        PaymentMethod parsed = PaymentMethod.valueOf("CARD");
        System.out.println("\nParsed: " + parsed);

        // =========================================
        // 🔹 SWITCH
        // =========================================
        switch (parsed) {
            case CASH -> System.out.println("Cash selected");
            case CARD -> System.out.println("Card selected");
            case MOBILE -> System.out.println("Mobile selected");
        }
    }
}


🚀 DHS Payment System Starting...

⚙️ Initializing PaymentMethod: CASH
⚙️ Initializing PaymentMethod: CARD
⚙️ Initializing PaymentMethod: MOBILE

📄 Request: REQ-1
Method: CASH
💵 Paying $100.0 in CASH
Fee %: 0.0

📄 Request: REQ-2
Method: CARD
💳 CARD payment: $205.0 (includes fee)
Fee %: 2.5

📄 Request: REQ-3
Method: MOBILE
📱 MOBILE payment: $303.0
Fee %: 1.0

Available Methods:
CASH → Fee: 0.0%
CARD → Fee: 2.5%
MOBILE → Fee: 1.0%

Recommended: MOBILE

Parsed: CARD
Card selected