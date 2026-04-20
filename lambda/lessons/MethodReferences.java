import java.util.*;

// =====================================================
// 🏢 ZEILA WILDLIFE INTELLIGENCE SYSTEM
// =====================================================
// System Goal:
// - Manage animals
// - Filter dynamically
// - Use Lambdas + Method References
//
// Concepts Covered:
// ✔ Record
// ✔ Functional Interface (SAM)
// ✔ Lambda Expressions
// ✔ Deferred Execution
// ✔ Lambda Syntax (ALL forms)
// ✔ Valid / Invalid Lambdas
// ✔ var Trap
// ✔ Functional Interface Rules
// ✔ Object Method Exception
// ✔ Method References (ALL 4 TYPES)
// =====================================================



// =====================================================
// 🔥 STEP 1: RECORD (DATA MODEL)
// =====================================================

record Animal(String species, boolean canHop, boolean canSwim) {}



// =====================================================
// 🔥 STEP 2: FUNCTIONAL INTERFACE (SAM)
// =====================================================

@FunctionalInterface
interface CheckTrait {
    boolean test(Animal a);
}



// =====================================================
// 🔥 STEP 3: TRADITIONAL CLASS
// =====================================================

class CheckIfHopper implements CheckTrait {
    public boolean test(Animal a) {
        return a.canHop();
    }
}



// =====================================================
// 🔥 STEP 4: MAIN SYSTEM
// =====================================================

public class ZeilaSystem {

    public static void main(String[] args) {

        // =====================================================
        // 🔥 STEP 5: CREATE DATA
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
        // kangaroo rabbit



        // =====================================================
        // 🔥 STEP 7: LAMBDA APPROACH
        // =====================================================

        print(animals, a -> a.canHop());
        // Output: kangaroo rabbit

        print(animals, a -> a.canSwim());
        // Output: fish turtle

        print(animals, a -> !a.canSwim());
        // Output: kangaroo rabbit



        // =====================================================
        // 🔥 STEP 8: LAMBDA SYNTAX VARIATIONS
        // =====================================================

        print(animals, (Animal a) -> a.canHop());
        // Output: kangaroo rabbit

        print(animals, a -> { return a.canHop(); });
        // Output: kangaroo rabbit

        print(animals, (Animal a) -> { return a.canHop(); });
        // Output: kangaroo rabbit



        // =====================================================
        // 🔥 STEP 9: EMPTY LAMBDA
        // =====================================================

        print(animals, a -> {});
        // Output: (nothing)



        // =====================================================
        // 🔥 STEP 10: VALID LAMBDA
        // =====================================================

        CheckTrait t = a -> a.canHop();
        t.test(new Animal("rabbit", true, false));
        // Output: true



        // =====================================================
        // 🔥 STEP 11: INVALID LAMBDAS (DO NOT COMPILE)
        // =====================================================

        // a, b -> true              ❌ missing ()
        // a -> { a.canHop(); }     ❌ missing return
        // Animal a -> true         ❌ missing ()
        // a -> { return a.canHop() } ❌ missing ;



        // =====================================================
        // 🔥 STEP 12: VAR TRAP
        // =====================================================

        // var invalid = (Animal a) -> a.canHop(); ❌

        CheckTrait valid = (Animal a) -> a.canHop(); // ✔



        // =====================================================
        // 🔥 STEP 13: FUNCTIONAL INTERFACE RULES
        // =====================================================

        Sprint tiger = speed ->
                System.out.println("Running: " + speed);

        tiger.sprint(60);

        // Output:
        // Running: 60



        // =====================================================
        // 🔥 STEP 14: METHOD REFERENCES
        // =====================================================


        // =====================================================
        // ✔ TYPE 1: STATIC METHOD
        // =====================================================

        Converter methodRef1 = Math::round;
        Converter lambda1 = x -> Math.round(x);

        System.out.println(methodRef1.round(100.1));
        // Output: 100



        // =====================================================
        // ✔ TYPE 2: INSTANCE METHOD (OBJECT)
        // =====================================================

        var str = "Zoo";

        StringStart ref2 = str::startsWith;
        StringStart lambda2 = s -> str.startsWith(s);

        System.out.println(ref2.beginningCheck("A"));
        // Output: false



        // =====================================================
        // ✔ TYPE 3: INSTANCE METHOD (PARAMETER)
        // =====================================================

        StringParameterChecker ref3 = String::isEmpty;
        StringParameterChecker lambda3 = s -> s.isEmpty();

        System.out.println(ref3.check("Zoo"));
        // Output: false



        // =====================================================
        // ✔ TYPE 4: TWO PARAMETERS
        // =====================================================

        StringTwoParameterChecker ref4 = String::startsWith;
        StringTwoParameterChecker lambda4 = (s, p) -> s.startsWith(p);

        System.out.println(ref4.check("Zoo", "A"));
        // Output: false



        // =====================================================
        // ✔ TYPE 5: CONSTRUCTOR REFERENCE
        // =====================================================

        EmptyStringCreator ref5 = String::new;
        EmptyStringCreator lambda5 = () -> new String();

        String s1 = ref5.create();

        System.out.println(s1.equals("Snake"));
        // Output: false



        // =====================================================
        // ✔ CONSTRUCTOR WITH PARAM
        // =====================================================

        StringCopier ref6 = String::new;
        StringCopier lambda6 = x -> new String(x);

        String s2 = ref6.copy("Zebra");

        System.out.println(s2.equals("Zebra"));
        // Output: true



        // =====================================================
        // 🔥 METHOD REFERENCE LIMITATION
        // =====================================================

        StringChecker lambdaOnly = () -> str.startsWith("Zoo");

        // Cannot convert to method reference ❌



    }



    // =====================================================
    // 🔥 CORE ENGINE (DEFERRED EXECUTION)
    // =====================================================

    static void print(List<Animal> animals, CheckTrait checker) {

        for (Animal a : animals) {
            if (checker.test(a)) {
                System.out.print(a.species() + " ");
            }
        }
        System.out.println();
    }
}



// =====================================================
// 🔥 EXTRA INTERFACES (FROM CONTEXT)
// =====================================================

interface Sprint {
    void sprint(int speed);
}

interface Converter {
    long round(double num);
}

interface StringStart {
    boolean beginningCheck(String prefix);
}

interface StringParameterChecker {
    boolean check(String text);
}

interface StringTwoParameterChecker {
    boolean check(String text, String prefix);
}

interface EmptyStringCreator {
    String create();
}

interface StringCopier {
    String copy(String value);
}

interface StringChecker {
    boolean check();
}