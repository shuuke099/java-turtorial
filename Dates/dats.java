// # 🧠 JAVA DATE & TIME — COMPLETE MASTER SCENARIO (OCP ENTERPRISE LEVEL)

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

// =====================================================
// 🌍 ZEILA GLOBAL SCHEDULING SYSTEM
// =====================================================

class ZeilaSystem {

    public static void main(String[] args) {

        // =====================================================
        // 🔥 1. CREATION (ALL TYPES)
        // =====================================================

        LocalDate orderDate = LocalDate.now(); 
        // Example: 2026-04-11

        LocalDate customDate = LocalDate.of(2026, 4, 10); 
        // 2026-04-10

        LocalDate parsedDate = LocalDate.parse("2026-04-10"); 
        // 2026-04-10

        LocalTime orderTime = LocalTime.now(); 
        // Example: 07:30:15.123

        LocalTime customTime = LocalTime.of(14, 30, 15, 1000); 
        // 14:30:15.000001

        LocalTime parsedTime = LocalTime.parse("14:30"); 
        // 14:30

        LocalDateTime orderDateTime = LocalDateTime.now(); 
        // Example: 2026-04-11T07:30:15

        LocalDateTime customDateTime = LocalDateTime.of(2026, 4, 10, 14, 30); 
        // 2026-04-10T14:30

        LocalDateTime parsedDateTime = LocalDateTime.parse("2026-04-10T14:30"); 
        // 2026-04-10T14:30

        ZoneId userZone = ZoneId.of("America/New_York");

        ZonedDateTime zonedNow = ZonedDateTime.now(userZone); 
        // Example: 2026-04-11T08:30:15-04:00[America/New_York]

        Instant systemInstant = Instant.now(); 
        // Example: 2026-04-11T12:30:15Z


        // =====================================================
        // 🔥 2. GET METHODS (FULL)
        // =====================================================

        int year = orderDate.getYear(); // 2026
        int monthValue = orderDate.getMonthValue(); // 4
        Month monthEnum = orderDate.getMonth(); // APRIL
        int day = orderDate.getDayOfMonth(); // 11
        int dayOfYear = orderDate.getDayOfYear(); // 101
        DayOfWeek dayOfWeek = orderDate.getDayOfWeek(); // SATURDAY
        boolean leap = orderDate.isLeapYear(); // false
        int lengthMonth = orderDate.lengthOfMonth(); // 30

        int fieldDay = orderDate.get(ChronoField.DAY_OF_MONTH); // 11

        // ChronoField
// YEAR
// MONTH_OF_YEAR
// DAY_OF_MONTH
// DAY_OF_YEAR
// DAY_OF_WEEK
// HOUR_OF_DAY
// MINUTE_OF_HOUR
// SECOND_OF_MINUTE
// EPOCH_DAY
// ERA
        long epochDay = orderDate.toEpochDay(); // e.g. 20160
        

        

        int hour = orderTime.getHour(); // e.g. 7
        int minute = orderTime.getMinute(); // e.g. 30
        int second = orderTime.getSecond(); // e.g. 15
        int nano = orderTime.getNano(); // e.g. 123000000

        int dtHour = orderDateTime.getHour(); // e.g. 7

        ZoneId zone = zonedNow.getZone(); // America/New_York

        long epochSeconds = systemInstant.getEpochSecond(); // e.g. 1712835000


        // =====================================================
        // 🔥 3. IMMUTABLE MODIFICATIONS (FULL)
        // =====================================================

        LocalDate deliveryDate = orderDate
                .plusDays(3)      // +3 days
                .plusWeeks(1)     // +7 days
                .plusMonths(2)
                .plusYears(1)
                .minusDays(2)
                .minusWeeks(1)
                .minusMonths(1)
                .minusYears(1)
                .withYear(2027)
                .withMonth(12)
                .withDayOfMonth(25)
                .withDayOfYear(200);

        // Example Result: 2027-07-19


        LocalDate advancedDate = orderDate
                .plus(5, ChronoUnit.DAYS)
                .minus(2, ChronoUnit.WEEKS)
                .with(ChronoField.MONTH_OF_YEAR, 6);

        // Example: 2026-06-02
// DAYS, WEEKS, MONTHS, YEARS, HOURS, MINUTES, SECONDS, MILLIS, NANOS, DECADES, CENTURIES

        LocalTime adjustedTime = orderTime
                .plusHours(2)
                .plusMinutes(15)
                .plusSeconds(30)
                .plusNanos(1000)
                .minusHours(1)
                .minusMinutes(5)
                .minusSeconds(10)
                .minusNanos(500)
                .withHour(10)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        // Result: 10:00


        // =====================================================
        // 🔥 4. COMPARISON
        // =====================================================

        boolean isBefore = orderDate.isBefore(deliveryDate); // true
        boolean isAfter = deliveryDate.isAfter(orderDate); // true
        boolean isEqual = orderDate.isEqual(customDate); // false

        int compare = orderDate.compareTo(deliveryDate); 
        // negative value (because orderDate < deliveryDate)


        // =====================================================
        // 🔥 5. PERIOD (DATE BASED)
        // =====================================================

        Period period = Period.of(1, 2, 3); 
        // 1 year, 2 months, 3 days

        LocalDate futureDate = orderDate.plus(period); 
        // Example: 2027-06-14


        // =====================================================
        // 🔥 6. DURATION (TIME BASED)
        // =====================================================

        Duration duration = Duration.ofHours(5);

        LocalTime endTime = orderTime.plus(duration); 
        // Example: 12:30

//         Others include:

// ofMinutes()
// ofSeconds()
// ofMillis()
// ofNanos()
// ofDays()
// of(long, ChronoUnit)
// between()


        // =====================================================
        // 🔥 7. CHRONOUNIT (DIFFERENCE)
        // =====================================================

        long daysBetween = ChronoUnit.DAYS.between(orderDate, deliveryDate); 
        // e.g. 464

        long hoursBetween = ChronoUnit.HOURS.between(orderTime, adjustedTime); 
        // depends on time → e.g. 2


        // =====================================================
        // 🔥 8. CONVERSIONS
        // =====================================================

        LocalDateTime combined = LocalDateTime.of(orderDate, orderTime); 
        // 2026-04-11T07:30

        ZonedDateTime zoned = combined.atZone(userZone); 
        // 2026-04-11T07:30-04:00

        Instant convertedInstant = zoned.toInstant(); 
        // 2026-04-11T11:30Z

        LocalDate extractedDate = zoned.toLocalDate(); 
        // 2026-04-11

        LocalTime extractedTime = zoned.toLocalTime(); 
        // 07:30


        // =====================================================
        // 🔥 9. ZONE OPERATIONS
        // =====================================================

        ZoneId somaliaZone = ZoneId.of("Africa/Mogadishu");

        ZonedDateTime somaliaTime = zoned.withZoneSameInstant(somaliaZone); 
        // Example: 2026-04-11T14:30+03:00


        // =====================================================
        // 🔥 10. INSTANT OPERATIONS
        // =====================================================

        Instant later = systemInstant.plusSeconds(60); 
        // +1 minute

        Instant earlier = systemInstant.minusMillis(1000); 
        // -1 second


        // =====================================================
        // 🔥 11. PARSE & FORMAT (FULL)
        // =====================================================

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String formatted = combined.format(formatter); 
        // Example: 11/04/2026 07:30

        LocalDate parsedAgain = LocalDate.parse("2026-04-10"); 
        // 2026-04-10

        LocalDate parsedCustom = LocalDate.parse(
                "10/04/2026",
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        ); 
        // 2026-04-10


        // =====================================================
        // 🔥 12. TEMPORAL ADJUSTERS (ADVANCED)
        // =====================================================

        LocalDate nextMonday = orderDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY)); 
        // Next Monday

        LocalDate firstDayOfMonth = orderDate.with(TemporalAdjusters.firstDayOfMonth()); 
        // 2026-04-01


        // =====================================================
        // 🔥 13. DST EDGE CASE
        // =====================================================

        ZonedDateTime dstTest = ZonedDateTime.of(
                2026, 3, 8, 2, 30, 0, 0,
                ZoneId.of("America/New_York")
        );

        // DST gap → Java shifts time automatically
        // Result: 2026-03-08T03:30-04:00


        // =====================================================
        // 🔥 14. OUTPUT
        // =====================================================

        System.out.println("Order Date: " + orderDate);
        // Order Date: 2026-04-11

        System.out.println("Delivery Date: " + deliveryDate);
        // Delivery Date: 2027-07-19

        System.out.println("Days Between: " + daysBetween);
        // Days Between: 464

        System.out.println("Formatted: " + formatted);
        // Formatted: 11/04/2026 07:30

        System.out.println("User Time: " + zoned);
        // User Time: 2026-04-11T07:30-04:00

        System.out.println("Somalia Time: " + somaliaTime);
        // Somalia Time: 2026-04-11T14:30+03:00

        System.out.println("Instant: " + systemInstant);
        // Instant: 2026-04-11T11:30:15Z
    }
}
// =====================================
// Common ChronoField values

