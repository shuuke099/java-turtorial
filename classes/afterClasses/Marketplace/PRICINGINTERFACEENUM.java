package marketplace.pricing;

// ==============================
// 🔌 Strategy Contract
// ==============================
public interface PricingStrategy {
    double apply(double price);
    String label();
}

// ==============================
// 🔥 Enum Strategy
// ==============================
public enum DiscountStrategy implements PricingStrategy {

    NONE {
        public double apply(double price) { return price; }
        public String label() { return "No Discount"; }
    },

    TEN_PERCENT {
        public double apply(double price) { return price * 0.9; }
        public String label() { return "10% Off"; }
    },

    BLACK_FRIDAY {
        public double apply(double price) { return price * 0.5; }
        public String label() { return "Black Friday 50%"; }
    }
}