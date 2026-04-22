import java.time.*;
import java.util.*;

public class HospitalSecuritySortingSystem {

    enum EventType {
        BADGE_SCAN,
        VISITOR_CHECKIN,
        PANIC_ALARM,
        SUSPICIOUS_ACTIVITY,
        DOOR_FORCED,
        PATIENT_ESCORT
    }

    // =====================================================
    // 1️⃣ COMPARABLE (NATURAL ORDER)
    // WHY: Define DEFAULT sorting rule (used everywhere automatically)
    // RULE: compareTo() == 0 → equals() must be true
    // =====================================================

    record SecurityEvent(
            long id,
            String employeeName,
            String department,
            EventType type,
            String location,
            int severity,
            boolean resolved,
            LocalDateTime timestamp
    ) implements Comparable<SecurityEvent> {

        @Override
        public int compareTo(SecurityEvent o) {

            // Null safety (VERY IMPORTANT)
            if (o == null) throw new IllegalArgumentException();

            // Natural order: by severity (ascending)
            return this.severity - o.severity;
        }
    }

    public static void main(String[] args) {

        // =====================================================
        // 2️⃣ CREATE DATA
        // =====================================================

        List<SecurityEvent> events = new ArrayList<>(List.of(
                new SecurityEvent(1, "Adan", "ER", EventType.PANIC_ALARM, "ER", 10, false, LocalDateTime.now()),
                new SecurityEvent(2, "Sara", "ICU", EventType.BADGE_SCAN, "Gate", 2, true, LocalDateTime.now()),
                new SecurityEvent(3, "Mohamed", "ER", EventType.DOOR_FORCED, "Pharmacy", 9, false, LocalDateTime.now()),
                new SecurityEvent(4, "Layla", "Lobby", EventType.VISITOR_CHECKIN, "Main", 1, true, LocalDateTime.now())
        ));

        // =====================================================
        // 3️⃣ BASIC SORT (Comparable)
        // =====================================================

        Collections.sort(events);

        // Output:
        // sorted by severity ascending → [1,2,9,10]

        // =====================================================
        // 4️⃣ LIST.SORT (modern alternative)
        // =====================================================

        events.sort(null); // uses Comparable

        // same output as above

        // =====================================================
        // 5️⃣ COMPARATOR (CUSTOM SORT)
        // WHY: when you want different sorting logic
        // =====================================================

        Comparator<SecurityEvent> byName =
                (e1, e2) -> e1.employeeName().compareTo(e2.employeeName());

        Collections.sort(events, byName);

        // Output:
        // sorted alphabetically by name

        // =====================================================
        // 6️⃣ METHOD REFERENCE (BEST PRACTICE)
        // =====================================================

        Comparator<SecurityEvent> bySeverity =
                Comparator.comparingInt(SecurityEvent::severity);

        events.sort(bySeverity);

        // Output:
        // sorted by severity ascending

        // =====================================================
        // 7️⃣ REVERSE SORT
        // =====================================================

        events.sort(bySeverity.reversed());

        // Output:
        // [10, 9, 2, 1]

        // =====================================================
        // 8️⃣ MULTI-FIELD SORTING (VERY IMPORTANT)
        // =====================================================

        Comparator<SecurityEvent> multiSort =
                Comparator.comparing(SecurityEvent::department)
                        .thenComparingInt(SecurityEvent::severity);

        events.sort(multiSort);

        // Output:
        // First grouped by department, then by severity

        // =====================================================
        // 9️⃣ HELPER METHODS
        // =====================================================

        Comparator<SecurityEvent> natural = Comparator.naturalOrder();
        Comparator<SecurityEvent> reverse = Comparator.reverseOrder();

        events.sort(natural);
        events.sort(reverse);

        // =====================================================
        // 🔟 SORTING WITHOUT COMPARABLE
        // =====================================================

        List<SecurityEvent> list2 = new ArrayList<>(events);

        list2.sort((a, b) -> a.id() > b.id() ? 1 : -1);

        // Output:
        // sorted by ID

        // =====================================================
        // 1️⃣1️⃣ REVERSE LIST (NOT SORTING)
        // =====================================================

        Collections.reverse(list2);

        // Output:
        // reversed order (not sorted)

        // =====================================================
        // 1️⃣2️⃣ BINARY SEARCH (VERY IMPORTANT)
        // RULE: LIST MUST BE SORTED FIRST
        // =====================================================

        Collections.sort(events); // MUST sort first

        int index = Collections.binarySearch(events, events.get(0));

        // Output:
        // index of found element

        // =====================================================
        // 1️⃣3️⃣ BINARY SEARCH NOT FOUND
        // =====================================================

        int result = Collections.binarySearch(events,
                new SecurityEvent(99,"X","ER",EventType.PANIC_ALARM,"ER",5,false,LocalDateTime.now()));

        // Output:
        // negative number → -(insertion point) - 1

        // =====================================================
        // 1️⃣4️⃣ BINARY SEARCH WITH COMPARATOR
        // =====================================================

        Comparator<SecurityEvent> comp = Comparator.comparingInt(SecurityEvent::severity);

        events.sort(comp);

        int idx = Collections.binarySearch(events, events.get(0), comp);

        // RULE: must use SAME comparator as sorting

        // =====================================================
        // 1️⃣5️⃣ TREESET SORTING (IMPORTANT)
        // =====================================================

        Set<SecurityEvent> set = new TreeSet<>();

        set.addAll(events);

        // Output:
        // automatically sorted using Comparable

        // =====================================================
        // 1️⃣6️⃣ TREESET WITH COMPARATOR
        // =====================================================

        Set<SecurityEvent> set2 =
                new TreeSet<>(Comparator.comparing(SecurityEvent::employeeName));

        set2.addAll(events);

        // Output:
        // sorted by employeeName

        // =====================================================
        // ⚠️ IMPORTANT RULES
        // =====================================================

        /*
        1. compareTo() == 0 → equals() must be true
        2. Always handle null in compareTo()
        3. Binary search requires sorted list
        4. TreeSet requires Comparable or Comparator
        5. Comparator must match sorting for binarySearch
        */

    }
}