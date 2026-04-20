package marketplace.domain;

import marketplace.dto.ProductData;

// ==============================
// 🔒 Controlled hierarchy
// ==============================
public sealed abstract class Product
        permits PhysicalProduct, DigitalProduct, ServiceProduct {

    protected final ProductData data;

    protected Product(ProductData data) {
        this.data = data;
    }

    public ProductData data() {
        return data;
    }

    public abstract String category();
}

// ==============================
// 📦 Physical
// ==============================
public final class PhysicalProduct extends Product {

    public PhysicalProduct(ProductData data) {
        super(data);
    }

    public String category() {
        return "Physical";
    }
}

// ==============================
// 💻 Digital
// ==============================
public final class DigitalProduct extends Product {

    public DigitalProduct(ProductData data) {
        super(data);
    }

    public String category() {
        return "Digital";
    }
}

// ==============================
// 🛠️ Service (open for extension)
// ==============================
public non-sealed class ServiceProduct extends Product {

    public ServiceProduct(ProductData data) {
        super(data);
    }

    public String category() {
        return "Service";
    }
}

// Example extension
class PremiumService extends ServiceProduct {
    public PremiumService(ProductData data) {
        super(data);
    }
}