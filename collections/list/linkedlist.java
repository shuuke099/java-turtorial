record OrderEvent(
    String status,
    String timestamp,
    String note
) {}


import java.util.*;

class OrderTimelineService {

    public void manageTimeline() {

        // -------------------------------------------------
        // ✅ CREATE LINKEDLIST (LIST BEHAVIOR)
        // -------------------------------------------------
        List<OrderEvent> timeline = new LinkedList<>();

        // -------------------------------------------------
        // ✅ ADD METHODS
        // -------------------------------------------------
        timeline.add(new OrderEvent("CREATED", "10:00", "Order placed"));
        timeline.add(new OrderEvent("PAID", "10:05", "Payment confirmed"));

        // insert at position (VERY IMPORTANT)
        timeline.add(1, new OrderEvent("VALIDATED", "10:02", "Order validated"));

        // bulk add
        List<OrderEvent> shippingEvents = List.of(
            new OrderEvent("PACKED", "11:00", "Packed in warehouse"),
            new OrderEvent("SHIPPED", "12:00", "Out for delivery")
        );
        timeline.addAll(shippingEvents);

        // insert bulk at index
        timeline.addAll(2, shippingEvents);

        // -------------------------------------------------
        // ✅ ACCESS METHODS
        // -------------------------------------------------
        OrderEvent first = timeline.get(0);
        OrderEvent middle = timeline.get(2);

        // -------------------------------------------------
        // ✅ SEARCH METHODS
        // -------------------------------------------------
        boolean hasPaid = timeline.contains(first);
        int firstIndex = timeline.indexOf(first);
        int lastIndex = timeline.lastIndexOf(first);

        // -------------------------------------------------
        // ✅ UPDATE METHODS
        // -------------------------------------------------
        timeline.set(0, new OrderEvent("CREATED", "10:00", "Order initiated"));

        // replace all (bulk update)
        timeline.replaceAll(e ->
            new OrderEvent(e.status, e.timestamp, e.note + " ✔")
        );

        // -------------------------------------------------
        // ✅ REMOVE METHODS
        // -------------------------------------------------
        timeline.remove(0);                  // remove by index
        timeline.remove(first);              // remove by object

        // remove multiple
        timeline.removeAll(shippingEvents);

        // retain only specific events
        timeline.retainAll(List.of(middle));

        // conditional remove
        timeline.removeIf(e -> e.status.equals("VALIDATED"));

        // -------------------------------------------------
        // ✅ INFO METHODS
        // -------------------------------------------------
        int size = timeline.size();
        boolean empty = timeline.isEmpty();

        // -------------------------------------------------
        // ✅ ITERATION
        // -------------------------------------------------

        // forEach
        timeline.forEach(e -> System.out.println(e));

        // iterator
        Iterator<OrderEvent> it = timeline.iterator();
        while (it.hasNext()) {
            System.out.println("Iterator: " + it.next());
        }

        // listIterator (bidirectional)
        ListIterator<OrderEvent> listIt = timeline.listIterator();

        while (listIt.hasNext()) {
            OrderEvent e = listIt.next();

            // modify during iteration (VERY IMPORTANT)
            if (e.status.equals("PAID")) {
                listIt.set(new OrderEvent("PAID", e.timestamp, "Verified Payment"));
            }
        }

        // backward traversal
        while (listIt.hasPrevious()) {
            System.out.println("Backward: " + listIt.previous());
        }

        // -------------------------------------------------
        // ✅ SUBLIST (VIEW)
        // -------------------------------------------------
        List<OrderEvent> sub = timeline.subList(0, Math.min(2, timeline.size()));

        // -------------------------------------------------
        // ✅ SORT (LESS COMMON BUT POSSIBLE)
        // -------------------------------------------------
        timeline.sort(Comparator.comparing(e -> e.timestamp));

        // -------------------------------------------------
        // ✅ CONVERSION
        // -------------------------------------------------
        Object[] arr = timeline.toArray();
        OrderEvent[] typed = timeline.toArray(new OrderEvent[0]);

        // -------------------------------------------------
        // ✅ COMPARISON
        // -------------------------------------------------
        List<OrderEvent> copy = new LinkedList<>(timeline);

        System.out.println(timeline.equals(copy));
        System.out.println(timeline.hashCode());

        // -------------------------------------------------
        // ✅ CLEAR
        // -------------------------------------------------
        timeline.clear();
    }
}