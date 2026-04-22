Full Professional Scenario: Hospital Security Incident Monitoring System

Imagine you are building a hospital security monitoring system for a large medical center.

Every day, the hospital receives thousands of security event records, such as:

unauthorized entry attempts
visitor check-ins
staff badge scans
emergency panic alarms
patient transport alerts
suspicious activity reports

You want to process this data using Java Streams.

This is perfect because Streams are designed for pipeline-style data processing, where data flows through stages like an assembly line: source → intermediate operations → terminal operation. That exact idea is explained in your guide.

import java.time.*;
import java.util.*;
import java.util.stream.*;

enum EventType {
    BADGE_SCAN,
    VISITOR_CHECKIN,
    PANIC_ALARM,
    SUSPICIOUS_ACTIVITY,
    DOOR_FORCED,
    PATIENT_ESCORT
}

record SecurityEvent(
        long id,
        String employeeName,
        String department,
        EventType type,
        String location,
        int severity,
        boolean resolved,
        LocalDateTime timestamp
) {};

List<SecurityEvent> events = List.of(
    new SecurityEvent(1, "Adan", "ER", EventType.PANIC_ALARM, "ER Room 4", 10, false, LocalDateTime.now().minusMinutes(10)),
    new SecurityEvent(2, "Sara", "ICU", EventType.BADGE_SCAN, "ICU Gate", 2, true, LocalDateTime.now().minusMinutes(30)),
    new SecurityEvent(3, "Mohamed", "ER", EventType.DOOR_FORCED, "Pharmacy", 9, false, LocalDateTime.now().minusMinutes(5)),
    new SecurityEvent(4, "Layla", "Lobby", EventType.VISITOR_CHECKIN, "Main Entrance", 1, true, LocalDateTime.now().minusHours(2)),
    new SecurityEvent(5, "Khalid", "Psych", EventType.SUSPICIOUS_ACTIVITY, "Psych Ward", 8, false, LocalDateTime.now().minusMinutes(15)),
    new SecurityEvent(6, "Asha", "ER", EventType.BADGE_SCAN, "ER Gate", 3, true, LocalDateTime.now().minusMinutes(20)),
    new SecurityEvent(7, "Hani", "ICU", EventType.PATIENT_ESCORT, "ICU Hall", 4, true, LocalDateTime.now().minusMinutes(25)),
    new SecurityEvent(8, "Fatima", "Pharmacy", EventType.DOOR_FORCED, "Drug Storage", 10, false, LocalDateTime.now().minusMinutes(2))
);


// every useful pipeline has three logical parts:

Source
Intermediate operations
Terminal operation

Stream<SecurityEvent> stream = events.stream();

// Example A: Find unresolved critical incidents.          
List<SecurityEvent> criticalOpenIncidents =
    events.stream()                              // source
          .filter(e -> !e.resolved())           // intermediate
          .filter(e -> e.severity() >= 8)       // intermediate
          .sorted(Comparator.comparing(SecurityEvent::severity).reversed()) // intermediate
          .toList();                            // terminal

        
//   Lazy Evaluation in Real Life
// nothing really happens until a terminal operation is called.
Stream<SecurityEvent> pipeline =events.stream()
          .filter(e -> {
              System.out.println("Filtering: " + e.id());
              return e.severity() >= 8;
          });

System.out.println("Nothing executed yet");
// Only when you add a terminal operation:
long count = pipeline.count();
System.out.println(count);


// streams behave like an assembly line: once data passes forward, it does not come back. A stream is consumed after terminal execution. You cannot reuse it.
Stream<SecurityEvent> s = events.stream();
long total = s.count();
// long again = s.count();   // throws IllegalStateException



// Finite vs Infinite Streams

// the events list is finite:
events.stream()
      .filter(e -> e.severity() >= 8)
      .forEach(System.out::println);

    //   Infinite stream example

Suppose the hospital simulates endless sensor pings:

Stream<String> alarmFeed = Stream.generate(() -> "Sensor ping");
alarmFeed.count(); // never ends

// But this is safe:
alarmFeed.limit(5).forEach(System.out::println);

// Stream Sources in the Professional Scenario

// A. From collection
Stream<SecurityEvent> fromList = events.stream();

// B. Empty stream
Suppose a hospital wing has no new alerts:

Stream<SecurityEvent> noEvents = Stream.empty();
System.out.println(noEvents.count()); // 0

// C. Stream.of()
Stream<String> departments = Stream.of("ER", "ICU", "Psych");

Useful for fixed configuration values.

