import java.time.*;
import java.time.format.DateTimeFormatter;
import java.text.*;
import java.util.*;

class ZeilaFormattingEngine {

    public static void main(String[] args) {

        // =====================================================
        // 🔥 STEP 1: RAW DATA (FROM SYSTEM)
        // =====================================================

        LocalDate travelDate = LocalDate.of(2026, Month.DECEMBER, 20); 
        // 2026-12-20

        LocalTime departureTime = LocalTime.of(18, 45, 30); 
        // 18:45:30

        LocalDateTime bookingDateTime =
                LocalDateTime.of(travelDate, departureTime);
        // 2026-12-20T18:45:30

        ZonedDateTime zoned =
                bookingDateTime.atZone(ZoneId.of("America/New_York"));
        // 2026-12-20T18:45:30-05:00[America/New_York]

        double ticketPrice = 1234.567;
        // 1234.567

        // =====================================================
        // 🔥 STEP 2: ISO FORMATTING (STANDARD OUTPUT)
        // =====================================================

        String isoDate =
                travelDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        // "2026-12-20"

        String isoTime =
                departureTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
        // "18:45:30"

        String isoDateTime =
                bookingDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        // "2026-12-20T18:45:30"

        // =====================================================
        // 🔥 STEP 3: CUSTOM DATE/TIME FORMATTING
        // =====================================================

        DateTimeFormatter fullFormatter =
                DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a");

        String fullDisplay =
                bookingDateTime.format(fullFormatter);
        // "December 20, 2026 at 06:45 PM"

        // =====================================================
        // 🔥 STEP 4: ADVANCED PATTERNS
        // =====================================================

        DateTimeFormatter pattern1 =
                DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss");

        DateTimeFormatter pattern2 =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String formatted1 = bookingDateTime.format(pattern1);
        // "12/20/2026 06:45:30"

        String formatted2 = bookingDateTime.format(pattern2);
        // "2026-12-20 18:45"

        // =====================================================
        // 🔥 STEP 5: SYMBOL RULES (EXAM CRITICAL)
        // =====================================================

        DateTimeFormatter symbolDemo =
                DateTimeFormatter.ofPattern("MMMM dd yyyy HH:mm");

        String symbolOutput =
                bookingDateTime.format(symbolDemo);
        // "December 20 2026 18:45"

        // =====================================================
        // 🔥 STEP 6: ZONE FORMATTING (ZonedDateTime ONLY)
        // =====================================================

        DateTimeFormatter zoneFormatter =
                DateTimeFormatter.ofPattern("hh:mm z");

        String zoneDisplay =
                zoned.format(zoneFormatter);
        // "06:45 EST"

        // =====================================================
        // 🔥 STEP 7: ESCAPING TEXT (VERY IMPORTANT)
        // =====================================================

        DateTimeFormatter escaped =
                DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm");

        String escapedOutput =
                bookingDateTime.format(escaped);
        // "December 20, 2026 at 06:45"

        // =====================================================
        // 🔥 STEP 8: NUMBER FORMATTING (DecimalFormat)
        // =====================================================

        NumberFormat simpleFormat =
                new DecimalFormat("###,###.0");

        NumberFormat detailedFormat =
                new DecimalFormat("000,000.000");

        NumberFormat currencyFormat =
                new DecimalFormat("Your Balance $#,###.##");

        String price1 = simpleFormat.format(ticketPrice);
        // "1,234.6"

        String price2 = detailedFormat.format(ticketPrice);
        // "001,234.567"

        String price3 = currencyFormat.format(ticketPrice);
        // "Your Balance $1,234.57"

        // =====================================================
        // 🔥 STEP 9: ROUNDING & PADDING
        // =====================================================

        NumberFormat roundingTest =
                new DecimalFormat("0.00");

        String rounded =
                roundingTest.format(ticketPrice);
        // "1234.57"

        // =====================================================
        // 🔥 FINAL RESULT (NO PRINTS — CLEAN FILE)
        // =====================================================
    }
}

