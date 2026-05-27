
//========================================
   Working with Advanced APIs
//========================================
Manipulating Input Streams
All input stream classes ( InputStream, Reader ) support:
boolean markSupported()
void mark(int readLimit)
void reset() throws IOException
long skip(long n) throws IOException



Buffer = Tank / Bag / Temporary Storage

// A buffer is like a small tank or bag that holds some data temporarily.

// River/File ---> Pipe ---> Tank/Buffer ---> Hospital/System

Java:BufferedInputStream
// Hospital example:
// The ICU system stores a small amount of heart-monitor data in a temporary tank.
// If the doctor needs to replay the last few seconds, the system can go back inside the buffer.

// That is why this often works:
InputStream input = new BufferedInputStream(new FileInputStream("heart-data.txt")
    );

System.out.println(input.markSupported()); // true
4. mark() = Put a Flag at This Water Point
input.mark(5000);

// River example:Put a marker at this exact point in the flowing water.
// Hospital example:Mark the current point in the ICU heart-monitor data.
5. reset() = Go Back to the Marker
input.reset();

// River example:
Go back to the water position where the marker was placed.
But this only works if the stream has a tank/buffer that remembered the water/data.

// Without a buffer, the water already flowed away.
6. markSupported() = Does This Pipe Have a Tank?
input.markSupported()

// Means:
Does this stream have the ability to remember a position and go back?
// River example:
Direct pipe from river:
No tank → cannot go back
Pipe with tank:
Has storage → can go back

// Java example:
InputStream input1 = new FileInputStream("heart-data.txt");
System.out.println(input1.markSupported()); // false
input1.mark(100);// usually does not throw an exception. But it has no real effect, because FileInputStream does not support marking.

input1.reset();// This will throw an IOException because there is no mark supported and no buffer to go back to.
InputStream input2 =
    new BufferedInputStream(
        new FileInputStream("heart-data.txt")
    );
System.out.println(input2.markSupported()); // true


Stream type	                markSupported()
InputStream base class	       Usually false
FileInputStream	Usually         false
BufferedInputStream	            true
ByteArrayInputStream	        true
Network/socket stream Usually   false

// import java.io.*;
public class Test {
    public static void main(String[] args) throws IOException {
        byte[] data = "LION".getBytes();
        InputStream is = new ByteArrayInputStream(data);
        readData(is);
    }
    public static void readData(InputStream is) throws IOException {
        System.out.print((char) is.read()); // L
        if (is.markSupported()) {
            is.mark(100);
            System.out.print((char) is.read()); // I
            System.out.print((char) is.read()); // O
            is.reset();
        }
        System.out.print((char) is.read()); // I
        System.out.print((char) is.read()); // O
        System.out.print((char) is.read()); // N
    }
}

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class ReaderExample {
    public static void main(String[] args) throws IOException {
        Reader reader = new StringReader("LION");
        readData(reader);
    }
    public static void readData(Reader reader) throws IOException {
        System.out.print((char) reader.read()); // L
        if (reader.markSupported()) {
            reader.mark(100);
            System.out.print((char) reader.read()); // I
            System.out.print((char) reader.read()); // O
            reader.reset();
        }
        System.out.print((char) reader.read()); // I
        System.out.print((char) reader.read()); // O
        System.out.print((char) reader.read()); // N
    }
}


import java.io.*;

public class MarkResetWorks {
    public static void main(String[] args) throws IOException {
        BufferedInputStream in = new BufferedInputStream(
                new FileInputStream("data.txt"));

        if (in.markSupported()) {
            in.mark(5); // remember position before reading 'a'

            System.out.println((char) in.read()); // a
            System.out.println((char) in.read()); // b
            System.out.println((char) in.read()); // c

            in.reset(); // goes back to the marked position

            System.out.println((char) in.read()); // a again
        }

        in.close();
    }
}

Example 2: reset() may fail

Here we mark with readLimit = 5, but we read 7 bytes.
import java.io.*;

public class MarkResetFails {
    public static void main(String[] args) throws IOException {
        BufferedInputStream in = new BufferedInputStream(
                new FileInputStream("data.txt"));

        if (in.markSupported()) {
            in.mark(5); // remember position before reading 'a'

            System.out.println((char) in.read()); // a
            System.out.println((char) in.read()); // b
            System.out.println((char) in.read()); // c
            System.out.println((char) in.read()); // d
            System.out.println((char) in.read()); // e
            System.out.println((char) in.read()); // f
            System.out.println((char) in.read()); // g

            in.reset(); // may fail because more than 5 bytes were read

            System.out.println((char) in.read()); // may not run
        }

        in.close();
    }
}

