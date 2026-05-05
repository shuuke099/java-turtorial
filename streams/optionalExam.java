OCP JAVA 21 — OPTIONAL MASTER EXAM (PART 1 ONLY)

Focus:



Creation (empty, of, ofNullable)


Factory pattern


Average-style logic


Presence vs absence


Type traps


Runtime traps


Subtle evaluation behavior



🔥 SECTION 1 — OPTIONAL CREATION (VERY TRICKY)

❓ Question 1 (Factory Pattern Trap)
Optional<String> opt = new Optional<>("java");
A. Compiles and runs
B. Compilation error
C. Runtime exception
D. Optional.empty

✅ Answer: B — Compilation error
💥 WHY
👉 Optional has NO public constructor
✔ Must use:
Optional.of(...)Optional.ofNullable(...)Optional.empty()



// ❓ Question 2 (of vs ofNullable — CRITICAL)
Optional<String> a = Optional.of("x");
Optional<String> b = Optional.ofNullable("y");
Optional<String> c = Optional.ofNullable(null);
System.out.println(a + " " + b + " " + c);


A. Optional[x] Optional[y] Optional.empty
B. Optional[x] Optional[y] Optional[null]
C. Compilation error
D. Runtime exception

✅ Answer: A
💥 WHY
👉 ofNullable(null) → empty
👉 Only of() throws on null

❓ Question 3 (of(null) — EXAM KILLER)
Optional<Integer> opt = Optional.of(null);
A. Optional.empty
B. Optional[null]
C. Compilation error
D. Runtime exception

✅ Answer: D — Runtime exception
💥 WHY
// 👉 Optional.of() → 💥 NullPointerException

// ❓ Question 4 (empty vs ofNullable(null))
Optional<String> a = Optional.empty();
Optional<String> b = Optional.ofNullable(null);
System.out.println(a.equals(b));
A. true
B. false
C. Compilation error
D. Runtime exception

✅ Answer: A — true
💥 WHY
// 👉 Both represent empty Optional


🔥 SECTION 2 — AVERAGE METHOD (REAL EXAM STYLE)

// ❓ Question 5 (Understanding Absence)
static Optional<Double> avg(int... nums){
    if (nums.length == 0) return Optional.empty();    
    return Optional.of(10.0);}
    System.out.println(avg());
A. 0
B. null
C. Optional.empty
D. Compilation error

✅ Answer: C
💥 WHY
// 👉 No data = no value, not 0

// ❓ Question 6 (Null Return Trap)
static Optional<Double> avg(int... nums) {    
    if (nums.length == 0) return null;    
    return Optional.of(10.0);}
    System.out.println(avg());


A. Optional.empty
B. null
C. Compilation error
D. Runtime exception

✅ Answer: B — null
💥 WHY
👉 Method returns Optional, but you returned null
👉 Allowed, but VERY BAD PRACTICE
⚠️ Exam trap: Optional can still be null

❓ Question 7 (Double Wrapping Trap)
Optional<Optional<Integer>> opt = Optional.of(Optional.of(10));System.out.println(opt);
A. Optional[10]
B. Optional[Optional[10]]
C. Compilation error
D. Runtime exception

✅ Answer: B
💥 WHY
👉 Nested Optional (very important trap)


🔥 SECTION 3 — ACCESSING VALUES (DANGEROUS ZONE)

// ❓ Question 8 (get() Safe vs Unsafe)
Optional<String> opt = Optional.empty();if (opt.isPresent())    System.out.println(opt.get());else    System.out.println("X");
A. X
B. null
C. Runtime exception
D. Compilation error

✅ Answer: A
💥 WHY
👉 Safe access pattern

❓ Question 9 (get() without check)
Optional<String> opt = Optional.empty();System.out.println(opt.get());
A. null
B. Optional.empty
C. Runtime exception
D. Compilation error

✅ Answer: C
💥 WHY
👉 💥 NoSuchElementException


🔥 SECTION 4 — orElse / orElseGet (VERY DEEP TRAP)

❓ Question 10 (EAGER vs LAZY — HARD)
Optional<String> opt = Optional.of("A");System.out.println(opt.orElse(expensive()));System.out.println(opt.orElseGet(() -> expensive()));static String expensive() {    System.out.print("X");    return "B";}
A. A A
B. XA A
C. X X A A
D. Compilation error

✅ Answer: B — XA A
💥 WHY
👉 orElse() → ALWAYS runs
👉 orElseGet() → NOT executed

❓ Question 11 (Return Type Trap)
Optional<Integer> opt = Optional.empty();System.out.println(opt.orElseGet(() -> null));
A. Compilation error
B. Runtime exception
C. null
D. 0

✅ Answer: C — null
💥 WHY
👉 Supplier<Integer> can return null

❓ Question 12 (Wrong Type Supplier)
Optional<Integer> opt = Optional.empty();opt.orElseGet(() -> "hello");
A. Compiles
B. Runtime exception
C. Compilation error
D. Returns hello

✅ Answer: C


🔥 SECTION 5 — orElseThrow (ADVANCED)

❓ Question 13 (Default Exception)
Optional<Integer> opt = Optional.empty();opt.orElseThrow();
A. null
B. 0
C. Runtime exception
D. Compilation error

✅ Answer: C
💥 WHY
👉 Throws:
NoSuchElementException

❓ Question 14 (Custom Exception)
Optional<Integer> opt = Optional.empty();opt.orElseThrow(() -> new IllegalArgumentException());
A. Compilation error
B. Runtime exception
C. null
D. 0

✅ Answer: B

❓ Question 15 (WRONG Usage — VERY TRICKY)
Optional<Integer> opt = Optional.empty();opt.orElseGet(() -> new RuntimeException());
A. Compiles
B. Runtime exception
C. Compilation error
D. Returns exception

✅ Answer: C
💥 WHY
👉 orElseGet expects value, not exception


🔥 SECTION 6 — ifPresent (SUBTLE)

❓ Question 16 (ifPresent behavior)
Optional<String> opt = Optional.of("A");opt.ifPresent(s -> System.out.print(s));System.out.print("B");
A. AB
B. BA
C. A
D. B

✅ Answer: A

❓ Question 17 (ifPresent empty)
Optional<String> opt = Optional.empty();opt.ifPresent(System.out::print);System.out.print("X");
A. X
B. nullX
C. nothing
D. exception

✅ Answer: A


🔥 FINAL OCP OPTIONAL RULES (MEMORIZE)

🧠 CREATION
Optional.of(x)        → ❌ null not allowedOptional.ofNullable(x)→ ✅ safeOptional.empty()      → empty

🧠 ACCESS
get() → ❌ dangerousisPresent() → old styleifPresent() → preferred

🧠 FALLBACK
orElse()     → ALWAYS runsorElseGet()  → LAZYorElseThrow()→ throws

🧠 EXAM TRAPS


Optional can be null itself


Nested Optional possible


Supplier type must match


Exception vs value confusion


orElse side effects



🚀 NEXT STEP
If you want to continue in the same depth, next we do:
👉 Part 2 — Stream Pipelines (EXTREME LEVEL)


laziness chains


flatMap traps


infinite streams


collector chaos


Just say:
👉 “next part streams extreme”