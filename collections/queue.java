import java.time.*;
import java.util.*;

public class HospitalSecurityQueueSystem {

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
        // 1️⃣ QUEUE (FIFO) — REAL-TIME EVENT PROCESSING
        // WHY Queue:
        // → Events must be handled in order (First In First Out)
        // =====================================================

        Queue<SecurityEvent> queue = new LinkedList<>();

        // -----------------------------------------------------
        // ADD METHODS (Exception vs Safe)
        // -----------------------------------------------------

        queue.add(new SecurityEvent(1, "Adan", "ER", EventType.PANIC_ALARM, "ER", 10, false, LocalDateTime.now()));
        // WHY: add event (throws exception if fails)

        queue.offer(new SecurityEvent(2, "Sara", "ICU", EventType.BADGE_SCAN, "Gate", 2, true, LocalDateTime.now()));
        // WHY: safe add (returns false if fails)

        // Output (conceptually):
        // queue = [Event1, Event2]

        // -----------------------------------------------------
        // READ METHODS (WITHOUT REMOVING)
        // -----------------------------------------------------

        SecurityEvent first1 = queue.element();
        // Output: Event1
        // WHY: strict read (throws exception if empty)

        SecurityEvent first2 = queue.peek();
        // Output: Event1
        // WHY: safe read (returns null if empty)

        // -----------------------------------------------------
        // REMOVE METHODS (FIFO PROCESSING)
        // -----------------------------------------------------

        SecurityEvent removed1 = queue.remove();
        // Output: Event1
        // WHY: process event (throws exception if empty)

        SecurityEvent removed2 = queue.poll();
        // Output: Event2
        // WHY: safe processing (returns null if empty)

        SecurityEvent removed3 = queue.poll();
        // Output: null (queue empty)

        // =====================================================
        // 2️⃣ DEQUE (DOUBLE-ENDED QUEUE)
        // WHY:
        // → Some events are urgent → must go to front
        // → Others normal → go to back
        // =====================================================

        Deque<SecurityEvent> deque = new LinkedList<>();

        // -----------------------------------------------------
        // ADD METHODS (FRONT & BACK)
        // -----------------------------------------------------

        deque.addFirst(new SecurityEvent(10, "URGENT", "ICU", EventType.PANIC_ALARM, "ICU", 10, false, LocalDateTime.now()));
        // WHY: emergency event → front

        deque.addLast(new SecurityEvent(11, "Normal", "ER", EventType.BADGE_SCAN, "Gate", 2, true, LocalDateTime.now()));
        // WHY: normal → back

        deque.offerFirst(new SecurityEvent(12, "SafeFront", "ICU", EventType.PANIC_ALARM, "ICU", 9, false, LocalDateTime.now()));
        // safe version

        deque.offerLast(new SecurityEvent(13, "SafeBack", "ER", EventType.BADGE_SCAN, "Gate", 2, true, LocalDateTime.now()));

        // State:
        // [SafeFront, URGENT, Normal, SafeBack]

        // -----------------------------------------------------
        // READ METHODS
        // -----------------------------------------------------

        SecurityEvent f1 = deque.getFirst();
        // Output: SafeFront (throws exception if empty)

        SecurityEvent f2 = deque.peekFirst();
        // Output: SafeFront (safe)

        SecurityEvent l1 = deque.getLast();
        // Output: SafeBack

        SecurityEvent l2 = deque.peekLast();
        // Output: SafeBack

        // -----------------------------------------------------
        // REMOVE METHODS
        // -----------------------------------------------------

        deque.removeFirst();
        // removes SafeFront

        deque.pollFirst();
        // removes URGENT

        deque.removeLast();
        // removes SafeBack

        deque.pollLast();
        // removes Normal

        // deque now empty

        SecurityEvent safeRemove = deque.pollFirst();
        // Output: null (safe)

        // =====================================================
        // 3️⃣ STACK MODE (LIFO) USING DEQUE
        // WHY:
        // → Undo operations / last event priority
        // =====================================================

        Deque<SecurityEvent> stack = new ArrayDeque<>();

        // push() → add to top
        stack.push(new SecurityEvent(20, "Stack1", "ER", EventType.BADGE_SCAN, "Gate", 2, true, LocalDateTime.now()));
        stack.push(new SecurityEvent(21, "Stack2", "ICU", EventType.PANIC_ALARM, "ICU", 9, false, LocalDateTime.now()));

        // State:
        // [Stack2, Stack1]

        // peek() → view top
        SecurityEvent top = stack.peek();
        // Output: Stack2

        // pop() → remove top
        stack.pop();
        // removes Stack2

        stack.pop();
        // removes Stack1

        SecurityEvent emptyPeek = stack.peek();
        // Output: null

        // =====================================================
        // 4️⃣ ITERATION (QUEUE / DEQUE)
        // WHY:
        // → Process all events sequentially
        // =====================================================

        deque.offer(new SecurityEvent(30, "Iter1", "ER", EventType.BADGE_SCAN, "Gate", 2, true, LocalDateTime.now()));
        deque.offer(new SecurityEvent(31, "Iter2", "ICU", EventType.PANIC_ALARM, "ICU", 10, false, LocalDateTime.now()));

        for (SecurityEvent e : deque) {
            // process each event
        }

        Iterator<SecurityEvent> it = deque.iterator();
        while (it.hasNext()) {
            SecurityEvent e = it.next();
        }

        // reverse iteration (important for logs/debug)
        Iterator<SecurityEvent> desc = deque.descendingIterator();
        while (desc.hasNext()) {
            SecurityEvent e = desc.next();
        }

        // =====================================================
        // 5️⃣ CLEAR
        // =====================================================

        deque.clear();
        // Output: empty deque

        // =====================================================
        // FINAL PROFESSIONAL FLOW
        // =====================================================

        /*
        Real system flow:

        Sensors → Queue (FIFO)
                → Deque (priority handling)
                → Stack (undo / last operations)

        Queue → normal processing
        Deque → urgent + flexible
        Stack → reverse operations
        */
    }
}









WHAT YOU JUST MASTERED
✅ Queue (FIFO)
add, offer
element, peek
remove, poll
✅ Deque (Double-ended)
addFirst, addLast
offerFirst, offerLast
getFirst, getLast
peekFirst, peekLast
removeFirst, removeLast
pollFirst, pollLast
✅ Stack (LIFO via Deque)
push, pop, peek
✅ Iteration
for-each
iterator
descendingIterator
🧠 FINAL PROFESSIONAL UNDERSTANDING

👉 From your lesson :

Queue → FIFO (line)
Stack → LIFO (plates)
Deque → both combined
🚀 FINAL TAKEAWAY

👉 In real systems:

Queue → process events in order
Deque → handle priority + flexibility
Stack → undo / rollback