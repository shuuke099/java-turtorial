Java Records — Full Student-Friendly Explanation

Your notes explain that Java records are used for data-focused classes, and they reduce repetitive code like constructors, accessors, equals(), hashCode(), and toString()

// A record is a special Java class used when your main goal is to store data.

1. Why Java Records Exist

Before records, Java programmers often wrote classes like this:

import java.util.Objects;

public class Student {
    private final int id;
    private final String name;
    private final double grade;

    public Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getGrade() {
        return grade;
    }
    @Override
    public String toString() {
        return "Student[id=" + id + ", name=" + name + ", grade=" + grade + "]";
    }
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        return id == other.id
                && Double.compare(grade, other.grade) == 0
                && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade);
    }
}

That is a lot of code just to store:
// id
// name
// grade

// So Java introduced records.
The same idea can be written like this:
public record Student(int id, String name, double grade) {}

That one line gives you:
// constructor
// accessor methods
// toString()
// equals()
// hashCode()
private final fields
2. Basic Record Syntax
public record Student(int id, String name, double grade) {}

/
(int id, String name, double grade) // this part is called the record header.

The variables inside the header are called record components.

So here:
// int id
// String name
// double grade
are the record components.

3. What Java Automatically Creates

When you write this:

public record Student(int id, String name, double grade) {}

Java secretly creates something similar to this:

import java.util.Objects;

public class Student {
    private final int id;
    private final String name;
    private final double grade;

    public Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Student[id=" + id + ", name=" + name + ", grade=" + grade + "]";
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Student other = (Student) obj;

        return id == other.id
                && Double.compare(grade, other.grade) == 0
                && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade);
    }
}

// Important: records do not create getters like this:
getId()
getName()
getGrade()

Instead, they create accessors with the same name as the field:
// id()
// name()
// grade()


4. Creating a Record Object
public record Student(int id, String name, double grade) {}

public class Main {
    public static void main(String[] args) {
        Student s = new Student(101, "Ali", 95.5);

        System.out.println(s.id());
        System.out.println(s.name());
        System.out.println(s.grade());
    }
}

Output:
// 101
// Ali
// 95.5


5. Record Accessors

// For a normal class, you may write:
student.getName();

// But for records, you write:
student.name();

// Example:
Student s = new Student(1, "Mahamud", 90.0);
System.out.println(s.name());// Output:Mahamud

So remember:
// record accessor = component name + parentheses


4. Creating a Record Object
public record Student(int id, String name, double grade) {}

public class Main {
    public static void main(String[] args) {
        Student s = new Student(101, "Ali", 95.5);

        System.out.println(s.id());
        System.out.println(s.name());
        System.out.println(s.grade());
    }
}

Output:
// 101
// Ali
// 95.5


5. Record Accessors

// For a normal class, you may write:
student.getName();

// But for records, you write:
student.name();

// Example:
Student s = new Student(1, "Mahamud", 90.0);
System.out.println(s.name());// Output:Mahamud

So remember:
// record accessor = component name + parentheses


6. Records Are Immutable
// Records are mostly used for immutable data.

Immutable means:
// After the object is created, its values cannot be changed.

Example:

public record Student(int id, String name) {}

This works: Student s = new Student(1, "Ali");

// This does not work:
s.id = 2;        // DOES NOT COMPILE
s.name = "Omar"; // DOES NOT COMPILE

Why?

// Because record fields are automatically:

private final
// private = outside code cannot directly access them
// final = they cannot be changed after construction



7. Records Do Not Have Setters Automatically
// Records create accessor methods, but they do not create setter methods.

This works:
s.name();

This does not work:
s.setName("Omar"); // DOES NOT COMPILE

// Because records are designed to be immutable.

8. How to “Change” a Record
// You do not really change a record.
// Instead, you create a new object.

Student s1 = new Student(1, "Ali", 90.0);
Student s2 = new Student(s1.id(), s1.name(), 95.0);

Now:
s1.grade() is 90.0
s2.grade() is 95.0

// You did not modify s1.
// You created a new Student.

9. Records Automatically Have toString()
// Example:
public record Student(int id, String name) {}
public class Main {
    public static void main(String[] args) {
        Student s = new Student(1, "Ali");
        System.out.println(s);
    }
}

// Output:
Student[id=1, name=Ali]
// You do not need to write toString() manually.

10. Records Automatically Have equals()
Example:

public record Student(int id, String name) {}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student(1, "Ali");
        Student s2 = new Student(1, "Ali");

        System.out.println(s1.equals(s2));
    }
}

Output:true
Why?
// Because records compare by values, not by memory location.

