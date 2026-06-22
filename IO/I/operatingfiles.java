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
Path icu = p.resolve("ICU");// hospital-records/ICU
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

// Java says:
If the second path is already a full address, use it as-is.

// ============Relativizing Paths===================

relativize() builds a relative path between two paths.

// Your notes say both paths must be absolute or both must be relative, and on Windows they must have the same drive letter.

Hospital Example
Path icu = Path.of("/hospital/ICU");
Path er = Path.of("/hospital/ER/triage.txt");

Path relative = icu.relativize(er);

System.out.println(relative);

Conceptual result: ../ER/triage.txt

Meaning:
From ICU, go up to hospital, then go into ER, then find triage.txt.

// my-examples
Path from = Path.of("hospital/doctors");
Path to = Path.of("hospital/patients/ali.txt");
Path result = from.relativize(to);
System.out.println(result);// ../patients/ali.txt


Path p1 = Path.of("hospital/doctors");
Path p2 = Path.of("hospital/patients");
System.out.println(p1.relativize(p2));// ../patients
System.out.println(p2.relativize(p1));// ../doctors

Path p1 = Path.of("hospital/patients");
Path p2 = Path.of("hospital/patients");
Path result = p1.relativize(p2);
System.out.println(result);// prints empty string

Path from = Path.of("hospital/patients/ali.txt");
Path to = Path.of("hospital/patients/sara.txt");
System.out.println(from.relativize(to));// ../sara.txt

// absolute with absolute = okay
// relative with relative = okay
// absolute with relative = exception
// relative with absolute = exception


Path from = Path.of("/hospital/doctors");
Path to = Path.of("hospital/patients");
System.out.println(from.relativize(to));

// Result: IllegalArgumentException



// =================== Normalizing Paths ====================

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


// =================== toRealPath() ====================

Your notes say toRealPath():

// resolves symbolic links
// converts to absolute path
// requires path to exist
// throws exception if path is invalid

Example:

Path real = Path.of("hospital-records/ICU/patient001.txt").toRealPath();
// Meaning:
Give me the real, absolute, confirmed location of this patient file.

Difference:
normalize() = cleans the text path only
toRealPath() = checks the real file system

Path path = Path.of("hospital/../hospital/patients/ali.txt");
Path real = path.toRealPath();
System.out.println(real); //if file exists, prints the absolute path; if not, throws exception
// /Users/mahamud/Desktop/hospital/patients/ali.txt
// NoSuchFileException

toRealPath() versus toAbsolutePath()
// toAbsolutePath(): Does not require the file to exist.

// example:
Path path = Path.of("fakeFile.txt");
System.out.println(path.toAbsolutePath());
Possible output: /Users/mahamud/Desktop/fakeFile.txt
// Even if the file does not exist, this works.


toRealPath()

Requires the file to exist.
Path path = Path.of("fakeFile.txt");
System.out.println(path.toRealPath());
// Possible result:NoSuchFileException

toRealPath() follows symbolic links by default
// A symbolic link is like a shortcut.

Example:
hospital/current-report.txt  →  hospital/reports/2026/report.txt

// If you call:
Path path = Path.of("hospital/current-report.txt");
System.out.println(path.toRealPath());
// Java follows the symbolic link and returns the real target path:
/Users/mahamud/Desktop/hospital/reports/2026/report.txt

// So by default:
toRealPath() follows links

// Not following symbolic links
Path path = Path.of("hospital/current-report.txt");
        Path real = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
        System.out.println(real);// This returns the symbolic link path itself, not the target:
// /Users/mahamud/Desktop/hospital/current-report.txt

// It can throw exceptions
Because toRealPath() touches the actual file system, it can throw IOException.

// That is why this code needs throws IOException:
public static void main(String[] args) throws IOException {
    Path path = Path.of("patients/ali.txt");
    System.out.println(path.toRealPath());
}

Common exceptions include:
// NoSuchFileException
// AccessDeniedException
// IOException





// ================ Copying Files and Directories ==================

Your notes say:
Files.copy(Path, Path)
// Shallow copy for directories
// Deep copy requires recursion
// REPLACE_EXISTING required to overwrite

Copy File Example
Path source = Path.of("hospital-records/ICU/patient001.txt");
Path target = Path.of("hospital-records/archive/patient001.txt");

