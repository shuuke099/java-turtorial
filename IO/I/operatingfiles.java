Chapter 14: Operating on File and Path

This lecture continues perfectly from Lecture 1.

In Lecture 1, we learned:

File and Path are addresses to places in the file system.

Now Lecture 2 is saying:

Once Java has the address, what can Java do with it?

Your notes say this section covers inspecting files/directories, navigating directory structures, creating, copying, moving, renaming, and deleting files. It also compares legacy File with modern Path + Files.

Big Picture
Lecture 1
Path/File = address

Example:

Path path = Path.of("/hospital/ICU/patient001.txt");

This only says: “Here is the location.”

Lecture 2
// Files/File methods = actions on that address

Example:

Files.exists(path);
Files.size(path);
Files.createDirectory(path);
Files.move(source, target);
Files.delete(path);

// This means: “Check it, inspect it, create it, move it, copy it, or delete it.”

// River Picture: think of the file system as a river system map.

Main river
 ├── branch
 │    ├── small stream
 │    └── water point
 └── branch
      └── water point

A Path is the map coordinate.

Path p = Path.of("/river/icu/patient001.txt");

// But now Java wants to operate on that coordinate:

Java Operation	River Meaning
Files.exists(path)	Does this water point really exist?
Files.isDirectory(path)	Is this a river branch?
Files.isRegularFile(path)	Is this an actual water point/file?
Files.size(path)	How much water/data is stored there?
Files.list(path)	What smaller streams/files are inside this branch?
Files.createDirectory(path)	Create a new branch
Files.copy(source, target)	Copy water/data to another place
Files.move(source, target)	Move/rename the water point
Files.delete(path)	Remove the water point or empty branch
Hospital Picture

// Think of the file system as a hospital records building.

hospital-records/
 ├── ICU/
 │    ├── patient001.txt
 │    └── heart-monitor.dat
 ├── ER/
 │    └── triage-report.txt
 └── pharmacy/
      └── medication-log.csv

A Path is the address.

Path patientFile = Path.of("/hospital-records/ICU/patient001.txt");

Now Java can ask questions:

Files.exists(patientFile);//true if patient file exists
Files.isDirectory(patientFile);//false, it's a file not a folder
Files.isRegularFile(patientFile);//true, it's a regular file
Files.size(patientFile);//size of patient file in bytes
Files.getLastModifiedTime(patientFile);//when was it last updated?

Hospital meaning:

“Does this patient file exist? Is it a real file? How large is it? When was it last updated?”

Most Important Difference: File vs Files

Your notes make a key distinction:

// Legacy I/O	Modern NIO.2

java.io.File	java.nio.file.Path + Files
Uses instance methods	Uses static helper methods
Example: file.exists()	Example: Files.exists(path)

Very important:

Files works with Path, not File.

So this is correct:

Path path = Path.of("patient001.txt");
Files.exists(path);

This is correct:

File file = new File("patient001.txt");
file.exists();

But this is wrong:

// File file = new File("patient001.txt");
// Files.exists(file); // DOES NOT COMPILE

// Because Files.exists() expects a Path, not a File. Your notes emphasize that Files works with Path, not File.

Basic Path Information

These methods inspect the address itself.

Question	File	Path
What is the file name?	file.getName()	path.getFileName()
What is the parent?	file.getParent()	path.getParent()
Is it absolute?	file.isAbsolute()	path.isAbsolute()
Give absolute path	file.getAbsolutePath()	path.toAbsolutePath()

Your notes list these as common operations for retrieving basic path information.

Hospital Example
Path path = Path.of("hospital-records/ICU/patient001.txt");

System.out.println(path.getFileName());
System.out.println(path.getParent());
System.out.println(path.isAbsolute());
System.out.println(path.toAbsolutePath());

Possible meaning:

patient001.txt
hospital-records/ICU
false
/Users/mahamud/project/hospital-records/ICU/patient001.txt

