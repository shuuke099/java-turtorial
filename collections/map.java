ULL PROFESSIONAL SCENARIO — Map (Hospital Security System)

This will cover:

✅ ALL Map methods
✅ HashMap, LinkedHashMap, TreeMap
✅ Real-world usage (lookup, grouping, counting)
✅ Immutable maps
✅ Advanced methods (merge, compute, etc.)
✅ WHY comments (very important)
✅ Outputs as comments






import java.time.*;
import java.util.*;

public class HospitalSecurityMapSystem {

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

        // =====================================================
        // 1️⃣ IMMUTABLE MAP (CONFIG / STATIC DATA)
        // WHY: prevent accidental modification
        // =====================================================

        Map<Integer, String> immutableMap = Map.of(
            1, "ER",
            2, "ICU"
        );

        // immutableMap.put(3,"Lobby"); ❌ Exception

        Map<Integer, String> immutableEntries = Map.ofEntries(
            Map.entry(3, "Pharmacy"),
            Map.entry(4, "Psych")
        );

        Map<Integer, String> copy = Map.copyOf(immutableEntries);
        // fully immutable copy

        // =====================================================
        // 2️⃣ HASHMAP — MAIN STORAGE (FAST LOOKUP)
        // WHY: O(1) lookup by ID (VERY IMPORTANT in real systems)
        // =====================================================

        Map<Long, SecurityEvent> eventMap = new HashMap<>();

        // put() → insert or replace
        eventMap.put(1L, new SecurityEvent(1,"Adan","ER",EventType.PANIC_ALARM,"ER",10,false,LocalDateTime.now()));
        eventMap.put(2L, new SecurityEvent(2,"Sara","ICU",EventType.BADGE_SCAN,"Gate",2,true,LocalDateTime.now()));

        // put returns old value if replaced
        eventMap.put(2L, new SecurityEvent(2,"Sara","ICU",EventType.BADGE_SCAN,"Gate",3,true,LocalDateTime.now()));
        // Output: old value replaced

        // get()
        SecurityEvent e = eventMap.get(1L);
        // Output: event with id 1

        // getOrDefault()
        SecurityEvent e2 = eventMap.getOrDefault(99L, null);
        // Output: null

        // containsKey()
        boolean hasKey = eventMap.containsKey(1L);
        // true

        // containsValue()
        boolean hasValue = eventMap.containsValue(e);
        // true

        // remove(key)
        eventMap.remove(2L);

        // remove(key, value)
        eventMap.remove(1L, e);
        // removes only if match

        // size()
        int size = eventMap.size();

        // isEmpty()
        boolean empty = eventMap.isEmpty();

        // =====================================================
        // 3️⃣ ADVANCED INSERTIONS
        // =====================================================

        // putIfAbsent()
        eventMap.putIfAbsent(3L, new SecurityEvent(3,"Mohamed","ER",EventType.DOOR_FORCED,"Pharmacy",9,false,LocalDateTime.now()));
        // only adds if key not present

        // =====================================================
        // 4️⃣ REPLACE METHODS
        // =====================================================

        // replace(key, value)
        eventMap.replace(3L, new SecurityEvent(3,"Updated","ER",EventType.DOOR_FORCED,"Pharmacy",9,true,LocalDateTime.now()));

        // replace(key, old, new)
        eventMap.replace(3L, eventMap.get(3L),
                new SecurityEvent(3,"Final","ER",EventType.DOOR_FORCED,"Pharmacy",9,true,LocalDateTime.now()));

        // replaceAll()
        eventMap.replaceAll((k,v) ->
                new SecurityEvent(v.id(), v.employeeName(), v.department(),
                        v.type(), v.location(), v.severity(), true, v.timestamp()));
        // WHY: mark all events resolved

        // =====================================================
        // 5️⃣ MERGE (VERY IMPORTANT)
        // =====================================================

        Map<String, Integer> departmentCount = new HashMap<>();

        departmentCount.merge("ER", 1, Integer::sum);
        departmentCount.merge("ER", 1, Integer::sum);

        // Output: ER = 2
        // WHY: counting events (VERY common real-world use)

        // merge removing case
        departmentCount.merge("ER", 1, (v1,v2) -> null);
        // Output: ER removed

        // =====================================================
        // 6️⃣ COMPUTE METHODS (ADVANCED)
        // =====================================================

        // compute()
        eventMap.compute(3L, (k,v) ->
                new SecurityEvent(3,"Computed","ER",EventType.DOOR_FORCED,"Pharmacy",9,true,LocalDateTime.now()));

        // computeIfAbsent()
        eventMap.computeIfAbsent(10L, k ->
                new SecurityEvent(10,"New","ER",EventType.PANIC_ALARM,"ER",10,false,LocalDateTime.now()));

        // computeIfPresent()
        eventMap.computeIfPresent(3L, (k,v) ->
                new SecurityEvent(3,"Present","ER",EventType.DOOR_FORCED,"Pharmacy",9,true,LocalDateTime.now()));

        // =====================================================
        // 7️⃣ ITERATION
        // =====================================================

        // keySet()
        for(Long key : eventMap.keySet()) {
            // process key
        }

        // values()
        for(SecurityEvent val : eventMap.values()) {
            // process value
        }

        // entrySet() → BEST way
        for(Map.Entry<Long, SecurityEvent> entry : eventMap.entrySet()) {
            Long key = entry.getKey();
            SecurityEvent value = entry.getValue();
        }

        // forEach()
        eventMap.forEach((k,v) -> {
            // process
        });

        // =====================================================
        // 8️⃣ LINKEDHASHMAP (ORDERED)
        // WHY: maintain insertion order (logs/UI)
        // =====================================================

        Map<Integer, String> orderedMap = new LinkedHashMap<>();
        orderedMap.put(1,"A");
        orderedMap.put(2,"B");

        // Output order: A, B

        // =====================================================
        // 9️⃣ TREEMAP (SORTED)
        // WHY: sorted keys (ranking / thresholds)
        // =====================================================

        Map<Integer, String> sortedMap = new TreeMap<>();
        sortedMap.put(5,"Low");
        sortedMap.put(1,"Critical");
        sortedMap.put(3,"Medium");

        // Output order: 1, 3, 5

        // =====================================================
        // 🔟 CLEAR
        // =====================================================

        eventMap.clear();
        // Output: empty map
    }
}


WHAT YOU JUST MASTERED
✅ Core Methods
put, get, getOrDefault
containsKey, containsValue
remove, clear
size, isEmpty
✅ Advanced Methods (VERY IMPORTANT)
putIfAbsent
replace, replaceAll
merge ⭐ (most important in real systems)
compute, computeIfAbsent, computeIfPresent
✅ Iteration
keySet()
values()
entrySet() ⭐ BEST
forEach()
✅ Map Types
HashMap → fast lookup
LinkedHashMap → ordered
TreeMap → sorted



FINAL PROFESSIONAL INSIGHT

👉 In real backend systems:

Map is the MOST USED collection
Used for:
ID lookup
grouping
caching
counting
indexing