
Part 4: Hard Java 21 OCP-Style Questions
Question 1

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("hospital/patients/ahmed.txt");
        System.out.println(p.getFileName());
        System.out.println(p.getParent());
    }
}

What is the output?
A.
ahmed.txt
hospital/patients

B.
hospital/patients/ahmed.txt
hospital/patients

C.
ahmed.txt
patients

D. The code does not compile

Answer: A
Explanation

getFileName() returns the last name element.
getParent() returns everything before the last name element.


Question 2

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("ahmed.txt");
        System.out.println(p.getFileName());
        System.out.println(p.getParent());
    }
}

What is the output?

A.

ahmed.txt
.

B.

ahmed.txt
null

C.

null
null

D. The code does not compile

Answer: B
Explanation

A simple filename has no parent in the path text.

Path.of("ahmed.txt").getParent()

returns:

null

It does not automatically return:

.
Question 3

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("missing/fake/file.txt");
        System.out.println(p.getFileName());
    }
}

Assume the path does not exist.

What is printed?

A. file.txt
B. null
C. false
D. An exception is thrown

Answer: A
Explanation

Path.getFileName() does not check the file system.

It only reads the path structure.

Question 4

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("missing/fake/file.txt");
        System.out.println(p.getParent());
    }
}

Assume the path does not exist.

What is printed?

A. missing/fake
B. null
C. false
D. An exception is thrown

Answer: A
Explanation

getParent() is also based on path text only.

The path does not need to exist.

Question 5

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/fake/missing.txt");
        System.out.println(p.isAbsolute());
    }
}

Assume the file does not exist.

What is printed on a Unix-like system?

A. true
B. false
C. null
D. An exception is thrown

Answer: A
Explanation

Absolute path means:

starts from root, It does not mean the file exists.

Question 6

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("fake/missing.txt");
        System.out.println(p.toAbsolutePath());
    }
}

Assume the file does not exist.

What is true?

A. The code does not compile
B. It throws NoSuchFileException
C. It can print an absolute path
D. It prints false

Answer: C
Explanation

toAbsolutePath() does not require the file to exist.

It converts the path text into an absolute path based on the current working directory.

Question 7

Given:

import java.io.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("hospital/patients/ahmed.txt");
        System.out.println(f.getName());
        System.out.println(f.getParent());
    }
}

What is the output?

A.

ahmed.txt
hospital/patients

B.

hospital/patients/ahmed.txt
hospital

C.

patients
hospital

D. The code does not compile

Answer: A
Explanation

File.getName() returns the last name.

File.getParent() returns the parent path string.

Question 8

Given:

import java.io.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("ahmed.txt");
        System.out.println(f.getParent());
    }
}

What is printed?

A. .
B. null
C. Empty string
D. The code does not compile

Answer: B
Explanation

A simple relative filename has no parent component.

So getParent() returns:

null
Question 9

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("hospital/patients/ahmed.txt");
        System.out.println(Files.exists(p));
    }
}

Does this code compile?

A. Yes
B. No, because Files.exists() throws IOException
C. No, because Files.exists() requires File
D. No, because Path is abstract

Answer: A
Explanation

Files.exists() does not throw checked IOException.This compiles without throws.

Question 10

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("missing.txt");
        System.out.println(Files.exists(p));
        System.out.println(Files.isDirectory(p));
        System.out.println(Files.isRegularFile(p));
    }
}

Assume missing.txt does not exist.

What is printed?

A.

false
false
false

B.

false
true
false

C.

false
false
true

D. An exception is thrown

Answer: A
Explanation

These methods return false for a missing path:

Files.exists(p)
Files.isDirectory(p)
Files.isRegularFile(p)
// No exception.

Question 11

Given:

import java.io.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("missing.txt");
        System.out.println(f.exists());
        System.out.println(f.isDirectory());
        System.out.println(f.isFile());
    }
}

Assume missing.txt does not exist.

What is printed?

A.

false
false
false

B.

false
true
false

C.

false
false
true

D. An exception is thrown

Answer: A
Explanation Old File checking methods also return false for a missing path.

Question 12

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("records");
        System.out.println(Files.isRegularFile(p));
    }
}

Assume records exists as a directory.

What is printed?

A. true
B. false
C. null
D. An exception is thrown

Answer: B
Explanation

A directory is not a regular file.

Question 13

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("records");
        System.out.println(Files.isDirectory(p));
    }
}

Assume records exists as a regular file.