Hospital translation:

getFileName()       = patient file name
getParent()         = department folder
isAbsolute()        = is this a full hospital address?
toAbsolutePath()    = convert it to the full address
File-System Interaction

These methods check the real file system.

Question	File	Files
Does it exist?	file.exists()	Files.exists(path)
Is it a directory?	file.isDirectory()	Files.isDirectory(path)
Is it a regular file?	file.isFile()	Files.isRegularFile(path)
Last modified?	file.lastModified()	Files.getLastModifiedTime(path)
Size?	file.length()	Files.size(path)

Your notes show these as common file-system interaction methods.

Hospital Example: Inspecting a Patient File
import java.nio.file.*;
import java.io.IOException;

public class HospitalFileInspector {
    public static void main(String[] args) throws IOException {

        Path path = Path.of("hospital-records/ICU/patient001.txt");

        if (Files.exists(path)) {
            System.out.println("Absolute Path: " + path.toAbsolutePath());
            System.out.println("Is Directory: " + Files.isDirectory(path));
            System.out.println("Parent Path: " + path.getParent());

            if (Files.isRegularFile(path)) {
                System.out.println("Size: " + Files.size(path));
                System.out.println("Last Modified: " + Files.getLastModifiedTime(path));
            }
        }
    }
}

Meaning:

The hospital system checks whether the patient file exists, whether it is a file or folder, where it is located, how big it is, and when it was last updated.

Directory Contents
Legacy File
File[] files = file.listFiles();
Modern NIO.2
Stream<Path> stream = Files.list(path);

Your notes say File.listFiles() lists contents, while Files.list(Path) returns a Stream<Path>.

Hospital Example
Path icuFolder = Path.of("hospital-records/ICU");

try (Stream<Path> stream = Files.list(icuFolder)) {
    stream.forEach(p -> System.out.println(p.getFileName()));
}

Meaning:

Open the ICU folder and list what is directly inside it.

Important:

Files.list(path) lists only one level.
It does not walk the whole tree deeply.

For deep traversal, use:

Files.walk(path)
Closing NIO.2 Streams

This is a major exam point.

Your notes say streams returned by NIO.2 must be closed, and terminal operations do not automatically close streams.

So this is best practice:

try (Stream<Path> stream = Files.list(path)) {
    stream.forEach(System.out::println);
}

Not this:

Stream<Path> stream = Files.list(path);
stream.forEach(System.out::println); // stream not closed
Hospital meaning

The hospital scanner opens access to the records folder.

After scanning, it must close the access properly.

Otherwise, there may be:

file locks
resource leaks
// Creating Directories
Operation	File	Files
Create one directory	file.mkdir()	Files.createDirectory(path)
Create directory tree	file.mkdirs()	Files.createDirectories(path)

Your notes compare single-directory creation with creating a full directory tree.

// createDirectory()
Files.createDirectory(Path.of("hospital-records/ICU"));

This creates only the final directory.

But if the parent does not exist, it fails.

Example:

Files.createDirectory(Path.of("hospital-records/ICU/monitor"));
// If hospital-records/ICU does not exist, this fails.

// createDirectories()
Files.createDirectories(Path.of("hospital-records/ICU/monitor"));

This creates missing parents too.

// Hospital meaning:If the hospital building, ICU department, or monitor folder is missing, Java creates the needed folders along the way.


// Your notes say createDirectory() fails if parents are missing, while createDirectories() creates missing parents and is safe if the directory already exists.



// Renaming and Moving

Legacy:  file.renameTo(newFile);

Modern: Files.move(source, target);

Your notes show File.renameTo(File) and Files.move(Path, Path, CopyOption...) for renaming and moving.

Hospital Example
Path oldName = Path.of("hospital-records/ICU/temp-report.txt");
Path newName = Path.of("hospital-records/ICU/final-report.txt");

Files.move(oldName, newName);