Files.copy(source, target);

Meaning:
Copy patient001.txt into the archive.
Important rule: target must not already exist
This throws an exception if archive/patient001.txt already exists:
Files.copy(source, target);
// Possible exception:FileAlreadyExistsException

to fix Replace Existing
Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
// Meaning:
If archive already has patient001.txt, replace it.

public static void main(String[] args) throws IOException {
        Path source = Path.of("hospital/reports/icu.txt");
        Path target = Path.of("backup/icu.txt");

        Files.copy(
                source,
                target,
                StandardCopyOption.REPLACE_EXISTING
        );
    }

// Directory Copy Warning
Copying a directory is shallow.

// That means:
Java copies the folder itself, not everything inside deeply.
ou can copy a directory itself:

Path source = Path.of("hospital/reports");
Path target = Path.of("backup/reports");

Files.copy(source, target);
// But very important:
Files.copy() does not automatically copy everything inside the directory.

If hospital/reports contains:
// hospital/reports
//  ├── icu.txt
//  ├── er.txt
//  └── surgery.txt

This:

Files.copy(
        Path.of("hospital/reports"),
        Path.of("backup/reports")
);

creates the directory:
// backup/reports
But it does not copy:
// icu.txt
// er.txt
// surgery.txt 
// Hospital meaning:
Java creates a new department folder label, but does not automatically copy every patient file inside unless you recursively walk the tree.

// Copying into a Directory Exam Trap
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

// ===========Files.Move() for Moving and Renaming===========


public static void main(String[] args) throws IOException {
        Path source = Path.of("hospital/reports/icu.txt");
        Path target = Path.of("archive/icu.txt");

        Files.move(source, target);//archive/icu.txt
    }
// Files.copy() = copy and paste
// Files.move() = cut and paste

// Files.move() is also used to rename.
Path source = Path.of("hospital/reports/old-name.txt");
Path target = Path.of("hospital/reports/new-name.txt");
Files.move(source, target);
It renames:
// old-name.txt to :new-name.txt

// Common mistake:
Path source = Path.of("hospital/reports/icu.txt");
Path target = Path.of("archive");
Files.move(source, target);


// Correct way:
Path source = Path.of("hospital/reports/icu.txt");
Path targetDirectory = Path.of("archive");
Files.move(
        source,
        targetDirectory.resolve(source.getFileName())
);
// Result:
archive/icu.txt

// Target must not already exist

This fails if the target exists:
Files.move(source, target);

Possible exception: FileAlreadyExistsException

// To replace existing target:
import java.nio.file.StandardCopyOption;

Files.move(
        source,
        target,
        StandardCopyOption.REPLACE_EXISTING
);


// Moving a directory
Path source = Path.of("hospital/reports");
Path target = Path.of("archive/reports");

Files.move(source, target);
// This moves the directory itself.

// If it works, everything inside moves with it:
hospital/reports
 ├── icu.txt
 └── er.txt

// becomes:
archive/reports
 ├── icu.txt
 └── er.txt

So for moving directories, unlike copy(), Java can move the directory with its contents.

// Important directory rule
Moving a directory usually works easily when it is on the same file system.

// Example:
same hard drive
same partition
same volume

But moving a directory across different file systems can fail.
// Example:
from Mac internal drive → external USB drive
from C: drive → D: drive

// Why?
Because Java may not be able to perform a simple directory move across file systems.

// In that case, you may need to:
1. Copy the directory tree
2. Delete the original directory tree

// ATOMIC_MOVE
Files.move(
        source,
        target,
        StandardCopyOption.ATOMIC_MOVE
);

Meaning:
Move the file as one single operation.

This is useful when you do not want a half-finished move.
// Example in hospital system:
A patient report should either be fully moved or not moved at all.
There should not be a partial file.

Code:

Files.move(
        Path.of("temp/patient-report.txt"),
        Path.of("final/patient-report.txt"),
        StandardCopyOption.ATOMIC_MOVE
);

Important:
If atomic move is not supported, Java throws:
AtomicMoveNotSupportedException

// Missing parent directory
This fails if the target parent folder does not exist:
Files.move(
        Path.of("icu.txt"),
        Path.of("archive/reports/icu.txt")
);

If this folder does not exist:
archive/reports
// Java may throw:NoSuchFileException