What is printed?

A. true
B. false
C. null
D. An exception is thrown

Answer: B
Explanation

A regular file is not a directory.

Question 14

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("link");
        System.out.println(Files.isDirectory(p));
    }
}

Assume:

link is a symbolic link to an existing directory

What is printed?

A. true
B. false
C. Always throws exception
D. Code does not compile

Answer: A
Explanation

By default, Files.isDirectory() follows symbolic links.

So a link to a directory is treated as a directory.

Question 15

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("link");
        System.out.println(Files.isDirectory(p, LinkOption.NOFOLLOW_LINKS));
    }
}

Assume:

link is a symbolic link to an existing directory

What is printed?

A. true
B. false
C. Always throws exception
D. Code does not compile

Answer: B
Explanation

With:

LinkOption.NOFOLLOW_LINKS

Java checks the link itself.

The link itself is not a directory.

So it returns:

false
Question 16

Given:

import java.io.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("missing.txt");
        System.out.println(f.length());
    }
}

Assume missing.txt does not exist.

What is printed?

A. 0
B. -1
C. false
D. NoSuchFileException

Answer: A
Explanation

File.length() returns 0L if the file does not exist.

This is a classic old API trap.

Question 17

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("missing.txt");
        System.out.println(Files.size(p));
    }
}

Assume missing.txt does not exist.

What is the result?

A. Prints 0
B. Prints -1
C. Throws NoSuchFileException
D. Prints false

Answer: C
Explanation

Files.size() interacts with the file system and throws an exception if the path does not exist.

Question 18

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("missing.txt");
        System.out.println(Files.size(p));
    }
}

What is the result?

A. Prints 0
B. Throws NoSuchFileException
C. Code does not compile
D. Prints false

Answer: C
Explanation

Files.size() throws checked IOException.

The code does not handle or declare it.

Correct: public static void main(String[] args) throws IOException

or:

try {
    System.out.println(Files.size(p));
} catch (IOException e) {
    e.printStackTrace();
}
Question 19

Given:

import java.io.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("missing.txt");
        System.out.println(f.lastModified());
    }
}

Assume missing.txt does not exist.

What is printed?

A. 0
B. -1
C. false
D. NoSuchFileException

Answer: A
Explanation

File.lastModified() returns 0L if the file does not exist or if an I/O error occurs.

Question 20

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("missing.txt");
        System.out.println(Files.getLastModifiedTime(p));
    }
}

Assume missing.txt does not exist.

What is the result?

A. Prints 0
B. Prints null
C. Throws NoSuchFileException
D. Prints false

Answer: C
Explanation Files.getLastModifiedTime() throws if the path does not exist.

Question 21

Given:

import java.io.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("hospital/patients");
        File[] list = f.listFiles();
        System.out.println(list.length);
    }
}

Assume:hospital/patients does not exist

What is the result?

A. Prints 0
B. Prints -1
C. Throws NullPointerException
D. Code does not compile

Answer: C
Explanation File.listFiles() returns null if the path does not exist or is not a directory.
Then this line fails: list.length because list is null.

Question 22

Given:

import java.io.*;
public class Test {
    public static void main(String[] args) {
        File f = new File("hospital/patient.txt");
        File[] list = f.listFiles();

        System.out.println(list);
    }
}

Assume:
hospital/patient.txt exists as a regular file

What is printed?
A. An empty array
B. null
C. The filename
D. Code does not compile

Answer: B
Explanation listFiles() works only for directories.
If the File is a regular file, it returns: null


Question 23

Given:

import java.nio.file.*;
import java.util.stream.*;

public class Test {
    public static void main(String[] args) throws Exception {
        try (Stream<Path> s = Files.list(Path.of("hospital/patient.txt"))) {
            s.forEach(System.out::println);
        }
    }
}

Assume:

hospital/patient.txt exists as a regular file

What is the result?

A. Prints nothing
B. Prints hospital/patient.txt
C. Throws NotDirectoryException
D. Code does not compile

Answer: C
Explanation
Files.list() expects a directory.

If the path is a regular file, it throws:NotDirectoryException



Question 24

import java.nio.file.*;
import java.util.stream.*;

public class Test {
    public static void main(String[] args) throws Exception {
        try (Stream<Path> s = Files.list(Path.of("hospital"))) {
            s.forEach(System.out::println);
        }
    }
}

Assume:

hospital exists as a directory
hospital/a.txt exists
hospital/b.txt exists
hospital/records/c.txt exists

