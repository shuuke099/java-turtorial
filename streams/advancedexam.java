
// ===========================================
  joining() — Build a dashboard message
// ===========================================

MOCK EXAM — Streams + Collectors (Tricky)
// ❓ Question 1
var stream = Stream.of(1, 2, 3);
String result = stream
        .map(x -> x + 1)
        .peek(System.out::print)
        .collect(Collectors.joining(","));

System.out.println(result);


// What happens?
A. 234 then 2,3,4
B. Compilation error
C. Runtime exception
D. 2,3,4 only
E. 234,

✅ Answer: B — Compilation error
💥 WHY
// .map(x -> x + 1)  // Stream<Integer>
// Then: Collectors.joining(",")

// 👉 ❌ requires Stream<CharSequence>
👉 You forgot .map(String::valueOf)




// ❓ Question 2
IntStream stream = IntStream.of(1, 2, 3);

var result = stream
        .map(x -> x * 2)
        .mapToObj(x -> x)
        .collect(Collectors.joining(","));

System.out.println(result);

// What happens?
A. 2,4,6
B. Compilation error
C. Runtime exception
D. 246

✅ Answer: B — Compilation error
💥 WHY
// .mapToObj(x -> x)

// 👉 produces:Stream<Integer>
// NOT String → ❌ joining() fails

// ✔ FIX
.mapToObj(x -> String.valueOf(x))


// ❓ Question 3 (VERY TRICKY)
var stream = Stream.of("a", "b", "c");
String result = stream
        .peek(x -> x.toUpperCase())
        .collect(Collectors.joining());

System.out.println(result);


// What is output?
A. ABC
B. abc
C. Compilation error
D. Runtime exception

✅ Answer: B — abc
💥 WHY (VERY IMPORTANT)
.peek(x -> x.toUpperCase())

// 👉 Strings are immutable
// 👉 toUpperCase() returns NEW String
// 👉 But you did NOT assign it

// So stream still has:a b c

// ✔ FIX
.map(String::toUpperCase)


// ❓ Question 4 (Stream reuse trap)

var stream = Stream.of("x", "y");
stream.forEach(System.out::print);

String result = stream.collect(Collectors.joining());

System.out.println(result);


What happens?
A. xyxy
B. xy
C. Compilation error
D. Runtime exception

✅ Answer: D — Runtime exception
💥 WHY
// stream.forEach(...)
// 👉 terminal operation → stream is CLOSED

// Then:stream.collect(...)
// 👉 ❌ IllegalStateException



// ❓ Question 5 (boxed vs mapToObj trap)
var result = IntStream.of(1,2,3)
        .boxed()
        .map(x -> x + "")
        .collect(Collectors.joining("-"));

System.out.println(result);


// What is output?
A. 1-2-3
B. Compilation error
C. 123
D. Runtime exception

✅ Answer: A — 1-2-3
💥 WHY
.map(x -> x + "")

👉 forces conversion to String

So becomes:

Stream<String>
❓ Question 6 (grouping + joining — HARD)
var result = Stream.of(1,2,2,3)
    .collect(Collectors.groupingBy(
        x -> x % 2,
        Collectors.mapping(
            String::valueOf,
            Collectors.joining(",")
        )
    ));

System.out.println(result);
What is output?

A. {0=2,2, 1=1,3}
B. {0=2,2, 1=1,3} (order may vary)
C. Compilation error
D. {0=[2,2], 1=[1,3]}

✅ Answer: B
💥 WHY

Grouping:

Key	Values
0 (even)	2,2
1 (odd)	1,3

Then:

joining(",")

👉 converts each group into string

Final:

{0=2,2, 1=1,3}

(order not guaranteed)

❓ Question 7 (hidden trap)
var result = Stream.of(1,2,3)
    .map(Object::toString)
    .collect(Collectors.joining());

System.out.println(result);
What happens?

A. 123
B. Compilation error
C. Runtime exception
D. 1,2,3

✅ Answer: A — 123
💥 WHY
Object::toString

✔ works because Integer extends Object

❓ Question 8 (ULTRA TRAP)
var result = IntStream.of(1,2,3)
    .mapToObj(x -> x + "")
    .peek(x -> x.concat("!"))
    .collect(Collectors.joining());

System.out.println(result);
What is output?

A. 1!2!3!
B. 123
C. 1 2 3
D. Compilation error

✅ Answer: B — 123
💥 WHY (same trap as before)
.peek(x -> x.concat("!"))