long skipped = in.skip(5);//
system.out.println("Skipped bytes: " + skipped);// This will skip 5 bytes and print how many bytes were actually skipped.

long skipped = in.skip(10);//You requested 10, but the result might be:
System.out.println(skipped);//4




//========================================
   Discovering File Attributes
//========================================
// Files.isDirectory(Path.of("/canine/fur.jpg"));//false
// Files.isSymbolicLink(Path.of("/canine/coyote"));//true
// Files.isRegularFile(Path.of("/canine/types.txt"));//true
Hospital Scenario

Imagine this hospital file system:

/hospital
    /patients
        ahmed.txt
        fatima.txt
    /lab-results
        blood-test.csv
    current-patient -> /hospital/patients/ahmed.txt

Now compare:

Directory
Files.isDirectory(Path.of("/hospital/patients"));//true

Regular file
Files.isRegularFile(Path.of("/hospital/patients/ahmed.txt"));//true


Symbolic link
Files.isSymbolicLink(Path.of("/hospital/current-patient"));//true


//========================================
        Checking File Accessibility
//========================================

// Files.isHidden(Path)
// Files.isReadable(Path)
// Files.isWritable(Path)
// Files.isExecutable(Path)

Hospital Scenario
Imagine your hospital system has this structure:
/hospital
    /patients
        ahmed.txt
    /lab-results
        blood-test.csv
    /private
        .audit-log
    /scripts
        nightly-backup.sh

Files.isHidden(Path.of("/hospital/private/.audit-log"));//true
Files.isReadable(Path.of("/hospital/lab-results/blood-test.csv"));//true
Files.isWritable(Path.of("/hospital/patients/ahmed.txt"));
“Can we update the patient record?”
Files.isExecutable(Path.of("/hospital/scripts/nightly-backup.sh"));
asks:“Can we run the backup script?”



Path path = Path.of("missing-file.txt");
System.out.println(Files.isReadable(path));   // false
System.out.println(Files.isWritable(path));   // false
System.out.println(Files.isExecutable(path)); // false
Files.isHidden(path) //this is different because it can throw an exception.
try {
    System.out.println(Files.isHidden(path));
} catch (IOException e) {
    System.out.println("Could not check hidden status.");
}


//========================================
    Attribute and View Types
//========================================
•Exam focus: BasicFileAttributes

1. What is BasicFileAttributeView?


BasicFileAttributeView view =
    Files.getFileAttributeView(path, BasicFileAttributeView.class);
// This line means: “Give me a special object that lets me view and modify basic attributes of this file.

Imagine a hospital file: /hospital/patients/ahmed-record.txt

The file content may contain:
    // Patient name: Ahmed
    // Diagnosis: ...
    // Medication: ...

// But the file attributes are separate:
    Created time: May 1, 2026
    Last modified time: May 18, 2026
    Last accessed time: May 20, 2026
    Size: 4 KB
    Type: regular file





    // 1. Reading the attributes
BasicFileAttributes attrs = view.readAttributes();
// This means:“Read the current metadata information for this file.”

// Now attrs contains information such as:
attrs.isRegularFile();
attrs.isDirectory();
attrs.size();
attrs.lastModifiedTime();
//Converting the time to milliseconds
  attrs.lastModifiedTime().toMillis() // Because milliseconds are easy to calculate with.

//2- modifying the attributes

BasicFileAttributeView view =
    Files.getFileAttributeView(path, BasicFileAttributeView.class);
//   Creating a new file time
FileTime newTime = FileTime.fromMillis(
    attrs.lastModifiedTime().toMillis() + 10_000);

// Updating the file time
view.setTimes(newTime, null, null);
// The method has three parameters:
setTimes(lastModifiedTime, lastAccessTime, creationTime);
// | Parameter          |     Value | Meaning         |
// | ------------------ | --------: | --------------- |
// | `lastModifiedTime` | `newTime` | Change this one |
// | `lastAccessTime`   |    `null` | Do not change   |
// | `creationTime`     |    `null` | Do not change   |

BasicFileAttributeView view =
    Files.getFileAttributeView(path, BasicFileAttributeView.class);

// Read current file metadata
BasicFileAttributes attrs = view.readAttributes();

