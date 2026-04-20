public class ZeilaEqualityMaster {

    public static void main(String[] args) {

        // =====================================================
        // 🔥 1. STRING POOL (CRITICAL CONCEPT)
        // =====================================================

        String x = "Hello World";
        String y = "Hello World";

        boolean poolRef = (x == y);        // true  (same pooled object)
        boolean poolVal = x.equals(y);     // true


        // =====================================================
        // 🔥 2. RUNTIME STRING (NOT IN POOL)
        // =====================================================

        String z = " Hello World".trim();

        boolean refCompare = (x == z);     // false (different object)
        boolean valCompare = x.equals(z);  // true


        // =====================================================
        // 🔥 3. CONCATENATION RUNTIME VS COMPILE TIME
        // =====================================================

        String single = "hello world";

        String concat = "hello ";
        concat += "world";

        boolean ref1 = (single == concat); // false
        boolean val1 = single.equals(concat); // true


        // =====================================================
        // 🔥 4. NEW STRING (FORCES NEW OBJECT)
        // =====================================================

        String a = "Hello World";
        String b = new String("Hello World");

        boolean ref2 = (a == b);           // false
        boolean val2 = a.equals(b);        // true


        // =====================================================
        // 🔥 5. INTERN() (VERY IMPORTANT)
        // =====================================================

        String c = "Hello World";
        String d = new String("Hello World").intern();

        boolean ref3 = (c == d);           // true
        boolean val3 = c.equals(d);        // true


        // =====================================================
        // 🔥 6. COMPILE-TIME CONSTANTS
        // =====================================================

        String first = "rat" + 1;
        String second = "r" + "a" + "t" + "1";
        String third = "r" + "a" + "t" + new String("1");

        boolean t1 = (first == second);           // true
        boolean t2 = (first == second.intern());  // true
        boolean t3 = (first == third);            // false
        boolean t4 = (first == third.intern());   // true


        // =====================================================
        // 🔥 7. STRINGBUILDER EQUALITY (BIG TRAP)
        // =====================================================

        StringBuilder sb1 = new StringBuilder("abc");
        StringBuilder sb2 = new StringBuilder("abc");

        boolean sbRef = (sb1 == sb2);        // false
        boolean sbEq = sb1.equals(sb2);      // false ❗ (NO override)


        // =====================================================
        // 🔥 8. STRINGBUILDER CHAINING REFERENCE
        // =====================================================

        StringBuilder one = new StringBuilder();
        StringBuilder two = new StringBuilder();

        StringBuilder three = one.append("a");

        boolean sbChain1 = (one == two);     // false
        boolean sbChain2 = (one == three);   // true


        // =====================================================
        // 🔥 9. STRING vs STRINGBUILDER (COMPILE ERROR)
        // =====================================================

        String name = "a";
        StringBuilder builder = new StringBuilder("a");

        // boolean error = (name == builder); // ❌ DOES NOT COMPILE


        // =====================================================
        // 🔥 10. PROPER COMPARISON (BEST PRACTICE)
        // =====================================================

        StringBuilder sbA = new StringBuilder("hello");
        StringBuilder sbB = new StringBuilder("hello");

        boolean correctCompare =
                sbA.toString().equals(sbB.toString()); // true


        // =====================================================
        // 🔥 11. REAL-WORLD LOGIN CHECK (IMPORTANT)
        // =====================================================

        String storedEmail = "user@zeila.com";
        String inputEmail = new String("user@zeila.com");

        boolean wrongCheck = (storedEmail == inputEmail); // false ❌
        boolean correctCheck = storedEmail.equals(inputEmail); // true ✅


        // =====================================================
        // 🔥 12. STRING POOL MEMORY OPTIMIZATION
        // =====================================================

        String p1 = "Zeila";
        String p2 = "Zeila";

        boolean pooled = (p1 == p2);        // true


        // =====================================================
        // 🔥 13. INTERN EDGE CASE
        // =====================================================

        String runtime = new String("Zeila");

        boolean beforeIntern = (p1 == runtime);        // false

        runtime = runtime.intern();

        boolean afterIntern = (p1 == runtime);         // true


        // =====================================================
        // 🔥 14. MIXED TRAP (VERY HARD)
        // =====================================================

        String s1 = "ab" + "c";              // pooled
        String s2 = "abc";                   // pooled
        String s3 = new String("abc");       // heap

        boolean m1 = (s1 == s2);             // true
        boolean m2 = (s1 == s3);             // false
        boolean m3 = (s1 == s3.intern());    // true


        // =====================================================
        // 🔥 15. FINAL ENTERPRISE LOGIC
        // =====================================================

        String sessionId = "SESSION123";
        String requestId = new String("SESSION123");

        boolean isSameSessionWrong = (sessionId == requestId); // false ❌
        boolean isSameSessionCorrect = sessionId.equals(requestId); // true ✅
    }
}

1. == vs equals()
Operator	Meaning
==	same memory reference
equals()	same content

TOP 6 EXAM TRAPS
"a" == "a" → true
new String() → false
.trim() → new object
StringBuilder.equals() → false
.intern() → force pool
compile-time vs runtime concat