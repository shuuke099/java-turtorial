// Yes. Here is the full sealed classes explanation again, including packages, modules, omitted permits, nested classes, sealed interfaces, pattern matching, and exam traps. Your uploaded notes say sealed classes restrict which subclasses can inherit, use sealed, permits, and non-sealed, require direct subclasses to be final, sealed, or non-sealed, include package/module rules, and work with sealed interfaces and pattern matching.

Java Sealed Classes — Full Explanation
1. What Is a Sealed Class?

A sealed class is a class that controls which classes are allowed to extend it.

// Normal class:
class Animal {}
class Dog extends Animal {}
class Cat extends Animal {}
class Bird extends Animal {}
Any class can extend Animal.

// Sealed class:
public sealed class Animal permits Dog, Cat {
}

// This means:
Only Dog and Cat are allowed to extend Animal.
Bird is not allowed.

2. Basic Example
public sealed class Animal permits Dog, Cat {
}

final class Dog extends Animal {
}

final class Cat extends Animal {
}
// This compiles.

// But this does not:
final class Bird extends Animal {
}

Why?

// Because Bird is not listed in:

permits Dog, Cat


3. Three Important Keywords
// Keyword	Meaning
sealed	The class/interface restricts inheritance
permits	Lists allowed direct subclasses
non-sealed	Opens inheritance again

Example:

public sealed class Vehicle permits Car, Truck {
}

final class Car extends Vehicle {
}

non-sealed class Truck extends Vehicle {
}

Meaning:
// Car cannot be extended.
// Truck can be extended by other classes.


4. Correct Syntax

// Correct:
public sealed class Shape permits Circle, Square {
}

// Wrong:
public class sealed Shape permits Circle, Square {
}

sealed must come before class.

// Correct order:
public sealed class

Not:
// public class sealed


5. Direct Subclass Rule
// Every class that directly extends a sealed class must use exactly one of these:

// final
// sealed
// non-sealed

Correct:
public sealed class Animal permits Dog, Cat, WildAnimal {
}

final class Dog extends Animal {
}

sealed class Cat extends Animal permits PersianCat {
}

non-sealed class WildAnimal extends Animal {
}

final class PersianCat extends Cat {
}

// Wrong:
public sealed class Animal permits Dog {
}

class Dog extends Animal {
}

This does not compile because Dog is missing:
// final, sealed, or non-sealed


6. final Subclass

A final subclass stops inheritance.

public sealed class Vehicle permits Car {
}

final class Car extends Vehicle {
}

// This is not allowed:
class SportsCar extends Car {
}

Why?
Because Car is final.


// 7. sealed Subclass
A sealed subclass continues the restriction.

public sealed class Vehicle permits Car {
}

sealed class Car extends Vehicle permits ElectricCar, GasCar {
}

final class ElectricCar extends Car {
}

final class GasCar extends Car {
}

Meaning:

Vehicle only permits Car.
Car only permits ElectricCar and GasCar.


8. non-sealed Subclass

A non-sealed subclass opens inheritance again.

public sealed class Vehicle permits Truck {
}

non-sealed class Truck extends Vehicle {
}

class DeliveryTruck extends Truck {
}

class FireTruck extends Truck {
}

This compiles.

Meaning:

Vehicle controls its direct child Truck.
But Truck allows more subclasses.


// 9. Abstract Sealed Classes

// A sealed class can also be abstract.
public abstract sealed class Animal permits Dog, Cat {
}

final class Dog extends Animal {
}

final class Cat extends Animal {
}

// This does not compile:
Animal a = new Animal();

// Why?
Because Animal is abstract.

// But this works:
Animal a = new Dog();

// Important:
sealed does not automatically mean abstract.
abstract is optional.

// This is also valid:
public sealed class Animal permits Dog {
}
final class Dog extends Animal {
}


10. Package Rule

// This is very important.
// In normal Java code, a sealed class and its direct permitted subclasses must be in the same package.

// Correct: Same Package

Animal.java
// package zoo;
public sealed class Animal permits Dog {
}

Dog.java
// package zoo;
public final class Dog extends Animal {
}

// This compiles because both are in:package zoo;


Wrong: Different Packages Without Modules
Animal.java
// package zoo;
public sealed class Animal permits Dog {
}
Dog.java
// package farm;
public final class Dog extends Animal {
}
// This does not compile.

Why?

Because:

// Animal is in package zoo.
// Dog is in package farm.
// Without named modules, direct permitted subclasses must be in the same package.

// A subpackage is NOT the same package
Example
Animal.java
package zoo;