Both records have:
id = 1
name = Ali
// So they are equal.

11. Records Automatically Have hashCode()

Records also automatically create hashCode().
// This is useful when you use records in collections like:
HashSet
HashMap

Example:

Set<Student> students = new HashSet<>();
students.add(new Student(1, "Ali"));
students.add(new Student(1, "Ali"));

System.out.println(students.size());
// Output:1

// Because both records are equal, the HashSet stores only one.

12. Records Are Final
// Records are automatically final.
// That means you cannot extend a record.

public record Student(int id, String name) {}
class CollegeStudent extends Student {
}
This does not compile.
Why?
// Because records cannot be parent classes.
// A record is already final.


13. Records Can Implement Interfaces
// Records cannot extend classes, but they can implement interfaces.

Example:

interface Printable {
    void print();
}

public record Student(int id, String name) implements Printable {
    public void print() {
        System.out.println(id + " " + name);
    }
}

This works.

// Records can implement interfaces like normal classes.

14. Record Constructor Types

// This is very important.

Records can have:

// Constructor Type	Meaning
// Canonical constructor	The main constructor with all components
// Compact constructor	Short form of the canonical constructor
// Overloaded constructor	Extra constructor with different parameters

Let’s explain each one clearly.

15. Canonical Constructor
// The canonical constructor is the main constructor of the record.

For this record:
public record Student(int id, String name, double grade) {}

// The canonical constructor is:
public Student(int id, String name, double grade)

// You can write it yourself:
public record Student(int id, String name, double grade) {

    public Student(int id, String name, double grade) {
        if (grade < 0) {
            throw new IllegalArgumentException("Grade cannot be negative");
        }

        this.id = id;
        this.name = name;
        this.grade = grade;
    }
}

This is a normal full constructor.
// Notice that you must assign every field:
this.id = id;
this.name = name;
this.grade = grade;

16. Compact Constructor
// A compact constructor is a shorter constructor.
// You do not write the parameter list.

Example:

public record Student(int id, String name, double grade) {

    public Student {
        if (grade < 0) {
            throw new IllegalArgumentException("Grade cannot be negative");
        }
    }
}

This is the compact constructor:

public Student {
}

Notice:
// No parentheses
// No parameter list
// No this.id = id
// No this.name = name
// No this.grade = grade

Java automatically assigns the fields after the compact constructor runs.

So this:
public Student {
    if (grade < 0) {
        throw new IllegalArgumentException();
    }
}

// is like saying:
public Student(int id, String name, double grade) {
    if (grade < 0) {
        throw new IllegalArgumentException();
    }

    this.id = id;
    this.name = name;
    this.grade = grade;
}

// But Java writes the assignments for you.

17. Compact Constructor Rule

// Inside a compact constructor, you should validate or change the parameters.

// an empty compact constructor is allowed.

Example:

public record Student(int id, String name) {

    public Student {
        // empty compact constructor
    }
}//This compiles.

Java still automatically does this after the compact constructor body:
this.id = id;
this.name = name;

So this:
public Student {
}

is legal, but it is usually useless because you are not validating or changing anything.

Better use:

Example:

public record Student(int id, String name) {

    public Student {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name required");
        }

        name = name.trim();
    }
}

This is allowed.

The parameter name is changed before it is stored into the field.

So:

new Student(1, " Ali ");

will store:
"Ali"


18. Full Constructor vs Compact Constructor
Full canonical constructor
public record Student(int id, String name) {

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

You write everything yourself.

Compact constructor
public record Student(int id, String name) {

    public Student {
        if (name == null) {
            throw new IllegalArgumentException();
        }
    }
}
// Java assigns the fields automatically.

19. Big Difference: Full Constructor Must Assign Fields

// This works:
public record Student(int id, String name) {

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

// This does not work:
public record Student(int id, String name) {

    public Student(int id, String name) {
        System.out.println("Creating student");
    }
}

Why?

// Because in a full canonical constructor, you must assign all fields.
You forgot:
this.id = id;
this.name = name;



20. In Compact Constructor, You Cannot Assign Fields Directly

// This does not compile:
public record Student(int id, String name) {

    public Student {
        this.id = id;       // DOES NOT COMPILE
        this.name = name;   // DOES NOT COMPILE
    }
}

Why?

// Because compact constructors automatically assign fields.
// You should not assign them yourself.

// Correct:
public record Student(int id, String name) {

    public Student {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
    }
}


21. Overloaded Constructors in Records

// A record can have more than one constructor.
// But every overloaded constructor must call the main constructor using this(...).

Example:

public record Student(int id, String name, double grade) {