// D. Infinite generate()
Stream<Double> randomRiskScores = Stream.generate(Math::random);

Could simulate probabilistic threat scoring.

// E. iterate()
Stream<Integer> badgeNumbers = Stream.iterate(1000, n -> n + 1);

Could generate sequential fake IDs for testing.

// F. Controlled iterate()
Stream<Integer> testLevels = Stream.iterate(1, n -> n < 6, n -> n + 1);
testLevels.forEach(System.out::println); // 1 2 3 4 5
This is the controlled form your guide mentions.


// 9. Intermediate Operations in One Professional Story

Your file lists the key intermediate operations: filter, distinct, limit, skip, map, flatMap, concat, sorted, peek.

Let us apply all of them.


//================================
  9.1 filter()
//================================
// Show only unresolved incidents:

events.stream()
      .filter(e -> !e.resolved())
      .forEach(System.out::println);
Business meaning
Focus only on active security problems.

// 9.2 distinct()
// Suppose you want unique departments with incidents:

events.stream()
      .map(SecurityEvent::department)
      .distinct()
      .forEach(System.out::println);
Business meaning
See which departments need security attention.


// Set<String> seenDepartments = new LinkedHashSet<>();

// for (SecurityEvent e : events) {

//     String dept = e.department();   // map step

//     if (!seenDepartments.contains(dept)) {  // distinct step
//         seenDepartments.add(dept);
//         System.out.println(dept);           // terminal (forEach)
//     }
// }

distinct() removes duplicates using equality, exactly as your file states.

// 9.3 limit()

Show only the top 3 most recent urgent events:

events.stream()
      .filter(e -> e.severity() >= 8)
      .sorted(Comparator.comparing(SecurityEvent::timestamp).reversed())
      .limit(3)
      .forEach(System.out::println);

// SecurityEvent[8, Fatima, Pharmacy, DOOR_FORCED, Drug Storage, 10, false, now-2min]
// SecurityEvent[3, Mohamed, ER, DOOR_FORCED, Pharmacy, 9, false, now-5min]
// SecurityEvent[1, Adan, ER, PANIC_ALARM, ER Room 4, 10, false, now-10min]

Business meaning
A supervisor dashboard often shows only the top few urgent alerts.

// // STEP 1: "filter" → keep only high severity
// List<SecurityEvent> step1 = new ArrayList<>();

// for (SecurityEvent e : events) {
//     if (e.severity() >= 8) {
//         step1.add(e);
//     }
// }

// // STEP 2: "sorted" → sort by timestamp DESC
// List<SecurityEvent> step2 = new ArrayList<>(step1);

// step2.sort(
//     Comparator.comparing(SecurityEvent::timestamp).reversed()
// );

// // STEP 3: "limit" → take first 3
// List<SecurityEvent> step3 = new ArrayList<>();

// for (int i = 0; i < step2.size() && i < 3; i++) {
//     step3.add(step2.get(i));
// }

// // STEP 4: "forEach" → print
// for (SecurityEvent e : step3) {
//     System.out.println(e);
// }




9.4 skip()

Suppose page 2 of a dashboard starts after the first 5 records:

events.stream()
      .sorted(Comparator.comparing(SecurityEvent::timestamp).reversed())
      .skip(5)
      .forEach(System.out::println);
Business meaning
Pagination.
// SecurityEvent[7, Hani, ICU, PATIENT_ESCORT, ICU Hall, 4, true, now-25min]
// SecurityEvent[2, Sara, ICU, BADGE_SCAN, ICU Gate, 2, true, now-30min]
// SecurityEvent[4, Layla, Lobby, VISITOR_CHECKIN, Main Entrance, 1, true, now-2hours]


9.5 map()
Convert full SecurityEvent objects into a display string:

events.stream()
      .map(e -> e.location() + " - " + e.type())
      .forEach(System.out::println);

// ER Room 4 - PANIC_ALARM
// ICU Gate - BADGE_SCAN
// Pharmacy - DOOR_FORCED
// Main Entrance - VISITOR_CHECKIN
// Psych Ward - SUSPICIOUS_ACTIVITY
// ER Gate - BADGE_SCAN
// ICU Hall - PATIENT_ESCORT
// Drug Storage - DOOR_FORCED

Business meaning
// Transform raw objects into report lines.

9.6 flatMap()

Suppose each department has multiple shift supervisors, and you want one flat stream of all names.

List<List<String>> supervisors = List.of(
    List.of("Adan", "Sara"),
    List.of("Mohamed", "Layla"),
    List.of("Fatima")
);

