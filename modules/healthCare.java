// Full Scenario: Healthcare System with Java Modules
// Business story

// A healthcare application is split into separate teams and separate modules:

// health.patient
// Owns patient information
// health.medication
// Owns medication logic and depends on patient data
// health.app
// Main application that starts the system

// This is the kind of large system where modules help a lot.


1. Real Folder Structure

// Here is the source-code structure from the beginning:

hospital-system/
├── health.patient/
│   ├── module-info.java
│   └── com/
│       └── health/
│           └── patient/
│               ├── Patient.java
│               └── PatientService.java
│
├── health.medication/
│   ├── module-info.java
│   └── com/
│       └── health/
│           └── medication/
│               ├── MedicationService.java
│               └── PrescriptionPrinter.java
│
├── health.app/
│   ├── module-info.java
│   └── com/
│       └── health/
│           └── app/
│               └── Main.java
│
└── mods/


// 2. What each module does
// health.patient

// This module manages patient data.

// health.medication

// This module uses patient data and prepares medication instructions.

// health.app

// This is the entry point. It starts the whole program.


3. Source Code
// Module 1: health.patient
health.patient/module-info.java
module health.patient {
    exports com.health.patient;
}
// Meaning
// module health.patient → defines the module name
// exports com.health.patient → other modules may use this package



// health.patient/com/health/patient/Patient.java
package com.health.patient;

public class Patient {
    private final int id;
    private final String name;
    private final int age;

    public Patient(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}


// health.patient/com/health/patient/PatientService.java
package com.health.patient;

public class PatientService {

    public Patient findPatientById(int id) {
        // In a real system, this might come from a database.
        if (id == 101) {
            return new Patient(101, "Amina Hassan", 47);
        }

        return new Patient(id, "Unknown Patient", 0);
    }
}




// Module 2: health.medication
// health.medication/module-info.java
module health.medication {
    requires health.patient;
    exports com.health.medication;
}
// Meaning
// requires health.patient → this module depends on health.patient
// exports com.health.medication → other modules may use this package
// health.medication/com/health/medication/MedicationService.java
// package com.health.medication;

import com.health.patient.Patient;
import com.health.patient.PatientService;

public class MedicationService {

    private final PatientService patientService = new PatientService();

    public String generateMedicationPlan(int patientId) {
        Patient patient = patientService.findPatientById(patientId);

        return "Medication Plan for " + patient.getName()
                + " (Age: " + patient.getAge() + "): Take 1 tablet of Lisinopril daily.";
    }
}


// health.medication/com/health/medication/PrescriptionPrinter.java
package com.health.medication;

public class PrescriptionPrinter {

    public void printPrescription(String medicationPlan) {
        System.out.println("=== PRESCRIPTION SUMMARY ===");
        System.out.println(medicationPlan);
        System.out.println("============================");
    }
}


// Module 3: health.app
health.app/module-info.java
module health.app {
    requires health.patient;
    requires health.medication;
}
Meaning
// health.app depends on both other modules
// It is the main module that runs the application
// health.app/com/health/app/Main.java
// package com.health.app;

import com.health.medication.MedicationService;
import com.health.medication.PrescriptionPrinter;

public class Main {