👉 concat() returns NEW string
👉 result ignored

✔ FIX
.map(x -> x + "!")
🧠 FINAL MENTAL CHECKLIST (EXAM GOLD)

Before using joining() ask:

🔹 Is my stream Stream<String>?
🔹 Did I accidentally keep Integer?
🔹 Am I using peek() incorrectly?
🔹 Did I reuse the stream?
🔹 Am I on primitive stream?


//===========================================
//   averagingInt() — Average officer productivity
//===========================================

❓ Question 1 (Return Type Trap)
var officers = List.of(
    new Officer("A", 10, "ER"),
    new Officer("B", 20, "ER")
);

int avg = officers.stream()
    .collect(Collectors.averagingInt(Officer::reportsHandled));

System.out.println(avg);
What happens?

A. 15
B. 15.0
C. Compilation error
D. Runtime exception

✅ Answer: C — Compilation error

💥 WHY

Collectors.averagingInt(...)

👉 returns double

int avg = ...

👉 ❌ incompatible types

✔ FIX

double avg = officers.stream()
    .collect(Collectors.averagingInt(Officer::reportsHandled));
❓ Question 2 (Primitive Stream Trap)
var avg = IntStream.of(10, 20, 30)
    .collect(Collectors.averagingInt(x -> x));
What happens?

A. 20
B. 20.0
C. Compilation error
D. Runtime exception

✅ Answer: C — Compilation error

💥 WHY

IntStream.collect(...)

👉 different signature than Stream.collect(...)

👉 ❌ cannot use Collectors

✔ FIX

double avg = IntStream.of(10, 20, 30)
    .boxed()
    .collect(Collectors.averagingInt(x -> x));

OR

double avg = IntStream.of(10, 20, 30)
    .average()
    .getAsDouble();
❓ Question 3 (Empty Stream Trap)
double avg = Stream.<Integer>empty()
    .collect(Collectors.averagingInt(x -> x));

System.out.println(avg);
What happens?

A. 0
B. 0.0
C. NaN
D. Runtime exception

✅ Answer: B — 0.0

💥 WHY
👉 Empty stream → returns 0.0
NOT exception, NOT NaN

✔ FIX (if you want Optional behavior)

OptionalDouble avg = IntStream.empty().average();
❓ Question 4 (Lambda Return Type Trap)
var avg = Stream.of("a", "bb", "ccc")
    .collect(Collectors.averagingInt(s -> s.length() + ""));
What happens?

A. 2.0
B. 3.0
C. Compilation error
D. Runtime exception

✅ Answer: C — Compilation error

💥 WHY

s -> s.length() + ""

👉 returns String

But:

averagingInt(...)

👉 requires int

✔ FIX

.collect(Collectors.averagingInt(s -> s.length()))
❓ Question 5 (Grouping Type Trap)
var result = Stream.of(
    new Officer("A", 10, "ER"),
    new Officer("B", 20, "ER")
).collect(Collectors.groupingBy(
    Officer::dept,
    Collectors.averagingInt(Officer::reportsHandled)
));

System.out.println(result.get("ER") instanceof Integer);
What happens?

A. true
B. false
C. Compilation error
D. Runtime exception

✅ Answer: B — false

💥 WHY
👉 result type:

Map<String, Double>

👉 NOT Integer

✔ FIX (if integer needed — manual)

.collect(Collectors.groupingBy(
    Officer::dept,
    Collectors.summingInt(Officer::reportsHandled)
));
❓ Question 6 (Integer Division Trap)
double avg = List.of(1, 2)
    .stream()
    .collect(Collectors.averagingInt(x -> x));

System.out.println(avg);
What happens?

A. 1
B. 1.0
C. 1.5
D. Runtime exception

✅ Answer: C — 1.5

💥 WHY
👉 Uses:

(double) sum / count

👉 NOT integer division

✔ FIX (if you wanted integer)

int avg = (1 + 2) / 2; // manual integer division
❓ Question 7 (map vs average Trap)
var avg = Stream.of(
    new Officer("A", 10, "ER"),
    new Officer("B", 20, "ER")
)
.map(Officer::reportsHandled)
.average();
What happens?

A. 15.0
B. Compilation error
C. Runtime exception
D. OptionalDouble[15.0]

✅ Answer: B — Compilation error

💥 WHY

.map(...)

👉 produces:

Stream<Integer>
.average()

👉 exists ONLY on primitive streams

✔ FIX