supervisors.stream()
           .flatMap(List::stream)
           .forEach(System.out::println);// adan sara mohamed layla fatima

// supervisors.stream()
//            .flatMap(list -> list.stream())
//            .forEach(name -> System.out.println(name));


// imperative version
// for (List<String> group : supervisors) {
//     for (String name : group) {
//         System.out.println(name);
//     }
// }
           
Business meaning
// Flatten nested collections into one continuous stream.

This is exactly the real purpose of flatMap().

9.7 concat()

Combine ER alerts with ICU alerts:

Stream<SecurityEvent> erEvents =
    events.stream().filter(e -> e.department().equals("ER"));

Stream<SecurityEvent> icuEvents =
    events.stream().filter(e -> e.department().equals("ICU"));

Stream.concat(erEvents, icuEvents)
      .forEach(System.out::println); //results all ER and ICU events together
      // SecurityEvent[1, Adan, ER, PANIC_ALARM, ER Room 4, 10, false, now-10min]
      // SecurityEvent[3, Mohamed, ER, DOOR_FORCED, Pharmacy, 9, false, now-5min]
      // SecurityEvent[6, Asha, ER, BADGE_SCAN, ER Gate, 3, true, now-20min]
      // SecurityEvent[2, Sara, ICU, BADGE_SCAN, ICU Gate, 2, true, now-30min]
      // SecurityEvent[7, Hani, ICU, PATIENT_ESCORT, ICU Hall, 4, true, now-25min]
Business meaning

Merge separate streams into one flow.

9.8 sorted()

Sort by severity:

events.stream()
      .sorted(Comparator.comparing(SecurityEvent::severity))
      .forEach(System.out::println);

Or highest first:

events.stream()
      .sorted(Comparator.comparing(SecurityEvent::severity).reversed())
      .forEach(System.out::println);
Business meaning
// Prioritize critical incidents first.
// Your file warns that sorting can use natural order or comparator and also warns about comparator pitfalls.

9.9 peek()
events.stream()
      .filter(e -> e.severity() >= 8)
      .peek(e -> System.out.println("Passing through pipeline: " + e.id()))
      .count();
Business meaning
// Debug pipeline flow.
Your file says peek() should be used for debugging only and side effects should be avoided. That is important for the exam.

10. Terminal Operations in One Professional Story

// Your guide covers major terminal operations like count, min, max, findAny, findFirst, matching methods, forEach, reduce, and collect.

Now let us apply them properly.

10.1 count()

Count unresolved incidents:

long openCount = events.stream()
                       .filter(e -> !e.resolved())
                       .count();
System.out.println("Open incidents: " + openCount);// Open incidents: 4
Business meaning

// “How many open security issues do we currently have?”
count() is terminal and will not terminate for truly infinite streams.

10.2 min() and max()

Find least severe and most severe incident:

Optional<SecurityEvent> leastSevere =
    events.stream()
          .min(Comparator.comparing(SecurityEvent::severity));
          // SecurityEvent[4, Layla, Lobby, VISITOR_CHECKIN, Main Entrance, 1, true, now-2hours]

Optional<SecurityEvent> mostSevere =
    events.stream()
          .max(Comparator.comparing(SecurityEvent::severity));
          // SecurityEvent[1, Adan, ER, PANIC_ALARM, ER Room 4, 10, false, now-10min]
Business meaning
// minimum severity = routine event
// maximum severity = crisis-level event


10.3 findFirst()
// Find first unresolved alert in encounter order:

Optional<SecurityEvent> firstOpen =
    events.stream()
          .filter(e -> !e.resolved())
          .findFirst();// SecurityEvent[1, Adan, ER, PANIC_ALARM, ER Room 4, 10, false, now-10min]
Business meaning
Useful when order matters.

10.4 findAny()
Optional<SecurityEvent> anyCritical =
    events.stream()
          .filter(e -> e.severity() >= 9)
          .findAny();//may return Adan, Mohamed, or Fatima
      // Optional[SecurityEvent[1, Adan, ER, PANIC_ALARM, ...]] or
      // Optional[SecurityEvent[3, Mohamed, ER, DOOR_FORCED, ...]] or
      // Optional[SecurityEvent[8, Fatima, Pharmacy, DOOR_FORCED, ...]]
Business meaning
Just find one critical event quickly.
This may be especially useful with parallel streams, where “any” is cheaper than “first”.

10.5 anyMatch(), allMatch(), noneMatch()
anyMatch()
boolean hasCritical =
    events.stream()
          .anyMatch(e -> e.severity() >= 9);// true because of Adan and Fatima