    public Student(int id, String name) {
        this(id, name, 0.0);
    }
}

This means:
If grade is not given, use 0.0

Usage:

Student s1 = new Student(1, "Ali", 95.5);
Student s2 = new Student(2, "Omar");

For s2, Java uses:

grade = 0.0
22. Important Overloaded Constructor Rule

This is correct:
public Student(int id, String name) {
    this(id, name, 0.0);
}

This is wrong:
public Student(int id, String name) {
    System.out.println("Student created");
}

Why?
// Because an overloaded constructor in a record must call:

this(...)

And it must be the first statement.

Correct:
public Student(int id, String name) {
    this(id, name, 0.0);
    System.out.println("Student created");
}
// But wait: in Java, this(...) must be first, so this is okay because it is first.

Wrong:
public Student(int id, String name) {
    System.out.println("Student created");
    this(id, name, 0.0); // DOES NOT COMPILE
}

23. Can Overloaded Constructors Add Extra Fields?
This is one of your earlier questions.

// Suppose your record is:
public record ProductListing(int id, String name, double price, double discount) {}

// Can you make this constructor?
public ProductListing(int id, String name, double price, double discount, boolean ordered) {
    this(id, name, price, discount);
}

// Yes, you can have an extra constructor parameter:
// boolean ordered But you cannot store it unless it is in the record header.
// So this is allowed:

public record ProductListing(int id, String name, double price, double discount) {

    public ProductListing(int id, String name, double price, double discount, boolean ordered) {
        this(id, name, price, discount);
        System.out.println("Ordered? " + ordered);
    }
}

ProductListing p = new ProductListing(1, "Laptop", 1200, 100, true);
// But ordered is not part of the object’s stored data.

// This will not work:
public record ProductListing(int id, String name, double price, double discount) {

    private final boolean ordered; // DOES NOT COMPILE
}
// Records cannot add extra instance fields.

If you want to store ordered, it must be in the record header:

public record ProductListing(
    int id,
    String name,
    double price,
    double discount,
    boolean ordered
) {}


24. Records Cannot Declare Extra Instance Fields
This does not compile:
public record Student(int id, String name) {
    private int age;
}

Why?

// Because records can only store the components listed in the record header.
// The record header is the complete data list.

Correct:

public record Student(int id, String name, int age) {}

25. Records Can Have Static Fields
This is allowed:
public record Student(int id, String name) {
    public static int count = 0;
}

Static fields belong to the class, not to each object.
So this is okay.

Example:

public record Student(int id, String name) {
    public static int count = 0;

    public Student {
        count++;
    }
}

Usage:
new Student(1, "Ali");
new Student(2, "Omar");

System.out.println(Student.count);
Output:2


26. Records Can Have Static Methods
This is allowed:

public record Student(int id, String name) {

    public static Student unknown() {
        return new Student(0, "Unknown");
    }
}

Usage:

Student s = Student.unknown();
System.out.println(s);

Output:

Student[id=0, name=Unknown]

// Can you make new Student() work?  // Miiine

Yes, but only by creating an overloaded no-argument constructor:
public record Student(int id, String name) {

    public Student() {
        this(0, "Unknown");
    }
}

Now this works:
Student s = new Student();

System.out.println(s);
// Output:Student[id=0, name=Unknown]


27. Records Can Have Instance Methods

Records can also have normal methods.

Example:

public record Student(int id, String name, double grade) {

    public boolean passed() {
        return grade >= 60;
    }
}

Usage:

Student s = new Student(1, "Ali", 75);
System.out.println(s.passed());
Output:true


28. Records Cannot Have Instance Initializers

This does not compile:
public record Student(int id, String name) {
    {
        System.out.println("Creating student");
    }
}
Records do not allow instance initializer blocks.


Use a constructor instead:
public record Student(int id, String name) {

    public Student {
        System.out.println("Creating student");
    }
}
29. Records Can Override Accessor Methods

A record automatically creates this:

public String name()

But you can override it.

Example:

public record Student(String name) {
    @Override
    public String name() {
        return name.toUpperCase();
    }
}

Usage:
Student s = new Student("ali");
System.out.println(s.name());
Output:ALI

Be careful: this changes what users see when they call the accessor.

30. Records Can Override toString()

Example:

public record Student(int id, String name) {

    @Override
    public String toString() {
        return "Student name is " + name;
    }
}

// Usage:
System.out.println(new Student(1, "Ali"));
// Output:
// Student name is Ali


31. Records Can Override equals() and hashCode()

// Records automatically provide good equals() and hashCode() methods.
// Usually, you should not override them unless you have a special reason.

Example:

public record Student(int id, String name) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student other)) {
            return false;
        }

        return this.id == other.id;
    }
}

