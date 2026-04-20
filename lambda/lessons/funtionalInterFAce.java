// =====================================================
// 🔥 STEP 17: FUNCTIONAL INTERFACE (SAM RULE)
// =====================================================

// SAM = Single Abstract Method

@FunctionalInterface
interface Sprint {
    void sprint(int speed);
}

// ✔ VALID functional interface (ONE abstract method)



// =====================================================
// 🔥 STEP 18: CLASS IMPLEMENTATION
// =====================================================

class Tiger implements Sprint {

    public void sprint(int speed) {
        System.out.println("Animal is sprinting fast! " + speed);
    }
}

// Usage:
Sprint tiger = new Tiger();
tiger.sprint(50);

// Output:
// Animal is sprinting fast! 50



// =====================================================
// 🔥 STEP 19: @FunctionalInterface RULE
// =====================================================

// ❌ DOES NOT COMPILE
/*
@FunctionalInterface
interface Dance {
    void move();
    void rest(); // ❌ second abstract method
}
*/

// Reason:
// Must have ONLY ONE abstract method



// =====================================================
// 🔥 STEP 20: FUNCTIONAL INTERFACE IDENTIFICATION
// =====================================================

// Base interface
interface SprintBase {
    void sprint(int speed);
}

// 1️⃣ VALID
interface Dash extends SprintBase {}

// ✔ inherits ONE abstract method → still functional



// 2️⃣ INVALID
interface Skip extends SprintBase {
    void skip();
}

// ❌ TWO abstract methods:
// sprint()
// skip()



// 3️⃣ INVALID
interface Sleep {

    private void snore() {}        // not abstract
    default int getZzz() { return 1; } // not abstract
}

// ❌ NO abstract methods → NOT functional



// 4️⃣ VALID
interface Climb {

    void reach();  // ONLY abstract method

    default void fall() {}     // ignored
    static int getBackUp() { return 100; } // ignored
    private static boolean checkHeight() { return true; } // ignored
}

// ✔ VALID functional interface



// =====================================================
// 🔥 STEP 21: OBJECT METHODS EXCEPTION
// =====================================================

// Java Object methods:
// toString()
// equals(Object)
// hashCode()

// These DO NOT count toward SAM rule



// ❌ NOT FUNCTIONAL
interface Soar {

    String toString(); // from Object → ignored
}

// ❌ ZERO abstract methods → NOT functional



// ✔ VALID FUNCTIONAL INTERFACE
interface Dive {

    String toString();           // ignored
    boolean equals(Object o);    // ignored
    int hashCode();              // ignored

    void dive(); // ONLY abstract method
}



// Usage:
Dive d = () -> {
    // simulate diving
};

// Output:
// (lambda executed when called)



// =====================================================
// 🔥 STEP 22: TRICKY OBJECT METHOD TRAP
// =====================================================

// ❌ NOT FUNCTIONAL
interface Hibernate {

    String toString();              // ignored
    boolean equals(Hibernate o);    // ❌ NOT Object method
    int hashCode();                 // ignored

    void rest(); // abstract
}

// WHY ❌?

// equals(Hibernate) ≠ equals(Object)

// So now:
// abstract methods =
// 1. equals(Hibernate)
// 2. rest()

// ❌ TWO abstract methods → NOT functional



// =====================================================
// 🔥 STEP 23: REAL LAMBDA USAGE WITH FUNCTIONAL INTERFACE
// =====================================================

Sprint fastRunner = speed -> {
    // lambda implementation
    // Output when executed:
    // Animal is sprinting fast! 100
};

fastRunner.sprint(100);

// Output:
// Animal is sprinting fast! 100



// =====================================================
// 🔥 STEP 24: FULL CONNECTION TO YOUR FIRST PART
// =====================================================

// Earlier:
// interface CheckTrait {
//     boolean test(Animal a);
// }

// ✔ This is ALSO a functional interface

// So lambda works:
CheckTrait check = a -> a.canHop();

check.test(new Animal("rabbit", true, false));

// Output:
// true



// =====================================================
// 🔥 FINAL UNDERSTANDING (FROM YOUR FILE)
// =====================================================

// ✔ Functional Interface = ONE abstract method
// ✔ Annotation is optional but recommended
// ✔ Default / static / private methods DO NOT count
// ✔ Object methods DO NOT count
// ✔ Signature must match exactly (equals(Object))
// ✔ Lambdas REQUIRE functional interfaces