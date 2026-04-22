import java.util.*;
import java.util.Objects;

class Product {
    String id;
    String name;
    double price;
    String category;

    Product(String id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product p = (Product) o;
        return Objects.equals(id, p.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return name + " ($" + price + ")";
    }
}

import java.util.Objects;

public record Product(
    String id,
    String name,
    double price,
    String category
) {

    // Custom equals (based only on id, same as your class)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product p)) return false;
        return Objects.equals(this.id, p.id);
    }

    // Custom hashCode (based only on id)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Custom toString
    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}


public record Product(String id, String name, double price, String category) {}

class OrderController {

    private final OrderService service = new OrderService();

    public void createOrder() {

        List<Product> requestItems = List.of(  // IMMUTABLE INPUT
            new Product("P1001","MacBook Pro 16",2499.99,"Electronics"),
            new Product("P1002","Logitech MX Master 3",99.99,"Accessories"),
            new Product("P1003","USB-C Hub",49.99,"Accessories"),
            new Product("P1004","4K Monitor",399.99,"Electronics"),
            new Product("P1002","Logitech MX Master 3",99.99,"Accessories"),
            new Product("P1005","Mechanical Keyboard",129.99,"Accessories"),
            new Product("P1006","Noise Cancelling Headphones",299.99,"Electronics")
        );

        service.processOrder(requestItems);
    }
}

import java.util.List;

public record OrderRequest(
    String customerId,
    List<Product> items
) {}

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public void createOrder(@RequestBody OrderRequest request) {

        // ✔ Data comes from frontend automatically
        List<Product> items = request.items();

        service.processOrder(items);
    }
}



class OrderService {

    public void processOrder(List<Product> requestItems) {

        // 🔒 IMMUTABLE → MUTABLE
        List<Product> cart = new ArrayList<>(requestItems);

        // -------------------------------------------------
        // ✅ BASIC INFO
        // -------------------------------------------------
        System.out.println("Cart Size: " + cart.size());
        if (cart.isEmpty()) throw new RuntimeException("Cart is empty");

        // -------------------------------------------------
        // ✅ ADD / INSERT
        // -------------------------------------------------
        cart.add(new Product("P2000","Extended Warranty",199.99,"Service"));
        cart.add(0, new Product("P2001","Priority Processing",29.99,"Service"));

        // BULK ADD
        List<Product> bundle = List.of(
            new Product("P3001","Laptop Sleeve",25.99,"Accessories"),
            new Product("P3002","Screen Cleaner",15.99,"Accessories")
        );
        cart.addAll(bundle);

        // INSERT AT POSITION
        cart.addAll(2, bundle);

        // -------------------------------------------------
        // ✅ SEARCH / VALIDATION
        // -------------------------------------------------
        Product sample = new Product("P1001","MacBook Pro 16",2499.99,"Electronics");

        System.out.println("Contains Laptop: " + cart.contains(sample));
        System.out.println("First Index: " + cart.indexOf(sample));
        System.out.println("Last Index: " + cart.lastIndexOf(sample));
        System.out.println("Contains Bundle: " + cart.containsAll(bundle));

        // -------------------------------------------------
        // ✅ UPDATE
        // -------------------------------------------------
        cart.set(1, new Product("P2001","Priority Processing+",39.99,"Service"));

        // -------------------------------------------------
        // ✅ REMOVE
        // -------------------------------------------------
        cart.remove(0);               // by index
        cart.remove(sample);          // by object
        cart.removeAll(bundle);       // bulk remove

        // CONDITIONAL REMOVE
        cart.removeIf(p -> p.price < 20); // remove cheap items

        // KEEP ONLY ELECTRONICS
        cart.removeIf(p -> !p.category.equals("Electronics"));

        // -------------------------------------------------
        // ✅ BULK OPERATIONS
        // -------------------------------------------------
        cart.replaceAll(p -> new Product(p.id, p.name, p.price * 0.9, p.category)); // discount

        cart.sort(Comparator.comparingDouble(p -> p.price));

        // -------------------------------------------------
        // ✅ SUBLIST (TOP ITEMS)
        // -------------------------------------------------
        List<Product> topItems = cart.subList(0, Math.min(3, cart.size()));

        // -------------------------------------------------
        // ✅ ITERATION
        // -------------------------------------------------
        cart.forEach(p -> System.out.println("Item: " + p));

        ListIterator<Product> it = cart.listIterator();
        while (it.hasNext()) {
            Product p = it.next();
            if (p.price > 1000) {
                it.set(new Product(p.id, p.name + " (Premium)", p.price, p.category));
            }
        }

        // -------------------------------------------------
        // ✅ STREAM (MODERN PROCESSING)
        // -------------------------------------------------
        cart.stream()
            .filter(p -> p.price > 300)
            .forEach(p -> System.out.println("High Value: " + p));

        // -------------------------------------------------
        // ✅ COMPARISON
        // -------------------------------------------------
        List<Product> snapshot = new ArrayList<>(cart);
        System.out.println("Equal carts: " + cart.equals(snapshot));
        System.out.println("Hash: " + cart.hashCode());

        // -------------------------------------------------
        // ✅ ARRAY CONVERSION
        // -------------------------------------------------
        Product[] arr = cart.toArray(Product[]::new);

        // -------------------------------------------------
        // ✅ CLEAR
        // -------------------------------------------------
        cart.clear();
    }
}


3. All Major Methods (You covered these)
✔ Add
add(E)
add(index, E)
addAll()
addAll(index, c)
✔ Access
get()
✔ Update
set()
replaceAll()
✔ Remove
remove(index)
remove(object)
removeAll()
retainAll()
removeIf()
clear()
✔ Search
contains()
containsAll()
indexOf()
lastIndexOf()
✔ Info
size()
isEmpty()
✔ Iteration
forEach()
iterator()
listIterator()
✔ Utility
sort()
subList()
toArray()
✔ Modern
stream()
✔ Comparison
equals()
hashCode()