Meaning: “Do we have at least one critical incident?”

allMatch()
boolean allResolved =
    events.stream()
          .allMatch(SecurityEvent::resolved);// false because of Adan, Mohamed, Khalid, and Fatima are unresolved

Meaning: “Is every event resolved?”

noneMatch()
boolean noLobbyThreats =
    events.stream()
          .noneMatch(e -> e.location().contains("Lobby") && e.severity() >= 8);// false because of Layla's visitor check-in in the lobby, but it is low severity

Meaning: “Are there zero serious lobby threats?”

Your guide notes these are boolean matching operations and may or may not terminate, especially on infinite streams.

10.6 forEach()
events.stream()
      .filter(e -> e.severity() >= 8)
      .forEach(System.out::println);
// SecurityEvent[1, Adan, ER, PANIC_ALARM, ER Room 4, 10, false, now-10min]
// SecurityEvent[3, Mohamed, ER, DOOR_FORCED, Pharmacy, 9, false, now-5min]
// SecurityEvent[5, Khalid, Psych, SUSPICIOUS_ACTIVITY, Psych Ward, 8, false, now-15min]
// SecurityEvent[8, Fatima, Pharmacy, DOOR_FORCED, Drug Storage, 10, false, now-2min]     

Business meaning
// Print emergency dashboard output.

// Your file clearly notes forEach() is terminal and that streams do not use the normal for-each loop syntax.

10.7 reduce()

// Suppose the hospital wants the total severity score of all unresolved incidents:

int totalSeverity =
    events.stream()
          .filter(e -> !e.resolved())
          .map(SecurityEvent::severity)
          .reduce(0, Integer::sum);// 10 + 9 + 8 + 10 = 37

int total =
    events.stream()
          .map(SecurityEvent::severity)
          .reduce(0, (a, b) -> Integer.sum(a, b));

int total =
    events.stream()
          .map(SecurityEvent::severity)
          .reduce(0, (a, b) -> a + b);          


Business meaning
Measure overall risk load.

Another example: highest severity using reduction:

Optional<Integer> maxSeverity =
    events.stream()
          .map(SecurityEvent::severity)
          .reduce(Integer::max);

Your file says reduce() combines all elements into one result and supports identity/accumulator and other forms.

10.8 collect()

// Collect unresolved events into a list:
List<SecurityEvent> unresolved =
    events.stream()
          .filter(e -> !e.resolved())
          .collect(Collectors.toList());// List of 4 unresolved events

//  Imperative Equivalent
List<SecurityEvent> unresolved = new ArrayList<>();

for (SecurityEvent e : events) {
    if (!e.resolved()) {
        unresolved.add(e);
    }
}         

// Collect unique departments into a set:
Set<String> departmentsWithIncidents =
    events.stream()
          .map(SecurityEvent::department)
          .collect(Collectors.toSet());

// Group events by department:
Map<String, List<SecurityEvent>> byDepartment =
    events.stream()
          .collect(Collectors.groupingBy(SecurityEvent::department));

// Count events by type:
Map<EventType, Long> countsByType =
    events.stream()
          .collect(Collectors.groupingBy(SecurityEvent::type, Collectors.counting()));
Business meaning

This is how real dashboards, reports, and analytics are built.

Your guide explains collect() as mutable reduction and highlights collectors.


// Requirement

// “Show the names and locations of the top 3 unresolved high-severity incidents, sorted by severity descending.”

List<String> dashboard =
    events.stream()
          .filter(e -> !e.resolved())
          .filter(e -> e.severity() >= 8)
          .sorted(Comparator.comparing(SecurityEvent::severity).reversed())
          .limit(3)
          .map(e -> e.employeeName() + " | " + e.location() + " | severity=" + e.severity())
          .toList();


// Parallel Stream Scenario
Suppose the hospital is processing 5 million archived security logs to calculate statistics.

long criticalCount =
    events.parallelStream()
          .filter(e -> e.severity() >= 8)
          .count();


        //   OCP exam point
Parallel streams can improve performance, but they add coordination overhead and may not preserve intuitive order.

Stream<String> testAlarms =
    Stream.iterate(1, n -> n + 1)
          .map(n -> "ALARM-" + n);

        //   Safe use:
testAlarms.limit(5).forEach(System.out::println);
Output:
// ALARM-1
// ALARM-2
// ALARM-3
// ALARM-4
// ALARM-5

Dangerous use:
testAlarms.count(); // never finishes

