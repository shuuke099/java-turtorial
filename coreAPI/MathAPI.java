import java.math.BigDecimal;
import java.math.BigInteger;

public class ZeilaMathMaster {

    public static void main(String[] args) {

        // =====================================================
        // 🔥 1. MIN / MAX
        // =====================================================

        int maxVal = Math.max(3, 7);        // 7
        int minVal = Math.min(7, -9);       // -9


        // =====================================================
        // 🔥 2. ROUND (VERY IMPORTANT)
        // =====================================================

        long r1 = Math.round(123.45);       // 123
        long r2 = Math.round(123.50);       // 124

        int r3 = Math.round(123.45f);       // 123


        // =====================================================
        // 🔥 3. CEIL / FLOOR
        // =====================================================

        double c = Math.ceil(3.14);         // 4.0
        double f = Math.floor(3.14);        // 3.0

        double c2 = Math.ceil(5.0);         // 5.0
        double f2 = Math.floor(5.0);        // 5.0


        // =====================================================
        // 🔥 4. POWER
        // =====================================================

        double p1 = Math.pow(5, 2);         // 25.0
        double p2 = Math.pow(16, 0.5);      // 4.0


        // =====================================================
        // 🔥 5. RANDOM
        // =====================================================

        double rand = Math.random();  
        // 0.0 <= rand < 1.0 (unknown exact value)


        // =====================================================
        // 🔥 6. RANDOM RANGE (REAL WORLD)
        // =====================================================

        int discount = (int)(Math.random() * 10);
        // 0 to 9


        // =====================================================
        // 🔥 7. RETURN TYPE TRAP
        // =====================================================

        double res1 = Math.max(5, 10);      // 10.0 (int → double possible)
        int res2 = Math.min(5, 10);         // 5


        // =====================================================
        // 🔥 8. FLOATING POINT ERROR (CRITICAL)
        // =====================================================

        double amount1 = 64.1 * 100;
        // 6409.999999999999 ❌


        // =====================================================
        // 🔥 9. BIGINTEGER CREATION
        // =====================================================

        BigInteger bigInt1 = BigInteger.valueOf(5000L);
        BigInteger bigInt2 = BigInteger.valueOf(10);

        BigInteger zero = BigInteger.ZERO;   // 0


        // =====================================================
        // 🔥 10. BIGINTEGER OPERATIONS
        // =====================================================

        BigInteger resultInt = BigInteger.valueOf(199)
                .add(BigInteger.valueOf(1))     // 200
                .divide(BigInteger.TEN)         // 20
                .max(BigInteger.valueOf(6));    // 20

        // resultInt = 20


        // =====================================================
        // 🔥 11. BIGDECIMAL CREATION
        // =====================================================

        BigDecimal bd1 = BigDecimal.valueOf(5000L);
        BigDecimal bd2 = BigDecimal.valueOf(5000.00);

        BigDecimal one = BigDecimal.ONE;     // 1


        // =====================================================
        // 🔥 12. BIGDECIMAL FIX PRECISION
        // =====================================================

        BigDecimal amount2 = BigDecimal.valueOf(64.1)
                .multiply(BigDecimal.valueOf(100));

        // 6410.0 ✅ (correct)


        // =====================================================
        // 🔥 13. BIGDECIMAL OPERATIONS
        // =====================================================

        BigDecimal bdCalc = BigDecimal.valueOf(1000)
                .add(BigDecimal.valueOf(250))     // 1250
                .subtract(BigDecimal.valueOf(200))// 1050
                .multiply(BigDecimal.valueOf(2))  // 2100
                .divide(BigDecimal.valueOf(10));  // 210

        // bdCalc = 210


        // =====================================================
        // 🔥 14. IMMUTABILITY (IMPORTANT)
        // =====================================================

        BigDecimal bd = BigDecimal.valueOf(10);

        bd.add(BigDecimal.ONE);   // ignored

        BigDecimal newBd = bd.add(BigDecimal.ONE); // 11


        // =====================================================
        // 🔥 15. COMPARISON
        // =====================================================

        BigInteger b1 = BigInteger.valueOf(5);
        BigInteger b2 = BigInteger.valueOf(10);

        int cmp = b1.compareTo(b2);   // negative


        // =====================================================
        // 🔥 16. LARGE NUMBER HANDLING
        // =====================================================

        BigInteger huge = new BigInteger("12345123451234512345");
        // valid

        // long invalid = 12345123451234512345L; // ❌ does not compile


        // =====================================================
        // 🔥 17. REAL-WORLD BILLING SCENARIO
        // =====================================================

        BigDecimal price = BigDecimal.valueOf(19.99);
        BigDecimal quantity = BigDecimal.valueOf(3);

        BigDecimal total = price.multiply(quantity);
        // 59.97

        BigDecimal tax = total.multiply(BigDecimal.valueOf(0.10));
        // 5.997

        BigDecimal finalAmount = total.add(tax);
        // 65.967


        // =====================================================
        // 🔥 18. ROUNDING MONEY (SIMULATION)
        // =====================================================

        double rounded = Math.round(65.967);  
        // 66


        // =====================================================
        // 🔥 19. MIXED TRAP
        // =====================================================

        double mix = Math.pow(2, 3) + Math.ceil(2.1);
        // 8.0 + 3.0 = 11.0


        // =====================================================
        // 🔥 20. FULL ENTERPRISE PIPELINE
        // =====================================================

        BigDecimal order = BigDecimal.valueOf(100)
                .add(BigDecimal.valueOf(50))   // 150
                .multiply(BigDecimal.valueOf(2)) // 300
                .divide(BigDecimal.valueOf(3)); // 100

        // order = 100
    }
}

| Method        | Return |
| ------------- | ------ |
| round(double) | long   |
| round(float)  | int    |
| ceil/floor    | double |
| pow           | double |

2. FLOATING POINT PROBLEM
64.1 * 100 → 6409.999999999999 ❌

👉 Use:

BigDecimal
🔥 3. BIG TYPES
Type	Use
BigInteger	very large integers
BigDecimal	money / precision
🔥 4. IMMUTABILITY
bd.add(...) → new object
⚠️ TOP EXAM TRAPS
round() return type
floating precision
BigDecimal chaining
Math.random range
pow returns double
BigInteger constructor vs valueOf

In Zeila:
Prices → BigDecimal
Discounts → Math.random
Rounding → Math.round
Analytics → Math.max/min
Large IDs → BigInteger