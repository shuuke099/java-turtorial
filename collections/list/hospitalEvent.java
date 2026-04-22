import java.time.*;
import java.util.*;

public class HospitalSecuritySystem {

    // ---------------------------------------------
    // ENUM: Event categories in hospital security
    // ---------------------------------------------
    enum EventType {
        BADGE_SCAN,
        VISITOR_CHECKIN,
        PANIC_ALARM,
        SUSPICIOUS_ACTIVITY,
        DOOR_FORCED,
        PATIENT_ESCORT
    }

    // ---------------------------------------------
    // RECORD: Immutable data model
    // WHY record?
    // → Security events should not change once created
    // ---------------------------------------------
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
        // 1️⃣ SOURCE DATA (IMMUTABLE — SAFE INPUT)
        // WHY List.of():
        // → Prevent accidental modification from developers
        // → Represents external data (API/DB snapshot)
        // =====================================================
        List<SecurityEvent> events = List.of(
            new SecurityEvent(1, "Adan", "ER", EventType.PANIC_ALARM, "ER Room 4", 10, false, LocalDateTime.now()),
            new SecurityEvent(2, "Sara", "ICU", EventType.BADGE_SCAN, "ICU Gate", 2, true, LocalDateTime.now()),
            new SecurityEvent(3, "Mohamed", "ER", EventType.DOOR_FORCED, "Pharmacy", 9, false, LocalDateTime.now())
        );

        // events.add(...) ❌ → throws exception (immutable)

        // =====================================================
        // 2️⃣ ARRAYLIST — ANALYSIS LAYER
        // WHY ArrayList:
        // → Fast reads + best for streams & general processing
        // → Most used collection in real systems
        // =====================================================
        List<SecurityEvent> list = new ArrayList<>(events);

        // size() → how many events exist
        int size = list.size();
        // Output: 3

        // isEmpty() → check before processing
        boolean empty = list.isEmpty();
        // Output: false

        // add(E) → add new incoming event
        list.add(new SecurityEvent(4, "System", "Security", EventType.PANIC_ALARM, "ICU", 10, false, LocalDateTime.now()));
        // WHY: new event arrived

        // add(index, E) → insert event at specific position
        list.add(1, new SecurityEvent(5, "Admin", "Lobby", EventType.BADGE_SCAN, "Gate", 5, true, LocalDateTime.now()));
        // WHY: late event correction (real-world case)

        // addAll() → bulk insert events
        list.addAll(List.of(
            new SecurityEvent(6, "Extra1", "ER", EventType.BADGE_SCAN, "ER Gate", 3, true, LocalDateTime.now())
        ));

        // addAll(index, ...) → insert multiple at position
        list.addAll(2, List.of(
            new SecurityEvent(7, "Bulk", "ER", EventType.PANIC_ALARM, "Room 1", 9, false, LocalDateTime.now())
        ));

        // get(index) → access specific event
        SecurityEvent first = list.get(0);
        // Output: first event

        // set(index, E) → update existing event
        list.set(0, new SecurityEvent(100, "Updated", "ER", EventType.PANIC_ALARM, "Room X", 10, true, LocalDateTime.now()));
        // WHY: correcting data

        // contains() → check if event exists
        boolean exists = list.contains(first);
        // Output: false (because we replaced it)

        // indexOf() → find position
        int idx = list.indexOf(first);
        // Output: -1

        // lastIndexOf() → last occurrence
        int lastIdx = list.lastIndexOf(first);
        // Output: -1

        // remove(index) → remove by position
        list.remove(0);
        // WHY: removing incorrect entry

        // remove(object) → remove by value
        list.remove(first);

        // removeIf() → remove based on condition
        list.removeIf(e -> e.severity() < 3);
        // WHY: filter out low-risk events

        // replaceAll() → bulk update
        list.replaceAll(e ->
            new SecurityEvent(
                e.id(),
                e.employeeName(),
                e.department(),
                e.type(),
                e.location(),
                e.severity(),
                true, // mark all resolved
                e.timestamp()
            )
        );
        // WHY: batch update (common in systems)