// Take old last-modified time and add 10 seconds
FileTime newTime = FileTime.fromMillis(
    attrs.lastModifiedTime().toMillis() + 10_000
);

// Update only the last-modified time
view.setTimes(newTime, null, null);


// Traversing a Directory Tree
Java uses Files.walk() to traverse a directory tree.

// Depth-First Search: DFS
DFS means: Go deep into one folder first before moving to the next folder.
Example:

// /hospital
//  ├── patients
//  │    ├── current
//  │    │    └── ahmed.txt
//  │    └── archived
//  │         └── old-record.txt
//  └── lab-results
//       └── blood.csv

DFS visits like this:
// /hospital
// /hospital/patients
// /hospital/patients/current
// /hospital/patients/current/ahmed.txt
// /hospital/patients/archived
// /hospital/patients/archived/old-record.txt
// /hospital/lab-results
// /hospital/lab-results/blood.csv

It goes deep first.

Breadth-First Search:
// BFS means:Visit everything at the same level first, then go deeper.

Using the same tree:

// /hospital
//  ├── patients
//  │    ├── current
//  │    │    └── ahmed.txt
//  │    └── archived
//  │         └── old-record.txt
//  └── lab-results
//       └── blood.csv

BFS would visit like this:

// /hospital
// /hospital/patients
// /hospital/lab-results
// /hospital/patients/current
// /hospital/patients/archived
// /hospital/lab-results/blood.csv
// /hospital/patients/current/ahmed.txt
// /hospital/patients/archived/old-record.txt
It handles the wide level first.


For the OCP exam, remember:

Files.walk() = depth-first

here are two common forms:

Stream<Path> Files.walk(Path start)

and:

Stream<Path> Files.walk(Path start, int maxDepth)
1. Walk everything
Files.walk(Path.of("/hospital"))

This starts at:/hospital and walks through all subdirectories and files.
By default, the depth is very large: Integer.MAX_VALUE

// So Java will keep walking as deep as possible unless you limit it. 
Your notes mention that Files.walk() is lazy and has a default depth of Integer.MAX_VALUE

// 2. Walk with a depth limit
Files.walk(Path.of("/hospital"), 2)

This means: Start at /hospital, but only go down 2 levels.

Example:

// Depth 0: /hospital
// Depth 1: /hospital/patients
// Depth 1: /hospital/lab-results
// Depth 2: /hospital/patients/ahmed.txt
// Depth 2: /hospital/lab-results/blood.csv

Important rule:

// Depth 0 = starting path
// Depth 1 = direct children
// Depth 2 = grandchildren

What does “lazy evaluation” mean?
// Your notes say Files.walk() uses lazy evaluation.
// That means Java does not immediately load every file and folder into memory.

// Instead, it produces paths gradually as the stream is processed.

Example:

try (Stream<Path> stream = Files.walk(Path.of("/hospital"))) {
    stream.forEach(System.out::println);
}

// Java walks through the directory as the stream is consumed.
// This is useful because a hospital system may have thousands or millions of files.

Calculating Directory Size

Your notes show this idea:

try (var s = Files.walk(source)) {
    return s.filter(p -> !Files.isDirectory(p))
            .mapToLong(this::getSize)
            .sum();
}

This calculates the size of a directory by:

// Walking through the directory tree
// Keeping only files
// Getting each file’s size
// Adding all sizes together



Professional Hospital Example

Imagine you want to calculate how much storage the hospital’s patient records are using.

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class HospitalDirectorySize {

    public static long getDirectorySize(Path source) throws IOException {
        try (Stream<Path> stream = Files.walk(source)) {
            return stream
                    .filter(path -> !Files.isDirectory(path))
                    .mapToLong(HospitalDirectorySize::getSize)
                    .sum();
        }
    }

    // s.filter(Predicate.not(Files::isDirectory))

    // mapToLong(this::getSize)
    // .mapToLong(p -> this.getSize(p))
    // .mapToLong(p -> getSize(p))

    private static long getSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            return 0L;
        }
    }

    public static void main(String[] args) throws IOException {
        Path hospitalRecords = Path.of("/hospital/patient-records");
 0 
        long totalSize = getDirectorySize(hospitalRecords);

        System.out.println("Total hospital records size: " + totalSize + " bytes");
    }
}


//////
// hospital/
//  ├── patients/
//  │    ├── patient-a.txt      100 bytes
//  │    └── patient-b.txt      200 bytes
//  ├── labs/
//  │    └── blood-test.txt     300 bytes
//  └── notes.txt               50 bytes

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class DirectorySizeExample {
    public static void main(String[] args) throws IOException {
        Path directory = Path.of("hospital");

        try (Stream<Path> paths = Files.walk(directory)) {
            long totalSize = paths
                    .filter(Files::isRegularFile)
                    .mapToLong(path -> {
                        try {
                            return Files.size(path);
                        } catch (IOException e) {
                            return 0L;
                        }
                    })
                    .sum();

            System.out.println("Total directory size: " + totalSize + " bytes");//650 bytes
        }
    }
}
try (Stream<Path> paths = Files.walk(directory)) {
    long totalSize = paths
            .parallel()
            .filter(Files::isRegularFile)
            .mapToLong(path -> {
                try {
                    return Files.size(path);
                } catch (IOException e) {
                    return 0L;
                }
            })
            .sum();

    System.out.println(totalSize);
}



// Step-by-Step Explanation
// Step 1: Walk the hospital records folder
// Files.walk(source)

// This visits:

// /hospital/patient-records
// /hospital/patient-records/ahmed.txt
// /hospital/patient-records/fatima.txt
// /hospital/patient-records/archive/old-record.txt
// Step 2: Remove directories
// .filter(path -> !Files.isDirectory(path))

// This keeps only files.

// It removes:

// /hospital/patient-records
// /hospital/patient-records/archive

// It keeps:

// /hospital/patient-records/ahmed.txt
// /hospital/patient-records/fatima.txt
// /hospital/patient-records/archive/old-record.txt
// Step 3: Get file size
// .mapToLong(HospitalDirectorySize::getSize)

// Example:

// ahmed.txt = 2,000 bytes
// fatima.txt = 3,000 bytes
// old-record.txt = 5,000 bytes
// Step 4: Add all file sizes
// .sum()

// Result:

// 2,000 + 3,000 + 5,000 = 10,000 bytes

// So the hospital records folder uses:

// 10,000 bytes

Why use try-with-resources?
try (Stream<Path> stream = Files.walk(source)) {
    ...
}

// Files.walk() opens system resources while walking the file tree.

// So you should close the stream when finished.

// That is why we use:

// try (...)

// This automatically closes the stream.


Symbolic Links in Traversal

This topic is about what happens when Java walks through folders using:
Files.walk(path) and it finds a symbolic link.?

// A symbolic link is like a shortcut or pointer to another file or folder.

1. Default behavior: 
// Files.walk() does not follow symbolic links

By default: Files.walk(Path.of("/hospital"))
// does not follow symbolic links.

// That means if Java sees a symbolic link, it notices the link, but it does not automatically enter the target folder.

Hospital Scenario

Imagine this hospital file system:

/hospital
 ├── patients
 │    └── ahmed.txt
 ├── lab-results
 │    └── blood.csv
 └── current-patient -> /hospital/patients

Here: current-patient -> /hospital/patients

means:
// current-patient is a symbolic link pointing to the real /hospital/patients folder.

// It is like a shortcut.


Files.walk(Path.of("/hospital"))

Java may see:

/hospital
/hospital/patients
/hospital/patients/ahmed.txt
/hospital/lab-results
/hospital/lab-results/blood.csv
/hospital/current-patient

// But it does not go inside:
/hospital/current-patient/ahmed.txt

2. How to follow symbolic links
// To tell Java to follow symbolic links, use:

FileVisitOption.FOLLOW_LINKS

Example:

Files.walk(
    Path.of("/hospital"),
    FileVisitOption.FOLLOW_LINKS
)

// Now Java is allowed to follow symbolic links into their target locations. Your notes show that symbolic link traversal can be enabled with FileVisitOption.FOLLOW_LINKS.

What is a cycle?

// A cycle happens when a symbolic link points back to a parent folder or ancestor folder.

Example:

/hospital
 ├── patients
 │    ├── ahmed.txt
 │    └── back-to-hospital -> /hospital

// Here:
/hospital/patients/back-to-hospital
points back to: /hospital , That creates a loop. This is the infinite traversal risk.

What does Java do?

Java NIO.2 protects you.

If Java detects a cycle while following symbolic links, 
it throws: FileSystemLoopException

Professional Hospital Example
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class HospitalWalker {

    public static void main(String[] args) throws IOException {
        Path hospital = Path.of("/hospital");

        try (Stream<Path> paths = Files.walk(
                hospital,
                FileVisitOption.FOLLOW_LINKS)) {

            paths.forEach(System.out::println);

        } catch (FileSystemLoopException e) {
            System.out.println("Cycle detected in hospital folders: " + e.getFile());
        }
    }
}


Searching with Files.find()

// Files.find() is like Files.walk(), but with a built-in search condition.

Instead of walking everything and then filtering later, Files.find() lets you say:

// “Walk this directory tree and only return paths that match my condition.”

// Your notes show that Files.find() provides built-in filtering and automatically gives you file attributes while searching.

// Method Signature
Stream<Path> Files.find(
    Path start,
    int maxDepth,
    BiPredicate<Path, BasicFileAttributes> matcher
)

// Example from your notes
Files.find(path, 10,
    (p, a) -> a.isRegularFile()
           && p.toString().endsWith(".java")
           && a.size() > 1_000)
    .forEach(System.out::println);

// This searches for files that meet all three conditions:
a.isRegularFile() // The path must be a normal file, not a directory.

p.toString().endsWith(".java")// The file name/path must end with .java.

a.size() > 1_000// The file must be larger than 1,000 bytes.

Final meaning:

// “Find all Java source files under this path, up to 10 levels deep, where the file size is greater than 1,000 bytes.”



Hospital Scenario

Imagine your hospital project folder looks like this:

// /hospital-system
//  ├── src
//  │    ├── PatientRecord.java
//  │    ├── Nurse.java
//  │    └── App.java
//  ├── logs
//  │    └── audit.log
//  └── documents
//       └── policy.txt

// You want to find Java files that are large enough to be important.

Path path = Path.of("/hospital-system");

try (Stream<Path> results = Files.find(path, 10,
        (p, a) -> a.isRegularFile()
               && p.toString().endsWith(".java")
               && a.size() > 1_000)) {

    results.forEach(System.out::println);
}

Possible output:

/hospital-system/src/PatientRecord.java
/hospital-system/src/Nurse.java
// Maybe App.java is too small, so it is not printed.

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class DirectorySizeExample {

    private static long getSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            return 0L;
        }
    }

    public static void main(String[] args) throws IOException {
        Path directory = Path.of("hospital");

        try (Stream<Path> paths = Files.find(
                directory,
                Integer.MAX_VALUE,
                (path, attrs) -> attrs.isRegularFile()
        )) {
            long totalSize = paths
                    .mapToLong(DirectorySizeExample::getSize)
                    .sum();

            System.out.println("Total directory size: " + totalSize + " bytes"); // 650 bytes
        }
    }
}
Why Files.find() is useful

// With Files.walk(), you usually do this:

Files.walk(path)
     .filter(p -> Files.isRegularFile(p))
     .filter(p -> p.toString().endsWith(".java"))
     .filter(p -> Files.size(p) > 1_000)


// But this can be less efficient because each call like Files.size(p) or Files.isRegularFile(p) may require separate file-system checks.

With Files.find(), Java gives you the attributes immediately:

(p, a) -> a.isRegularFile() && a.size() > 1_000     

p = Path
a = BasicFileAttributes

So this:
(p, a) -> a.isRegularFile()
       && p.toString().endsWith(".java")
       && a.size() > 1_000



// Using DirectoryStream     

This code:

try (DirectoryStream<Path> stream =
        Files.newDirectoryStream(path, "*.{txt,java}")) {

    for (Path p : stream)
        System.out.println(p);
}

means:

// “Open one directory, find files that match the pattern *.txt or *.java, print them, then close the directory stream.”

1. What is DirectoryStream<Path>?
DirectoryStream<Path> stream

// A DirectoryStream is like opening one folder and looking at the entries inside it.

Important:

// DirectoryStream does not automatically walk the whole tree.

// It usually checks only the direct contents of the directory.


Hospital Scenario

Imagine this hospital folder:

// /hospital
//  ├── patients.txt
//  ├── Nurse.java
//  ├── billing.pdf
//  ├── lab.csv
//  └── archive
//       └── old-record.txt

Now: Files.newDirectoryStream(path, "*.{txt,java}")

// means: “Inside /hospital, show me entries ending in .txt or .java.”

Possible output:

// /hospital/patients.txt
// /hospital/Nurse.java

It does not automatically go inside: /hospital/archive

// So it will not find: /hospital/archive/old-record.txt
// unless you open that directory separately.

