import java.util.Arrays;

public class ZeilaArrayMaster {

    public static void main(String[] args) {

        // =====================================================
        // 🔥 1. ARRAY CREATION (PRIMITIVES)
        // =====================================================

        int[] nums1 = new int[3];          // [0, 0, 0]
        int[] nums2 = {42, 55, 99};        // [42, 55, 99]
        int[] nums3 = new int[]{1, 2, 3};  // [1, 2, 3]

        int len1 = nums1.length;           // 3


        // =====================================================
        // 🔥 2. DECLARATION STYLES
        // =====================================================

        int[] a, b;    // both arrays
        int c[], d;    // c is array, d is int


        // =====================================================
        // 🔥 3. DEFAULT VALUES
        // =====================================================

        int defaultVal = nums1[0];         // 0


        // =====================================================
        // 🔥 4. ARRAY OF OBJECTS
        // =====================================================

        String[] bugs = {"cricket", "beetle", "ladybug"};
        String[] alias = bugs;
        String[] another = {"cricket", "beetle", "ladybug"};

        boolean eq1 = bugs.equals(alias);     // true (same reference)
        boolean eq2 = bugs.equals(another);   // false


        // =====================================================
        // 🔥 5. ARRAY toString()
        // =====================================================

        String arrStr = bugs.toString();     
        // something like: [Ljava.lang.String;@xxxx

        int[] arr = {1, 2, 3};
        System.out.println(arr);

        👉 Output:[I@6d06d69c ❌


        // =====================================================
        // 🔥 6. Arrays.toString()
        // =====================================================
        String[] bugs = {"cricket", "beetle", "ladybug"};
        String readable = Arrays.toString(bugs);
        // "[cricket, beetle, ladybug]"


        // =====================================================
        // 🔥 7. NULL ARRAY
        // =====================================================

        String[] names = null;
        // names[0] → ❌ NullPointerException


        // =====================================================
        // 🔥 8. ARRAY WITH NULL ELEMENTS
        // =====================================================

        String[] names2 = new String[2];   // [null, null]

        String first = names2[0];          // null


        // =====================================================
        // 🔥 9. CASTING ARRAYS (DANGEROUS)
        // =====================================================

        String[] strings = {"A"};
        Object[] objects = strings;
        String[] again = (String[]) objects;

        // objects[0] = new StringBuilder(); 
        // ❌ ArrayStoreException at runtime


        // =====================================================
        // 🔥 10. ACCESSING ELEMENTS
        // =====================================================

        String[] mammals = {"monkey", "chimp", "donkey"};

        String m1 = mammals[0];            // "monkey"
        String m2 = mammals[1];            // "chimp"
        String m3 = mammals[2];            // "donkey"


        // =====================================================
        // 🔥 11. LOOPING
        // =====================================================

        int[] numbers = new int[5];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 5;
        }
        // [5, 6, 7, 8, 9]

        int firstNum = numbers[0];         // 5


        // =====================================================
        // 🔥 12. FOR-EACH LOOP
        // =====================================================

        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        // sum = 35


        // =====================================================
        // 🔥 13. INDEX OUT OF BOUNDS TRAPS
        // =====================================================

        // numbers[5] → ❌ ArrayIndexOutOfBoundsException
        // numbers[numbers.length] → ❌


        // =====================================================
        // 🔥 14. SORTING
        // =====================================================

        int[] sortNums = {6, 9, 1};
        Arrays.sort(sortNums);
        // [1, 6, 9]


        // =====================================================
        // 🔥 15. STRING SORT (TRICK)
        // =====================================================

        String[] strNums = {"10", "9", "100"};
        Arrays.sort(strNums);
        // ["10", "100", "9"]  (alphabetical)


        // =====================================================
        // 🔥 16. BINARY SEARCH (SORTED ONLY)
        // =====================================================
        // Binary search ONLY works correctly on sorted arrays:
        int[] arr = {2, 4, 6, 8};

        int bs1 = Arrays.binarySearch(arr, 2);  // 0
        int bs2 = Arrays.binarySearch(arr, 4);  // 1
        int bs3 = Arrays.binarySearch(arr, 1);  // -1
        int bs4 = Arrays.binarySearch(arr, 3);  // -2
        int bs5 = Arrays.binarySearch(arr, 9);  // -5

        // -(insertionPoint) - 1


        // =====================================================
        // 🔥 17. BINARY SEARCH UNSORTED
        // =====================================================

        int[] unsorted = {3, 2, 1};

        int u1 = Arrays.binarySearch(unsorted, 2); 
        // unpredictable result


        // =====================================================
        // 🔥 18. Arrays.equals()
        // =====================================================
        // Compares contents (element-by-element)
        // 👉 NOT the reference (memory address)
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};