// Now students are equal if they have the same id, even if names are different.

// But be careful: if you override equals(), you should also override hashCode().

32. Records and Null Values

Records do not automatically prevent null.
Example:

public record Student(String name) {}

// This compiles:
Student s = new Student(null);
// If you do not want null, you must check it.

public record Student(String name) {

    public Student {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
    }
}

Or:

import java.util.Objects;

public record Student(String name) {
    public Student {
        Objects.requireNonNull(name);
    }
}
33. Record Validation Example
public record BankAccount(String owner, double balance) {

    public BankAccount {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner is required");
        }

        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }
}

Usage:

BankAccount a = new BankAccount("Ali", 500);

Valid.

BankAccount b = new BankAccount("Ali", -50);
// Throws exception.

34. Record Transformation Example
public record Student(String name) {

    public Student {
        name = name.trim();
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}

Usage:
Student s = new Student("  aLI  ");
System.out.println(s.name());

Output:

Ali
The parameter is cleaned before Java stores it.

35. Records and Mutable Objects

Important exam trick.

Records are shallowly immutable, not deeply immutable.
Example:

import java.util.List;

public record Classroom(List<String> students) {}

// This record field is final, meaning you cannot replace the list:

classroom.students = anotherList; // DOES NOT COMPILE

But the list itself can still be changed:

List<String> names = new ArrayList<>();
names.add("Ali");

Classroom c = new Classroom(names);

c.students().add("Omar");

System.out.println(c.students());

// Output:[Ali, Omar]

// So the record is immutable, but the object inside it may still be mutable.

To protect it, make a defensive copy:

import java.util.List;

public record Classroom(List<String> students) {

    public Classroom {
        students = List.copyOf(students);
    }
}

// Now this will fail:
c.students().add("Omar");
// Because List.copyOf() returns an unmodifiable list.

36. Records and Arrays
// Arrays are mutable too.
public record Scores(int[] values) {}

// This is dangerous:
int[] nums = {90, 80}
Scores s = new Scores(nums);
nums[0] = 10;

System.out.println(s.values()[0]);
// Output:10

// Even though records are immutable, the array content changed.
Better:
public record Scores(int[] values) {

    public Scores {
        values = values.clone();
    }

    @Override
    public int[] values() {
        return values.clone();
    }
// }This protects the internal array.

37. Records and Pattern Matching
Modern Java allows records to be used with pattern matching.

Example:

public record Student(int id, String name) {}
// You can match and extract values:

Object obj = new Student(1, "Ali");

if (obj instanceof Student(int id, String name)) {
    System.out.println(name);
}

Output:Ali

This means:
// If obj is a Student record,
// take out id and name.

38. Record Pattern with var

// You can also use var:
if (obj instanceof Student(var id, var name)) {
    System.out.println(id);
    System.out.println(name);
}

Java figures out the types:

// id is int
// name is String

39. Pattern Matching Rule: Order Matters

// For this record:
public record Student(int id, String name) {}

// This is correct:
if (obj instanceof Student(int id, String name)) {}

// This is wrong:
if (obj instanceof Student(String name, int id)) {}

Why?
// Because the record header order is:
// int id first
// String name second
// The pattern must follow the same order.

40. Pattern Variable Names Do Not Need to Match

For this record:

public record Student(int id, String name) {}

This is okay:
if (obj instanceof Student(int x, String y)) {
    System.out.println(x);
    System.out.println(y);
}

The variable names do not need to be:

// id
// name
// They can be:
// x
// y

// But the types and order must match.

41. Nested Record Patterns

A record can contain another record.

public record Address(String city, String state) {}

public record Student(String name, Address address) {}

Create object:

Student s = new Student("Ali", new Address("Minneapolis", "MN"));

Pattern matching:

Object obj = s;

if (obj instanceof Student(String name, Address(String city, String state))) {
    System.out.println(name);
    System.out.println(city);
    System.out.println(state);
}

Output:

Ali
Minneapolis
MN

This extracts data from both:

Student
Address


42. Records with switch

Records can also be used in switch pattern matching.

Example:

record Student(String name, int grade) {}
record Teacher(String name, String subject) {}

static void printInfo(Object obj) {
    switch (obj) {
        case Student(String name, int grade) ->
            System.out.println(name + " has grade " + grade);

        case Teacher(String name, String subject) ->
            System.out.println(name + " teaches " + subject);

        default ->
            System.out.println("Unknown person");
    }
}
43. Records vs Normal Classes
Feature	Record	Normal Class
Good for data?	Yes	Yes
Less code?	Yes	No
Fields automatically private final?	Yes	No
Accessors automatic?	Yes	No
Setters automatic?	No	No
Can extend another class?	No	Yes
Can be extended?	No	Yes, unless final
Can implement interfaces?	Yes	Yes
Can add extra instance fields?	No	Yes
Can have methods?	Yes	Yes
Can have constructors?	Yes	Yes
44. POJO vs JavaBean vs Record
POJO

A simple Java object.

public class Student {
    private String name;
}
JavaBean

A class that usually has:

private fields
public no-argument constructor
getters
setters

Example:

public class StudentBean {
    private String name;