.mapToInt(Officer::reportsHandled)
.average()
.getAsDouble();
❓ Question 8 (boxed Trap)
var avg = IntStream.of(10, 20, 30)
    .boxed()
    .collect(Collectors.averagingInt(x -> x));
What happens?

A. 20
B. 20.0
C. Compilation error
D. Runtime exception

✅ Answer: B — 20.0

💥 WHY

.boxed()

👉 converts to Stream<Integer> → valid

✔ FIX (better approach)

IntStream.of(10, 20, 30)
    .average()
    .getAsDouble();
❓ Question 9 (Record Access Trap)
var avg = Stream.of(
    new Officer("A", 10, "ER")
)
.collect(Collectors.averagingInt(o -> o.reportsHandled));
What happens?

A. 10
B. 10.0
C. Compilation error
D. Runtime exception

✅ Answer: C — Compilation error

💥 WHY

o.reportsHandled

👉 ❌ not valid for record

Must call method:

✔ FIX

.collect(Collectors.averagingInt(o -> o.reportsHandled()))
❓ Question 10 (Mixed Filter + Group Trap)
var result = Stream.of(
    new Officer("A", 10, "ER"),
    new Officer("B", 20, "ER"),
    new Officer("C", 30, "ICU"),
    new Officer("D", 40, "ICU")
)
.filter(o -> o.reportsHandled() >= 20)
.collect(Collectors.groupingBy(
    Officer::dept,
    Collectors.averagingInt(Officer::reportsHandled)
));

System.out.println(result);
What happens?

A. {ER=20.0, ICU=35.0}
B. {ER=15.0, ICU=35.0}
C. {ICU=35.0}
D. {ER=20.0, ICU=30.0}

✅ Answer: A — {ER=20.0, ICU=35.0}

💥 WHY

After filter:

Value	Included
10	❌
20	✅
30	✅
40	✅

👉 ER → [20] → 20.0
👉 ICU → [30,40] → 35.0

✔ FIX (if you wanted all values)

.remove the filter()




// ===========================================
    toList() vs Collectors.toList()
// ===========================================


❓ Question 1 (Mutation Trap)
var list = Stream.of(1, 2, 3).toList();
list.add(4);

System.out.println(list);
What happens?

A. [1, 2, 3, 4]
B. [1, 2, 3]
C. Compilation error
D. Runtime exception

✅ Answer: D — Runtime exception

💥 WHY

.toList()

👉 returns unmodifiable list

list.add(4);

👉 ❌ UnsupportedOperationException

✔ FIX

var list = Stream.of(1,2,3)
    .collect(Collectors.toList());



// ❓ Question 2 (Same code, different collector)
var list = Stream.of(1, 2, 3)
    .collect(Collectors.toList());

list.add(4);

System.out.println(list);
What happens?

A. [1,2,3]
B. [1,2,3,4]
C. Compilation error
D. Runtime exception

✅ Answer: B — [1,2,3,4]

💥 WHY
👉 Collectors.toList() returns mutable list (typically ArrayList)

✔ FIX (if immutability needed)

.collect(Collectors.toUnmodifiableList());
❓ Question 3 (Type Assumption Trap)
var list = Stream.of(1,2,3).toList();

if (list instanceof ArrayList) {
    System.out.println("ArrayList");
}
What happens?

A. Prints ArrayList
B. Prints nothing
C. Compilation error
D. Runtime exception

✅ Answer: B — Prints nothing

💥 WHY
👉 .toList() does NOT guarantee implementation

👉 Not necessarily ArrayList

✔ FIX
// Use List interface only — do NOT rely on implementation


// ❓ Question 4 (Null Handling Trap)
var list = Stream.of(1, null, 3).toList();

System.out.println(list);
What happens?

A. Compilation error
B. Runtime exception
C. [1, null, 3]
D. NullPointerException

✅ Answer: C — [1, null, 3]

💥 WHY
👉 .toList() allows null elements

✔ FIX (if nulls forbidden)

.filter(Objects::nonNull)
❓ Question 5 (Copy vs Reference Trap)
var original = new ArrayList<>(List.of(1,2,3));

var list = original.stream().toList();

original.add(4);

System.out.println(list);
What happens?

A. [1,2,3,4]
B. [1,2,3]
C. Compilation error
D. Runtime exception

✅ Answer: B — [1,2,3]

💥 WHY
👉 .toList() creates new list (snapshot)

👉 not backed by original

✔ FIX (if you want shared structure)