3. Why use try-with-resources?
try (DirectoryStream<Path> stream =
        Files.newDirectoryStream(path, "*.{txt,java}")) {

// DirectoryStream uses operating system resources. So Java should close it after use.

// That is why we use: try (...) This automatically closes the stream

4. The for loop
for (Path p : stream)
    System.out.println(p);

This means: “For each matching path, print it.”

Example output:

// /hospital/patients.txt
// /hospital/Nurse.java
        }


Stream Class Notes

// This part is comparing two important stream ideas:

InputStreamReader / OutputStreamWriter
// and
FilterInputStream / FilterOutputStream


1. InputStreamReader
// Meaning InputStreamReader is a bridge from bytes to characters.

// It converts byte stream into character reader


// Why do we need it?
Because: InputStream reads bytes. But sometimes those bytes represent text.

Example: Patient Name: Ahmed
// Inside the computer, that text is stored as bytes. So Java needs a way to convert bytes into characters. That is where InputStreamReader comes in.

Hospital scenario
// Imagine the hospital receives patient data from a network connection:

Patient: Ahmed
Room: 204
Status: Admitted

// The network sends the data as bytes. Java receives it as: InputStream
// But you want to read it as text. 


// So you wrap it:
InputStream input = new FileInputStream("patient.txt");
Reader reader = new InputStreamReader(input);

// Now Java can read characters instead of raw bytes.

2. OutputStreamWriter
// Meaning OutputStreamWriter is the opposite bridge.
// It converts character writer into byte output stream

Why do we need it?
// Because: OutputStream writes bytes. But sometimes your program has text characters.

Example: Discharge summary completed.
// Before Java can write that text into a file or network stream, it must convert characters into bytes. That is what OutputStreamWriter does.

// Hospital example
OutputStream output = new FileOutputStream("discharge-summary.txt");
Writer writer = new OutputStreamWriter(output);

writer.write("Patient discharged in stable condition.");
writer.close();

// This means:“Take character text and write it out as bytes to the file.”


3. FilterInputStream
// Meaning FilterInputStream: is a parent class for input streams that wrap another input stream and add extra behavior.

// You usually do not use FilterInputStream directly. Instead, you use one of its subclasses.

Example:

BufferedInputStream
DataInputStream

// Hospital scenario
// Imagine a hospital data river. The raw stream contains patient data.

// A filter stream is like placing a special checkpoint on the river.

// The checkpoint does not create the river.
// It adds extra behavior while the data passes through.

Example: BufferedInputStream
InputStream input = new FileInputStream("patient-record.txt");
InputStream buffered = new BufferedInputStream(input);

This means:
// “Read the patient file, but use buffering to make reading more efficient.”

// The file stream brings the data.The buffered stream improves how the data is read.


4. FilterOutputStream
// Meaning FilterOutputStream: is a parent class for output streams that wrap another output stream and add extra behavior.

// You usually do not use it directly.Instead, you use subclasses.

Example:

BufferedOutputStream
DataOutputStream
PrintStream


// Hospital example
OutputStream output = new FileOutputStream("audit-log.txt");
OutputStream buffered = new BufferedOutputStream(output);

This means:
// “Write hospital audit log data to a file, but use buffering for better performance.”



// Professional Hospital Example

Suppose your hospital system reads patient information from a file and writes a processed report.

// Reading text from bytes
InputStream input = new FileInputStream("patient-record.txt");
Reader reader = new InputStreamReader(input);

Meaning:
// File bytes → Java characters


// Writing text as bytes
OutputStream output = new FileOutputStream("patient-summary.txt");
Writer writer = new OutputStreamWriter(output);

Meaning:
// Java characters → File bytes


Full Example
import java.io.*;

public class HospitalStreamBridgeExample {

    public static void main(String[] args) throws IOException {

        try (
            InputStream input = new FileInputStream("patient-record.txt");
            Reader reader = new InputStreamReader(input);

            OutputStream output = new FileOutputStream("patient-summary.txt");
            Writer writer = new OutputStreamWriter(output)
        ) {
            int data;

            while ((data = reader.read()) != -1) {
                writer.write(data);
            }
        }
    }
}
What this does

This program:
// Opens a patient record file as a byte stream.
// Uses InputStreamReader to convert bytes into characters.
// Reads the characters.
// Uses OutputStreamWriter to convert characters back into bytes.
// Writes them to another file.