        System.out.println(Arrays.equals(a, b)); // ✅ true
        boolean e1 = Arrays.equals(
                new int[]{1}, new int[]{1});      // true

        boolean e2 = Arrays.equals(
                new int[]{1}, new int[]{2});      // false

        boolean e3 = Arrays.equals(
                new int[]{1}, new int[]{1,2});    // false


        System.out.println(a.equals(b)); // ❌ false
        // Because arrays don’t override equals(), it uses Object’s equals() which compares references, not contents.
        This compares:Reference vs Reference
        Rules of Arrays.equals()
            Returns true ONLY if:
                Same length
                Same elements
                Same order
                Order matters

        // ❌ Order matters
        int[] a = {1, 2, 3};
        int[] b = {3, 2, 1};
        Arrays.equals(a, b); // ❌ false

        // ❌ Length matters
        int[] a = {1, 2};
        int[] b = {1, 2, 3};
        Arrays.equals(a, b); // ❌ false

        
        🔥 3. Objects inside arrays (Important!)
        String[] a = {"A", "B"};
        String[] b = {"A", "B"};

        Arrays.equals(a, b); // ✅ true
        Works because String.equals() compares values, not references

        ⚠️ Custom Objects
        class Product {
        int id;
        }

        Product[] p1 = { new Product() };
        Product[] p2 = { new Product() };

        Arrays.equals(p1, p2); // ❌ false

        👉 Why?
        Because Product does NOT override .equals()

        ➡️ It compares references of elements

        💣 4. MULTI-DIMENSIONAL ARRAYS (BIG TRAP)
        int[][] a = {{1,2}, {3,4}};
        int[][] b = {{1,2}, {3,4}};

        Arrays.equals(a, b); // ❌ false

        👉 Because inner arrays are compared as references

        ✅ Correct Way for Nested Arrays
        Arrays.deepEquals(a, b); // ✅ true

        🧠 5. Arrays.equals() vs Arrays.deepEquals()
        Method	Works for
        equals()	1D arrays
        deepEquals()	Nested arrays

        🏢 6. PROFESSIONAL SCENARIO
        Zeila Marketplace — Product IDs comparison
        int[] idsFromDB = {101, 102, 103};
        int[] idsFromAPI = {101, 102, 103};

        if (Arrays.equals(idsFromDB, idsFromAPI)) {
        System.out.println("Data is consistent ✅");
        }
        ❌ Dangerous mistake
        if (idsFromDB.equals(idsFromAPI)) {
        // ❌ never true (unless same reference)
        }
        🔥 7. OCP TRICK QUESTIONS
        ❓ What is output?
        int[] a = {1,2,3};
        int[] b = {1,2,3};

        System.out.println(a == b);              // ❌ false
        System.out.println(a.equals(b));         // ❌ false
        System.out.println(Arrays.equals(a,b));  // ✅ true
        🧠 FINAL MENTAL MODEL

        “Arrays.equals() checks values,
        array.equals() checks memory”
        // =====================================================
        // 🔥 19. Arrays.compare()
        // =====================================================
        // Compares two arrays lexicographically (like dictionary order)
        int cmp1 = Arrays.compare(
                new int[]{1}, new int[]{2});      // negative

        int cmp2 = Arrays.compare(
                new int[]{1,2}, new int[]{1});    // positive

        int cmp3 = Arrays.compare(
                new int[]{1,2}, new int[]{1,2});  // 0


        Return Values
        Result	Meaning
          0	Arrays are equal
        < 0	First array is smaller
        > 0	First array is greater

