Yes. Here is a full scenario for overloaded constructors in a Java record.

Scenario: Marketplace Product Listing

Imagine you are building a marketplace app like ZailaMart.

A seller can list a product in different ways:

Full product information
Product without discount
Product with only name and price
Empty/default product for testing

So we create a record:

public record ProductListing(
        int id,
        String name,
        double price,
        double discount
) {

    // Canonical constructor: validates and controls final values
    public ProductListing {
        if (id < 0) {
            throw new IllegalArgumentException("id cannot be negative");
        }

        if (price < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }

        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("discount must be between 0 and 100");
        }

        name = name.trim();
    }

    // Overloaded constructor 1
    // Seller gives id, name, and price, but no discount
    public ProductListing(int id, String name, double price) {
        this(id, name, price, 0.0);
    }

    // Overloaded constructor 2
    // Seller gives only name and price
    public ProductListing(String name, double price) {
        this(0, name, price, 0.0);
    }

    // Overloaded constructor 3
    // Default product
    public ProductListing() {
        this(0, "Unknown Product", 0.0, 0.0);
    }
}
How each constructor works
1. Canonical constructor
public ProductListing {
    name = name.trim();
}

This matches the record header:

ProductListing(int id, String name, double price, double discount)

This is the canonical constructor.

Because it is compact, Java automatically does this at the end:

this.id = id;
this.name = name;
this.price = price;
this.discount = discount;
2. Overloaded constructor with no discount
public ProductListing(int id, String name, double price) {
    this(id, name, price, 0.0);
}

Usage:

ProductListing p1 = new ProductListing(101, "Table", 250.00);

Java converts it to:

new ProductListing(101, "Table", 250.00, 0.0);

So discount becomes 0.0.

3. Overloaded constructor with no id
public ProductListing(String name, double price) {
    this(0, name, price, 0.0);
}

Usage:

ProductListing p2 = new ProductListing("Chair", 80.00);

Java converts it to:

new ProductListing(0, "Chair", 80.00, 0.0);
4. No-argument constructor
public ProductListing() {
    this(0, "Unknown Product", 0.0, 0.0);
}

Usage:

ProductListing p3 = new ProductListing();

Java converts it to:

new ProductListing(0, "Unknown Product", 0.0, 0.0);
Full test code
public class Test {
    public static void main(String[] args) {

        ProductListing p1 =
                new ProductListing(101, " Table ", 250.00, 10.0);
        ProductListing p2 =
                new ProductListing(102, "Chair", 80.00);
        ProductListing p3 =
                new ProductListing("Sofa", 500.00);
        ProductListing p4 =
                new ProductListing();

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
    }
}

Output:
ProductListing[id=101, name=Table, price=250.0, discount=10.0]
ProductListing[id=102, name=Chair, price=80.0, discount=0.0]
ProductListing[id=0, name=Sofa, price=500.0, discount=0.0]
ProductListing[id=0, name=Unknown Product, price=0.0, discount=0.0]

Notice this: