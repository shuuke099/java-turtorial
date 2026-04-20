import java.util.*;
import java.util.function.*;

// =====================================================
// 🏢 ZEILA WILDLIFE INTELLIGENCE SYSTEM
// =====================================================
// FINAL COVERAGE:
//
// ✔ Lambdas
// ✔ Functional Interfaces (SAM)
// ✔ Method References
// ✔ Built-in Functional Interfaces
// ✔ Predicate / Function chaining
// ✔ Primitive Interfaces
// ✔ Variables in Lambdas (FINAL PART)
// =====================================================



// =====================================================
// 🔥 STEP 1: RECORD
// =====================================================

record Animal(String species, boolean canHop, boolean canSwim) {}



// =====================================================
// 🔥 STEP 2: FUNCTIONAL INTERFACE
// =====================================================

@FunctionalInterface
interface CheckTrait {
    boolean test(Animal a);
}



// =====================================================
// 🔥 STEP 3: ENGINE (DEFERRED EXECUTION)
// =====================================================

class Engine {

    static void print(List<Animal> list, CheckTrait checker) {

        for (Animal a : list) {
            if (checker.test(a)) {
                System.out.print(a.species() + " ");
            }
        }
        System.out.println();
    }
}



// =====================================================
// 🔥 STEP 4: MAIN SYSTEM
// =====================================================

public class ZeilaUltimate {

    public static void main(String[] args) {

        var animals = new ArrayList<Animal>();

        animals.add(new Animal("fish", false, true));
        animals.add(new Animal("kangaroo", true, false));
        animals.add(new Animal("rabbit", true, false));
        animals.add(new Animal("turtle", false, true));



        // =====================================================
        // 🔥 STEP 5: LAMBDAS
        // =====================================================

        Engine.print(animals, a -> a.canHop());
        // Output: kangaroo rabbit

        Engine.print(animals, a -> a.canSwim());
        // Output: fish turtle



        // =====================================================
        // 🔥 STEP 6: BUILT-IN FUNCTIONAL INTERFACES
        // =====================================================

        Predicate<String> p = String::isEmpty;
        System.out.println(p.test(""));
        // Output: true

        Function<String, Integer> f = String::length;
        System.out.println(f.apply("cluck"));
        // Output: 5

        Consumer<String> c = System.out::println;
        c.accept("Hello");
        // Output: Hello

        Supplier<String> s = String::new;
        System.out.println(s.get());
        // Output: (empty)



        // =====================================================
        // 🔥 STEP 7: CHAINING
        // =====================================================

        Predicate<String> egg = x -> x.contains("egg");
        Predicate<String> brown = x -> x.contains("brown");

        System.out.println(egg.and(brown).test("brown egg"));
        // Output: true

        System.out.println(egg.and(brown.negate()).test("white egg"));
        // Output: true



        // =====================================================
        // 🔥 STEP 8: FUNCTION COMPOSE
        // =====================================================

        Function<Integer, Integer> before = x -> x + 1;
        Function<Integer, Integer> after = x -> x * 2;

        System.out.println(after.compose(before).apply(3));
        // Output: 8



        // =====================================================
        // 🔥 STEP 9: VARIABLES IN LAMBDAS
        // =====================================================


        // ------------------------------
        // ✔ PARAMETER TYPES
        // ------------------------------

        Predicate<String> p1 = x -> true;
        Predicate<String> p2 = (var x) -> true;
        Predicate<String> p3 = (String x) -> true;

        // All valid
        // x type = String


        // ------------------------------
        // ✔ TYPE INFERENCE
        // ------------------------------

        consume((var x) -> System.out.println(x), 123);
        // Output: 123

        // x is Integer (from context)



        // ------------------------------
        // ✔ SORT CONTEXT
        // ------------------------------

        List<Integer> nums = Arrays.asList(3, 1, 2);

        nums.sort((var x, var y) -> x.compareTo(y));

        System.out.println(nums);
        // Output: [1, 2, 3]



        // ------------------------------
        // ❌ INVALID PARAMETER FORMATS
        // ------------------------------

        // (var x, y) -> true           ❌
        // (var x, Integer y) -> true   ❌
        // (String x, var y) -> true    ❌
        // (Integer x, y) -> true       ❌



        // =====================================================
        // 🔥 STEP 10: LOCAL VARIABLES INSIDE LAMBDA
        // =====================================================

        BiFunction<Integer, Integer, Integer> func =
                (a, b) -> {
                    int c = 0;
                    return a + b + c;
                };

        System.out.println(func.apply(2, 3));
        // Output: 5


        // ❌ INVALID (redeclare parameter)
        // (a, b) -> { int a = 0; return 5; }



        // =====================================================
        // 🔥 STEP 11: SYNTAX ERROR TRAPS
        // =====================================================

        // Example with errors:

        // int b = 1;
        // Predicate<Integer> pErr = a -> {
        //     int b = 0;   ❌ redeclare
        //     int c = 0;
        //     return b == c;
        // }               ❌ missing semicolon

        // TOTAL ERRORS:
        // 1. reuse parameter name
        // 2. redeclare local variable
        // 3. missing semicolon



        // =====================================================
        // 🔥 STEP 12: REFERENCING VARIABLES
        // =====================================================

        String name = "Crow";
        String volume = "loudly";

        Consumer<String> consumer = s ->
                System.out.println(name + " says " + volume);

        consumer.accept("hello");

        // Output:
        // Crow says loudly



        // =====================================================
        // 🔥 STEP 13: EFFECTIVELY FINAL RULE
        // =====================================================

        String word = "Hi";

        Consumer<String> test = x ->
                System.out.println(word + x);

        // word = "Bye"; ❌ would break effectively final



        // =====================================================
        // 🔥 STEP 14: INSTANCE VARIABLE ACCESS
        // =====================================================

        ZeilaUltimate obj = new ZeilaUltimate();
        obj.color = "black";

        Consumer<String> c2 = x ->
                System.out.println(obj.color);

        c2.accept("test");

        // Output:
        // black



        // =====================================================
        // 🔥 STEP 15: BOOLEAN SUPPLIER
        // =====================================================

        BooleanSupplier bs = () -> true;

        System.out.println(bs.getAsBoolean());
        // Output: true
    }



    // =====================================================
    // 🔥 HELPER METHOD
    // =====================================================

    static void consume(Consumer<Integer> c, int num) {
        c.accept(num);
    }


    // =====================================================
    // 🔥 INSTANCE VARIABLE
    // =====================================================

    String color;
}