// 14. Common OCP Traps Inside the Scenario

// Trap 1: No terminal operation means no execution
events.stream()
      .filter(e -> e.severity() > 5); // nothing happens

//   Trap 2: Stream cannot be reused
Stream<SecurityEvent> s = events.stream();
s.count();
// s.forEach(System.out::println); // IllegalStateException    


// Trap 3: findFirst() vs findAny()
// findFirst() cares about encounter order
// findAny() may return any matching element, especially in parallel
// Trap 4: reduce() is not the same as collect()
// reduce() combines into one value
// collect() gathers into mutable structures

Stream.generate(Math::random).count(); // never ends

Trap 6: peek() is not business logic

// Use it for debugging, not for real mutation logic. Your file explicitly warns about that.

import java.time.*;
import java.util.*;
import java.util.stream.*;

public class HospitalSecurityStreamDemo {

    enum EventType {
        BADGE_SCAN,
        VISITOR_CHECKIN,
        PANIC_ALARM,
        SUSPICIOUS_ACTIVITY,
        DOOR_FORCED,
        PATIENT_ESCORT
    }

    record SecurityEvent(
            long id,
            String employeeName,
            String department,
            EventType type,
            String location,
            int severity,
            boolean resolved,
            LocalDateTime timestamp
    ) {}

    public static void main(String[] args) {
        List<SecurityEvent> events = List.of(
            new SecurityEvent(1, "Adan", "ER", EventType.PANIC_ALARM, "ER Room 4", 10, false, LocalDateTime.now().minusMinutes(10)),
            new SecurityEvent(2, "Sara", "ICU", EventType.BADGE_SCAN, "ICU Gate", 2, true, LocalDateTime.now().minusMinutes(30)),
            new SecurityEvent(3, "Mohamed", "ER", EventType.DOOR_FORCED, "Pharmacy", 9, false, LocalDateTime.now().minusMinutes(5)),
            new SecurityEvent(4, "Layla", "Lobby", EventType.VISITOR_CHECKIN, "Main Entrance", 1, true, LocalDateTime.now().minusHours(2)),
            new SecurityEvent(5, "Khalid", "Psych", EventType.SUSPICIOUS_ACTIVITY, "Psych Ward", 8, false, LocalDateTime.now().minusMinutes(15)),
            new SecurityEvent(6, "Asha", "ER", EventType.BADGE_SCAN, "ER Gate", 3, true, LocalDateTime.now().minusMinutes(20)),
            new SecurityEvent(7, "Hani", "ICU", EventType.PATIENT_ESCORT, "ICU Hall", 4, true, LocalDateTime.now().minusMinutes(25)),
            new SecurityEvent(8, "Fatima", "Pharmacy", EventType.DOOR_FORCED, "Drug Storage", 10, false, LocalDateTime.now().minusMinutes(2))
        );

        long openCount = events.stream()
                .filter(e -> !e.resolved())
                .count();
        System.out.println("Open incidents: " + openCount);

        List<String> topCritical = events.stream()
                .filter(e -> !e.resolved())
                .filter(e -> e.severity() >= 8)
                .sorted(Comparator.comparing(SecurityEvent::severity).reversed())
                .limit(3)
                .map(e -> e.employeeName() + " | " + e.location() + " | severity=" + e.severity())
                .toList();
        System.out.println(topCritical);

        Optional<SecurityEvent> firstCritical = events.stream()
                .filter(e -> e.severity() >= 9)
                .findFirst();
        System.out.println("First critical: " + firstCritical);

        boolean anyPanic = events.stream()
                .anyMatch(e -> e.type() == EventType.PANIC_ALARM);
        System.out.println("Any panic alarm? " + anyPanic);

        int totalOpenSeverity = events.stream()
                .filter(e -> !e.resolved())
                .map(SecurityEvent::severity)
                .reduce(0, Integer::sum);
        System.out.println("Total open severity: " + totalOpenSeverity);

        Map<String, List<SecurityEvent>> byDepartment = events.stream()
                .collect(Collectors.groupingBy(SecurityEvent::department));
        System.out.println("Grouped by department: " + byDepartment.keySet());

        Set<String> uniqueDepartments = events.stream()
                .map(SecurityEvent::department)
                .distinct()
                .collect(Collectors.toSet());
        System.out.println("Unique departments: " + uniqueDepartments);

        Stream<String> testAlarms = Stream.iterate(1, n -> n + 1)
                .map(n -> "ALARM-" + n);

        testAlarms.limit(5).forEach(System.out::println);
    }
}