Perfect—this is the Java 21+ layer that ties everything together: Sequenced Collections.
I’ll apply it to your Hospital Security System and show real, practical uses, with:

✅ SequencedCollection, SequencedSet, SequencedMap
✅ All key methods (addFirst, getLast, reversed, etc.)
✅ Where it works vs where it throws
✅ WHY comments + expected outputs as comments
✅ Professional, single-file example



import java.time.*;
import java.util.*;

public class HospitalSecuritySequencedSystem {

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
    ) implements Comparable<SecurityEvent> {

        // Natural order (for TreeSet / TreeMap sorting)
        @Override
        public int compareTo(SecurityEvent o) {
            return this.severity - o.severity;
        }
    }

    public static void main(String[] args) {

        // =====================================================
        // 1️⃣ SEQUENCED COLLECTION (List / ArrayList)
        // WHY:
        // → Events arrive in order → need predictable processing
        // =====================================================

        SequencedCollection<SecurityEvent> seqList =
                new ArrayList<>();
        seqList.add(new SecurityEvent(1,"Adan","ER",EventType.PANIC_ALARM,"ER",10,false,LocalDateTime.now())); // ✅ OK
        seqList.addLast(new SecurityEvent(1,"Adan","ER",EventType.PANIC_ALARM,"ER",10,false,LocalDateTime.now()));
        seqList.addLast(new SecurityEvent(2,"Sara","ICU",EventType.BADGE_SCAN,"Gate",2,true,LocalDateTime.now()));

        seqList.addFirst(new SecurityEvent(0,"URGENT","ICU",EventType.PANIC_ALARM,"ICU",10,false,LocalDateTime.now()));
        // WHY: urgent events inserted at front

        // Order:
        // [URGENT, Adan, Sara]
        SequencedCollection<SecurityEvent> reversed = seqList.reversed();
        // Output:
        // [Sara, Adan, URGENT]

        SecurityEvent first = seqList.getFirst();
        // Output: URGENT

        SecurityEvent last = seqList.getLast();
        // Output: Sara

        seqList.removeFirst();
        // removes URGENT

        seqList.removeLast();
        // removes Sara

        // Remaining:
        // [Adan]

        // =====================================================
        // 2️⃣ REVERSED VIEW (VERY IMPORTANT)
        // WHY:
        // → audit logs / reverse timeline view
        // =====================================================

        SequencedCollection<SecurityEvent> reversed = seqList.reversed();

        // Output (reverse order view):
        // same elements but reversed

        // NOTE:
        // This is a VIEW, not a copy

        // =====================================================
        // 3️⃣ MOVE FIRST TO LAST (REAL USE CASE)
        // WHY:
        // → retry failed event at end of queue
        // =====================================================

        if (!seqList.isEmpty()) {
            seqList.addLast(seqList.removeFirst());
        }

        // =====================================================
        // 4️⃣ LINKEDLIST (BEST SEQUENCED COLLECTION)
        // WHY:
        // → supports fast front/back operations
        // =====================================================

        SequencedCollection<SecurityEvent> linked =
                new LinkedList<>();

        linked.addFirst(new SecurityEvent(10,"Emergency","ICU",EventType.PANIC_ALARM,"ICU",10,false,LocalDateTime.now()));
        linked.addLast(new SecurityEvent(11,"Normal","ER",EventType.BADGE_SCAN,"Gate",2,true,LocalDateTime.now()));

        // Output:
        // [Emergency, Normal]

        // =====================================================
        // 5️⃣ TREESET (SORTED + SEQUENCED)
        // =====================================================

        SequencedSet<SecurityEvent> sortedSet =
                new TreeSet<>();

        sortedSet.add(new SecurityEvent(1,"A","ER",EventType.PANIC_ALARM,"ER",10,false,LocalDateTime.now()));
        sortedSet.add(new SecurityEvent(2,"B","ER",EventType.PANIC_ALARM,"ER",5,false,LocalDateTime.now()));

        // Output:
        // Sorted by severity → [5, 10]

        sortedSet.getFirst();
        // Output: lowest severity

        sortedSet.getLast();
        // Output: highest severity

        // ⚠️ IMPORTANT TRAP
        // sortedSet.addLast(...) ❌ UnsupportedOperationException
        // WHY:
        // → TreeSet is sorted, cannot control position manually

        // =====================================================
        // 6️⃣ SEQUENCED SET (ORDERED SET)
        // =====================================================

        SequencedSet<String> badgeSet =
                new LinkedHashSet<>();

        badgeSet.add("Badge1");
        badgeSet.add("Badge2");
        badgeSet.add("Badge1"); // duplicate ignored

        // Output:
        // [Badge1, Badge2] (in insertion order)

        badgeSet.getFirst();
        // Output: Badge1

        badgeSet.getLast();
        // Output: Badge2

        // =====================================================
        // 7️⃣ SEQUENCED MAP (VERY IMPORTANT)
        // WHY:
        // → maintain order of events by ID
        // =====================================================

        SequencedMap<Long, SecurityEvent> seqMap =
                new LinkedHashMap<>();

        seqMap.putLast(1L, new SecurityEvent(1,"Adan","ER",EventType.PANIC_ALARM,"ER",10,false,LocalDateTime.now()));
        seqMap.putLast(2L, new SecurityEvent(2,"Sara","ICU",EventType.BADGE_SCAN,"Gate",2,true,LocalDateTime.now()));

        seqMap.putFirst(0L, new SecurityEvent(0,"URGENT","ICU",EventType.PANIC_ALARM,"ICU",10,false,LocalDateTime.now()));

        // Order:
        // [0=URGENT, 1=Adan, 2=Sara]

        Map.Entry<Long, SecurityEvent> firstEntry = seqMap.firstEntry();
        // Output: 0=URGENT

        Map.Entry<Long, SecurityEvent> lastEntry = seqMap.lastEntry();
        // Output: 2=Sara

        seqMap.pollFirstEntry();
        // removes first

        seqMap.pollLastEntry();
        // removes last

        // Remaining:
        // [1=Adan]

        // =====================================================
        // 8️⃣ REVERSED MAP
        // =====================================================

        SequencedMap<Long, SecurityEvent> reversedMap = seqMap.reversed();

        // Output:
        // reversed order view

        // =====================================================
        // 9️⃣ TREEMAP (SORTED SEQUENCED MAP)
        // =====================================================

        SequencedMap<Integer, String> sortedMap =
                new TreeMap<>();

        sortedMap.put(5,"Low");
        sortedMap.put(1,"Critical");
        sortedMap.put(3,"Medium");

        // Output:
        // Sorted by key → [1,3,5]

        sortedMap.firstEntry();
        // Output: 1=Critical

        sortedMap.lastEntry();
        // Output: 5=Low

        // =====================================================
        // 🔟 IMPORTANT RULES (CRITICAL)
        // =====================================================

        /*
        1. Sequenced = predictable order (insertion or defined)
        2. Sorted = order defined by comparator or natural order
        3. Not all operations supported:
           TreeSet → cannot addFirst/addLast
        4. reversed() → returns VIEW (not copy)
        5. HashMap / HashSet → NOT sequenced
        */
    }
}


WHAT YOU JUST MASTERED
✅ SequencedCollection
addFirst, addLast
getFirst, getLast
removeFirst, removeLast
reversed()
✅ SequencedSet
LinkedHashSet → insertion order
TreeSet → sorted order
⚠️ no manual positioning for sorted sets
✅ SequencedMap
putFirst, putLast
firstEntry, lastEntry
pollFirstEntry, pollLastEntry
reversed()
🧠 FINAL PROFESSIONAL UNDERSTANDING

👉 Sequenced Collections solve a BIG problem:

Before Java 21:
- Order handling was inconsistent

After Java 21:
- Unified API for ordered collections
⚖️ KEY DISTINCTION (INTERVIEW LEVEL)
Concept	Meaning
Sequenced	predictable order
Sorted	comparator-based order