// FULL LIST (MEMORIZE FOR OCP)
// 📅 Date
// ISO_LOCAL_DATE
// ISO_OFFSET_DATE
// ISO_DATE
// ISO_ORDINAL_DATE
// ISO_WEEK_DATE
// BASIC_ISO_DATE
// ⏰ Time
// ISO_LOCAL_TIME
// ISO_OFFSET_TIME
// ISO_TIME
// 📅⏰ Date-Time
// ISO_LOCAL_DATE_TIME
// ISO_OFFSET_DATE_TIME
// ISO_ZONED_DATE_TIME
// ISO_DATE_TIME
// 🌍 Global
// ISO_INSTANT


// import java.time.*;
// import java.time.format.DateTimeFormatter;
class ISOFormattingFull {

    public static void main(String[] args) {

        // =====================================================
        // 🔥 BASE DATA
        // =====================================================

        LocalDate date = LocalDate.of(2026, 4, 11); 
        // 2026-04-11

        LocalTime time = LocalTime.of(7, 30, 45); 
        // 07:30:45

        LocalDateTime dateTime = LocalDateTime.of(date, time); 
        // 2026-04-11T07:30:45

        ZonedDateTime zoned = dateTime.atZone(ZoneId.of("America/Chicago"));
        // 2026-04-11T07:30:45-05:00[America/Chicago]

        OffsetDateTime offsetDT = dateTime.atOffset(ZoneOffset.of("-05:00"));
        // 2026-04-11T07:30:45-05:00

        Instant instant = Instant.parse("2026-04-11T12:30:45Z");
        // 2026-04-11T12:30:45Z

        // =====================================================
        // 🔥 1. ISO_LOCAL_DATE
        // =====================================================
        String f1 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        // "2026-04-11"

        // =====================================================
        // 🔥 2. ISO_OFFSET_DATE
        // =====================================================
        String f2 = offsetDT.format(DateTimeFormatter.ISO_OFFSET_DATE);
        // "2026-04-11-05:00"

        // =====================================================
        // 🔥 3. ISO_DATE
        // (LOCAL or OFFSET)
        // =====================================================
        String f3 = offsetDT.format(DateTimeFormatter.ISO_DATE);
        // "2026-04-11-05:00"

        // =====================================================
        // 🔥 4. ISO_LOCAL_TIME
        // =====================================================
        String f4 = time.format(DateTimeFormatter.ISO_LOCAL_TIME);
        // "07:30:45"

        // =====================================================
        // 🔥 5. ISO_OFFSET_TIME
        // =====================================================
        String f5 = offsetDT.format(DateTimeFormatter.ISO_OFFSET_TIME);
        // "07:30:45-05:00"

        // =====================================================
        // 🔥 6. ISO_TIME
        // =====================================================
        String f6 = offsetDT.format(DateTimeFormatter.ISO_TIME);
        // "07:30:45-05:00"

        // =====================================================
        // 🔥 7. ISO_LOCAL_DATE_TIME
        // =====================================================
        String f7 = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        // "2026-04-11T07:30:45"

        // =====================================================
        // 🔥 8. ISO_OFFSET_DATE_TIME
        // =====================================================
        String f8 = offsetDT.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        // "2026-04-11T07:30:45-05:00"

        // =====================================================
        // 🔥 9. ISO_ZONED_DATE_TIME
        // =====================================================
        String f9 = zoned.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        // "2026-04-11T07:30:45-05:00[America/Chicago]"

        // =====================================================
        // 🔥 10. ISO_DATE_TIME
        // (SMART formatter)
        // =====================================================
        String f10 = zoned.format(DateTimeFormatter.ISO_DATE_TIME);
        // "2026-04-11T07:30:45-05:00[America/Chicago]"

        // =====================================================
        // 🔥 11. ISO_ORDINAL_DATE
        // =====================================================
        String f11 = date.format(DateTimeFormatter.ISO_ORDINAL_DATE);
        // "2026-101"  (101st day of year)

        // =====================================================
        // 🔥 12. ISO_WEEK_DATE
        // =====================================================
        String f12 = date.format(DateTimeFormatter.ISO_WEEK_DATE);
        // "2026-W15-6"

        // =====================================================
        // 🔥 13. ISO_INSTANT
        // =====================================================
        String f13 = DateTimeFormatter.ISO_INSTANT.format(instant);
        // "2026-04-11T12:30:45Z"

        // =====================================================
        // 🔥 14. BASIC_ISO_DATE
        // =====================================================
        String f14 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        // "20260411"

    }
}