        // sort() → order by severity
        list.sort(Comparator.comparingInt(SecurityEvent::severity));
        // WHY: prioritize processing

        // subList() → get portion of data
        List<SecurityEvent> sub = list.subList(0, Math.min(2, list.size()));
        // WHY: show top critical events (UI/dashboard)

        // forEach() → iterate
        list.forEach(e -> {});
        // WHY: process all events

        // iterator() → manual iteration
        Iterator<SecurityEvent> it = list.iterator();
        while (it.hasNext()) {
            SecurityEvent e = it.next();
        }

        // listIterator() → bidirectional + safe update
        ListIterator<SecurityEvent> lit = list.listIterator();
        while (lit.hasNext()) {
            SecurityEvent e = lit.next();
            if (e.severity() > 8) {
                lit.set(new SecurityEvent(
                    e.id(), e.employeeName(), e.department(),
                    e.type(), e.location(), e.severity(), false, e.timestamp()
                ));
            }
        }
        // WHY: modify during iteration safely

        // equals() → compare lists
        List<SecurityEvent> copy = new ArrayList<>(list);
        boolean same = list.equals(copy);
        // Output: true

        // hashCode()
        int hash = list.hashCode();

        // toArray()
        Object[] arr = list.toArray();
        SecurityEvent[] typed = list.toArray(new SecurityEvent[0]);

        // clear() → remove everything
        List<SecurityEvent> temp = new ArrayList<>(list);
        temp.clear();
        // Output: empty list

        // =====================================================
        // 3️⃣ LINKEDLIST — PROCESSING QUEUE
        // WHY LinkedList:
        // → Fast add/remove at beginning/end (O(1))
        // → Perfect for real-time event handling
        // =====================================================
        LinkedList<SecurityEvent> queue = new LinkedList<>(list);

        // addFirst() → urgent events first
        queue.addFirst(new SecurityEvent(200, "URGENT", "ICU", EventType.PANIC_ALARM, "ICU", 10, false, LocalDateTime.now()));

        // addLast() → normal events
        queue.addLast(new SecurityEvent(201, "Normal", "ER", EventType.BADGE_SCAN, "Gate", 2, true, LocalDateTime.now()));

        // offer() → queue-style add
        queue.offer(new SecurityEvent(202, "Offer", "ER", EventType.BADGE_SCAN, "Gate", 2, true, LocalDateTime.now()));

        // offerFirst / offerLast → safe queue inserts
        queue.offerFirst(new SecurityEvent(203, "OfferFirst", "ICU", EventType.PANIC_ALARM, "ICU", 9, false, LocalDateTime.now()));
        queue.offerLast(new SecurityEvent(204, "OfferLast", "ER", EventType.BADGE_SCAN, "Gate", 2, true, LocalDateTime.now()));

        // peek() → check next event (safe)
        SecurityEvent next = queue.peek();
        // Output: first element or null

        // peekFirst / peekLast
        SecurityEvent firstQ = queue.peekFirst();
        SecurityEvent lastQ = queue.peekLast();

        // remove() → remove first (unsafe)
        queue.remove();

        // poll() → safe remove
        queue.poll();

        // removeFirst / removeLast
        queue.removeFirst();
        queue.removeLast();

        // pollFirst / pollLast
        queue.pollFirst();
        queue.pollLast();

        // push() → stack behavior (LIFO)
        queue.push(new SecurityEvent(300, "STACK", "ER", EventType.PANIC_ALARM, "ER", 10, false, LocalDateTime.now()));

        // pop() → remove top
        queue.pop();

        // descendingIterator() → reverse processing
        Iterator<SecurityEvent> desc = queue.descendingIterator();
        while (desc.hasNext()) {
            SecurityEvent e = desc.next();
        }

        // clear queue
        queue.clear();
    }
}