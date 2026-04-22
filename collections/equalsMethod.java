@Override
public boolean equals(Object o) {
    if (this == o) return true;              // (1)
    if (!(o instanceof Product)) return false; // (2)
    Product p = (Product) o;                // (3)
    return Objects.equals(id, p.id);        // (4)
}

Think of it like a decision chain;

// Step 1 → Same object? → YES → return true → STOP
// Step 2 → Same type? → NO → return false → STOP
// Step 3 → Otherwise → compare IDs → return result

Example 1: First return executes
Product a = new Product(1);
Product b = a;

a.equals(b);
Execution:
this == o → ✅ true
return true → method ends
❌ nothing else runs
🔷 Example 2: First return is skipped
Product a = new Product(1);
Product b = new Product(1);

a.equals(b);
Execution:
this == o → ❌ false → continue
instanceof → ✅ true → continue
compare ids → ✅ true
🔷 Example 3: Second return executes
Product a = new Product(1);

a.equals("hello");
Execution:
this == o → ❌ false
instanceof → ❌ false
return false → method ends
🔥 Why multiple returns are used

This is called guard clauses (very professional style):