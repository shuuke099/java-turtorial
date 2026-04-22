
// ===========================================
3. Chaining Optional Like a Professional
// ===========================================
this explains that Optional also supports chaining methods such as:
filter() , map() , flatMap() 

// This is very important because many OCP questions treat Optional almost like a mini stream pipeline.

Airport Example: Validate a Passenger Risk Code
// Suppose a risk code may or may not exist:

Optional<Integer> optionalRisk = Optional.of(742);
// You want to print it only if it is a three-digit number.

// Imperative version
if (optionalRisk.isPresent()) {
    var num = optionalRisk.get();
    var text = "" + num;
    if (text.length() == 3) {
        System.out.println(text);
    }
} // This works, but it is verbose.

// Functional chained version
optionalRisk
    .map(n -> "" + n)
    .filter(s -> s.length() == 3)
    .ifPresent(System.out::println);

In the airport system:
// map() converts the risk number to text
// filter() keeps only valid three-digit codes
// ifPresent() runs only when a valid value remains


// ===========================================
4. map() vs flatMap() with Optional
// ===========================================

// This is one of the biggest OCP traps.
Your file gives the key rule:
// use map() when the function returns a normal value
// use flatMap() when the function returns an Optional


Professional Example: Passenger clearance calculator

// Suppose you have a method:
static Optional<Integer> calculateRiskAdjustment(String note) {
    if (note.length() < 3) return Optional.empty();
    return Optional.of(note.length() * 10);
}

// And you already have:
Optional<String> optionalNote = Optional.of("BAG"); 

optionalNote.map(AdvancedDemo::calculateRiskAdjustment);//Wrong approach

That gives: Optional<Optional<Integer>>.; That is not what you want.

// Correct approach
Optional<Integer> adjusted =
    optionalNote.flatMap(AdvancedDemo::calculateRiskAdjustment); // Now the result is: Optional<Integer>

Airport meaning

// A note may produce: a real recalculated score
// or no score
// So when the function itself returns Optional, use flatMap().