Which paths are listed?

A. a.txt, b.txt, and records/c.txt
B. Only direct children of hospital
C. All files recursively
D. Nothing because Files.list() does not work on directories

Answer: B
Explanation Files.list() is shallow.

It lists direct children only.

It does not recursively enter subdirectories.

Question 25

Given:

import java.io.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("hospital/reports");
        System.out.println(f.mkdir());
    }
}

Assume:

hospital does not exist

What is printed?

A. true
B. false
C. NoSuchFileException
D. Code does not compile

Answer: B
Explanation

mkdir() creates only one directory. It does not create missing parents.

Since hospital does not exist, creating hospital/reports fails and returns: false



Question 26

Given:

import java.io.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("hospital/reports");
        System.out.println(f.mkdirs());
    }
}

Assume:hospital does not exist

What is printed?

A. true
B. false
C. NoSuchFileException
D. Code does not compile

Answer: A
Explanation

mkdirs() creates the whole directory tree.

It creates:

hospital
hospital/reports
Question 27

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.createDirectory(Path.of("hospital/reports"));
    }
}

Assume:

hospital does not exist

What is the result?

A. Creates both hospital and reports
B. Creates only reports
C. Throws NoSuchFileException
D. Code does not compile

Answer: C
Explanation

createDirectory() creates only one directory.The parent must already exist.

Use: Files.createDirectories(Path.of("hospital/reports"));to create missing parents.

Question 28

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.createDirectories(Path.of("hospital/reports/2026"));
    }
}

Assume:

hospital does not exist

What happens?

A. Creates the whole directory tree
B. Throws NoSuchFileException
C. Returns false
D. Code does not compile

Answer: A
Explanation

createDirectories() creates missing parent directories.

It creates:

hospital
hospital/reports
hospital/reports/2026
Question 29

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.createDirectory(Path.of("hospital"));
    }
}

Assume:

hospital already exists as a directory

What happens?

A. Nothing; it succeeds
B. Throws FileAlreadyExistsException
C. Returns false
D. Deletes and recreates the directory

Answer: B
Explanation createDirectory() fails if the directory already exists.

Question 30

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.createDirectories(Path.of("hospital"));
        System.out.println("Done");
    }
}

Assume:

hospital already exists as a directory

What happens?

A. Throws FileAlreadyExistsException
B. Prints Done
C. Returns false
D. Deletes and recreates the directory

Answer: B
Explanation createDirectories() does not fail just because the directory already exists.



Question 31

Given:

import java.io.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("hospital");
        System.out.println(f.mkdirs());
    }
}

Assume:

hospital already exists as a directory

What is printed?

A. true
B. false
C. FileAlreadyExistsException
D. Code does not compile

Answer: B
Explanation mkdirs() returns false if the directory already exists.

This is different from: Files.createDirectories(...)
which succeeds when the directory already exists.

Question 32

Given:

import java.io.*;

public class Test {
    public static void main(String[] args) {
        File oldName = new File("a.txt");
        File newName = new File("b.txt");

        System.out.println(oldName.renameTo(newName));
    }
}

Does this code require throws IOException?

A. Yes
B. No
C. Only if a.txt does not exist
D. Only if b.txt exists

Answer: B
Explanation

File.renameTo() does not throw checked IOException. It returns boolean.

Question 33

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Files.move(Path.of("a.txt"), Path.of("b.txt"));
    }
}

What is the result?

A. Compiles and moves the file
B. Does not compile
C. Always prints true
D. Always prints false

Answer: B
Explanation Files.move() throws checked IOException.
The code needs: public static void main(String[] args) throws IOException or try/catch.

Question 34
Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.move(Path.of("a.txt"), Path.of("backup"));
    }
}

Assume:

a.txt exists
backup exists as a directory

What is the result?

A. a.txt is moved into backup/a.txt
B. backup is replaced by a.txt
C. An exception is thrown
D. Code does not compile

Answer: C
Explanation

Files.move() does not automatically add the filename.

Correct: Files.move(Path.of("a.txt"), Path.of("backup/a.txt"));


Question 35

Given:

import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.move(Path.of("a.txt"), Path.of("b.txt"), REPLACE_EXISTING);
    }
}

Assume:

a.txt exists
b.txt exists

What happens?

A. b.txt is replaced and a.txt is gone
B. Both files remain
C. Nothing happens
D. Code does not compile

Answer: A
Explanation Files.move() moves the source to the target.