Meaning: Rename the temporary ICU report to final report.

Moving to another directory:

Path source = Path.of("hospital-records/ICU/patient001.txt");
Path target = Path.of("hospital-records/archive/patient001.txt");

Files.move(source, target);

Meaning: Move the patient file from ICU active records into archive.

// Handling IOException Many NIO.2 methods throw checked IOException.

// Your notes say common causes include file system communication loss, insufficient permissions, nonexistent required paths, and attempts to overwrite restricted files.

Example:

public void nio(Path path) throws IOException {
    if (Files.exists(path)) {
        System.out.println(Files.size(path));
    }
}

Hospital meaning:

The system may fail because the file is missing, permission is denied, the network drive disconnected, or the file is locked.

Important:

Files.exists(path)

does not throw IOException.

But many other Files methods do.

Optional Parameters: Varargs

Some NIO.2 methods accept optional parameters.

Your notes list examples like:

LinkOption.NOFOLLOW_LINKS
StandardCopyOption.REPLACE_EXISTING
StandardCopyOption.ATOMIC_MOVE
StandardOpenOption.CREATE
StandardOpenOption.READ
StandardOpenOption.WRITE

These are like extra instructions.

Hospital example
Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

Meaning:

Copy this patient file, and if an older version already exists, replace it.



// Path Immutability

This is very important.

// Your notes say Path objects are immutable and methods return new Path instances.

Example:

Path p = Path.of("whale");
p.resolve("krill");
System.out.println(p); // whale

Why?

Because resolve() does not change p.

Correct version:

Path p = Path.of("whale");
p = p.resolve("krill");
System.out.println(p); // whale/krill
Hospital meaning
Path p = Path.of("hospital-records");
p.resolve("ICU");

This does not change p.

It is like saying:

“Here is a possible route to ICU,”

but not saving the new address.

Correct:

Path p = Path.of("hospital-records");
Path icu = p.resolve("ICU");
Viewing Path Components

Example from your notes:

Path path = Path.of("/land/hippo/harry.happy");
path.getNameCount();
path.getName(i);

Important rules:

Root is NOT included.
Index is zero-based.
Invalid index throws IllegalArgumentException.

Hospital Example
Path path = Path.of("/hospital/ICU/patient001.txt");

System.out.println(path.getNameCount());
System.out.println(path.getName(0));
System.out.println(path.getName(1));
System.out.println(path.getName(2));

Output concept:

// 3
// hospital
// ICU
// patient001.txt

// Root / is not counted.

getFileName(), getParent(), getRoot()

Your notes list:

getFileName() → final segment
getParent() → containing directory or null
getRoot() → root or null

Example:

Path path = Path.of("/hospital/ICU/patient001.txt");

System.out.println(path.getRoot());      // /
System.out.println(path.getParent());    // /hospital/ICU
System.out.println(path.getFileName());  // patient001.txt

Hospital meaning:

getRoot()      = top of records system
getParent()    = ICU folder
getFileName()  = patient file
Resolving Paths

// resolve() behaves like concatenation.

Your notes say:

Relative input → appended
Absolute input → returned as-is
Cannot combine two absolute paths

Example
Path base = Path.of("/hospital");
Path result = base.resolve("ICU/patient001.txt");

System.out.println(result);

Output:

/hospital/ICU/patient001.txt

Meaning:

Start at hospital, then go into ICU, then patient file.

Absolute Input Rule
Path base = Path.of("/hospital");
Path other = Path.of("/archive/patient001.txt");

System.out.println(base.resolve(other));

Output:

// /archive/patient001.txt

Because other is absolute.

Java says:

If the second path is already a full address, use it as-is.

Relativizing Paths

relativize() builds a relative path between two paths.

Your notes say both paths must be absolute or both must be relative, and on Windows they must have the same drive letter.

Hospital Example
Path icu = Path.of("/hospital/ICU");
Path er = Path.of("/hospital/ER/triage.txt");