public sealed class Animal permits Camel {
}
Camel.java
package zoo.camel;

public final class Camel extends Animal {
}

Without modules, this does not compile.

Why?
// Because:
// Animal is in package zoo
// Camel is in package zoo.camel

11. Named Module Exception
// With named modules, permitted subclasses can be in different packages if they are in the same module.

module-info.java
module zoo.module {
}
Animal.java
// package zoo;
public sealed class Animal permits farm.Dog {
}

Dog.java
// package farm;
public final class Dog extends zoo.Animal {
}

// This can compile because both packages are inside the same named module.

Important:
// No named module = same package required.
// Named module = same module required.


12. Direct Subclasses Only
// The package/module rule applies to direct permitted subclasses.

Example:
// package zoo;
public sealed class Animal permits Mammal {
}
package zoo;
public non-sealed class Mammal extends Animal {
}

Now this can be in another package:
// package farm;
public class Dog extends Mammal {
}

Why?
// Because Dog does not directly extend Animal.
// It extends Mammal.
// And Mammal is non-sealed.

13. Every Permitted Class Must Actually Extend the Sealed Class
// Wrong:
public sealed class Animal permits Dog {
}

final class Dog {
}
// This does not compile.

Why?

// Because Dog is listed in permits, but it does not extend Animal.

Correct:

public sealed class Animal permits Dog {
}
final class Dog extends Animal {
}


14. Can You Omit permits?
Yes, sometimes.
You can omit permits if the sealed class and its direct subclasses are in the same source file.
// Example:
sealed class Animal {
}
final class Dog extends Animal {
}
final class Cat extends Animal {
}
// Java can figure out the permitted subclasses because they are in the same file.



15. Separate Files Need permits
// If the subclasses are in separate files, use permits.

Animal.java
public sealed class Animal permits Dog, Cat {
}
Dog.java
public final class Dog extends Animal {
}
Cat.java
public final class Cat extends Animal {
}

// This is the normal clear style.

16. Nested Subclasses
// Subclasses can be declared inside the sealed class.

public sealed class Animal {

    public static final class Dog extends Animal {
    }

    public static final class Cat extends Animal {
    }
}

// Here, permits can be omitted.
// Java can see the nested subclasses.
// You could also write it explicitly:

public sealed class Animal permits Animal.Dog, Animal.Cat {

    public static final class Dog extends Animal {
    }

    public static final class Cat extends Animal {
    }
}


17. Sealed Interfaces
// Interfaces can also be sealed.

public sealed interface Payment permits CashPayment, CardPayment {
}
final class CashPayment implements Payment {
}
final class CardPayment implements Payment {
}

Meaning:
// Only CashPayment and CardPayment can implement Payment.


18. Sealed Interface with Records
// Records can implement sealed interfaces.
public sealed interface Shape permits Circle, Rectangle {
}

public record Circle(double radius) implements Shape {
}

public record Rectangle(double length, double width) implements Shape {
}
// This is common in modern Java.
Why?
// Because:
// sealed interface controls the allowed types
// records store the data for each type


19. Important Interface Rule
// Interfaces cannot be final.

// Wrong:
final interface Payment {
}

So if an interface extends a sealed interface, it can be:
sealed
or non-sealed

Example:

sealed interface Payment permits OnlinePayment {
}

non-sealed interface OnlinePayment extends Payment {
}This is allowed.

// Also allowed:
sealed interface Payment permits OnlinePayment {
}

sealed interface OnlinePayment extends Payment permits PayPalPayment {
}

final class PayPalPayment implements OnlinePayment {
}
20. Pattern Matching with Sealed Classes

Sealed classes work well with switch.
Because Java knows all permitted subclasses, it can check whether all cases are handled.

// Example:
sealed interface Payment permits CashPayment, CardPayment {
}
record CashPayment(double amount) implements Payment {
}
record CardPayment(double amount, String cardNumber) implements Payment {
}

// Now:
static void process(Payment payment) {
    switch (payment) {
        case CashPayment c ->
            System.out.println("Cash: " + c.amount());
        case CardPayment c ->
            System.out.println("Card: " + c.amount());
    }
}

No default is needed because Java knows Payment can only be:
// CashPayment
// CardPayment

// 21. Sealed Class vs Enum
They are similar because both can represent a fixed set of possibilities.
Enum
enum Size {
    SMALL, MEDIUM, LARGE
}
Sealed Class
sealed class Size permits Small, Medium, Large {
}

final class Small extends Size {
}

final class Medium extends Size {
}