With REPLACE_EXISTING, the target can be replaced.

After the move:a.txt no longer exists , b.txt contains what a.txt used to contain



Question 36

Given:

import java.io.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("a.txt");
        File g = new File("b.txt");

        boolean result = f.renameTo(g);
        System.out.println(result);
    }
}

Which statement is true?

A. If the rename fails, an IOException is thrown
B. If the rename fails, it returns false
C. The method always returns true
D. The code does not compile

Answer: B
Explanation

renameTo() returns boolean. It does not give detailed exception information.

Question 37

Given:

import java.io.*;
import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("hospital/patients/ahmed.txt");
        Path p = Path.of("hospital/patients/ahmed.txt");

        System.out.println(f.getName());
        System.out.println(p.getFileName());
    }
}

What is true?

A. Both print ahmed.txt
B. File.getName() prints the full path
C. Path.getFileName() checks if the file exists
D. The code does not compile

Answer: A
Explanation

Both return the final name element. Neither one requires the file to exist.

Question 38

import java.io.*;
import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("fake/missing.txt");
        Path p = Path.of("fake/missing.txt");

        System.out.println(f.getAbsolutePath());
        System.out.println(p.toAbsolutePath());
    }
}

Assume the file does not exist.

What is true?

A. Both can print absolute paths
B. Both throw NoSuchFileException
C. File prints null, Path throws exception
D. Code does not compile

Answer: A
Explanation Absolute path retrieval does not require the file to exist.

Question 39

Given:

import java.nio.file.*;
import java.io.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("file.txt");
        File f = p.toFile();

        System.out.println(f.getName());
    }
}

What is printed?

A. file.txt
B. Path
C. null
D. Code does not compile

Answer: A
Explanation

A Path can be converted to a File using:

p.toFile()

Then getName() returns the filename.

Question 40

Given:

import java.io.*;
import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        File f = new File("file.txt");
        Path p = f.toPath();

        System.out.println(p.getFileName());
    }
}

What is printed?

A. file.txt
B. null
C. false
D. Code does not compile

Answer: A
Explanation

A File can be converted to a Path using:

f.toPath()
Part 5: Master Trap Table
Operation	Old API	New API	Exam Trap
File name	File.getName()	Path.getFileName()	Does not require file to exist
Parent	File.getParent()	Path.getParent()	Simple filename parent is null
Absolute check	File.isAbsolute()	Path.isAbsolute()	Absolute does not mean exists
Absolute path	File.getAbsolutePath()	Path.toAbsolutePath()	Does not require file to exist
Exists	File.exists()	Files.exists(path)	Files.exists() does not throw checked IOException
Directory check	File.isDirectory()	Files.isDirectory(path)	Missing path returns false
File check	File.isFile()	Files.isRegularFile(path)	Method names are different
Last modified	File.lastModified()	Files.getLastModifiedTime(path)	File returns long; Files returns FileTime
Size	File.length()	Files.size(path)	File.length() can return 0; Files.size() throws
List contents	File.listFiles()	Files.list(path)	listFiles() can return null; Files.list() throws
List depth	File.listFiles()	Files.list(path)	Both are shallow
Single directory	File.mkdir()	Files.createDirectory(path)	Parent must exist
Directory tree	File.mkdirs()	Files.createDirectories(path)	Creates missing parents
Already exists	mkdirs()	createDirectories()	mkdirs() returns false; createDirectories() succeeds
Rename/move	File.renameTo(file)	Files.move(path, path)	renameTo() returns boolean; move() throws
Final Memory Summary
Path methods usually inspect the path text.
Files methods usually inspect the real file system.
File methods are older and often return false/null/0 instead of throwing.
Biggest exam traps
getParent() of "file.txt" is null.

toAbsolutePath() does not require the file to exist.

Files.exists(), isDirectory(), isRegularFile()
return false for missing paths.

File.length() and File.lastModified()
return 0 for missing paths.

Files.size() and Files.getLastModifiedTime()
throw IOException for missing paths.

File.listFiles()
can return null.

Files.list()
throws if the path is not a directory
and returns a Stream that should be closed.

mkdir() and createDirectory()
create one directory only.

mkdirs() and createDirectories()
create missing parent directories.
File.renameTo()
returns boolean.

Files.move()
throws IOException.

Files.move(file, directory)
does not automatically add the filename.






//===================================
   PART 2
//===================================


Java 21 OCP-Style Hard Questions
Topic: Files.copy(), move(), delete(), isSameFile(), mismatch()