Path relative = icu.relativize(er);

System.out.println(relative);

Conceptual result: ../ER/triage.txt

Meaning:

From ICU, go up to hospital, then go into ER, then find triage.txt.

Normalizing Paths

normalize() removes redundant . and ...

Your notes say it does not access the file system and is useful for comparing equivalent paths.

Example:

Path path = Path.of("/hospital/ER/../ICU/./patient001.txt");

System.out.println(path.normalize());

Output:

/hospital/ICU/patient001.txt

River meaning:

Remove unnecessary turns in the route.

Hospital meaning:

Clean the address by removing “go up,” “stay here,” and repeated movement.

toRealPath()

Your notes say toRealPath():

resolves symbolic links
converts to absolute path
requires path to exist
throws exception if path is invalid

Example:

Path real = Path.of("hospital-records/ICU/patient001.txt").toRealPath();

Meaning:

Give me the real, absolute, confirmed location of this patient file.

Difference:

normalize() = cleans the text path only
toRealPath() = checks the real file system
Copying Files and Directories

Your notes say:

Files.copy(Path, Path)
Shallow copy for directories
Deep copy requires recursion
REPLACE_EXISTING required to overwrite

Copy File Example
Path source = Path.of("hospital-records/ICU/patient001.txt");
Path target = Path.of("hospital-records/archive/patient001.txt");

Files.copy(source, target);

Meaning:

Copy patient001.txt into the archive.

Replace Existing
Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

Meaning:

If archive already has patient001.txt, replace it.

Directory Copy Warning

Copying a directory is shallow.

That means:

Java copies the folder itself, not everything inside deeply.

Hospital meaning:

Java creates a new department folder label, but does not automatically copy every patient file inside unless you recursively walk the tree.

Copying into a Directory Exam Trap

Your notes say copying file to directory path throws exception, and the target must include the filename.

Wrong:

Files.copy(
    Path.of("hospital-records/ICU/patient001.txt"),
    Path.of("hospital-records/archive")
);

Correct:

Files.copy(
    Path.of("hospital-records/ICU/patient001.txt"),
    Path.of("hospital-records/archive/patient001.txt")
);

Why?

Because Java needs the exact target file name.

Atomic Move

Your notes say StandardCopyOption.ATOMIC_MOVE means the operation is indivisible, and unsupported systems throw an exception.

Example:

Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);

Hospital meaning:

Move the patient file in one complete operation. It should not be half-moved.

This matters for important files where partial movement could be dangerous.

// Deleting Paths

Your notes say:

delete() throws exception if missing
deleteIfExists() is safer
directories must be empty

Example
Files.delete(Path.of("hospital-records/archive/old-report.txt"));

If the file does not exist, exception.

Safer:

Files.deleteIfExists(Path.of("hospital-records/archive/old-report.txt"));

If it exists, delete it.

If not, no problem.

Important:
A directory must be empty before Java can delete it.


// Comparing Paths
isSameFile()

Your notes say isSameFile() resolves symbolic links and compares actual file identity.

Example:

Files.isSameFile(path1, path2);

Hospital meaning:

// Do these two addresses point to the same actual patient file?

This matters with symbolic links.

mismatch()

Your notes say mismatch() compares file contents and returns:

-1 = identical
index = first difference

Example:

long result = Files.mismatch(file1, file2);

If result is:

-1

The files are identical.

If result is:

25

The first difference is at byte index 25.

Hospital meaning:

Compare two patient reports and find where they first differ.

Full Professional Hospital Scenario

The hospital IT department has this file system:

hospital-records/
 ├── ICU/
 │    ├── patient001.txt
 │    └── monitor.dat
 ├── ER/
 │    └── triage-report.txt
 └── archive/

The system needs to:

Check if ICU records exist.
List ICU files.
Check patient file size and last modified time.
Create archive folders.
Copy patient files to archive.
Rename temporary reports.
Delete old reports.
Compare files.
Complete Example
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class HospitalFileOperations {

    public static void main(String[] args) throws IOException {

        Path icuFolder = Path.of("hospital-records/ICU");
        Path patientFile = icuFolder.resolve("patient001.txt");
        Path archiveFolder = Path.of("hospital-records/archive");
        Path archiveFile = archiveFolder.resolve("patient001.txt");

        // 1. Check existence
        if (Files.exists(icuFolder)) {
            System.out.println("ICU folder exists.");
        }

        // 2. Inspect patient file
        if (Files.isRegularFile(patientFile)) {
            System.out.println("Patient file: " + patientFile.getFileName());
            System.out.println("Parent: " + patientFile.getParent());
            System.out.println("Absolute: " + patientFile.toAbsolutePath());
            System.out.println("Size: " + Files.size(patientFile));
            System.out.println("Last modified: " + Files.getLastModifiedTime(patientFile));
        }

        // 3. List ICU folder contents
        if (Files.isDirectory(icuFolder)) {
            try (Stream<Path> stream = Files.list(icuFolder)) {
                stream.forEach(p -> System.out.println("ICU item: " + p.getFileName()));
            }
        }

        // 4. Create archive folder if needed
        Files.createDirectories(archiveFolder);

        // 5. Copy patient file to archive
        if (Files.exists(patientFile)) {
            Files.copy(patientFile, archiveFile, StandardCopyOption.REPLACE_EXISTING);
        }

        // 6. Rename/move file
        Path tempReport = Path.of("hospital-records/ICU/temp-report.txt");
        Path finalReport = Path.of("hospital-records/ICU/final-report.txt");

        if (Files.exists(tempReport)) {
            Files.move(tempReport, finalReport, StandardCopyOption.REPLACE_EXISTING);
        }

        // 7. Compare copied file with original
        if (Files.exists(patientFile) && Files.exists(archiveFile)) {
            long mismatch = Files.mismatch(patientFile, archiveFile);

            if (mismatch == -1) {
                System.out.println("Original and archive copy are identical.");
            } else {
                System.out.println("Files differ at byte index: " + mismatch);
            }
        }

        // 8. Delete old file safely
        Path oldReport = Path.of("hospital-records/archive/old-report.txt");
        Files.deleteIfExists(oldReport);
    }
}
The Clean Mental Model
Lecture 1:
Path/File = address

Lecture 2:
Files/File methods = actions on the address
River version
Path = coordinate on river map
Files.exists() = check if water point exists
Files.list() = inspect branch contents
Files.copy() = copy water/data
Files.move() = move or rename water point
Files.delete() = remove water point
Hospital version
Path = hospital file address
Files.exists() = check if record exists
Files.list() = list department folder
Files.copy() = copy record to archive
Files.move() = rename or relocate record
Files.delete() = remove old record
OCP Exam Memory Table
Topic	Must Remember
File	Legacy API
Path	Modern location representation
Files	Static helper class for Path
Files.exists(path)	Does not throw IOException
Many Files methods	Throw checked IOException
Files.list(path)	Returns Stream<Path>
NIO.2 streams	Must be closed
Terminal operation	Does not auto-close stream
Path	Immutable
resolve()	Combines paths, but absolute input wins
relativize()	Both paths must be same type: absolute/relative
normalize()	Cleans . and .., no file-system access
toRealPath()	Requires real existing path
createDirectory()	Parent must exist
createDirectories()	Creates missing parents
copy() directory	Shallow copy
Copy file to directory	Target must include filename
move()	Used for move or rename
delete()	Throws if missing
deleteIfExists()	Safer if missing
Directory delete	Directory must be empty
isSameFile()	Compares actual file identity
mismatch()	Compares file contents
Final Memory Sentence

Lecture 1 teaches how Java points to a file location using File or Path.
Lecture 2 teaches how Java operates on that location using old File methods or modern Files methods.