final class Large extends Size {
}

Comparison:

Enum	Sealed Class
Fixed constants	Fixed subclasses
Good for simple values	Good for type families
All values same enum type	Each subtype can have different fields
Simple and compact	More flexible
22. Marketplace Example
public sealed interface Payment
        permits CashPayment, CardPayment, OnlinePayment {
}

public record CashPayment(double amount) implements Payment {
}

public record CardPayment(double amount, String cardNumber) implements Payment {
}

public record OnlinePayment(double amount, String email) implements Payment {
}

Usage:

public class Main {
    public static void main(String[] args) {
        Payment p = new CardPayment(100.0, "1234");

        System.out.println(p);
    }
}

Output:

CardPayment[amount=100.0, cardNumber=1234]
23. Marketplace Switch Example
public static void process(Payment payment) {
    switch (payment) {
        case CashPayment c ->
            System.out.println("Cash payment: " + c.amount());

        case CardPayment c ->
            System.out.println("Card payment: " + c.amount());

        case OnlinePayment o ->
            System.out.println("Online payment: " + o.amount());
    }
}

Because all permitted payment types are handled, a default branch is not required.

24. Full Vehicle Example
public sealed class Vehicle permits Car, Truck, Motorcycle {
}

public final class Car extends Vehicle {
}

public non-sealed class Truck extends Vehicle {
}

public sealed class Motorcycle extends Vehicle permits SportBike {
}

public final class SportBike extends Motorcycle {
}

Meaning:

Vehicle allows only Car, Truck, and Motorcycle directly.
Car stops inheritance.
Truck opens inheritance.
Motorcycle continues sealing.
SportBike stops inheritance.

This is allowed:

class DeliveryTruck extends Truck {
}

This is not allowed:

class SportsCar extends Car {
}

This is not allowed:

class Scooter extends Vehicle {
}

Why?

Scooter is not in Vehicle's permits list.
25. Common OCP Exam Traps
Trap 1: Missing subclass modifier

Wrong:

sealed class Animal permits Dog {
}

class Dog extends Animal {
}

Correct:

sealed class Animal permits Dog {
}

final class Dog extends Animal {
}
Trap 2: Class not in permits list

Wrong:

sealed class Animal permits Dog {
}

final class Cat extends Animal {
}

Cat is not permitted.

Trap 3: Permitted class does not extend parent

Wrong:

sealed class Animal permits Dog {
}

final class Dog {
}

Dog must extend Animal.

Trap 4: Wrong keyword order

Wrong:

public class sealed Animal permits Dog {
}

Correct:

public sealed class Animal permits Dog {
}
Trap 5: Direct subclass missing final, sealed, or non-sealed

Wrong:

sealed class Vehicle permits Car {
}

class Car extends Vehicle {
}

Correct:

sealed class Vehicle permits Car {
}

final class Car extends Vehicle {
}
Trap 6: non-sealed used incorrectly

Wrong:

non-sealed class Car {
}

A class can only be non-sealed when it directly extends a sealed class or implements a sealed interface.

Correct:

sealed class Vehicle permits Car {
}

non-sealed class Car extends Vehicle {
}
Trap 7: Interface marked final

Wrong:

final interface Payment {
}

Interfaces cannot be final.

Trap 8: Different package without module

Wrong:

package zoo;

public sealed class Animal permits farm.Dog {
}
package farm;

public final class Dog extends zoo.Animal {
}

Without a named module, this does not compile.

Trap 9: Same file can omit permits

This can compile:

sealed class Animal {
}

final class Dog extends Animal {
}

Because they are in the same source file.

But in separate files, use:

sealed class Animal permits Dog {
}
26. Final Memory Rules

Remember:

sealed = restrict inheritance
permits = list direct allowed subclasses
non-sealed = reopen inheritance

Every direct subclass must be:

final
sealed
non-sealed

Package/module rule:

No named module = same package
Named module = same module

Permitted subclass rule:

If a class is listed in permits, it must actually extend the sealed class.

Omitting permits:

Allowed when direct subclasses are in the same source file
or nested inside the sealed class.

Sealed interfaces:

Interfaces can be sealed.
Interfaces cannot be final.

Pattern matching:

Sealed classes help switch know all possible subclasses.
27. Simplest Example to Memorize
sealed class Animal permits Dog, Cat {
}

final class Dog extends Animal {
}

final class Cat extends Animal {
}

Meaning:

Only Dog and Cat can extend Animal.
Dog cannot be extended.
Cat cannot be extended.

That is the core idea of sealed classes.