These are exam-style tricky questions. Try answering first, then check the explanations.

Question 1

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path source = Path.of("data/patient.txt");
        Path target = Path.of("backup");

        Files.copy(source, target);
        System.out.println("Done");
    }
}

Assume:

// data/patient.txt exists as a regular file
// backup exists as a directory

What is the result?

A. The file is copied into backup/patient.txt and Done is printed
B. The file replaces the backup directory
C. An exception is thrown
D. The code does not compile

Answer: C
Explanation

This is a classic trap.

Files.copy(source, target);

does not automatically copy the file inside the directory.

The target must include the filename:

Files.copy(source, Path.of("backup/patient.txt"));

So this throws an exception because backup already exists as a directory.

Question 2

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path source = Path.of("hospital/reports");
        Path target = Path.of("backup/reports");

        Files.copy(source, target);
    }
}

Assume:

hospital/reports is a directory
hospital/reports/a.txt exists
hospital/reports/b.txt exists
backup exists
backup/reports does not exist

What is created?

A. backup/reports directory only
B. backup/reports/a.txt and backup/reports/b.txt
C. Nothing; directories cannot be copied
D. The code does not compile

Answer: A
Explanation

Files.copy() performs a shallow copy for directories.

It copies the directory itself, but not the contents.

So Java creates:

backup/reports/

But it does not copy:

a.txt
b.txt
Question 3

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("hospital/old");
        Files.deleteIfExists(p);
    }
}

What is the result?

A. It compiles and deletes the file if it exists
B. It compiles and prints false if the file does not exist
C. It does not compile
D. It compiles only if p is a directory

Answer: C
Explanation

Files.deleteIfExists() throws checked IOException.

The method must handle or declare it.

Correct version:

public static void main(String[] args) throws IOException {
    Path p = Path.of("hospital/old");
    Files.deleteIfExists(p);
}

OCP trick:

Even deleteIfExists() can throw IOException.

It is only “safe” for missing files, not for all I/O problems.

Question 4

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path p = Path.of("hospital/archive");
        System.out.println(Files.deleteIfExists(p));
    }
}

Assume:

hospital/archive exists as an empty directory

What is the output?

A. true
B. false
C. Success
D. An exception is thrown

Answer: A
Explanation

deleteIfExists() can delete:

regular file
symbolic link
empty directory

Since the directory exists and is empty, it is deleted and the method returns:

true
Question 5

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path p = Path.of("hospital/archive");
        System.out.println(Files.deleteIfExists(p));
    }
}

Assume:

hospital/archive exists as a directory
hospital/archive/patient.txt exists

What is the result?

A. true
B. false
C. DirectoryNotEmptyException
D. The directory and all contents are deleted

Answer: C
Explanation

deleteIfExists() does not recursively delete.

A directory must be empty.

So this throws:

DirectoryNotEmptyException

Memory trick:

deleteIfExists() is not deleteEverythingInside().
Question 6

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path source = Path.of("a.txt");
        Path target = Path.of("b.txt");

        Files.copy(source, target);
    }
}

Assume:

a.txt exists
b.txt already exists

What is the result?

A. b.txt is overwritten
B. The copy is ignored
C. An exception is thrown
D. The code does not compile

Answer: C
Explanation

By default, Files.copy() does not overwrite.

To overwrite:

Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
Question 7

Given:

import java.nio.file.*;
import java.io.IOException;
import static java.nio.file.StandardCopyOption.*;

public class Test {
    public static void main(String[] args) throws IOException {
        Files.copy(
            Path.of("a.txt"),
            Path.of("b.txt"),
            REPLACE_EXISTING
        );
    }
}

Assume:

a.txt exists
b.txt already exists as a regular file

What is the result?

A. b.txt is overwritten
B. An exception is thrown because copy never overwrites
C. a.txt is deleted
D. The code does not compile

Answer: A
Explanation

REPLACE_EXISTING allows the target to be replaced.

Important:

copy() copies.
It does not remove the source.

So a.txt still exists.

Question 8

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Files.move(
            Path.of("patients/ahmed.txt"),
            Path.of("patients/ahmed-old.txt")
        );
    }
}

What does this code do?

A. Copies the file
B. Deletes the file
C. Renames the file
D. Creates a symbolic link

Answer: C
Explanation

When source and target are in the same directory but have different names, Files.move() acts like a rename.

patients/ahmed.txt

becomes:patients/ahmed-old.txt

