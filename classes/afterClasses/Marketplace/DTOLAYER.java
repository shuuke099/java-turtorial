package marketplace.dto;

import java.util.List;
import java.util.Objects;

// ==============================
// 🔹 Product Data (Immutable)
// ==============================
public record ProductData(
        String id,
        String name,
        double price
) {
    public ProductData {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        if (price < 0) throw new IllegalArgumentException("Price must be >= 0");
    }
}

// ==============================
// 🔹 Order Item
// ==============================
public record OrderItem(
        ProductData product,
        int quantity
) {
    public OrderItem {
        if (quantity <= 0) throw new IllegalArgumentException("Invalid quantity");
    }
}

// ==============================
// 🔹 Order
// ==============================
public record Order(
        String orderId,
        List<OrderItem> items
) {
    public Order {
        Objects.requireNonNull(orderId);
        items = List.copyOf(items); // defensive copy
    }
}