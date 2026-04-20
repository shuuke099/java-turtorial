// ZEILA GLOBAL TRAVEL, BILLING & LOCALIZATION ENGINE
// 🔥 (ULTIMATE FULL ENTERPRISE SCENARIO — NOTHING LEFT)
// 🏢 SYSTEM OVERVIEW

// You are building a global platform used in:

// 🇺🇸 USA
// 🇩🇪 Germany
// 🇫🇷 France
// 🇸🇴 Somalia

// The system must:

// Show correct language & formats
// Handle currency differences
// Display dates correctly per country
// Parse user input correctly

// 👉 This is where Localization & Formatting combine




package untitled folder.Dates;

public class Localization {
    
}
import java.time.*;
import java.time.format.*;
import java.time.temporal.ChronoUnit;
import java.text.*;
import java.util.*;

public class ZeilaGlobalEngine {

    public static void main(String[] args) throws Exception {

        // =====================================================
        // 🔥 STEP 1: SYSTEM SETUP (LOCALE)
        // =====================================================

        Locale us = Locale.US;
        Locale germany = Locale.GERMANY;
        Locale france = Locale.FRANCE;

        Locale custom = Locale.of("en", "US");

        // Default locale
        Locale.setDefault(us);

        // =====================================================
        // 🔥 STEP 2: BOOKING CREATION
        // =====================================================

        LocalDate travelDate = LocalDate.of(2026, 12, 20);
        LocalTime travelTime = LocalTime.of(18, 45);

        LocalDateTime booking =
                LocalDateTime.of(travelDate, travelTime);

        ZonedDateTime zoned =
                booking.atZone(ZoneId.of("America/New_York"));

        Instant globalTime = Instant.now();

        // =====================================================
        // 🔥 STEP 3: LOCALIZED DATE FORMATTING
        // =====================================================

        DateTimeFormatter shortDate =
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

        System.out.println("US Date: " +
                shortDate.withLocale(us).format(travelDate));

        System.out.println("Germany Date: " +
                shortDate.withLocale(germany).format(travelDate));

        // =====================================================
        // 🔥 STEP 4: LOCALIZED DATETIME
        // =====================================================

        DateTimeFormatter fullDateTime =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

        System.out.println("US DateTime: " +
                fullDateTime.withLocale(us).format(booking));

        System.out.println("France DateTime: " +
                fullDateTime.withLocale(france).format(booking));

        // =====================================================
        // 🔥 STEP 5: CUSTOM FORMAT (UI)
        // =====================================================

        DateTimeFormatter customFormat =
                DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a");

        System.out.println("UI Format: " +
                booking.format(customFormat));

        // =====================================================
        // 🔥 STEP 6: NUMBER LOCALIZATION
        // =====================================================

        int users = 3_200_000;

        NumberFormat usFormat =
                NumberFormat.getInstance(us);

        NumberFormat deFormat =
                NumberFormat.getInstance(germany);

        System.out.println("US Users: " + usFormat.format(users));
        System.out.println("Germany Users: " + deFormat.format(users));

        // =====================================================
        // 🔥 STEP 7: CURRENCY FORMATTING
        // =====================================================

        double price = 48;

        NumberFormat usCurrency =
                NumberFormat.getCurrencyInstance(us);

        NumberFormat frCurrency =
                NumberFormat.getCurrencyInstance(france);

        System.out.println("US Price: " + usCurrency.format(price));
        System.out.println("FR Price: " + frCurrency.format(price));

        // =====================================================
        // 🔥 STEP 8: PERCENT FORMATTING
        // =====================================================

        double success = 0.802;

        NumberFormat percentUS =
                NumberFormat.getPercentInstance(us);

        System.out.println("Success Rate: " +
                percentUS.format(success));

        // =====================================================
        // 🔥 STEP 9: PARSING NUMBERS (CRITICAL)
        // =====================================================

        String input = "40.45";

        NumberFormat parserUS =
                NumberFormat.getInstance(us);

        Number parsedValue =
                parserUS.parse(input);

        System.out.println("Parsed: " + parsedValue);

        // =====================================================
        // 🔥 STEP 10: PARSING CURRENCY
        // =====================================================

        String salary = "$92,807.99";

        NumberFormat currencyParser =
                NumberFormat.getCurrencyInstance(us);

        double parsedSalary =
                currencyParser.parse(salary).doubleValue();

        System.out.println("Salary: " + parsedSalary);

        // =====================================================
        // 🔥 STEP 11: COMPACT NUMBER FORMAT
        // =====================================================

        NumberFormat compact =
                NumberFormat.getCompactNumberInstance(
                        us, NumberFormat.Style.SHORT);

        System.out.println("Compact: " +
                compact.format(7_123_456));

        // =====================================================
        // 🔥 STEP 12: ANALYTICS (CHRONOUNIT)
        // =====================================================

        long days =
                ChronoUnit.DAYS.between(LocalDate.now(), travelDate);

        // =====================================================
        // 🔥 STEP 13: PERIOD & DURATION
        // =====================================================

        Period stay = Period.ofDays(5);
        Duration flight = Duration.ofHours(8);

        LocalDate checkout = travelDate.plus(stay);
        LocalTime arrival = travelTime.plus(flight);

        // =====================================================
        // 🔥 STEP 14: LOCALE CATEGORIES
        // =====================================================

        Locale.setDefault(Locale.Category.DISPLAY, france);
        Locale.setDefault(Locale.Category.FORMAT, germany);

        System.out.println(Locale.getDefault());

        // =====================================================
        // 🔥 FINAL OUTPUT
        // =====================================================

        System.out.println("Checkout: " + checkout);
        System.out.println("Arrival: " + arrival);
        System.out.println("Days Left: " + days);
    }
}



// WHAT THIS SCENARIO COVERS (100%)
// 🌍 INTERNATIONALIZATION (i18n)

// 👉 System designed to support multiple regions
// 👉 Uses formatters instead of hardcoding

// ✔ Concept covered

// 🌐 LOCALIZATION (l10n)

// 👉 Same data → different display

// ✔ Date formats
// ✔ Currency
// ✔ Numbers

// ✔ Concept covered

// 🌍 LOCALE
// ✔ Creation
// Locale.US
// Locale.of("en","US")
// ✔ Default
// Locale.getDefault()
// Locale.setDefault()
// 🔢 NUMBERFORMAT METHODS
// getInstance()
// getNumberInstance()
// getCurrencyInstance()
// getPercentInstance()
// getIntegerInstance()
// getCompactNumberInstance()

// ✔ ALL covered

// 🔄 PARSING METHODS
// parse(String)

// 👉 String → Number
// 👉 Throws checked exception

// 📅 DATETIME FORMATTERS
// ofLocalizedDate()
// ofLocalizedTime()
// ofLocalizedDateTime()
// withLocale()
// ⚠️ CRITICAL TRAPS (ALL INCLUDED)
// ❌ Locale format rules (en_US only)
// ❌ Parsing wrong locale
// ❌ Compact parsing surprises
// ❌ NumberFormat not thread-safe
// ❌ Default locale confusion
// ❌ DISPLAY vs FORMAT category

👉