// What happens?

Java renames/moves the file:

Before	                                      After
patients/ahmed.txt exists	                  patients/ahmed.txt no longer exists
patients/ahmed-old.txt does not exist	      patients/ahmed-old.txt now exists
Question 9

// Exam trap
If patients/ahmed-old.txt already exists, this code throws:
FileAlreadyExistsException

// Unless you add:StandardCopyOption.REPLACE_EXISTING

// Example:
Files.move(
    Path.of("patients/ahmed.txt"),
    Path.of("patients/ahmed-old.txt"),
    StandardCopyOption.REPLACE_EXISTING
);

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Files.move(
            Path.of("patients/ahmed.txt"),
            Path.of("archive")
        );
    }
}

Assume:

patients/ahmed.txt exists
archive exists as a directory

What is the result?

A. File is moved to archive/ahmed.txt
B. File replaces the archive directory
C. An exception is thrown
D. Code does not compile

Answer: C
Explanation

Same trap as copy().

Java does not automatically add the filename.

Correct:

Files.move(
    Path.of("patients/ahmed.txt"),
    Path.of("archive/ahmed.txt")
);




// Question 10

import java.nio.file.*;
import java.io.IOException;
import static java.nio.file.StandardCopyOption.*;

public class Test {
    public static void main(String[] args) throws IOException {
        Files.move(
            Path.of("a.txt"),
            Path.of("b.txt"),
            ATOMIC_MOVE
        );
    }
}

Which statement is true?

A. The move is guaranteed to work on all file systems
B. The operation is all-or-nothing if supported
C. ATOMIC_MOVE copies but does not delete the source
D. ATOMIC_MOVE only works for directories




Answer: B
Explanation

Atomic means:

complete success or no partial change
But it is not guaranteed on all systems.

If unsupported, Java may throw:AtomicMoveNotSupportedException


Question 11
import java.nio.file.*;
import java.io.IOException;
import static java.nio.file.StandardCopyOption.*;

public class Test {
    public static void main(String[] args) throws IOException {
        Files.move(
            Path.of("a.txt"),
            Path.of("b.txt"),
            REPLACE_EXISTING
        );
    }
}

// Assume:
a.txt exists
b.txt exists

// What happens?
A. a.txt is copied to b.txt, and both files remain
B. b.txt is replaced, and a.txt no longer exists
C. The code does not compile
D. Nothing happens because move() cannot replace files

Answer: B
Explanation

move() with REPLACE_EXISTING replaces the target.

Because this is a move, the source is removed from the original location.
a.txt gone
b.txt now contains old a.txt content


Question 12


import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path p1 = Path.of("records/a.txt");
        Path p2 = Path.of("records/../records/a.txt");

        System.out.println(p1.equals(p2));
        System.out.println(Files.isSameFile(p1, p2));
    }
}

Assume both paths refer to the same existing file.

What is the possible output?

A.
true
true
B.
false
true
C.
true
false

D.
false
false
Answer: B
Explanation

Path.equals() compares path objects/text.

These are not textually equal:

records/a.txt
records/../records/a.txt

So:
p1.equals(p2) // false
But Files.isSameFile() checks the actual file.

So:

Files.isSameFile(p1, p2) // true


Question 13

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path p1 = Path.of("a.txt");
        Path p2 = Path.of("b.txt");

        System.out.println(Files.isSameFile(p1, p2));
    }
}

Assume:

a.txt and b.txt are different files
but they contain exactly the same text

What is printed?

A. true
B. false
C. -1
D. The code does not compile

Answer: B
Explanation

isSameFile() checks file identity, not content.

Different files with the same content are still different files.
For content comparison, use:
Files.mismatch(p1, p2)


Question 14

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path p1 = Path.of("a.txt");
        Path p2 = Path.of("b.txt");

        System.out.println(Files.mismatch(p1, p2));
    }
}

Assume:

a.txt contains: ABCD
b.txt contains: ABCD

What is printed?

A. true
B. false
C. -1
D. 0

Answer: C
Explanation

mismatch() returns:

-1 if the files are identical
index of first difference if different

Identical content means:
-1


Question 15
import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path p1 = Path.of("a.txt");
        Path p2 = Path.of("b.txt");

        System.out.println(Files.mismatch(p1, p2));
    }
}

Assume:

a.txt contains: ABCD
b.txt contains: ABXD

What is printed?

A. -1
B. 0
C. 2
D. 3

