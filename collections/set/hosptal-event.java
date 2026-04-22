PROFESSIONAL SCENARIO — Access Control & De-duplication (Set)
🧭 Business Context

In the hospital security platform, you need to:

Prevent duplicate alerts (same event processed twice)
Track unique badge IDs currently inside secure zones
Maintain a blocklist of employees with revoked access
Quickly check membership (is this ID already seen / blocked?)

👉 These are classic Set problems:

Uniqueness enforced
Fast contains() checks
No indexing needed


Type	When to use	Why
HashSet	Default choice	Fast add/contains/remove (≈ O(1))
LinkedHashSet	Need insertion order	Predictable iteration order
TreeSet	Need sorted order	Keeps elements sorted (log n ops)


import java.time.*;
import java.util.*;

enum EventType {
    BADGE_SCAN, VISITOR_CHECKIN, PANIC_ALARM,
    SUSPICIOUS_ACTIVITY, DOOR_FORCED, PATIENT_ESCORT
}

// Equality choice matters for Set behavior.
// Here we define uniqueness by (id).
record SecurityEvent(
    long id,
    String employeeName,
    String department,
    EventType type,
    String location,
    int severity,
    boolean resolved,
    LocalDateTime timestamp
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof SecurityEvent e) && this.id == e.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


import java.time.*;
import java.util.*;

public class HospitalSecuritySetDemo {

    public static void main(String[] args) {

        // =====================================================
        // 1️⃣ SOURCE (Immutable input)
        // WHY: treat inbound data as read-only snapshot
        // =====================================================
        List<SecurityEvent> incoming = List.of(
            new SecurityEvent(1, "Adan", "ER", EventType.PANIC_ALARM, "ER Room 4", 10, false, LocalDateTime.now()),
            new SecurityEvent(2, "Sara", "ICU", EventType.BADGE_SCAN, "ICU Gate", 2, true, LocalDateTime.now()),
            new SecurityEvent(1, "Adan", "ER", EventType.PANIC_ALARM, "ER Room 4", 10, false, LocalDateTime.now()) // duplicate id
        );

        // =====================================================
        // 2️⃣ DE-DUPLICATION (HashSet)
        // WHY: remove duplicates automatically by id
        // =====================================================
        Set<SecurityEvent> uniqueEvents = new HashSet<>(incoming);
        // Output: only 2 unique events (id=1, id=2)

        // size()
        int count = uniqueEvents.size(); // 2

        // isEmpty()
        boolean empty = uniqueEvents.isEmpty(); // false

        // =====================================================
        // 3️⃣ MEMBERSHIP CHECKS (FAST contains)
        // WHY: constant-time checks for security rules
        // =====================================================
        boolean hasAdanEvent = uniqueEvents.contains(
            new SecurityEvent(1, "Adan", "ER", EventType.PANIC_ALARM, "ER Room 4", 10, false, LocalDateTime.now())
        );
        // true (same id)

        // =====================================================
        // 4️⃣ ADD / ADDALL
        // WHY: ingest new events
        // =====================================================
        uniqueEvents.add(new SecurityEvent(3, "Mohamed", "ER", EventType.DOOR_FORCED, "Pharmacy", 9, false, LocalDateTime.now()));
        // returns true if added, false if duplicate

        Set<SecurityEvent> more = Set.of(
            new SecurityEvent(4, "Layla", "Lobby", EventType.VISITOR_CHECKIN, "Main Entrance", 1, true, LocalDateTime.now()),
            new SecurityEvent(5, "Khalid", "Psych", EventType.SUSPICIOUS_ACTIVITY, "Psych Ward", 8, false, LocalDateTime.now())
        );
        uniqueEvents.addAll(more); // union (adds all new uniques)

        // =====================================================
        // 5️⃣ REMOVE / REMOVEALL / REMOVEIF
        // WHY: clean up or enforce policies
        // =====================================================
        uniqueEvents.remove(new SecurityEvent(2, "Sara", "ICU", EventType.BADGE_SCAN, "ICU Gate", 2, true, LocalDateTime.now()));
        // remove by value (id-based)

        uniqueEvents.removeIf(e -> e.severity() < 3);
        // remove low-severity noise

        uniqueEvents.removeAll(more);
        // remove a group (set difference)

        // =====================================================
        // 6️⃣ RETAINALL (INTERSECTION)
        // WHY: keep only events that match a whitelist
        // =====================================================
        Set<SecurityEvent> whitelist = Set.of(
            new SecurityEvent(3, "Mohamed", "ER", EventType.DOOR_FORCED, "Pharmacy", 9, false, LocalDateTime.now())
        );
        uniqueEvents.retainAll(whitelist);
        // now only id=3 remains

        // =====================================================
        // 7️⃣ ITERATION (no index!)
        // WHY: process all unique events
        // =====================================================
        for (SecurityEvent e : uniqueEvents) {
            // process e
        }

        Iterator<SecurityEvent> it = uniqueEvents.iterator();
        while (it.hasNext()) {
            SecurityEvent e = it.next();
        }

        // =====================================================
        // 8️⃣ CONTAINSALL (subset check)
        // WHY: verify all required events present
        // =====================================================
        boolean hasAll = uniqueEvents.containsAll(whitelist); // true

        // =====================================================
        // 9️⃣ TOARRAY (integration with legacy APIs)
        // =====================================================
        Object[] arr = uniqueEvents.toArray();
        SecurityEvent[] typed = uniqueEvents.toArray(new SecurityEvent[0]);

        // =====================================================
        // 🔟 ORDERED SET (LinkedHashSet)
        // WHY: keep insertion order for UI/logs
        // =====================================================
        Set<String> badgeOrder = new LinkedHashSet<>();
        badgeOrder.add("BADGE-1");
        badgeOrder.add("BADGE-2");
        badgeOrder.add("BADGE-1"); // ignored duplicate
        // Iteration order: BADGE-1, BADGE-2

        // =====================================================
        // 1️⃣1️⃣ SORTED SET (TreeSet)
        // WHY: always sorted (e.g., by badge ID)
        // =====================================================
        Set<Integer> severityLevels = new TreeSet<>();
        severityLevels.add(10);
        severityLevels.add(3);
        severityLevels.add(7);
        // Iteration order: 3, 7, 10

        // =====================================================
        // 1️⃣2️⃣ CLEAR
        // =====================================================
        uniqueEvents.clear();
        // now empty
    }

    // Same record as above (included here for completeness)
    enum EventType {
        BADGE_SCAN, VISITOR_CHECKIN, PANIC_ALARM,
        SUSPICIOUS_ACTIVITY, DOOR_FORCED, PATIENT_ESCORT
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
    ) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            return (o instanceof SecurityEvent e) && this.id == e.id;
        }
        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}