    public static void main(String[] args) {
        MedicationService medicationService = new MedicationService();
        String plan = medicationService.generateMedicationPlan(101);

        PrescriptionPrinter printer = new PrescriptionPrinter();
        printer.printPrescription(plan);
    }
}
4. Why this is modular

Each team owns its own module:
patient team → health.patient
medication team → health.medication
application team → health.app

Each module clearly says:

what it needs → requires
what it shares → exports

That is the heart of JPMS.

5. Compile Time: Start from the beginning

Now let’s compile from scratch.

Assume you are inside:

hospital-system/
Step A: compile health.patient
javac -d mods/health.patient \
    health.patient/module-info.java \
    health.patient/com/health/patient/*.java  .............*/
What this means
javac

Java compiler

-d mods/health.patient

Put compiled output into this folder:

mods/health.patient/
source files

Compile:

module-info.java
all .java files in com.health.patient
Output after compiling health.patient
mods/
└── health.patient/
    ├── module-info.class
    └── com/
        └── health/
            └── patient/
                ├── Patient.class
                └── PatientService.class
Step B: compile health.medication

This module depends on health.patient, so at compile time Java must know where to find that module.

javac -p mods -d mods/health.medication \
    health.medication/module-info.java \
    health.medication/com/health/medication/*.java
Meaning
-p mods

Use mods as the module path

Java checks there for required modules.

Since health.medication says:

requires health.patient;

Java looks in:

mods/health.patient
Output after compiling health.medication
mods/
├── health.patient/
│   ├── module-info.class
│   └── com/health/patient/...
│
└── health.medication/
    ├── module-info.class
    └── com/
        └── health/
            └── medication/
                ├── MedicationService.class
                └── PrescriptionPrinter.class
Step C: compile health.app

This module depends on both other modules.

javac -p mods -d mods/health.app \
    health.app/module-info.java \
    health.app/com/health/app/Main.java
Output after compiling health.app
mods/
├── health.patient/
│   ├── module-info.class
│   └── com/health/patient/...
│
├── health.medication/
│   ├── module-info.class
│   └── com/health/medication/...
│
└── health.app/
    ├── module-info.class
    └── com/
        └── health/
            └── app/
                └── Main.class
6. Compile order matters

This is very important.

Because:

health.patient has no dependency here, so compile it first
health.medication requires health.patient, so compile it second
health.app requires both, so compile it last

So the normal order is:

health.patient
health.medication
health.app
7. Runtime: run the application

After compiling, run the main module:

java -p mods -m health.app/com.health.app.Main
Meaning
java

Start the JVM

-p mods

Tell Java where the compiled modules live

-m health.app/com.health.app.Main

Run:

module: health.app
main class: com.health.app.Main
8. What happens at runtime internally

When you run:

java -p mods -m health.app/com.health.app.Main

Java does this:

Step 1

Looks in mods for modules

Step 2

Finds module health.app

Step 3

Reads its module-info.class

module health.app {
    requires health.patient;
    requires health.medication;
}
Step 4

Resolves dependencies:

health.patient
health.medication
Step 5

Reads health.medication descriptor

module health.medication {
    requires health.patient;
    exports com.health.medication;
}
Step 6

Ensures required modules are present and accessible

Step 7

Finds com.health.app.Main

Step 8

Calls:

public static void main(String[] args)
9. Final runtime output

The output will be:

=== PRESCRIPTION SUMMARY ===
Medication Plan for Amina Hassan (Age: 47): Take 1 tablet of Lisinopril daily.
============================
10. Very important: why exports matters

Suppose health.patient did not export com.health.patient:

module health.patient {
    // no exports
}

Then health.medication would not be allowed to use:

import com.health.patient.Patient;
import com.health.patient.PatientService;

That would fail.

So:

requires = I need another module
exports = I allow others to use this package
11. Real compile-and-run flow in one place
Source folders
health.patient/
health.medication/
health.app/
Compile commands
javac -d mods/health.patient \
    health.patient/module-info.java \
    health.patient/com/health/patient/*.java

javac -p mods -d mods/health.medication \
    health.medication/module-info.java \
    health.medication/com/health/medication/*.java

javac -p mods -d mods/health.app \
    health.app/module-info.java \
    health.app/com/health/app/Main.java
Run command
java -p mods -m health.app/com.health.app.Main
12. What problem this solves compared to classpath

Without modules, all JARs are just thrown together.

Problems:

hard to control access
one team can use internal classes from another team
dependencies are unclear
runtime errors are more likely

With modules:

dependencies are explicit
access is controlled
package visibility is cleaner
Java checks more at compile time and startup time
13. Real-life mental model

Think of it like this:

Without modules

A hospital where every department can walk into every room.

With modules

A hospital where each department has:

a name
approved doors
allowed shared services
restricted internal rooms

That is exactly what JPMS is doing.

14. Important exam meanings of the command parts
javac

compile source code

-d

destination of compiled .class files

-p

module path used during compilation or runtime

java

run program

-m

run a specific module and its main class

module-info.java

the descriptor that defines module rules

15. One complete picture
WRITE SOURCE CODE
    ↓
Create module-info.java for each module
    ↓
Compile dependency modules first
    ↓
Store compiled output in mods/
    ↓
Run with:
java -p mods -m mainModule/mainClass
    ↓
JVM resolves modules and starts main()
16. Small exam traps
Trap 1: forgetting module-info.java

Then it is not a named module.

Trap 2: forgetting -p mods

Then Java cannot find required modules.

Trap 3: wrong compile order

If a required module is not compiled yet, dependent module compilation fails.

Trap 4: package not exported

The module may exist, but its package is still inaccessible.

17. Final short memory version
At compile time

Java checks module dependencies using -p

At runtime

Java resolves and loads modules using -p, then starts the main class using -m

18. The clean final version you can remember
Compile
javac -d mods/health.patient \
    health.patient/module-info.java \
    health.patient/com/health/patient/*.java

javac -p mods -d mods/health.medication \
    health.medication/module-info.java \
    health.medication/com/health/medication/*.java

javac -p mods -d mods/health.app \
    health.app/module-info.java \
    health.app/com/health/app/Main.java
Run
java -p mods -m health.app/com.health.app.Main

If you want, next I can give you the same scenario as one single full Java file set in clean exam style, with comments on every line and every terminal command.

Can you please add module discoverys?
Thought for a couple of seconds

Yes — and it is very important in JPMS.

You likely mean module discovery:
how Java finds modules, reads their descriptors, and builds the module graph before running or compiling.

I’ll add it directly into the same healthcare scenario.

Adding Module Discovery to the Full Scenario
Big idea

Before Java can compile or run a modular program, it must first discover modules.

That means Java must answer:

What modules exist?
Where are they located?
What does each module require?
Which modules become part of this run?
1. Same real project
hospital-system/
├── health.patient/
│   ├── module-info.java
│   └── com/health/patient/...
│
├── health.medication/
│   ├── module-info.java
│   └── com/health/medication/...
│
├── health.app/
│   ├── module-info.java
│   └── com/health/app/...
│
└── mods/
    ├── health.patient/
    ├── health.medication/
    └── health.app/

After compilation, the mods/ folder contains the compiled modules.

2. What “module discovery” means
Definition

Module discovery is the process where Java looks on the module path and finds available modules.

For each discovered module, Java reads:

module health.medication {
    requires health.patient;
    exports com.health.medication;
}

So Java learns:

this module is named health.medication
it depends on health.patient
it exports com.health.medication
3. Where Java discovers modules from

Java can discover modules from:

the module path
the system modules built into the JDK
A. System modules

These are modules that come with Java itself, such as:

java.base
java.sql
java.logging

These are already available in the JDK.

B. Application modules on module path

When you write:

java -p mods -m health.app/com.health.app.Main

Java searches mods for modules.

That is where your own modules are discovered.

4. Discovery during compile time

Let’s use this compile command again:

javac -p mods -d mods/health.medication \
    health.medication/module-info.java \
    health.medication/com/health/medication/*.java
What Java does here

When compiling health.medication, Java reads:

module health.medication {
    requires health.patient;
    exports com.health.medication;
}

Then it asks:

Where is health.patient?

Because of:

-p mods

Java checks inside mods/ and discovers:

mods/health.patient

Then it reads that module’s descriptor:

module health.patient {
    exports com.health.patient;
}

Now Java knows the dependency is available.

5. Discovery during runtime

Now the run command:

java -p mods -m health.app/com.health.app.Main

At runtime Java starts with the root module:

health.app

Then discovery begins.

Step-by-step runtime discovery
Step 1

Java discovers root module:

health.app
Step 2

Java reads:

module health.app {
    requires health.patient;
    requires health.medication;
}
Step 3

Java now discovers those required modules on the module path:

health.patient
health.medication
Step 4

Java reads health.medication:

module health.medication {
    requires health.patient;
    exports com.health.medication;
}
Step 5

Java sees health.patient is also required there

Step 6

Java builds the full module graph

6. Very important term: root module

The module you directly ask Java to run is the root module.

In this command:

java -p mods -m health.app/com.health.app.Main

the root module is:

health.app

Java starts discovery from that root.

7. Very important term: module graph

After discovery, Java builds a module graph.

That is the dependency map.

For our scenario, the graph looks like this:

health.app
 ├── requires health.medication
 │      └── requires health.patient
 └── requires health.patient

This means:

health.app depends on health.medication
health.app also depends on health.patient
health.medication also depends on health.patient
8. Why discovery matters

Without module discovery, Java would not know:

which modules are present
whether dependencies exist
what packages are exported
whether access is legal

So before Java runs code, it first resolves the module world.

9. Discovery vs resolution

These are related but slightly different.

Discovery

Java finds modules on the module path

Resolution

Java connects them based on requires

So:

discover = “I found these modules”
resolve = “I know how they depend on each other”
10. Full flow with discovery included
Source code stage

You write:

module-info.java
normal .java files
Compile stage

Example:

javac -p mods -d mods/health.app \
    health.app/module-info.java \
    health.app/com/health/app/Main.java
What happens
Java reads health.app/module-info.java
discovers modules on mods
finds health.patient and health.medication
resolves dependencies
compiles source
Runtime stage

Example:

java -p mods -m health.app/com.health.app.Main
What happens
Java discovers root module health.app
reads descriptor
discovers dependent modules
builds module graph
checks exports/access
runs main()
11. Real visual picture
MODULE PATH: mods/
   ├── health.patient
   ├── health.medication
   └── health.app

DISCOVERY:
   Java scans mods/
   ↓
Finds available modules
   ↓
Reads module-info.class
   ↓
RESOLUTION:
Builds dependency graph
   ↓
RUNTIME:
Starts root module main class
12. In our scenario, what exactly gets discovered?

When running:

java -p mods -m health.app/com.health.app.Main

Java discovers:

From your app
health.app
health.medication
health.patient
From the JDK
java.base automatically
and possibly more if needed
13. Very important: java.base

Every module automatically depends on:

java.base

You do not write it explicitly.

So even this:

module health.patient {
    exports com.health.patient;
}

still implicitly reads java.base.

That is why classes like String and System are available.

14. What if Java cannot discover a required module?

Example:

module health.medication {
    requires health.patient;
}

But health.patient is missing from mods.

Then compilation or runtime fails because Java cannot resolve the dependency.

This is one of the big advantages of modules: problems are detected earlier and more clearly.

15. Real professional explanation in hospital terms

Think of module discovery like this:

The hospital director says: “Start the hospital app.”
The system first checks which departments exist:
patient
medication
app
Then it checks which departments each one depends on
Then it allows only approved connections

So discovery is like checking all departments before opening the hospital for the day.

16. Add this to your memory
Module path

Where Java looks for modules

Discovery

Finding available modules on that path

Root module

The starting module you ask Java to run

Resolution

Connecting modules based on requires

Module graph

The final dependency structure

17. Clean exam-style summary
Compile time

Java discovers required modules on the module path and uses them to compile the current module.

Runtime

Java discovers the root module, then recursively discovers required modules, resolves them, builds the module graph, and starts the main class.

18. Final one-line understanding

Module discovery = Java locating modules and reading their descriptors before it can resolve dependencies and run the program.

19. Your scenario with discovery added in one compact block
1. Write source code and module-info.java
2. Compile first module
3. Put compiled modules into mods/
4. Use -p mods
5. Java discovers modules in mods/
6. Java reads module-info.class
7. Java resolves requires relationships
8. Java builds module graph
9. Java runs root module main class

If you want, I can now give you the same full healthcare scenario again, but upgraded with “compile time discovery + runtime discovery + module graph + root module” all inside one polished exam-ready note.