Answer: C
Explanation

Compare by byte index:

A B C D
0 1 2 3

A B X D
0 1 2 3

First difference:

C vs X

at index:

2
Question 16

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path p1 = Path.of("a.txt");
        Path p2 = Path.of("b.txt");

        System.out.println(Files.mismatch(p1, p2));
    }
}

Assume:

a.txt contains: ABC
b.txt contains: ABCD

What is printed?

A. -1
B. 0
C. 3
D. 4

Answer: C
Explanation

The files match until:

ABC

Then a.txt ends.

The first mismatch is at index:

3
Question 17

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path p1 = Path.of("a.txt");
        Path p2 = Path.of("b.txt");

        long x = Files.mismatch(p1, p2);
        long y = Files.mismatch(p2, p1);

        System.out.println(x == y);
    }
}

Assume both files exist.

What is printed?

A. Always true
B. Always false
C. true only if files are identical
D. The code does not compile

Answer: A
Explanation

mismatch() is symmetric.

Files.mismatch(p1, p2)

and:

Files.mismatch(p2, p1)

return the same value.

Question 18

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("missing.txt");

        System.out.println(Files.deleteIfExists(p));
    }
}

Assume:

missing.txt does not exist

What is printed?

A. true
B. false
C. NoSuchFileException
D. Nothing

Answer: B
Explanation

deleteIfExists() returns false when the path does not exist.

It does not throw just because the path is missing.

Question 19

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("missing.txt");

        Files.delete(p);
        System.out.println("Deleted");
    }
}

Assume:

missing.txt does not exist

What is the result?

A. Prints Deleted
B. Prints false
C. Throws NoSuchFileException
D. Code does not compile

Answer: C
Explanation

delete() is stricter than deleteIfExists().

Files.delete(p);

throws an exception if the file does not exist.

Question 20

Given:

import java.nio.file.*;
import java.io.IOException;
import static java.nio.file.StandardCopyOption.*;

public class Test {
    public static void main(String[] args) throws IOException {
        Files.copy(
            Path.of("source"),
            Path.of("target"),
            REPLACE_EXISTING
        );
    }
}

Assume:

source is a non-empty directory
target is an existing empty directory

What is true?

A. The contents of source are recursively copied into target
B. target is replaced by an empty copy of source
C. Only files inside source are copied
D. The code does not compile

Answer: B
Explanation

Directory copy is shallow.

With REPLACE_EXISTING, the existing target directory can be replaced.

But the contents are not copied.

So the result is an empty copied directory structure at target.

Question 21

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("link");

        Files.deleteIfExists(p);
    }
}

Assume:

link is a symbolic link to a real file target.txt

What is deleted?

A. The symbolic link
B. The real target file
C. Both the link and the real target file
D. Nothing because symbolic links cannot be deleted

Answer: A
Explanation

Deleting a symbolic link deletes the link itself, not the file it points to.

Memory trick:

delete(link) deletes the shortcut, not the original file.
Question 22

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p1 = Path.of("link");
        Path p2 = Path.of("target.txt");

        System.out.println(Files.isSameFile(p1, p2));
    }
}

Assume:

link is a symbolic link to target.txt
target.txt exists

What is printed?

A. true
B. false
C. -1
D. Nothing guaranteed because symbolic links are ignored

Answer: A
Explanation

isSameFile() resolves symbolic links.

So if link points to target.txt, then they refer to the same actual file.

Question 23

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p1 = Path.of("a.txt");
        Path p2 = Path.of("b.txt");

        if (Files.isSameFile(p1, p2))
            System.out.println(Files.mismatch(p1, p2));
    }
}

Assume:

p1 and p2 refer to the same actual file

What is printed?

A. true
B. false
C. -1
D. The index of the first difference

Answer: C
Explanation

If two paths refer to the same actual file, then the contents are identical to themselves.

So:
Files.mismatch(p1, p2)
returns:-1
Question 24

Given:

import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p1 = Path.of("a.txt");
        Path p2 = Path.of("b.txt");

        Files.move(p1, p2, ATOMIC_MOVE, REPLACE_EXISTING);
    }
}

Which is true?

A. This never compiles because ATOMIC_MOVE and REPLACE_EXISTING cannot appear together
B. The code compiles
C. REPLACE_EXISTING always overrides ATOMIC_MOVE
D. The operation becomes a copy instead of a move

Answer: B
Explanation

The code compiles because both are valid CopyOption values.

But behavior can depend on the file system.