Fix:
Path source = Path.of("icu.txt");
Path target = Path.of("archive/reports/icu.txt");

Files.createDirectories(target.getParent());
Files.move(source, target);


// Moving symbolic links
If the source is a symbolic link:
// latest-report.txt -> reports/icu.txt

Then:

Files.move(
        Path.of("latest-report.txt"),
        Path.of("archive/latest-report.txt")
);

Usually moves the symbolic link itself, not the file it points to.
// So the link moves from:
latest-report.txt
// to:
archive/latest-report.txt

The target file remains where it was.

// ============= Deleting Paths ================

Your notes say:
delete() throws exception if missing
deleteIfExists() is safer
directories must be empty

Example
Files.delete(Path.of("hospital-records/archive/old-report.txt"));
If the file does not exist, exception.//NoSuchFileException

Safer:
Files.deleteIfExists(Path.of("hospital-records/archive/old-report.txt"));

If it exists, delete it.
If not, no problem.

Important:
A directory must be empty before Java can delete it.

ath path = Path.of("hospital/reports/icu.txt");

boolean deleted = Files.deleteIfExists(path);

System.out.println(deleted);

// Possible results:
true: means the file existed and was deleted.
false: means the file did not exist.
// No NoSuchFileException if it is missing.

// Deleting an empty directory
Path dir = Path.of("hospital/temp");

Files.delete(dir);
// This works only if:
hospital/temp is empty.

7. Deleting a non-empty directory

// This fails:
Path dir = Path.of("hospital/reports");

Files.delete(dir); 

If the folder contains files:
hospital/reports
 ├── icu.txt
 └── er.txt

Java throws: DirectoryNotEmptyException

Important rule:

Files.delete() does not delete a directory tree automatically.

// Deleting a full directory tree
// Use Files.walk() and delete children before parents.

Example structure:

hospital-temp
 ├── reports
 │    ├── icu.txt
 │    └── er.txt
 └── logs
      └── app.log

Code:

import java.nio.file.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Stream;

public class DeleteDirectoryTree {
    public static void main(String[] args) throws IOException {

        Path root = Path.of("hospital-temp");

        try (Stream<Path> paths = Files.walk(root)) {

            paths.sorted(Comparator.reverseOrder())
                 .forEach(path -> {
                     try {
                         Files.delete(path);
                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     }
                 });
        }
    }
}
10. Why reverseOrder() is needed

Files.walk(root) gives paths like:

hospital-temp
hospital-temp/reports
hospital-temp/reports/icu.txt
hospital-temp/reports/er.txt
hospital-temp/logs
hospital-temp/logs/app.log

If Java tries to delete hospital-temp first, it fails because the directory is not empty.

So we reverse the order:

hospital-temp/logs/app.log
hospital-temp/logs
hospital-temp/reports/er.txt
hospital-temp/reports/icu.txt
hospital-temp/reports
hospital-temp

Now Java deletes:

files first
subdirectories next
root directory last

That works.




// 11. Hospital scenario

Imagine a hospital system creates temporary folders:

hospital/temp/session-123
 ├── upload.tmp
 ├── scan.tmp
 └── notes.tmp

After the patient upload is processed, the system deletes the temporary folder.

import java.nio.file.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Stream;

public class HospitalTempCleanup {
    public static void main(String[] args) throws IOException {

        Path sessionFolder = Path.of("hospital/temp/session-123");

        try (Stream<Path> paths = Files.walk(sessionFolder)) {

            paths.sorted(Comparator.reverseOrder())
                 .forEach(path -> {
                     try {
                         Files.deleteIfExists(path);
                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     }
                 });
        }
    }
}

Meaning:

// Delete all temporary files first.
// Delete subfolders.
// Delete session-123 folder last.

Common exceptions:

// NoSuchFileException
// DirectoryNotEmptyException
// AccessDeniedException
// IOException

import java.nio.file.*;
import java.io.IOException;

