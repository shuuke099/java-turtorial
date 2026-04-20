import java.util.*;

// =====================================================
// 🏢 ZEILA WILDLIFE SYSTEM
// =====================================================
// Goal:
// Filter animals dynamically using:
// - Traditional OOP
// - Functional Programming (Lambdas)
//
// Concepts Covered:
// ✔ Record
// ✔ Functional Interface
// ✔ Traditional Class Implementation
// ✔ Lambda Expressions
// ✔ Deferred Execution
// ✔ Lambda Syntax (ALL forms)
// ✔ Valid / Invalid Lambdas
// ✔ var Trap
// =====================================================


// =====================================================
// 🔥 STEP 1: RECORD (DATA MODEL)
// =====================================================

record Animal(String species, boolean canHop, boolean canSwim) {}

// Example:
// new Animal("fish", false, true)
// species = "fish"
// canHop = false
// canSwim = true



// =====================================================
// 🔥 STEP 2: FUNCTIONAL INTERFACE
// =====================================================

interface CheckTrait {
    boolean test(Animal a);
}

// ✔ MUST have ONE abstract method
// ✔ Enables lambda expressions



// =====================================================
// 🔥 STEP 3: TRADITIONAL CLASS IMPLEMENTATION
// =====================================================

class CheckIfHopper implements CheckTrait {
    public boolean test(Animal a) {
        return a.canHop();
    }
}

// ✔ Checks if animal can hop



// =====================================================
// 🔥 STEP 4: MAIN APPLICATION (TRADITIONAL)
// =====================================================

public class ZeilaWildlifeSystem {

    public static void main(String[] args) {

        // =====================================================
        // 🔥 STEP 5: CREATE LIST OF ANIMALS
        // =====================================================

        var animals = new ArrayList<Animal>();

        animals.add(new Animal("fish", false, true));
        animals.add(new Animal("kangaroo", true, false));
        animals.add(new Animal("rabbit", true, false));
        animals.add(new Animal("turtle", false, true));

        // =====================================================
        // 🔥 STEP 6: TRADITIONAL APPROACH
        // =====================================================

        print(animals, new CheckIfHopper());

        // Output:
        // Animal[species=kangaroo, canHop=true, canSwim=false]
        // Animal[species=rabbit, canHop=true, canSwim=false]


        // =====================================================
        // 🔥 STEP 7: LAMBDA APPROACH (REPLACES CLASS)
        // =====================================================

        print(animals, a -> a.canHop());

        // Output:
        // kangaroo rabbit


        // =====================================================
        // 🔥 STEP 8: MORE LAMBDA CONDITIONS
        // =====================================================

        // Animals that swim
        print(animals, a -> a.canSwim());

        // Output:
        // fish turtle


        // Animals that CANNOT swim
        print(animals, a -> !a.canSwim());

        // Output:
        // kangaroo rabbit



        // =====================================================
        // 🔥 STEP 9: LAMBDA SYNTAX VARIATIONS
        // =====================================================

        // ✔ Minimal syntax
        print(animals, a -> a.canHop());
        // Output: kangaroo rabbit

        // ✔ With type
        print(animals, (Animal a) -> a.canHop());
        // Output: kangaroo rabbit

        // ✔ With block (requires return)
        print(animals, a -> {
            return a.canHop();
        });
        // Output: kangaroo rabbit

        // ✔ Full verbose
        print(animals, (Animal a) -> {
            return a.canHop();
        });
        // Output: kangaroo rabbit


        // =====================================================
        // 🔥 STEP 10: EMPTY LAMBDA
        // =====================================================

        print(animals, a -> {});

        // Output:
        // (nothing printed)



        // =====================================================
        // 🔥 STEP 11: ZERO PARAMETER LAMBDA (EXAMPLE)
        // =====================================================

        CheckTrait alwaysTrue = a -> true;

        print(animals, alwaysTrue);

        // Output:
        // fish kangaroo rabbit turtle



        // =====================================================
        // 🔥 STEP 12: CONTEXT (VERY IMPORTANT)
        // =====================================================

        // Java understands:
        // a -> a.canHop()

        // Because:
        // test(Animal a) → returns boolean

        // So:
        // a = Animal
        // return type = boolean



        // =====================================================
        // 🔥 STEP 13: DEFERRED EXECUTION
        // =====================================================

        // Lambda is defined here:
        print(animals, a -> a.canHop());

        // But executed inside print() method later



        // =====================================================
        // 🔥 STEP 14: VALID LAMBDAS (FROM TABLE)
        // =====================================================

        CheckTrait t1 = a -> a.canHop();                // ✔
        CheckTrait t2 = (Animal a) -> a.canHop();       // ✔
        CheckTrait t3 = a -> { return a.canHop(); };    // ✔

        // Example usage:
        t1.test(new Animal("rabbit", true, false)); 
        // Output: true



        // =====================================================
        // 🔥 STEP 15: INVALID LAMBDAS (DO NOT COMPILE)
        // =====================================================

        // ❌ Missing parentheses
        // a, b -> a.canHop();

        // ❌ Missing return
        // a -> { a.canHop(); }

        // ❌ Missing parentheses with type
        // Animal a -> a.canHop();

        // ❌ Missing semicolon inside block
        // a -> { return a.canHop() }



        // =====================================================
        // 🔥 STEP 16: VAR TRAP
        // =====================================================

        // ❌ DOES NOT COMPILE
        // var invalid = (Animal a) -> a.canHop();

        // ✔ FIX:
        CheckTrait valid = (Animal a) -> a.canHop();

        // Output:
        valid.test(new Animal("kangaroo", true, false));
        // true
    }



    // =====================================================
    // 🔥 CORE ENGINE (DEFERRED EXECUTION HAPPENS HERE)
    // =====================================================

    private static void print(List<Animal> animals, CheckTrait checker) {

        for (Animal animal : animals) {

            if (checker.test(animal)) {
                System.out.print(animal.species() + " ");
            }
        }

        System.out.println();
    }
}