                // 2. Basic Examples
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        Arrays.compare(a, b); // 0 ✅

        
        🔹 First difference decides
        int[] a = {1, 2, 3};
        int[] b = {1, 4, 3};
        Arrays.compare(a, b); // negative ❌ (2 < 4)

        👉 Stops at index 1:  2 vs 4 → 2 is smaller → result < 0

        
        🔹 Greater case
        int[] a = {1, 5, 3};
        int[] b = {1, 4, 3};

        Arrays.compare(a, b); // positive ✅ (5 > 4)
        👉 Stops at index 1:  5 vs 4 → 5 is greater → result > 0

        // 📏 3. If all elements equal → LENGTH decides
        int[] a = {1, 2};
        int[] b = {1, 2, 3};

        Arrays.compare(a, b); // negative ❌

        👉 Why?

        Same prefix
        But a is shorter → smaller
        // Arrays.equals()	Are they identical?
        // Arrays.compare()	Which one is bigger/smaller?
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 4};

        Arrays.equals(a, b);  // false
        Arrays.compare(a, b); // negative

        // =====================================================
        // 🔥 20. Arrays.compare() STRING RULES
        // =====================================================

        int cmp4 = Arrays.compare(
                new String[]{"a"}, new String[]{"aa"}); // negative

        int cmp5 = Arrays.compare(
                new String[]{"a"}, new String[]{"A"});  // positive
                String[] a = {"apple", "banana"};
        String[] b = {"apple", "cherry"};

        Arrays.compare(a, b); // negative

        // 👉 Because:"banana".compareTo("cherry") < 0

REAL USE CASE
1.Sorting Complex Data
Example: Ranking users by scores
int[] userA = {5, 4, 3};
int[] userB = {5, 4, 5};

int result = Arrays.compare(userA, userB);

2. Sorting Arrays of Arrays (VERY COMMON)
int[][] data = {
    {3, 1},
    {1, 9},
    {1, 2}
};

Arrays.sort(data, Arrays::compare);
// Arrays.sort(data, (a, b) -> Arrays.compare(a, b));

👉 Result:

[1,2]
[1,9]
[3,1]
3. Version Comparison System (VERY REAL)

        int[] v1 = {1, 2, 0};
        int[] v2 = {1, 3, 0};
        Arrays.compare(v1, v2); // negative


4. Priority / Scheduling Systems
        int[] task1 = {priority, timestamp};
        int[] task2 = {priority, timestamp};
        Arrays.compare(task1, task2);