Just use original list directly
❓ Question 6 (Sorting Trap)
var list = Stream.of(3,1,2).toList();

Collections.sort(list);

System.out.println(list);
What happens?

A. [1,2,3]
B. Compilation error
C. Runtime exception
D. [3,1,2]

✅ Answer: C — Runtime exception

💥 WHY
👉 list is unmodifiable

Collections.sort(list);

👉 ❌ modifies list → UnsupportedOperationException

✔ FIX

var list = Stream.of(3,1,2)
    .collect(Collectors.toList());

Collections.sort(list);
❓ Question 7 (Replace Trap)
var list = Stream.of("a","b").toList();

list.set(0, "z");
What happens?

A. ["z","b"]
B. Compilation error
C. Runtime exception
D. ["a","b"]

✅ Answer: C — Runtime exception

💥 WHY
👉 .toList() → unmodifiable

👉 .set() not allowed

✔ FIX

.collect(Collectors.toList())
❓ Question 8 (Collectors.toList() assumption trap)
var list = Stream.of(1,2,3)
    .collect(Collectors.toList());

list.remove(1);

System.out.println(list);
What happens?

A. [1,3]
B. Runtime exception
C. Compilation error
D. [1,2,3]

✅ Answer: A — [1,3]

💥 WHY
👉 mutable list → removal allowed

✔ FIX (if immutability needed)

Collectors.toUnmodifiableList()
❓ Question 9 (Equality Trap)
var list1 = Stream.of(1,2,3).toList();
var list2 = Stream.of(1,2,3).collect(Collectors.toList());

System.out.println(list1.equals(list2));
What happens?

A. true
B. false
C. Compilation error
D. Runtime exception

✅ Answer: A — true

💥 WHY
👉 List equality is based on elements, not implementation

✔ FIX
No fix needed — correct behavior

❓ Question 10 (Ultimate Trap — Mutation after pipeline)
var list = Stream.of(1,2,3)
    .peek(System.out::print)
    .toList();

list.add(5);
What happens?

A. prints 123 and adds 5
B. prints 123 then runtime exception
C. compilation error
D. prints nothing

✅ Answer: B — prints 123 then runtime exception

💥 WHY

.peek(System.out::print)

👉 executes during terminal op

.toList()

👉 produces unmodifiable list

list.add(5);

👉 ❌ UnsupportedOperationException

✔ FIX
.collect(Collectors.toList())


FINAL EXAM RULES (MEMORIZE)
🔥 toList()
❌ unmodifiable
❌ cannot add/remove/set/sort
✅ allows nulls
✅ snapshot copy



// ===========================================
   toMap() — Build lookup tables
// ===========================================

FIRST — ALL toMap() SIGNATURES (MUST MEMORIZE)
toMap(keyMapper, valueMapper)

toMap(keyMapper, valueMapper, mergeFunction)

toMap(keyMapper, valueMapper, mergeFunction, mapSupplier)
❓ Question 1 (Duplicate Key — CLASSIC KILLER)
var map = Stream.of("a", "a")
    .collect(Collectors.toMap(
        s -> s,
        s -> s.length()
    ));

System.out.println(map);
What happens?

A. {a=1}
B. {a=2}
C. Compilation error
D. Runtime exception

✅ Answer: D — Runtime exception

💥 WHY
👉 Duplicate key "a"

toMap(key, value)

👉 ❌ NO merge function → IllegalStateException

✔ FIX

.collect(Collectors.toMap(
    s -> s,
    s -> s.length(),
    (a, b) -> a
));
❓ Question 2 (Merge Function Direction Trap)
var map = Stream.of("a", "aa", "aaa")
    .collect(Collectors.toMap(
        String::length,
        s -> s,
        (a, b) -> a
    ));

System.out.println(map);
What happens?

A. {1=a, 2=aa, 3=aaa}
B. {1=a, 2=aa, 3=aaa}
C. {1=a, 2=aa, 3=aaa}
D. {1=a, 2=aa, 3=aaa}

👉 Looks same? Trick is subtle 😈

✅ Answer: {1=a, 2=aa, 3=aaa}

💥 WHY
No duplicates → merge NEVER used

✔ FIX
No fix — but exam trick: merge only triggers on duplicates

❓ Question 3 (Real Merge Trigger)
var map = Stream.of("a", "b", "c")
    .collect(Collectors.toMap(
        s -> 1,
        s -> s,
        (a, b) -> a + b
    ));

System.out.println(map);
What happens?

A. {1=a}
B. {1=abc}
C. Runtime exception
D. Compilation error