// Here are the most important ones you’ll see (OCP exam 🔥):

// YEAR
// MONTH_OF_YEAR
// DAY_OF_MONTH
// DAY_OF_YEAR
// DAY_OF_WEEK
// HOUR_OF_DAY
// MINUTE_OF_HOUR
// SECOND_OF_MINUTE
// EPOCH_DAY
// ERA

// import java.time.LocalDate;
// import java.time.temporal.ChronoField;
// 💻 Example 1 — Getting values
class Mainm {
    public static void main(String[] args) {

        LocalDate date = LocalDate.of(2026, 4, 11);

        int year = date.get(ChronoField.YEAR);
        int month = date.get(ChronoField.MONTH_OF_YEAR);
        int day = date.get(ChronoField.DAY_OF_MONTH);

        System.out.println(year);   // 2026
        System.out.println(month);  // 4
        System.out.println(day);    // 11
    }
}


Example 2 — Modifying values
public class Main {
    public static void main(String[] args) {

        LocalDate date = LocalDate.of(2026, 4, 11);

        LocalDate newDate = date.with(ChronoField.YEAR, 2030);

        System.out.println(newDate); // 2030-04-11
    }
}
// Example 3 — With LocalDateTime

public class Main {
    public static void main(String[] args) {

        LocalDateTime dt = LocalDateTime.now();

        int hour = dt.get(ChronoField.HOUR_OF_DAY);
        int minute = dt.get(ChronoField.MINUTE_OF_HOUR);

        System.out.println(hour);
        System.out.println(minute);
    }
}


// ⚠️ Important OCP Notes (VERY IMPORTANT)
// 1. Not all fields work with all classes
// LocalDate date = LocalDate.now();

// // ❌ Runtime Exception
// date.get(ChronoField.HOUR_OF_DAY);

// 👉 LocalDate has no time → throws:
// UnsupportedTemporalTypeException

// 2. Difference between get() and getLong()
// get() → returns int
// getLong() → returns long (for large values like EPOCH_DAY)
// 3. Works with Temporal interface

// All date/time classes implement Temporal:

// LocalDate
// LocalTime
// LocalDateTime
// ZonedDateTime

// 👉 That’s why ChronoField works with all of them

// 🧩 When should you use ChronoField?

// Use it when:

// You need generic access to fields
// You are writing framework-level or reusable code
// You want dynamic field access

// 👉 But in normal code, this is more readable:

// date.getYear(); // ✅ preferred

// Instead of:

// date.get(ChronoField.YEAR); // 😐 less readable
// 🧠 Interview / OCP Trick Insight

// 👉 These two are NOT the same in intent:

// date.getYear();                  // Type-safe, readable
// date.get(ChronoField.YEAR);      // Generic, flexible