Important exam idea:

Compilation and runtime behavior are separate.
Question 25

Given:

import java.nio.file.*;
import java.io.*;

public class Test {
    public static void main(String[] args) throws Exception {
        try (InputStream in = new FileInputStream("a.txt")) {
            Files.copy(in, Path.of("b.txt"));
        }
    }
}

Assume:

a.txt exists
b.txt already exists

What happens?

A. b.txt is overwritten
B. An exception is thrown
C. a.txt is deleted
D. The code does not compile

Answer: B
Explanation

The stream version also does not overwrite by default.

To overwrite:

Files.copy(in, Path.of("b.txt"), StandardCopyOption.REPLACE_EXISTING);
Question 26

Given:

import java.nio.file.*;
import java.io.*;

public class Test {
    public static void main(String[] args) throws Exception {
        try (OutputStream out = new FileOutputStream("b.txt")) {
            Files.copy(Path.of("a.txt"), out);
        }
    }
}

What does this do?

A. Copies a.txt into the output stream
B. Copies output stream into a.txt
C. Moves a.txt to b.txt
D. Deletes a.txt after copying

Answer: A
Explanation

This overload:

Files.copy(Path source, OutputStream out)

means:

file → stream

Since the stream is connected to b.txt, the contents of a.txt are written into b.txt.

Question 27

Given:

import java.nio.file.*;
import java.io.*;

public class Test {
    public static void main(String[] args) throws Exception {
        try (InputStream in = new FileInputStream("a.txt")) {
            Files.copy(in, Path.of("b.txt"));
        }
    }
}

Which direction is the copy?

A. a.txt to b.txt
B. b.txt to a.txt
C. Both files are exchanged
D. Depends on the file size

Answer: A
Explanation

The InputStream reads from a.txt.

The target path is b.txt.

So:

InputStream → Path
a.txt → b.txt
Question 28

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path p = Path.of("hospital/patients");

        Files.copy(p, Path.of("backup/patients"));
        Files.delete(p);
    }
}

Assume:

hospital/patients is a non-empty directory
backup/patients does not exist

What happens?

A. Directory is copied deeply, then original is deleted
B. Empty backup/patients is created, then Files.delete(p) throws exception
C. Nothing compiles
D. Files.delete(p) deletes the directory and all files inside

Answer: B
Explanation

First:

Files.copy(p, Path.of("backup/patients"));

creates a shallow directory copy.

Then:

Files.delete(p);

tries to delete the original non-empty directory.

That throws:

DirectoryNotEmptyException
Question 29

Given:

import java.nio.file.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Path p1 = Path.of("a.txt");
        Path p2 = Path.of("b.txt");

        System.out.println(Files.mismatch(p1, p2) == -1);
    }
}

Assume:

a.txt and b.txt are different files
but have identical content

What is printed?

A. true
B. false
C. -1
D. Exception always thrown

Answer: A
Explanation

mismatch() compares contents.

If contents are identical, it returns:

-1

So:

Files.mismatch(p1, p2) == -1

is:

true
Question 30

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("data");

        if (Files.deleteIfExists(p))
            System.out.println("Deleted");
        else
            System.out.println("Missing");
    }
}

Assume:

data exists as a non-empty directory

What is the result?

A. Prints Deleted
B. Prints Missing
C. Throws DirectoryNotEmptyException
D. Deletes all files inside and prints Deleted

Answer: C
Explanation

This is tricky because people think:

deleteIfExists()

means:

never throws

But that is false.

It only avoids exception when the file is missing.

A non-empty directory still throws:

DirectoryNotEmptyException
Final Exam Memory Table
Method	Main exam trick
Files.copy(file, dir)	Does not auto-add filename
Files.copy(directory, target)	Shallow copy only
Files.copy()	Does not overwrite unless REPLACE_EXISTING
Files.move()	Can rename or move
Files.move(file, dir)	Does not auto-add filename
Files.move()	Same overwrite rule as copy
ATOMIC_MOVE	All-or-nothing, but not always supported
Files.delete()	Throws if missing
Files.deleteIfExists()	Returns false if missing
deleteIfExists()	Can still throw for I/O problems
Delete directory	Must be empty
Delete symbolic link	Deletes the link, not target
isSameFile()	Compares actual file identity
isSameFile()	Resolves symbolic links
mismatch()	Compares contents
mismatch()	-1 means identical
mismatch()	Index means first different byte
Many methods	Need throws IOException or try/catch