✅ Answer: B — {1=abc}

💥 WHY

All keys = 1

Merge flow:

a + b → "a" + "b" = "ab"
ab + c = "abc"

✔ FIX
Understand merge order:

(existing, incoming)
❓ Question 4 (Null Key Trap — VERY IMPORTANT)
var map = Stream.of("a", null)
    .collect(Collectors.toMap(
        s -> s,
        s -> 1
    ));
What happens?

A. {a=1, null=1}
B. Runtime exception
C. Compilation error
D. {a=1}

✅ Answer: B — Runtime exception

💥 WHY
👉 toMap() uses HashMap internally BUT disallows null key here

👉 ❌ NullPointerException

✔ FIX

s -> s == null ? "UNKNOWN" : s
❓ Question 5 (Null Value Trap)
var map = Stream.of("a", "b")
    .collect(Collectors.toMap(
        s -> s,
        s -> null
    ));
What happens?

A. {a=null, b=null}
B. Runtime exception
C. Compilation error
D. {}

✅ Answer: B — Runtime exception

💥 WHY
👉 toMap() does NOT allow null values

✔ FIX

s -> Optional.ofNullable(value).orElse(defaultValue)
❓ Question 6 (Type Inference Trap)
var map = Stream.of(1,2,3)
    .collect(Collectors.toMap(
        x -> x,
        x -> x + 1
    ));
What is type?

A. Map<Integer, Integer>
B. Map<int, int>
C. Compilation error
D. Map<Object, Object>

✅ Answer: A — Map<Integer, Integer>

💥 WHY
👉 Streams use wrapper types

✔ FIX
Know autoboxing

❓ Question 7 (Map Supplier Trap)
var map = Stream.of("b", "a")
    .collect(Collectors.toMap(
        s -> s,
        s -> s,
        (a,b) -> a,
        TreeMap::new
    ));

System.out.println(map);
What happens?

A. {b=b, a=a}
B. {a=a, b=b}
C. Runtime exception
D. Compilation error

✅ Answer: B — sorted order

💥 WHY
👉 TreeMap sorts keys

✔ FIX
Use LinkedHashMap if insertion order needed

❓ Question 8 (Order Trap — VERY COMMON)
var map = Stream.of("b", "a")
    .collect(Collectors.toMap(
        s -> s,
        s -> s
    ));

System.out.println(map);
What happens?

A. {b=b, a=a}
B. {a=a, b=b}
C. Order not guaranteed
D. Runtime exception

✅ Answer: C — not guaranteed

💥 WHY
👉 HashMap → no ordering

✔ FIX

LinkedHashMap::new
❓ Question 9 (groupingBy vs toMap — BIG TRAP)
var map = Stream.of("a", "a")
    .collect(Collectors.toMap(
        s -> s,
        s -> List.of(s),
        (a, b) -> {
            var list = new ArrayList<>(a);
            list.addAll(b);
            return list;
        }
    ));

System.out.println(map);
What happens?

A. {a=[a]}
B. {a=[a, a]}
C. Runtime exception
D. Compilation error

✅ Answer: B — {a=[a, a]}

💥 WHY
👉 You manually simulated grouping

✔ FIX
👉 Just use:

groupingBy(...)
❓ Question 10 (Immutable Value Trap)
var map = Stream.of("a", "a")
    .collect(Collectors.toMap(
        s -> s,
        s -> List.of(s),
        (a, b) -> {
            a.addAll(b);
            return a;
        }
    ));
What happens?

A. {a=[a,a]}
B. Runtime exception
C. Compilation error
D. {a=[a]}

✅ Answer: B — Runtime exception

💥 WHY
👉 List.of() → immutable

a.addAll(b)

👉 ❌ UnsupportedOperationException

✔ FIX

new ArrayList<>(List.of(s))
❓ Question 11 (Parallel Trap — ADVANCED)
var map = Stream.of("a","b","c")
    .parallel()
    .collect(Collectors.toMap(
        s -> 1,
        s -> s,
        (a,b) -> a + b
    ));
What happens?

A. {1=abc}
B. Order unpredictable
C. Runtime exception
D. Compilation error

✅ Answer: B — order unpredictable

💥 WHY
👉 Parallel stream → merge order not guaranteed

✔ FIX
Avoid relying on order

❓ Question 12 (Final Boss Trap)
var map = Stream.of("a", "bb", "ccc")
    .collect(Collectors.toMap(
        String::length,
        s -> s,
        (a,b) -> b,
        LinkedHashMap::new
    ));