public class SafeDelete {
    public static void main(String[] args) {

        Path path = Path.of("hospital/reports/old.txt");

        try {
            Files.delete(path);

            System.out.println("Deleted successfully.");

        } catch (NoSuchFileException e) {
            System.out.println("File does not exist: " + e.getFile());

        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty: " + e.getFile());

        } catch (AccessDeniedException e) {
            System.out.println("Permission denied: " + e.getFile());

        } catch (IOException e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }
}


// 13. Deleting symbolic links
// If the path is a symbolic link:
latest-report.txt -> reports/icu.txt
// Then:
Files.delete(Path.of("latest-report.txt"));
deletes the symbolic link itself, not the target file.

So:
latest-report.txt is deleted
reports/icu.txt remains

// Important exam note:
Deleting a symbolic link removes the link, not the file it points to.

// Comparing Paths
isSameFile()
Your notes say isSameFile() resolves symbolic links and compares actual file identity.

Example:

Files.isSameFile(path1, path2);
Returns: boolean

Possible results:true or false

Path p1 = Path.of("hospital/reports/icu.txt");
Path p2 = Path.of("hospital/reports/../reports/icu.txt");

System.out.println(Files.isSameFile(p1, p2) );//true
// Because both paths refer to the same physical file


Path.equals()
// Compares path objects.
Path p1 = Path.of("hospital/reports/icu.txt");
Path p2 = Path.of("hospital/reports/../reports/icu.txt");

System.out.println(p1.equals(p2));// false
// Because the path strings are different. Java compares path representations.

File Must Usually Exist
Unlike: Path.equals()
isSameFile() checks the real file system.

// Example:
Path p1 = Path.of("missing.txt");
Path p2 = Path.of("missing.txt");

Files.isSameFile(p1, p2);
If the file doesnt exist: NoSuchFileException may occur.
Because Java must verify the real file.

boolean Files.isSameFile(Path p1,Path p2) hrows IOException //Method signature:

Same Path Object

Example:

Path p = Path.of("hospital/reports/icu.txt");

System.out.println(Files.isSameFile(p, p));// true
// Because it is the same Path object, so it must be the same file.

Symbolic Links

This is where isSameFile() becomes very useful.

// Suppose:
latest-report.txt
      ↓
icu-report.txt

Symbolic link:latest-report.txt → icu-report.txt

Code:
Path link = Path.of("latest-report.txt");
Path target = Path.of("icu-report.txt");

System.out.println(  Files.isSameFile(link, target));// true

Hospital meaning:
// Do these two addresses point to the same actual patient file?

This matters with symbolic links.

// Comparing Relative and Absolute Paths
Example:
Path p1 =
        Path.of("hospital/reports/icu.txt");

Path p2 =Path.of("/Users/admin/hospital/reports/icu.txt");

If both refer to the same actual file:
Files.isSameFile(p1, p2)// true

// ================= mismatch() =================
Your notes say mismatch() compares file contents and returns:

Return values
-1	The files are exactly the same
0	They are different at the first byte
positive number	The first byte position where they differ
// Example:
Path p1 = Path.of("report1.txt");
Path p2 = Path.of("report2.txt");

System.out.println(Files.mismatch(p1, p2));//-1
Meaning:no mismatch was found.

Different files

File 1:

ICU

File 2:

IRU

Positions:

I C U
0 1 2

I R U
0 1 2

Code:

System.out.println(Files.mismatch(p1, p2));

Output:

1

Because the first difference is at index 1.

C != R
5. Difference at the beginning

File 1:

ICU

File 2:

ECU

Output:

0

Because the first byte is already different.

I != E
6. One file is shorter

File 1:

ICU

File 2:

ICU Report

Output:

3

Why?

The first 3 bytes are the same:

I C U
0 1 2

Then file 1 ends, but file 2 continues.

So the mismatch is at position:

3
7. Very important: index starts at zero

Files.mismatch() uses zero-based position.

Position 0 = first byte
Position 1 = second byte
Position 2 = third byte

Example:

ABC
AXC

Mismatch:

B != X

Position:

1
8. It compares bytes, not characters

This is important.

Files.mismatch() compares the actual bytes stored in the file.

For simple English text, byte position usually looks like character position.

But for non-English characters, one character can use multiple bytes.

Example:

é

may take more than one byte in UTF-8.

So mismatch() returns a byte position, not a character number.

9. It does not compare file names
report1.txt
report2.txt

Different names do not matter.

If the contents are the same:

Files.mismatch(
        Path.of("report1.txt"),
        Path.of("report2.txt")
);

returns:

-1
10. It does not check whether paths are the same file

For same physical file:

Path p1 = Path.of("hospital/report.txt");
Path p2 = Path.of("./hospital/report.txt");

System.out.println(Files.mismatch(p1, p2));

Output:

-1

Because the content is the same.

But to check whether they are the same actual file, use:
Files.isSameFile(p1, p2)

11. mismatch() vs isSameFile()
// Method	Question it answers
Files.isSameFile(p1, p2)	Do these paths point to the same actual file?
Files.mismatch(p1, p2)	Are the file contents different, and where?

// Example:
fileA.txt = ICU
fileB.txt = ICU
Files.isSameFile(fileA, fileB);  // false
Files.mismatch(fileA, fileB);    // -1

Why?
They are two different files, but their contents are identical.

12. mismatch() vs equals()
Path p1 = Path.of("a.txt");
Path p2 = Path.of("./a.txt");

System.out.println(p1.equals(p2));

May output:false
// Because path strings are different.

But:

System.out.println(Files.mismatch(p1, p2));
May output:-1
Because the file content is the same.

13. It can throw IOException
Because Java reads real files.

public static void main(String[] args) throws IOException {
    Path p1 = Path.of("a.txt");
    Path p2 = Path.of("b.txt");

    long result = Files.mismatch(p1, p2);

    System.out.println(result);
}

// Possible exceptions:
NoSuchFileException
AccessDeniedException
IOException


14. If one file does not exist
Files.mismatch(
        Path.of("report.txt"),
        Path.of("missing.txt")
);

// Result:
NoSuchFileException

Because both files must exist.

15. Directories are not regular files

Files.mismatch() is for files.

// If you pass a directory, it may throw an exception such as:
IOException
or:
AccessDeniedException
depending on the operating system.

16. Hospital scenario

// Imagine a hospital generates two patient reports:
today-report.txt
backup-report.txt

You want to confirm the backup is identical.
Path original = Path.of("hospital/reports/today-report.txt");
Path backup = Path.of("hospital/backup/today-report.txt");

long result = Files.mismatch(original, backup);

if (result == -1) {
    System.out.println("Backup is identical.");
} else {
    System.out.println("Backup differs at byte position: " + result);
}
17. Full working example
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class MismatchExample {
    public static void main(String[] args) throws IOException {

        Path file1 = Path.of("icu-report.txt");
        Path file2 = Path.of("backup-icu-report.txt");

        long position = Files.mismatch(file1, file2);

        if (position == -1) {
            System.out.println("Files are identical.");
        } else {
            System.out.println("Files first differ at byte: " + position);
        }
    }
}
18. Creating files and testing mismatch
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class MismatchDemo {
    public static void main(String[] args) throws IOException {

        Path p1 = Path.of("a.txt");
        Path p2 = Path.of("b.txt");

        Files.writeString(p1, "ICU Report");
        Files.writeString(p2, "ICU Record");

        long result = Files.mismatch(p1, p2);

        System.out.println(result);
    }
}

Compare:

ICU Report
ICU Record

They are same until:

ICU Re

Then:p != c
Output:6

19. Step-by-step byte positions

// Text:
ICU Report
ICU Record

// Positions:
I C U   R e p o r t
0 1 2 3 4 5 6 7 8 9

I C U   R e c o r d
0 1 2 3 4 5 6 7 8 9

First difference:

p != c
Position:

6

So:

Files.mismatch(p1, p2)
returns:6
20. Important OCP exam notes
Rule 1
mismatch() returns:
// -1 if files are identical
Rule 2
It returns the first byte index where files differ.

Rule 3
If one file is shorter but all previous bytes match, the result is the size of the shorter file.

Example:

ABC
ABCDE

Result:
3
Rule 4

It compares contents, not names.
Rule 5

It can throw IOException.

21. Comparison table
Method	Purpose
Files.isSameFile()	Same physical file?
Files.mismatch()	Same content? If not, where different?
Path.equals()	Same path object representation?
Files.size()	File size in bytes
Files.readString()	Reads text content
Final summary
long result = Files.mismatch(path1, path2);

Meaning:

Compare two files and return the first byte position where they differ.

Remember:

-1 means identical
0 means different at the first byte
positive number means first difference position
compares bytes
requires real files
throws IOException
does not compare file names
does not prove same physical file