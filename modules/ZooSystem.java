// Perfect. Now we build a full, professional, real Java system (Zoo System) that actually runs, not just theory.
// This will directly implement everything from your “Diving into module declaration” section:

// exports
// exports to
// requires
// requires transitive
// opens
// open module
// real classes interacting


FULL PROFESSIONAL ZOO SYSTEM (JPMS)
🔷 1. SYSTEM OVERVIEW

We design a real zoo platform:

// zoo.animal.feeding   → feeding animals
// zoo.animal.care      → medical care
// zoo.animal.talks     → visitor talks/events
// zoo.staff            → staff system (main app)




2. MODULE 1 — FEEDING (FOUNDATION)
// 📄 module-info.java
module zoo.animal.feeding {
    exports zoo.animal.feeding;
}

// FeedingService.java
// package zoo.animal.feeding;

public class FeedingService {

    public void feedAnimal(String animal) {
        System.out.println("Feeding " + animal);
    }
}

3. MODULE 2 — CARE (TRANSITIVE DEPENDENCY)
// 📄 module-info.java

module zoo.animal.care {
    exports zoo.animal.care.medical;
    // CRITICAL: transitive dependency
    requires transitive zoo.animal.feeding;
}


// 📄 MedicalService.java


package zoo.animal.care.medical;
import zoo.animal.feeding.FeedingService;

public class MedicalService {

    public void treatAnimal(String animal) {

        System.out.println("Treating " + animal);

        // uses feeding indirectly
        FeedingService feeding = new FeedingService();
        feeding.feedAnimal(animal);
    }
}





🔷 4. MODULE 3 — TALKS (ADVANCED MODULE)
📄 module-info.java
module zoo.animal.talks {

    // PUBLIC API
    exports zoo.animal.talks.media;
    exports zoo.animal.talks.schedule;

    // RESTRICTED EXPORT
    exports zoo.animal.talks.content to zoo.staff;

    // TRANSITIVE DEPENDENCY
    requires transitive zoo.animal.care;

    // REFLECTION ACCESS
    opens zoo.animal.talks.schedule;

    // QUALIFIED REFLECTION
    opens zoo.animal.talks.media to zoo.staff;
}


📄 TalkContent.java (RESTRICTED)
package zoo.animal.talks.content;

public class TalkContent {

    public String getContent() {
        return "Lion feeding session";
    }
}

📄 MediaService.java
package zoo.animal.talks.media;

public class MediaService {

    public void playVideo() {
        System.out.println("Playing zoo educational video...");
    }
}
📄 ScheduleService.java
package zoo.animal.talks.schedule;

public class ScheduleService {

    private String schedule = "10 AM - Lion Talk";

    public String getSchedule() {
        return schedule;
    }
}


🔷 5. MODULE 4 — STAFF (MAIN APPLICATION)
📄 module-info.java
module zoo.staff {

    requires zoo.animal.talks;

    // Notice:
    // NO direct requires feeding or care
    // → gets them via transitive
}
📄 ZooStaffApp.java
package zoo.staff;

import zoo.animal.talks.media.MediaService;
import zoo.animal.talks.schedule.ScheduleService;
import zoo.animal.talks.content.TalkContent;
import zoo.animal.care.medical.MedicalService;

import java.lang.reflect.Field;

public class ZooStaffApp {

    public static void main(String[] args) throws Exception {

        System.out.println("=== Zoo Staff System ===");

        // 1. Media access (exported)
        MediaService media = new MediaService();
        media.playVideo();

        // 2. Schedule access (exported)
        ScheduleService schedule = new ScheduleService();
        System.out.println("Schedule: " + schedule.getSchedule());

        // 3. Restricted content (allowed because of exports to zoo.staff)
        TalkContent content = new TalkContent();
        System.out.println("Content: " + content.getContent());

        // 4. Transitive dependency (care → feeding)
        MedicalService medical = new MedicalService();
        medical.treatAnimal("Lion");

        // 5. Reflection (opens)
        Field field = ScheduleService.class.getDeclaredField("schedule");
        field.setAccessible(true);

        System.out.println("Reflection value: " + field.get(schedule));
    }
}
🔷 6. HOW EVERYTHING WORKS (VERY IMPORTANT)
🔥 1. TRANSITIVE DEPENDENCY
zoo.staff → zoo.animal.talks → zoo.animal.care → zoo.animal.feeding

👉 Staff can access feeding WITHOUT requiring it directly

🔥 2. QUALIFIED EXPORT
exports zoo.animal.talks.content to zoo.staff;

👉 Only zoo.staff can access TalkContent

🔥 3. EXPORT vs OPENS
Feature	exports	opens
Compile-time	✅	❌
Reflection	❌	✅
🔥 4. REFLECTION WORKS
field.setAccessible(true);

👉 Works because:

opens zoo.animal.talks.schedule;
🔥 5. ACCESS RULES DEMO
Package	Staff Access
media	✅
schedule	✅
content	✅ (restricted allowed)
non-exported	❌
🔷 7. COMPILATION (REAL TERMINAL)

// # compile feeding
// javac -d mods/zoo.animal.feeding \
// zoo.animal.feeding/module-info.java \
// zoo.animal.feeding/zoo/animal/feeding/*.java ..........*/

// # compile care
// javac -p mods -d mods/zoo.animal.care \
// zoo.animal.care/module-info.java \
// zoo.animal.care/zoo/animal/care/medical/*.java ..........*/

// # compile talks
// javac -p mods -d mods/zoo.animal.talks \
// zoo.animal.talks/module-info.java \
// zoo.animal.talks/zoo/animal/talks/*/*.java ..........*/

// # compile staff
// javac -p mods -d mods/zoo.staff \
// zoo.staff/module-info.java \
// zoo.staff/zoo/staff/*.java    ..........*/


🔷 8. RUNNING
// java -p mods -m zoo.staff/zoo.staff.ZooStaffApp

🔷 9. OUTPUT (REAL)
=== Zoo Staff System ===
Playing zoo educational video...
Schedule: 10 AM - Lion Talk
Content: Lion feeding session
Treating Lion
Feeding Lion
Reflection value: 10 AM - Lion Talk
🔥 FINAL UNDERSTANDING
🧠 YOU JUST SAW:
Real modules interacting
Real dependency chain
Real access control
Real reflection
Real transitive behavior