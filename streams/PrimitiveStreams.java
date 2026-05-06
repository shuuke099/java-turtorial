// Full Professional Scenario: Hospital Security Payroll and Risk Analytics System

// Imagine you work on a hospital security analytics platform.

// Every day, the system needs to process numeric data such as:

// hours worked by each security officer
// overtime minutes
// incident severity scores
// daily patrol counts
// checkpoint scan totals
// response times in seconds
// monthly payroll totals

// This is where primitive streams become very important.

// Instead of always using Stream<Integer>, Stream<Long>, or Stream<Double>, Java provides:

// IntStream
// LongStream
// DoubleStream

// These are designed for numeric work, avoid unnecessary boxing/unboxing, and provide built-in arithmetic operations like sum(), average(), min(), and max(). That is the main reason your PDF says primitive streams are needed.

Why Primitive Streams Exist
// Suppose the hospital stores incident severity values:
Stream<Integer> severities = Stream.of(4, 8, 10, 7);

// You can sum them like this:
int total = Stream.of(4, 8, 10, 7)
                  .reduce(0, (a, b) -> a + b);
int total = Stream.of(4, 8, 10, 7)
                  .mapToInt(x -> x)
                  .sum();

// more expressive
// built for numbers
// avoids extra boxing/unboxing
// has direct numeric terminal methods                  


// 2. Business Scenario Setup

// Let us say the hospital security department tracks officer work hours and incident metrics.

import java.util.*;
import java.util.stream.*;

public class PrimitiveStreamsHospitalDemo {
    public static void main(String[] args) {
        int[] patrolCounts = {12, 15, 18, 10, 20};
        double[] responseTimes = {2.5, 3.1, 1.8, 4.0, 2.2};
        long[] monthlyBadgeScans = {1200L, 1500L, 1800L, 1700L};
    }
}

// 4. Full Enterprise Story: Daily Patrol Analytics

A hospital security supervisor wants answers to these questions:

// How many patrol rounds were completed today?
// What is the average patrol count?
// What is the minimum and maximum patrol count?
// What is the total?
// What is the range of values?

This is exactly where primitive streams shine.

// Example with IntStream
IntStream patrols = IntStream.of(12, 15, 18, 10, 20);
System.out.println(patrols.sum()); // 75;

Numeric Terminal Operations in the Scenario
// 5.1 sum()
int totalPatrols = IntStream.of(12, 15, 18, 10, 20).sum();
System.out.println(totalPatrols); // 75

// average()
OptionalDouble avgResponse =
    DoubleStream.of(2.5, 3.1, 1.8, 4.0, 2.2).average();
System.out.println(avgResponse.getAsDouble()); // 2.72 .....“What was the average emergency response time today?”

// 5.3 min()
// Fastest response:
OptionalDouble fastest =
    DoubleStream.of(2.5, 3.1, 1.8, 4.0, 2.2).min();
System.out.println(fastest.getAsDouble()); // 1.8 “What was our best response time?”

// 5.4 max()

Highest patrol count:

OptionalInt maxPatrols =
    IntStream.of(12, 15, 18, 10, 20).max();
System.out.println(maxPatrols.getAsInt()); // 20. // “What was the busiest patrol unit today?”


//=============================================
// 6. Creating Primitive Streams
// =============================================
Your file shows several ways to create primitive streams: empty(), of(), generate(), iterate(), range(), and rangeClosed().

// 6.1 empty()
// Suppose no incident durations were recorded yet:
DoubleStream emptyDurations = DoubleStream.empty();
System.out.println(emptyDurations.count()); // 0

// 6.2 of()
// Fixed numeric data:

IntStream patrolCounts = IntStream.of(12, 15, 18, 10, 20);
patrolCounts.forEach(System.out::println);
// Manual batch of patrol counts from 5 officers.

// 6.3 generate()
// Suppose the hospital simulates random security risk scores:

