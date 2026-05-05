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
        // 1️⃣ IMMUTABLE MAP
        // =====================================================

        Map<Integer, String> immutableMap = Map.of(1, "ER", 2, "ICU");
        // Output: {1=ER, 2=ICU}

        Map<Integer, String> immutableEntries = Map.ofEntries(
            Map.entry(3, "Pharmacy"),
            Map.entry(4, "Psych")
        );
        // Output: {3=Pharmacy, 4=Psych}

        Map<Integer, String> copy = Map.copyOf(immutableEntries);
        // Output: {3=Pharmacy, 4=Psych}

        // =====================================================
        // 2️⃣ HASHMAP
        // =====================================================

        Map<Long, SecurityEvent> eventMap = new HashMap<>();
        // Output: {}

        eventMap.put(1L, new SecurityEvent(1,"Adan","ER",EventType.PANIC_ALARM,"ER",10,false,LocalDateTime.now()));
        // Output: {1=Adan(severity=10)}

        eventMap.put(2L, new SecurityEvent(2,"Sara","ICU",EventType.BADGE_SCAN,"Gate",2,true,LocalDateTime.now()));
        // Output: {1=Adan(10), 2=Sara(2)}

        eventMap.put(2L, new SecurityEvent(2,"Sara","ICU",EventType.BADGE_SCAN,"Gate",3,true,LocalDateTime.now()));
        // Output: {1=Adan(10), 2=Sara(3)}  // replaced

        SecurityEvent e = eventMap.get(1L);
        // Output: Adan(10)

        SecurityEvent e2 = eventMap.getOrDefault(99L, null);
        // Output: null

        boolean hasKey = eventMap.containsKey(1L);
        // Output: true

        boolean hasValue = eventMap.containsValue(e);
        // Output: true

        eventMap.remove(2L);
        // Output: {1=Adan(10)}

        eventMap.remove(1L, e);
        // Output: {}

        int size = eventMap.size();
        // Output: 0

        boolean empty = eventMap.isEmpty();
        // Output: true

        // =====================================================
        // 3️⃣ ADVANCED INSERTIONS
        // =====================================================

        eventMap.putIfAbsent(3L, new SecurityEvent(3,"Mohamed","ER",EventType.DOOR_FORCED,"Pharmacy",9,false,LocalDateTime.now()));
        // Output: {3=Mohamed(9)}

        // =====================================================
        // 4️⃣ REPLACE METHODS
        // =====================================================

        eventMap.replace(3L,
            new SecurityEvent(3,"Updated","ER",EventType.DOOR_FORCED,"Pharmacy",9,true,LocalDateTime.now()));
        // Output: {3=Updated(9)}

        eventMap.replace(3L, eventMap.get(3L),
            new SecurityEvent(3,"Final","ER",EventType.DOOR_FORCED,"Pharmacy",9,true,LocalDateTime.now()));
        // Output: {3=Final(9)}

        eventMap.replaceAll((k,v) ->
            new SecurityEvent(v.id(), v.employeeName(), v.department(),
                v.type(), v.location(), v.severity(), true, v.timestamp()));
        // Output: {3=Final(9)} (already resolved=true)

        // =====================================================
        // 5️⃣ MERGE
        // =====================================================

        Map<String, Integer> departmentCount = new HashMap<>();
        // Output: {}

        departmentCount.merge("ER", 1, Integer::sum);
        // Output: {ER=1}

        departmentCount.merge("ER", 1, Integer::sum);
        // Output: {ER=2}

        departmentCount.merge("ER", 1, (v1,v2) -> null);
        // Output: {} (removed)

        // =====================================================
        // 6️⃣ COMPUTE METHODS
        // =====================================================

        eventMap.compute(3L, (k,v) ->
            new SecurityEvent(3,"Computed","ER",EventType.DOOR_FORCED,"Pharmacy",9,true,LocalDateTime.now()));
        // Output: {3=Computed(9)}

        eventMap.computeIfAbsent(10L, k ->
            new SecurityEvent(10,"New","ER",EventType.PANIC_ALARM,"ER",10,false,LocalDateTime.now()));
        // Output: {3=Computed(9), 10=New(10)}

        eventMap.computeIfPresent(3L, (k,v) ->
            new SecurityEvent(3,"Present","ER",EventType.DOOR_FORCED,"Pharmacy",9,true,LocalDateTime.now()));
        // Output: {3=Present(9), 10=New(10)}

        // =====================================================
        // 7️⃣ ITERATION
        // =====================================================

        for(Long key : eventMap.keySet()) {
            // Output: keys → [3, 10] (order not guaranteed)
        }

        for(SecurityEvent val : eventMap.values()) {
            // Output: values → [Present(9), New(10)]
        }

        for(Map.Entry<Long, SecurityEvent> entry : eventMap.entrySet()) {
            // Output:
            // 3=Present(9)
            // 10=New(10)
        }

        eventMap.forEach((k,v) -> {
            // Output:
            // 3=Present(9)
            // 10=New(10)
        });

        // =====================================================
        // 8️⃣ LINKEDHASHMAP (ORDERED)
        // =====================================================

        Map<Integer, String> orderedMap = new LinkedHashMap<>();
        orderedMap.put(1,"A");
        orderedMap.put(2,"B");

        // Output: {1=A, 2=B}  (insertion order)

        // =====================================================
        // 9️⃣ TREEMAP (SORTED)
        // =====================================================

        Map<Integer, String> sortedMap = new TreeMap<>();
        sortedMap.put(5,"Low");
        sortedMap.put(1,"Critical");
        sortedMap.put(3,"Medium");

        // Output: {1=Critical, 3=Medium, 5=Low}

        // =====================================================
        // 🔟 CLEAR
        // =====================================================

        eventMap.clear();
        // Output: {}
    }
}