5. Database / Search Engine Logic
        Composite keys (multiple fields)
        int[] key1 = {userId, timestamp};
        int[] key2 = {userId, timestamp};

        Arrays.compare(key1, key2);

        // =====================================================
        // 🔥 21. Arrays.mismatch()
        // =====================================================

        int mm1 = Arrays.mismatch(
                new int[]{1}, new int[]{1});      // -1

        int mm2 = Arrays.mismatch(
                new String[]{"a"}, new String[]{"A"}); // 0

        int mm3 = Arrays.mismatch(
                new int[]{1,2}, new int[]{1});    // 1

        // Method	         Purpose
        Arrays.equals()	        Are arrays identical?
        Arrays.compare()	Which one is bigger?
        Arrays.mismatch()	Where do they differ?

        // 1. Return Values
        Result	Meaning
        >= 0	Index of first mismatch
        -1	Arrays are completely equal

        // 🧪 2. Basic Examples
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        Arrays.mismatch(a, b); // -1 ✅ (no difference)

        
        // 🔹 First mismatch
        int[] a = {1, 2, 3};
        int[] b = {1, 5, 3};
        Arrays.mismatch(a, b); // 1    ✅ (mismatch at index 1)
       

        🔹 Different lengths
        int[] a = {1, 2};
        int[] b = {1, 2, 3};
        Arrays.mismatch(a, b); // 2    ✅ (mismatch at index 2, because a has no element there)
        

       
        // =====================================================
        // 🔥 22. VARARGS
        // =====================================================

        int varLen = testVarargs(1, 2, 3);        // 3

        varLen.length;                            // 3 (varargs is treated as an array inside the method)
        varlen[index] // access elements like an array
        vaerLen[0];                            // 1
        varLen[1];                            // 2
        varLen[2];                            // 3


        // =====================================================
        // 🔥 23. 2D ARRAYS
        // =====================================================

        int[][] twoD = new int[2][3];

        int val = twoD[0][1];                    // 0

        // Visual Model. “An array of arrays”
        int[][] matrix = {
        {1, 2, 3},
        {4, 5, 6}
        };

        👉 Memory looks like:
        matrix
        ↓
        [ ref ] → [1,2,3]
        [ ref ] → [4,5,6]

        ✔ Each row is a separate int[]
        2. Accessing Elements
        matrix[0][0] → 1
        matrix[0][1] → 2
        matrix[1][2] → 6
        👉 Rule: matrix[row][column]    
        
        🔥 3. Length (VERY IMPORTANT)
        matrix.length        // number of rows
        matrix[0].length     // columns in row 0
        Example:
        int[][] matrix = {
        {1, 2, 3},
        {4, 5}
        };
        matrix.length      // 2 rows
        matrix[0].length   // 3
        matrix[1].length   // 2 ❗ different lengths allowed

        // =====================================================
        // 🔥 24. JAGGED ARRAYS
        // =====================================================

        int[][] jagged = {
                {1, 4},
                {3},
                {9, 8, 7}
        };

        int jVal = jagged[2][1];                 // 8

        Rows can have different sizes

        int[][] jagged = new int[3][];
        jagged =[
                null,
                null,
                null
                ]

        jagged[0] = new int[2];
        jagged[1] = new int[5];
        jagged[2] = new int[1];

        [
        [0, 0],                // size 2
        [0, 0, 0, 0, 0],      // size 5
        [0]                   // size 1
        ]
        Proper way to print contents
        ✅ Using Arrays.deepToString
        import java.util.Arrays;

        System.out.println(Arrays.deepToString(jagged));
        ✅ Output:
        [[0, 0], [0, 0, 0, 0, 0], [0]]

        ⚠️ OCP TRAP
         int[][] arr = new int[2][3];
                 arr = /*[
                [0, 0, 0],
                [0, 0, 0]
                ]*/
         arr[0][0] = 5; // ✅ works fine

         int[][] arr = new int[2][];
         arr[0][0] = 5; // ❌ NullPointerException
         Because inner arrays are not initialized yet
         You must initialize inner arrays before accessing elements
         arr[0] is still null
        arr[0] = new int[3]; // Now arr[0][0] = 5 works fine

         Initialization Styles
        ✔ Full initialization
        int[][] a = {
        {1,2},
        {3,4}
        };
        ✔ Partial initialization
        int[][] a = new int[2][3];

        👉 Creates:2 rows × 3 columns
        All values = 0

        ✔ Mixed
        int[][] a = new int[2][];
        a[0] = new int[]{1,2};
        a[1] = new int[]{3,4,5};

        // =====================================================
        // 🔥 25. DYNAMIC INNER ARRAYS
        // =====================================================

        int[][] dynamic = new int[2][];

        dynamic[0] = new int[5];
        dynamic[1] = new int[3];

        int dLen0 = dynamic[0].length;           // 5
        int dLen1 = dynamic[1].length;           // 3


        // =====================================================
        // 🔥 26. LOOP 2D ARRAY
        // =====================================================
        int[][] jagged = {
                {1, 4},
                {3},
                {9, 8, 7}
        };

        int total = 0;

        for (int[] inner : jagged) {
            for (int n : inner) {
                total += n;
            }
        }
        // total = 1+4+3+9+8+7 = 32
        }

        int[][] matrix = {
        {1, 2, 3},
        {4, 5, 6}
                };
        System.out.println(Arrays.deepToString(matrix)); // [[1, 2, 3], [4, 5, 6]]

        Classic loop
        for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
        }
        }

        Enhanced for-loop
        for (int[] row : matrix) {
        for (int val : row) {
                System.out.print(val + " ");
        }
        }


Wrong for 2D arrays

    // =====================================================
    // 🔥 VARARGS METHOD
    // =====================================================

    static int testVarargs(int... nums) {
        return nums.length;   // length of passed values
    }
}