DoubleStream randomScores = DoubleStream.generate(Math::random);
randomScores.limit(3).forEach(System.out::println);

// 0.734829374
// 0.129384756
// 0.982374923
// Generate random test scores for system simulation.

// 6.4 iterate()
// Suppose you simulate increasing patrol zone numbers:

IntStream zones = IntStream.iterate(1, n -> n + 1).limit(5);
zones.forEach(System.out::print); // 12345

// Testing zone identifiers 1 through 5.

// 7. range() and rangeClosed() in Real Systems
// Your file emphasizes these because they are much cleaner than using iterate().limit().

7.1 range()
IntStream zoneNumbers = IntStream.range(1, 6);
zoneNumbers.forEach(System.out::print); // 12345
// start inclusive
// end exclusive
// Hospital security zones 1 through 5.

// 7.2 rangeClosed()
IntStream floorChecks = IntStream.rangeClosed(1, 5);
floorChecks.forEach(System.out::print); // 12345
// start inclusive
// end inclusive
// Business meaning
// Check floors 1 to 5 inclusive.

Easy memory trick
// range(1, 6) → includes 1, excludes 6
// rangeClosed(1, 5) → includes both 1 and 5


// ======================================================
8. Mapping from Object Streams to Primitive Streams
// ======================================================
// Your file explains that object streams can be converted using mapToInt(), mapToLong(), and mapToDouble().

Suppose hospital officers are represented as objects:

record Officer(String name, int patrolsCompleted, double responseTime) {};

List<Officer> officers = List.of(
    new Officer("Adan", 12, 2.5),
    new Officer("Sara", 15, 3.1),
    new Officer("Mohamed", 18, 1.8)
);

// 8.1 mapToInt()
int totalOfficerPatrols = officers.stream()
                                  .mapToInt(Officer::patrolsCompleted)
                                  .sum();

System.out.println(totalOfficerPatrols); // 45
// Compute total patrols from officer records.

// 8.2 mapToDouble()
OptionalDouble avgOfficerResponse = officers.stream()
                                            .mapToDouble(Officer::responseTime)
                                            .average();

System.out.println(avgOfficerResponse.getAsDouble()); // 2.4666...
Business meaning

Average response time across all officers.

// 8.3 mapToLong()
// Suppose each department stores long-valued monthly scans:

record DepartmentStats(String name, long scans) {}

List<DepartmentStats> stats = List.of(
    new DepartmentStats("ER", 1200L),
    new DepartmentStats("ICU", 1500L),
    new DepartmentStats("Lobby", 1800L)
);

long totalScans = stats.stream()
                       .mapToLong(DepartmentStats::scans)
                       .sum();

System.out.println(totalScans); // 4500
// Compute total badge scans across the hospital.

// ======================================================
9. Mapping from Primitive Streams Back to Object Streams
// ======================================================

// Your file shows two ways:
// mapToObj()
// boxed()

// 9.1 mapToObj()

// Suppose we convert patrol numbers into report strings:

Stream<String> patrolLabels =
    IntStream.of(12, 15, 18)
             .mapToObj(n -> "Patrol Count: " + n);

patrolLabels.forEach(System.out::println);

Output:
// Patrol Count: 12
// Patrol Count: 15
// Patrol Count: 18


9.2 boxed()
Stream<Integer> boxedPatrols =
    IntStream.of(12, 15, 18).boxed(); 

boxedPatrols.forEach(System.out::println);  //12, 15, 18 as Integer objects


// You need objects because a collector or API expects Stream<Integer> rather than IntStream.
// Your PDF says boxed() is shorter and automatically wraps primitives in their wrapper types.
// ======================================================
10. flatMap for Primitive Streams
// ======================================================
Your file covers:

flatMapToInt()
flatMapToDouble()
flatMapToLong()

Suppose each hospital department reports a list of incident severities, and you want one flat numeric stream.

