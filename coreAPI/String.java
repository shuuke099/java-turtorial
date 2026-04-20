public class ZeilaStringMaster {

    public static void main(String[] args) {

        // =====================================================
        // 🔥 1. STRING CREATION
        // =====================================================

        String a = "Zeila";
        String b = new String("Zeila");
        String c = """
                Zeila
                """;

        boolean refCompare = (a == b);              // false
        boolean valueCompare = a.equals(b);         // true
        // String object implements CharSequence, Serializable, Comparable<String>

        // =====================================================
        // 🔥 2. CONCATENATION RULES (CRITICAL)
        // =====================================================

        String r1 = 1 + 2 + "";                     // "3"
        String r2 = "" + 1 + 2;                     // "12"
        String r3 = "A" + 1 + 2;                    // "A12"
        String r4 = 1 + 2 + "A";                    // "3A"
        String r5 = "A" + null;                     // "Anull"

        String s = "1";
        s += "2";                                  // "12"
        s += 3;                                    // "123"


        // =====================================================
        // 🔥 3. LENGTH
        // =====================================================

        int len = "animals".length();               // 7


        // =====================================================
        // 🔥 4. CHAR ACCESS
        // =====================================================

        char ch1 = "animals".charAt(0);             // 'a'
        char ch2 = "animals".charAt(6);             // 's'
        // char ch3 = "animals".charAt(7);          // ❌ StringIndexOutOfBoundsException


        // =====================================================
        // 🔥 5. CODE POINTS
        // =====================================================

        String uni = "A😊";

        int cp1 = uni.codePointAt(0);               // 65
        int cp2 = uni.codePointBefore(2);           // emoji code
        int cpCount = uni.codePointCount(0, uni.length()); // 2


        // =====================================================
        // 🔥 6. SUBSTRING (VERY TRICKY)
        // =====================================================

        String sub1 = "animals".substring(3);       // "mals"
        String sub2 = "animals".substring(3, 4);    // "m"
        String sub3 = "animals".substring(3, 7);    // "mals"
        String sub4 = "animals".substring(3, 3);    // ""

        // String sub5 = "animals".substring(3, 2); // ❌ Exception
        // String sub6 = "animals".substring(3, 8); // ❌ Exception


        // =====================================================
        // 🔥 7. INDEX OF
        // =====================================================

        int i1 = "animals".indexOf('a');            // 0
        int i2 = "animals".indexOf("al");           // 4
        int i3 = "animals".indexOf('a', 4);         // 4
        int i4 = "animals".indexOf("al", 5);        // -1
        int i5 = "animals".indexOf('x');            // -1


        // =====================================================
        // 🔥 8. CASE METHODS
        // =====================================================

        String up = "abc".toUpperCase();            // "ABC"
        String low = "ABC".toLowerCase();           // "abc"


        // =====================================================
        // 🔥 9. EQUALITY
        // =====================================================

        boolean e1 = "abc".equals("ABC");           // false
        boolean e2 = "abc".equalsIgnoreCase("ABC"); // true
        boolean e3 = "abc".equals(123);             // false


        // =====================================================
        // 🔥 10. SEARCHING
        // =====================================================

        boolean sw1 = "abc".startsWith("a");        // true
        boolean sw2 = "abc".startsWith("b", 1);     // true
        boolean sw3 = "abc".startsWith("b", 2);     // false

        boolean ew1 = "abc".endsWith("c");          // true
        boolean ew2 = "abc".endsWith("a");          // false

        boolean con1 = "abc".contains("b");         // true
        boolean con2 = "abc".contains("B");         // false


        // =====================================================
        // 🔥 11. REPLACE
        // =====================================================

        String rep1 = "abcabc".replace('a', 'A');   // "AbcAbc"
        String rep2 = "abcabc".replace("ab", "XY"); // "XYcXYc"


        // =====================================================
        // 🔥 12. TRIM / STRIP
        // =====================================================

        String dirty = " \t abc \n ";

        int t1 = dirty.trim().length();             // 3
        int t2 = dirty.strip().length();            // 3
        int t3 = dirty.stripLeading().length();     // 6
        int t4 = dirty.stripTrailing().length();    // 5


        // =====================================================
        // 🔥 13. INDENT / STRIPINDENT
        // =====================================================

        String multi = " a\n b\n c";

        String ind1 = multi.indent(1);              // adds space + adds newline
        String ind2 = multi.indent(-1);             // removes leading spaces
        String ind3 = multi.stripIndent();          // normalized left


        // =====================================================
        // 🔥 14. EMPTY / BLANK
        // =====================================================

        boolean em1 = "".isEmpty();                 // true
        boolean em2 = " ".isEmpty();                // false

        boolean bl1 = "".isBlank();                 // true
        boolean bl2 = " ".isBlank();                // true


        // =====================================================
        // 🔥 15. FORMAT / FORMATTED
        // =====================================================

        String f1 = String.format("User %s has %d orders", "Adan", 5);
        // "User Adan has 5 orders"

        String f2 = "User %s has %d orders".formatted("Adan", 5);
        // "User Adan has 5 orders"

        String f3 = String.format("%.2f", 3.14159); // "3.14"
        String f4 = String.format("%012f", 3.14);   // "000003.140000"


        // =====================================================
        // 🔥 16. METHOD CHAINING
        // =====================================================

        String chain = "  AniMaL  "
                .trim()
                .toLowerCase()
                .replace('a', 'A');                // "AnimAl"


        // =====================================================
        // 🔥 17. IMMUTABILITY
        // =====================================================

        String original = "hello";
        original.toUpperCase();                    // ignored

        String same = original;                    // "hello"
        original = original.toUpperCase();         // "HELLO"


        // =====================================================
        // 🔥 18. TEXT BLOCK + INDENT EFFECT
        // =====================================================

      public class TextBlockFullGuideWithLength {

    public static void main(String[] args) {

        // =====================================================
        // 🔥 1. BASIC TEXT BLOCK
        // =====================================================
        String block = """
                a
                b
                c""";

        System.out.println(block);
        // a
        // b
        // c

        System.out.println(block.length());
        // 5  -> "a\nb\nc"



        // =====================================================
        // 🔥 2. TRAILING NEWLINE (EXAM TRAP)
        // =====================================================
        String block2 = """
                a
                b
                c
                """;

        System.out.println(block2);
        // a
        // b
        // c

        System.out.println(block2.length());
        // 6  -> "a\nb\nc\n"



        // =====================================================
        // 🔥 3. INDENT(int n)
        // =====================================================
        String indented = block.indent(1);

        System.out.println(indented);
        //  a
        //  b
        //  c

        System.out.println(indented.length());
        // 9 -> " a\n b\n c\n"



        // =====================================================
        // 🔥 4. INDENT NEGATIVE
        // =====================================================
        String ind2 = indented.indent(-1);

        System.out.println(ind2);
        // a
        // b
        // c

        System.out.println(ind2.length());
        // 6 -> "a\nb\nc\n"  ⚠️ NOTE: newline added by indent()



        // =====================================================
        // 🔥 5. stripIndent()
        // =====================================================
        String messy = """
                a
                  b
                c
                """;

        String clean = messy.stripIndent();

        System.out.println(clean);
        // a
        //   b
        // c

        System.out.println(clean.length());
        // 8 -> "a\n  b\nc\n"



        // =====================================================
        // 🔥 6. translateEscapes()
        // =====================================================
        String raw = """
                line1\\nline2
                """;

        System.out.println(raw);
        // line1\nline2

        System.out.println(raw.length());
        // 13 -> "line1\\nline2\n"

        String translated = raw.translateEscapes();

        System.out.println(translated);
        // line1
        // line2

        System.out.println(translated.length());
        // 11 -> "line1\nline2"



        // =====================================================
        // 🔥 7. strip / trim
        // =====================================================
        String spaced = """
                
                a
                b
                c
                
                """;

        String stripped = spaced.strip();

        System.out.println(stripped);
        // a
        // b
        // c

        System.out.println(stripped.length());
        // 5 -> "a\nb\nc"



        // =====================================================
        // 🔥 8. isBlank()
        // =====================================================
        String emptyBlock = """
                
                """;

        System.out.println(emptyBlock.isBlank());
        // true

        System.out.println(emptyBlock.length());
        // 1 -> "\n"



        // =====================================================
        // 🔥 9. lines()
        // =====================================================
        System.out.println(block.lines().count());
        // 3



        // =====================================================
        // 🔥 10. repeat()
        // =====================================================
        String repeated = block.repeat(2);

        System.out.println(repeated);
        // a
        // b
        // c
        // a
        // b
        // c

        System.out.println(repeated.length());
        // 10 -> "a\nb\nca\nb\nc"



        // =====================================================
        // 🔥 11. formatted()
        // =====================================================
        String template = """
                Name: %s
                Age: %d
                """;

        String result = template.formatted("Adan", 25);

        System.out.println(result);
        // Name: Adan
        // Age: 25

        System.out.println(result.length());
        // 21 -> includes newline at end



        // =====================================================
        // 🔥 12. ESCAPES INSIDE TEXT BLOCK
        // =====================================================
        String esc = """
                Line1\nLine2
                """;

        System.out.println(esc);
        // Line1\nLine2

        System.out.println(esc.length());
        // 13 -> "\" + "n" counted literally

        String esc2 = esc.translateEscapes();

        System.out.println(esc2);
        // Line1
        // Line2

        System.out.println(esc2.length());
        // 11



        // =====================================================
        // 🔥 13. BACKSLASH LINE CONTINUATION
        // =====================================================
        String noNewLine = """
                a \
                b \
                c
                """;

        System.out.println(noNewLine);
        // a b c

        System.out.println(noNewLine.length());
        // 5 -> "a b c"



        // =====================================================
        // 🔥 14. EQUALS
        // =====================================================
        String b1 = """
                a
                b
                c""";

        String b2 = "a\nb\nc";

        System.out.println(b1.equals(b2));
        // true

        System.out.println(b1.length());
        // 5

        System.out.println(b2.length());
        // 5



        // =====================================================
        // 🔥 15. stripIndent + indent combo
        // =====================================================
        String combo = messy.stripIndent().indent(2);

        System.out.println(combo);
        //   a
        //     b
        //   c

        System.out.println(combo.length());
        // 11



        // =====================================================
        // 🔥 16. CHAR ACCESS
        // =====================================================
        System.out.println((int) block.charAt(1));
        // 10  -> '\n'

        System.out.println(block.length());
        // 5



        // =====================================================
        // 🔥 17. contains / startsWith / endsWith
        // =====================================================
        System.out.println(block.contains("b"));
        // true

        System.out.println(block.startsWith("a"));
        // true

        System.out.println(block.endsWith("c"));
        // true



        // =====================================================
        // 🏥 18. PROFESSIONAL SCENARIO
        // =====================================================
        String report = """
                Patient Report
                --------------
                Name: %s
                Condition: %s
                Status: %s
                """;

        String formattedReport = report.formatted("John Doe", "Injury", "Stable");

        System.out.println(formattedReport);
        // Patient Report
        // --------------
        // Name: John Doe
        // Condition: Injury
        // Status: Stable

        System.out.println(formattedReport.length());
        // ~80+ (depends exact spacing, includes newlines)

        System.out.println(formattedReport.lines().count());
        // 5



        // =====================================================
        // 🔥 FINAL EXAM NOTES
        // =====================================================

        // ❗ length depends on trailing newline
        // ❗ indent() ALWAYS adds newline
        // ❗ "\" removes newline
        // ❗ escape sequences NOT interpreted unless translateEscapes()
        // ❗ text block == normal String

    }
}

        // =====================================================
        // 🔥 19. FORMAT SYMBOLS
        // =====================================================

        String fmt = "%s scored %f out of %d".formatted("Adan", 90.25, 100);
        // "Adan scored 90.250000 out of 100"


        // =====================================================
        // 🔥 20. ADVANCED CHAIN (OCP TRAP)
        // =====================================================

        String x = "abc";
        String y = x.toUpperCase()
                .replace("B", "2")
                .replace('C', '3');                // "A23"

        String xVal = x;                           // "abc"
        String yVal = y;                           // "A23"
    }
}