System.out.println(map);
What happens?

A. {1=a, 2=bb, 3=ccc}
B. {1=a, 2=bb, 3=ccc}
C. {1=a, 2=bb, 3=ccc}
D. depends on merge

✅ Answer: A (no duplicates → merge unused)

💥 WHY
👉 No same length → merge never runs

✔ FIX
Understand: merge only applies when keys collide










===========================================
    groupingBy() — One of the most important advanced collectors
// ===========================================.


CORE SIGNATURES (LOCK FIRST)
groupingBy(classifier)
groupingBy(classifier, downstream)
groupingBy(classifier, mapFactory, downstream)



// ❓ Question 1 (Basic Type Trap)
var result = Stream.of("a", "bb", "ccc")
    .collect(Collectors.groupingBy(String::length));

System.out.println(result);


// What happens?
A. Map<Integer, String>
B. Map<Integer, List<String>>
C. Compilation error
D. Runtime exception

✅ Answer: B — Map<Integer, List<String>>

💥 WHY
// 👉 Default downstream = Collectors.toList()

✔ FIX (if you want count instead)

.collect(Collectors.groupingBy(
    String::length,
    Collectors.counting()
));
// 👉 Map<Integer, Long> 
// Result:{1=1, 2=2, 3=1}



// ❓ Question 2 (Null Key Trap — VERY IMPORTANT)
var result = Stream.of("a", null, "bb")
    .collect(Collectors.groupingBy(s -> s == null ? null : s.length()));
What happens?

A. Works fine
B. Compilation error
C. Runtime exception
D. {null=[null], 1=[a], 2=[bb]}

✅ Answer: C — Runtime exception

💥 WHY
👉 groupingBy() uses HashMap
// 👉 null keys NOT allowed in classifier result
👉 ❌ NullPointerException

// ✔ FIX
.collect(Collectors.groupingBy(
    s -> s == null ? -1 : s.length()
));
// Result: {-1=[null], 1=[a], 2=[bb]}

❓ Question 3 (Downstream Type Trap)
var result = Stream.of("a", "bb", "ccc")
    .collect(Collectors.groupingBy(
        String::length,
        Collectors.counting()
    ));
  //{1=1, 2=1, 3=1}

System.out.println(result.get(1) instanceof Integer);
What happens?

A. true
B. false
C. Compilation error
D. Runtime exception

✅ Answer: B — false

💥 WHY
👉 counting() returns: Long. NOT Integer

✔ FIX
Use correct type:
Long count = result.get(1);


❓ Question 4 (Mutability Trap)
var result = Stream.of("a", "bb")
    .collect(Collectors.groupingBy(String::length));

result.get(1).add("z");

System.out.println(result);
What happens?

A. {1=[a, z], 2=[bb]}
B. Runtime exception
C. Compilation error
D. No change

✅ Answer: A — {1=[a, z], 2=[bb]}

💥 WHY
// 👉 default toList() → mutable list

// ✔ FIX (if immutability needed)
Collectors.groupingBy(
    String::length,
    Collectors.toUnmodifiableList()
)


❓ Question 5 (Key Order Trap)
var result = Stream.of("a", "bb", "ccc")
    .collect(Collectors.groupingBy(String::length));

System.out.println(result);
What happens?

A. Always sorted
B. Always insertion order
C. Order not guaranteed
D. Compilation error

✅ Answer: C — Order not guaranteed

💥 WHY
// 👉 Default map = HashMap

// ✔ FIX (if order needed)
Collectors.groupingBy(
    String::length,
    TreeMap::new,
    Collectors.toList()
)


❓ Question 6 (Map Factory Trap)
var result = Stream.of("a", "bb", "ccc")
    .collect(Collectors.groupingBy(
        String::length,
        TreeMap::new,
        Collectors.toList()
    ));

System.out.println(result);
What happens?

A. Sorted keys
B. Random order
C. Compilation error
D. Runtime exception

✅ Answer: A — Sorted keys

💥 WHY
// 👉 Using TreeMap → natural ordering

✔ FIX
No fix — correct usage

❓ Question 7 (Duplicate Key vs groupingBy)
var result = Stream.of("a", "a")
    .collect(Collectors.groupingBy(s -> s));
What happens?

A. Exception
B. {a=[a]}
C. {a=[a, a]}
D. Compilation error

✅ Answer: C — {a=[a, a]}