List<List<Integer>> departmentSeverities = List.of(
    List.of(4, 7, 8),
    List.of(10, 6),
    List.of(3, 9)
);

Flatten to one IntStream:

IntStream flatSeverities =
    departmentSeverities.stream()
                        .flatMapToInt(list -> list.stream().mapToInt(Integer::intValue));
flatSeverities.forEach(System.out::print); // 47810639
Business meaning

// Combine all departmental incident scores into one stream for central analytics.
// ======================================================
11. Primitive Optional Types
//======================================================
Your file explains that primitive streams use:

OptionalInt
OptionalLong
OptionalDouble

These are used instead of Optional<Integer>, etc., to avoid boxing and to make numeric intent clear.

11.1 OptionalInt
OptionalInt highestPatrol = IntStream.of(12, 15, 18, 10, 20).max();
System.out.println(highestPatrol.getAsInt()); // 20
11.2 OptionalLong
OptionalLong maxScans = LongStream.of(1200L, 1500L, 1800L).max();
System.out.println(maxScans.getAsLong()); // 1800
11.3 OptionalDouble
OptionalDouble avg = DoubleStream.of(2.5, 3.1, 1.8, 4.0, 2.2).average();
System.out.println(avg.getAsDouble()); // 2.72

Your file also highlights that the getter names differ:

getAsInt()
getAsLong()
getAsDouble()
12. Safe Use of Primitive Optionals
OptionalDouble avgResponse = DoubleStream.of(2.5, 3.1, 1.8).average();

avgResponse.ifPresent(System.out::println);
System.out.println(avgResponse.orElseGet(() -> Double.NaN));

// Your PDF specifically shows ifPresent(), getAsDouble(), and orElseGet().

If there were no recorded response times, you do not want the program to crash.

Example with empty stream:

OptionalDouble emptyAvg = DoubleStream.empty().average();
System.out.println(emptyAvg.orElse(Double.NaN)); // NaN


13. summaryStatistics() in a Real Dashboard

This is one of the most important professional uses.

Your file explains that streams can only be consumed once, so if you need count, min, max, sum, and average together, use summary statistics.

Suppose the hospital wants one full report of daily incident severity values.

IntSummaryStatistics stats =
    IntStream.of(4, 8, 10, 7, 9).summaryStatistics();

System.out.println(stats.getCount());   // 5
System.out.println(stats.getSum());     // 38
System.out.println(stats.getMin());     // 4
System.out.println(stats.getMax());     // 10
System.out.println(stats.getAverage()); // 7.6
Business meaning

A hospital dashboard may need all those numbers at once.

Why this is better

Without summaryStatistics(), you might try:

one stream for count
another stream for min
another stream for max
another stream for sum
another stream for average

That is wasteful and impossible if you are reusing the same already-consumed stream.

14. Full Professional Example: Security Shift Analytics

Now let us build one complete business report.

Requirement

For one security shift, calculate:

total patrols
average response time
highest incident severity
unique numeric zone checks from 1 to 5
summary statistics for severity values
import java.util.*;
import java.util.stream.*;

public class SecurityShiftAnalytics {
    public static void main(String[] args) {

        int totalPatrols = IntStream.of(12, 15, 18, 10, 20).sum();
        System.out.println("Total patrols: " + totalPatrols); // 75

        OptionalDouble avgResponse =
                DoubleStream.of(2.5, 3.1, 1.8, 4.0, 2.2).average();
        System.out.println("Average response: " + avgResponse.getAsDouble()); // 2.72

        OptionalInt highestSeverity =
                IntStream.of(4, 8, 10, 7, 9).max();
        System.out.println("Highest severity: " + highestSeverity.getAsInt()); // 10

        IntStream.rangeClosed(1, 5)
                 .forEach(n -> System.out.print(n + " ")); // 1 2 3 4 5
        System.out.println();

        IntSummaryStatistics stats =
                IntStream.of(4, 8, 10, 7, 9).summaryStatistics();

        System.out.println("Count: " + stats.getCount());     // 5
        System.out.println("Sum: " + stats.getSum());         // 38
        System.out.println("Min: " + stats.getMin());         // 4
        System.out.println("Max: " + stats.getMax());         // 10
        System.out.println("Average: " + stats.getAverage()); // 7.6
    }
}
15. Infinite Primitive Stream Trap