    public StudentBean() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
Record

A compact immutable data carrier.

public record Student(String name) {}
45. Record Header Is Everything

This is important.

public record Product(int id, String name, double price) {}

The record stores exactly:

id
name
price

Nothing else.

You cannot later add:

private int quantity;

Wrong:

public record Product(int id, String name, double price) {
    private int quantity; // DOES NOT COMPILE
}

Correct:

public record Product(int id, String name, double price, int quantity) {}
46. Example: Marketplace Product Record
public record ProductListing(
    int id,
    String name,
    double price,
    boolean ordered
) {
    public ProductListing {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name required");
        }

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}

Usage:

ProductListing p = new ProductListing(1, "Laptop", 750.00, false);

System.out.println(p.name());
System.out.println(p.price());
System.out.println(p.ordered());
47. Example: Overloaded Marketplace Constructor
public record ProductListing(
    int id,
    String name,
    double price,
    boolean ordered
) {
    public ProductListing(int id, String name, double price) {
        this(id, name, price, false);
    }
}

Usage:

ProductListing p1 = new ProductListing(1, "Laptop", 750.00, true);
ProductListing p2 = new ProductListing(2, "Phone", 400.00);

For p2, Java uses:

ordered = false
48. Common Exam Tricks
Trick 1: Access fields directly
Student s = new Student(1, "Ali");

System.out.println(s.name); // DOES NOT COMPILE

Correct:

System.out.println(s.name());
Trick 2: Use getter name
System.out.println(s.getName()); // DOES NOT COMPILE

Correct:

System.out.println(s.name());
Trick 3: Try to change field
s.name = "Omar"; // DOES NOT COMPILE

Records are immutable.

Trick 4: Add instance field
public record Student(int id, String name) {
    private int age; // DOES NOT COMPILE
}

Extra instance fields are not allowed.

Trick 5: Add static field
public record Student(int id, String name) {
    static int count; // OK
}

Static fields are allowed.

Trick 6: Extend record
class CollegeStudent extends Student {} // DOES NOT COMPILE

Records are final.

Trick 7: Overloaded constructor does not call this(...)
public record Student(int id, String name) {

    public Student(String name) {
        System.out.println(name);
    }
}

Does not compile.

Correct:

public record Student(int id, String name) {

    public Student(String name) {
        this(0, name);
    }
}
Trick 8: Compact constructor tries to assign fields
public record Student(int id, String name) {

    public Student {
        this.id = id; // DOES NOT COMPILE
    }
}

Wrong.

Correct:

public record Student(int id, String name) {

    public Student {
        if (id < 0) {
            throw new IllegalArgumentException();
        }
    }
}
49. Full Student Example
public record Student(int id, String name, double grade) {

    public Student {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be positive");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }

        name = name.trim();
    }

    public Student(int id, String name) {
        this(id, name, 0.0);
    }

    public boolean passed() {
        return grade >= 60;
    }
}

Usage:

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student(1, " Ali ", 85);
        Student s2 = new Student(2, "Omar");

        System.out.println(s1);
        System.out.println(s1.name());
        System.out.println(s1.passed());

        System.out.println(s2);
    }
}

Output:

Student[id=1, name=Ali, grade=85.0]
Ali
true
Student[id=2, name=Omar, grade=0.0]
50. Simple Memory Rule

Think of a record like this:

A record is a small final class for storing fixed data. Java writes the boring code for you.

Or even simpler:

record = data class + constructor + accessors + toString + equals + hashCode
51. What You Must Remember for the Exam
Records automatically provide:
private final fields
constructor
accessor methods
toString()
equals()
hashCode()
Records do not provide:
setters
getSomething() methods
extra instance fields
inheritance
instance initializer blocks
Records allow:
constructors
compact constructors
overloaded constructors
methods
static fields
static methods
interfaces
nested records
pattern matching
Records are:
final
immutable by default
data-focused
concise
52. Final Simple Example

Normal class:

public class Student {
    private final int id;
    private final String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }
}

Record:

public record Student(int id, String name) {}

Both can store student data.

But the record is much shorter.

That is the main purpose of records.