💥 WHY
// 👉 groupingBy allows duplicates → collects into list

✔ FIX (if single value needed)

Use:

Collectors.toMap(...)
❓ Question 8 (toMap vs groupingBy Trap)
var result = Stream.of("a", "a")
    .collect(Collectors.toMap(
        s -> s,
        s -> s
    ));
What happens?

A. {a=a}
B. Compilation error
C. Runtime exception
D. {a=[a,a]}

✅ Answer: C — Runtime exception

💥 WHY
// 👉 Duplicate key → IllegalStateException

✔ FIX

Collectors.toMap(
    s -> s,
    s -> s,
    (v1, v2) -> v1
)
❓ Question 9 (Nested Collector Trap — HARD)
var result = Stream.of("a", "bb", "ccc")
    .collect(Collectors.groupingBy(
        String::length,
        Collectors.mapping(
            String::toUpperCase,
            Collectors.toList()
        )
    ));

System.out.println(result);
What happens?

A. {1=[a],2=[bb],3=[ccc]}
B. {1=[A],2=[BB],3=[CCC]}
C. Compilation error
D. Runtime exception

✅ Answer: B — {1=[A],2=[BB],3=[CCC]}

💥 WHY
👉 mapping() transforms values BEFORE collecting

✔ FIX
No fix — correct

❓ Question 10 (averaging + grouping Trap)
var result = Stream.of(1,2,3,4)
    .collect(Collectors.groupingBy(
        x -> x % 2,
        Collectors.averagingInt(x -> x)
    ));

System.out.println(result);
What happens?

A. {0=3, 1=2}
B. {0=3.0, 1=2.0}
C. Compilation error
D. Runtime exception

✅ Answer: B — {0=3.0, 1=2.0}

💥 WHY
👉 averagingInt() → returns double

✔ FIX
No fix — expected behavior

❓ Question 11 (Collector Type Mismatch Trap)
var result = Stream.of("a", "bb")
    .collect(Collectors.groupingBy(
        String::length,
        Collectors.averagingInt(String::length)
    ));
What happens?

A. Compilation error
B. Runtime exception
C. {1=1.0,2=2.0}
D. {1=[a],2=[bb]}

✅ Answer: C — {1=1.0,2=2.0}

💥 WHY
👉 perfectly valid — downstream works on grouped elements

✔ FIX
No fix needed

❓ Question 12 (Concurrent Trap — ADVANCED)
var result = Stream.of("a","bb","ccc")
    .parallel()
    .collect(Collectors.groupingBy(String::length));
What happens?

A. Always thread-safe map
B. Not thread-safe map
C. Compilation error
D. Runtime exception

✅ Answer: B — Not thread-safe map

💥 WHY
👉 groupingBy() → uses HashMap

👉 NOT concurrent-safe

✔ FIX
Collectors.groupingByConcurrent(String::length)




// ===========================================
partitioningBy() — Always two groups
// ===========================================

CORE IDEA (LOCK THIS FIRST)
partitioningBy(predicate)
partitioningBy(predicate, downstream)

👉 ALWAYS returns:

Map<Boolean, ...>

👉 Keys are ONLY:

true and false
🧪 BASIC EXAMPLE
var result = Stream.of("a", "bb", "ccc")
    .collect(Collectors.partitioningBy(s -> s.length() > 1));

System.out.println(result);
Output:
{false=[a], true=[bb, ccc]}
🔥 ULTRA HARD OCP TRAPS
❓ Question 1 (Return Type Trap)
var result = Stream.of("a", "bb")
    .collect(Collectors.partitioningBy(s -> s.length() > 1));
What is type?

A. Map<Boolean, String>
B. Map<Boolean, List<String>>
C. Map<Integer, List<String>>
D. Compilation error

✅ Answer: B — Map<Boolean, List<String>>

💥 WHY
👉 Default downstream = toList()

✔ FIX

partitioningBy(predicate, downstream)
❓ Question 2 (ALWAYS TWO KEYS — VERY IMPORTANT)
var result = Stream.of("a")
    .collect(Collectors.partitioningBy(s -> s.length() > 5));

System.out.println(result);
What happens?

A. {false=[a]}
B. {true=[]}
C. {false=[a], true=[]}
D. Runtime exception

✅ Answer: C — BOTH keys exist

💥 WHY
👉 partitioning ALWAYS creates:

true and false

Even if empty

✔ FIX
No fix — expected behavior

