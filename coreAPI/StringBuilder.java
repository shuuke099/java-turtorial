public class ZeilaStringBuilderMaster {

    public static void main(String[] args) {

        // =====================================================
        // 🔥 1. WHY STRINGBUILDER (PERFORMANCE)
        // =====================================================

        String alpha = "";
        for (char c = 'a'; c <= 'c'; c++) {
            alpha += c;
        }
        // Creates multiple objects: "", "a", "ab", "abc"

        StringBuilder sbPerf = new StringBuilder();
        for (char c = 'a'; c <= 'c'; c++) {
            sbPerf.append(c);
        }
        String perfResult = sbPerf.toString(); // "abc"


        // =====================================================
        // 🔥 2. CREATION
        // =====================================================

        StringBuilder sb1 = new StringBuilder();        // ""
        StringBuilder sb2 = new StringBuilder("animal");// "animal"
        StringBuilder sb3 = new StringBuilder(10);      // capacity 10


        // =====================================================
        // 🔥 3. MUTABILITY (CRITICAL DIFFERENCE)
        // =====================================================

        StringBuilder sb = new StringBuilder("start");

        sb.append("+middle");                           // "start+middle"
        StringBuilder same = sb.append("+end");         // "start+middle+end"

        boolean sameRef = (sb == same);                 // true


        // =====================================================
        // 🔥 4. CHAINING (VERY IMPORTANT)
        // =====================================================

        StringBuilder a = new StringBuilder("abc");
        StringBuilder b = a.append("de");               // "abcde"

        b = b.append("f").append("g");                  // "abcdefg"

        String aVal = a.toString();                     // "abcdefg"
        String bVal = b.toString();                     // "abcdefg"


        // =====================================================
        // 🔥 5. COMMON METHODS (LIKE STRING)
        // =====================================================

        StringBuilder animals = new StringBuilder("animals");

        String sub = animals.substring(
                animals.indexOf("a"),
                animals.indexOf("al")
        );                                              // "anim"

        int len = animals.length();                     // 7
        char ch = animals.charAt(6);                    // 's'


        // =====================================================
        // 🔥 6. APPEND (MOST IMPORTANT)
        // =====================================================

        StringBuilder ap = new StringBuilder();

        ap.append(1).append('c');                       // "1c"
        ap.append("-").append(true);                    // "1c-true"

        String apVal = ap.toString();                   // "1c-true"


        // =====================================================
        // 🔥 7. APPEND CODE POINT
        // =====================================================

        StringBuilder cp = new StringBuilder();

        cp.appendCodePoint(87)                          // 'W'
          .append(',')
          .append((char)87)                            // 'W'
          .append(',')
          .append(87)                                  // "87"
          .append(',')
          .appendCodePoint(8217);                      // special char

        String cpVal = cp.toString();                  // "W,W,87,â€™"


        // =====================================================
        // 🔥 8. INSERT
        // =====================================================

        StringBuilder ins = new StringBuilder("animals");

        ins.insert(7, "-");                             // "animals-"
        ins.insert(0, "-");                             // "-animals-"
        ins.insert(4, "-");                             // "-ani-mals-"

        String insVal = ins.toString();                 // "-ani-mals-"


        // =====================================================
        // 🔥 9. DELETE
        // =====================================================

        StringBuilder del = new StringBuilder("abcdef");

        del.delete(1, 3);                               // "adef"

        // del.deleteCharAt(5);                          // ❌ Exception

        String delVal = del.toString();                 // "adef"


        // =====================================================
        // 🔥 10. DELETE (EDGE CASE)
        // =====================================================

        StringBuilder del2 = new StringBuilder("abcdef");

        del2.delete(1, 100);                            // "a"

        String del2Val = del2.toString();               // "a"


        // =====================================================
        // 🔥 11. REPLACE
        // =====================================================

        StringBuilder rep = new StringBuilder("pigeon dirty");

        rep.replace(3, 6, "sty");                       // "pigsty dirty"

        String repVal = rep.toString();                 // "pigsty dirty"


        // =====================================================
        // 🔥 12. REPLACE (EDGE)
        // =====================================================

        StringBuilder rep2 = new StringBuilder("pigeon dirty");

        rep2.replace(3, 100, "");                       // "pig"

        String rep2Val = rep2.toString();               // "pig"


        // =====================================================
        // 🔥 13. REVERSE
        // =====================================================

        StringBuilder rev = new StringBuilder("ABC");

        rev.reverse();                                  // "CBA"

        String revVal = rev.toString();                 // "CBA"


        // =====================================================
        // 🔥 14. toString()
        // =====================================================

        StringBuilder builder = new StringBuilder("Zeila");

        String finalStr = builder.toString();           // "Zeila"


        // =====================================================
        // 🔥 15. FULL CHAIN (ENTERPRISE)
        // =====================================================

        StringBuilder chain = new StringBuilder("User");

        chain.append(":")
             .append("Adan")
             .insert(0, "[")
             .append("]")
             .replace(1, 5, "CLIENT");

        String chainVal = chain.toString();             // "[CLIENT:Adan]"


        // =====================================================
        // 🔥 16. IMPORTANT BEHAVIOR TEST
        // =====================================================

        StringBuilder test = new StringBuilder("abc");

        StringBuilder ref1 = test.append("d");          // "abcd"
        StringBuilder ref2 = test.append("e");          // "abcde"

        String testVal = test.toString();               // "abcde"
        String ref1Val = ref1.toString();               // "abcde"
        String ref2Val = ref2.toString();               // "abcde"
    }
}

StringBuilder a = new StringBuilder("abc");
StringBuilder b = a.append("d");

a == b → true;

sb.append("a").append("b");

modifies SAME object


substring() RETURNS String
String s = sb.substring(0, 2);
does NOT modify builder

StringBuilder sb = new StringBuilder("pigeon dirty");

sb.replace(3, 6, "sty");   // "pigsty dirty"

StringBuilder sb = new StringBuilder("pigeon dirty");

sb.replace(3, 6, "sty");   // "pigsty dirty"

delete() RANGE
sb.delete(3, 6);
➡️ re (3), o (4), n (5)

StringBuilder sb = new StringBuilder(5);

int c1 = sb.capacity();       // 5
int l1 = sb.length();         // 0

sb.append("hello");           // "hello"

int c2 = sb.capacity();       // 5
int l2 = sb.length();         // 5

sb.append("!");               // "hello!"

int c3 = sb.capacity();       // 12  (5*2 + 2)
int l3 = sb.length();         // 6


// =======================

Trap 1: capacity ≠ length
StringBuilder sb = new StringBuilder(10);

sb.length();      // 0
🔥 Trap 2: NO characters exist
sb.charAt(0);     // ❌ Exception

👉 because length = 0

🔥 Trap 3: capacity grows automatically
newCapacity = (old * 2) + 2
🔥 Trap 4: capacity is NOT fixed
StringBuilder sb = new StringBuilder(1);

sb.append("abc");   // capacity expands automatically