Your file warns that primitive streams behave like normal streams when infinite.

Example:

DoubleStream doubles = DoubleStream.generate(() -> Math.PI);
// OptionalDouble min = doubles.min(); // never terminates

Why?

Because the stream never ends, so min() keeps waiting for more values forever.

Safe version:

OptionalDouble min = DoubleStream.generate(() -> Math.PI)
                                 .limit(5)
                                 .min();

System.out.println(min.getAsDouble()); // 3.141592653589793
Business meaning

If your hospital simulator produces endless random sensor values, you must limit or short-circuit the stream.

16. Professional Payroll Scenario with Primitive Streams

Primitive streams are also perfect for payroll.

Suppose officers worked these hours this week:

double[] weeklyHours = {40.0, 36.5, 42.0, 38.0, 45.5};
double hourlyRate = 27.88;

// Calculate total payroll estimate:

double payroll = DoubleStream.of(40.0, 36.5, 42.0, 38.0, 45.5)
                             .map(h -> h * 27.88)
                             .sum();

System.out.println(payroll);
Business meaning

Use DoubleStream whenever payroll or rate calculations involve decimals.

17. Common OCP Traps for Primitive Streams;

OptionalDouble avg = IntStream.of(1, 2, 3).average();
// Even IntStream.average() returns OptionalDouble, not OptionalInt.;

// Trap 2: Confusing boxed() with mapToObj()
Stream<Integer> a = IntStream.of(1, 2, 3).boxed();
Stream<String> b = IntStream.of(1, 2, 3).mapToObj(n -> "Zone " + n);

Both convert primitive streams to object streams, but:
// boxed() wraps automatically
// mapToObj() lets you transform into something else

// Trap 3: Forgetting range() end is exclusive
IntStream.range(1, 5).forEach(System.out::print); // 1234
IntStream.rangeClosed(1, 5).forEach(System.out::print); // 12345

// Trap 4: Using consumed streams again
IntStream s = IntStream.of(1, 2, 3);
s.sum();
// s.max(); // IllegalStateException

// Trap 5: Infinite numeric stream + non-terminating terminal operation
LongStream.iterate(1, n -> n + 1).max(); // never ends

8. One Full Enterprise Pipeline Story

// Now let us make one realistic stream pipeline.

// Requirement

// “From a list of officers, calculate the average response time of officers who completed at least 15 patrols.”

record Officer(String name, int patrolsCompleted, double responseTime) {}

List<Officer> officers = List.of(
    new Officer("Adan", 12, 2.5),
    new Officer("Sara", 15, 3.1),
    new Officer("Mohamed", 18, 1.8),
    new Officer("Layla", 10, 4.0)
);

OptionalDouble avg =
    officers.stream()
            .filter(o -> o.patrolsCompleted() >= 15)
            .mapToDouble(Officer::responseTime)
            .average();

System.out.println(avg.orElse(Double.NaN)); // 2.45


20. Final OCP Summary

From your PDF, the most important exam ideas are:

primitive streams exist for efficiency and built-in numeric operations
the three types are IntStream, LongStream, and DoubleStream
common methods include sum(), average(), min(), max(), boxed(), range(), rangeClosed(), and summaryStatistics()
you can map between object and primitive streams with mapToInt(), mapToLong(), mapToDouble(), mapToObj(), and boxed()
primitive streams use OptionalInt, OptionalLong, and OptionalDouble
summaryStatistics() gives multiple numeric results in one terminal operation
infinite primitive streams can still hang if used with the wrong terminal operation