❓ Question 3 (Downstream Trap)
var result = Stream.of("a", "bb", "ccc")
    .collect(Collectors.partitioningBy(
        s -> s.length() > 1,
        Collectors.counting()
    ));

System.out.println(result);
What happens?

A. {false=[a], true=[bb,ccc]}
B. {false=1, true=2}
C. Compilation error
D. Runtime exception

✅ Answer: B — {false=1, true=2}

💥 WHY
👉 downstream = counting()

👉 changes value type to Long

✔ FIX
Know return type:

Map<Boolean, Long>
❓ Question 4 (Difference with groupingBy — BIG TRAP)
var result = Stream.of("a", "bb", "ccc")
    .collect(Collectors.groupingBy(s -> s.length() > 1));
What happens?

A. same as partitioningBy
B. only keys present if used
C. always 2 keys
D. compilation error

✅ Answer: B — keys only if used

💥 WHY

groupingBy → only creates keys that exist
partitioningBy → ALWAYS 2 keys

✔ FIX
Use partitioningBy when you need guaranteed keys

❓ Question 5 (Mutability Trap)
var result = Stream.of("a", "bb")
    .collect(Collectors.partitioningBy(s -> s.length() > 1));

result.get(true).add("zzz");
What happens?

A. Works
B. Runtime exception
C. Compilation error
D. No change

✅ Answer: A — Works

💥 WHY
👉 Default downstream = toList() → mutable

✔ FIX (if immutability needed)

Collectors.toUnmodifiableList()
❓ Question 6 (Null Predicate Trap)
var result = Stream.of("a", null)
    .collect(Collectors.partitioningBy(s -> s.length() > 1));
What happens?

A. Works
B. Runtime exception
C. Compilation error
D. {false=[a,null]}

✅ Answer: B — Runtime exception

💥 WHY

s.length()

👉 null → NullPointerException

✔ FIX

s -> s != null && s.length() > 1
❓ Question 7 (Type Trap — VERY TRICKY)
var result = Stream.of(1,2,3)
    .collect(Collectors.partitioningBy(
        x -> x > 1,
        Collectors.averagingInt(x -> x)
    ));
What is type?

A. Map<Boolean, List<Integer>>
B. Map<Boolean, Double>
C. Map<Boolean, Integer>
D. Compilation error

✅ Answer: B — Map<Boolean, Double>

💥 WHY
👉 downstream controls value type

✔ FIX
Know:

averagingInt → Double
❓ Question 8 (Parallel Trap)
var result = Stream.of("a","bb","ccc")
    .parallel()
    .collect(Collectors.partitioningBy(s -> s.length() > 1));
What happens?

A. Not thread-safe
B. Thread-safe result
C. Compilation error
D. Runtime exception

✅ Answer: B — Thread-safe result

💥 WHY
👉 partitioningBy is safe for parallel streams

✔ FIX
No fix needed

❓ Question 9 (Nested Downstream Trap)
var result = Stream.of("a", "bb", "ccc")
    .collect(Collectors.partitioningBy(
        s -> s.length() > 1,
        Collectors.mapping(
            String::toUpperCase,
            Collectors.joining(",")
        )
    ));

System.out.println(result);
What happens?

A. {false=A, true=BB,CCC}
B. {false=[A], true=[BB,CCC]}
C. Compilation error
D. Runtime exception

✅ Answer: A

💥 WHY

mapping → uppercase
joining → String

✔ FIX
Understand nested collectors

❓ Question 10 (Final Boss Trap)
var result = Stream.of("a","bb","ccc","dddd")
    .collect(Collectors.partitioningBy(
        s -> s.length() % 2 == 0,
        Collectors.counting()
    ));

System.out.println(result);
What happens?

A. {true=2, false=2}
B. {true=3, false=1}
C. {true=[bb,dddd], false=[a,ccc]}
D. Compilation error

✅ Answer: A

💥 WHY

Even length:

bb, dddd → 2

Odd:

a, ccc → 2

✔ FIX
No fix — correct

🧠 FINAL MASTER RULES (EXAM GOLD)
🔥 partitioningBy ALWAYS:
returns Map<Boolean, ...>
ALWAYS has 2 keys (true, false)
downstream controls value type
default value = List<T>
🔥 Most tested traps:
❗ Always 2 keys (even empty)
❗ Value type depends on downstream
❗ Null predicate → crash
❗ groupingBy ≠ partitioningBy
❗ Mutable list by default
🧠 FINAL MEMORY LINE

partitioningBy = boolean grouping with guaranteed 2 buckets