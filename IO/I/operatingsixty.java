Hard Java 21 OCP-Style Questions
Topic: IOException, NIO.2 Options, Path Immutability, Path Components, resolve(), relativize(), normalize(), toRealPath()

Unless stated otherwise, assume a Unix-like file system.

Question 1 — Choose all that apply

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

Which statements are true?

A. The code does not compile because Files.exists() throws IOException
B. The code does not compile because Files.isDirectory() throws IOException
C. The code compiles
D. The output is three lines of false
E. Files.isDirectory() can throw unchecked SecurityException

Answer: C, D, E
Explanation

These methods do not throw checked IOException:

Files.exists(p)
Files.isDirectory(p)
Files.isRegularFile(p)

For a missing path, they normally return:

false
false
false

But they may throw unchecked SecurityException in restricted environments.

Question 2 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("missing.txt");
        System.out.println(Files.size(p));
    }
}

Which statements are true?

A. The code compiles and prints 0 if the file is missing
B. The code does not compile
C. Files.size() declares IOException
D. Adding throws Exception to main() would make the code compile
E. Files.size() behaves like File.length()

Answer: B, C, D
Explanation

Files.size(p) declares checked IOException.

So this does not compile unless you handle or declare the exception.

Correct:

public static void main(String[] args) throws Exception {
    Path p = Path.of("missing.txt");
    System.out.println(Files.size(p));
}

Files.size() is not like File.length().

File.length() may return 0 for missing files, but Files.size() can throw NoSuchFileException.

Question 3 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("missing.txt");

        System.out.println(Files.exists(p));
        System.out.println(Files.size(p));
    }
}

Assume missing.txt does not exist.

Which statements are true?

A. The first line prints false
B. The second line prints 0
C. The second line throws NoSuchFileException
D. The code does not compile
E. Files.exists() prevents Files.size() from throwing

Answer: A, C
Explanation

This line:

System.out.println(Files.exists(p));

prints:

false

But the next line still runs:

System.out.println(Files.size(p));

Since the file does not exist, Files.size() throws an exception.

Files.exists() does not protect later code unless you use it in an if.

Question 4 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("whale");
        p.resolve("krill");
        System.out.println(p);
    }
}

What is the output?

A. whale
B. whale/krill
C. The code does not compile
D. Path objects are immutable
E. resolve() modifies the existing Path

Answer: A, D
Explanation

Path objects are immutable.

This line creates a new Path, but the result is ignored:

p.resolve("krill");

So p is still:

whale

Correct version:

p = p.resolve("krill");
Question 5 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/land/hippo/harry.happy");

        System.out.println(p.getNameCount());
        System.out.println(p.getName(0));
        System.out.println(p.getName(1));
        System.out.println(p.getName(2));
    }
}

What is the output on a Unix-like system?

A.

3
land
hippo
harry.happy

B.

4
/
land
hippo

C. The root / is included in the name count
D. The root / is not included in the name count
E. Indexing is zero-based

Answer: A, D, E
Explanation

For:

/land/hippo/harry.happy

The root is:

/

But the names are:

land
hippo
harry.happy

So:

p.getNameCount()

returns:

3
Question 6 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/land/hippo/harry.happy");
        System.out.println(p.getName(3));
    }
}

What happens?

A. Prints harry.happy
B. Prints null
C. Throws IllegalArgumentException
D. Throws IndexOutOfBoundsException
E. The code does not compile

Answer: C
Explanation

Valid indexes are:

0, 1, 2

So:

p.getName(3)

throws:

IllegalArgumentException

This is an OCP trap because many students expect IndexOutOfBoundsException.

Question 7 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/land/hippo/harry.happy");

        System.out.println(p.subpath(0, 2));
        System.out.println(p.subpath(1, 3));
    }
}

What is the output?

A.

/land/hippo
/hippo/harry.happy

B.

land/hippo
hippo/harry.happy

C. subpath() includes the root
D. subpath() excludes the root
E. The end index is exclusive

Answer: B, D, E
Explanation

The name elements are:

0: land
1: hippo
2: harry.happy

So:

p.subpath(0, 2)

returns:

land/hippo

and:

p.subpath(1, 3)

returns:

hippo/harry.happy

The root / is not included.

Question 8 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/land/hippo/harry.happy");

        System.out.println(p.subpath(0, 4));
    }
}

What happens?

A. Prints /land/hippo/harry.happy
B. Prints land/hippo/harry.happy
C. Throws IllegalArgumentException
D. The code does not compile
E. The root counts as index 0

Answer: C
Explanation

The path has only 3 name elements:

land
hippo
harry.happy

Valid subpath end index can be at most:

3

So:

p.subpath(0, 4)

throws IllegalArgumentException.

Question 9 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("land/hippo/harry.happy");

        System.out.println(p.getRoot());
        System.out.println(p.getParent());
        System.out.println(p.getFileName());
    }
}

What is printed?

A.

null
land/hippo
harry.happy

B.

/
land/hippo
harry.happy

C. getRoot() returns null for relative paths
D. getParent() always returns an absolute path
E. getFileName() returns the final segment

Answer: A, C, E
Explanation

For a relative path:

land/hippo/harry.happy

There is no root.

So:

p.getRoot()

returns:

null

Parent:

land/hippo

File name:

harry.happy
Question 10 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("harry.happy");

        System.out.println(p.getRoot());
        System.out.println(p.getParent());
        System.out.println(p.getFileName());
    }
}

What is printed?

A.

null
null
harry.happy

B.

/
.
harry.happy

C. A simple relative filename has no parent
D. getParent() returns .
E. getRoot() returns null

Answer: A, C, E
Explanation

A simple filename has no parent component in the path text.

So:

Path.of("harry.happy").getParent()

returns:

null

It does not return ".".

Question 11 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p1 = Path.of("/zoo");
        Path p2 = Path.of("animals/tiger");

        System.out.println(p1.resolve(p2));
    }
}

What is printed?

A. /zoo/animals/tiger
B. animals/tiger
C. /animals/tiger
D. resolve() appends a relative input
E. resolve() accesses the file system

Answer: A, D
Explanation

When the input to resolve() is relative, it is appended:

Path.of("/zoo").resolve("animals/tiger")

Result:

/zoo/animals/tiger

No file system access happens.

Question 12 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p1 = Path.of("/zoo");
        Path p2 = Path.of("/animals/tiger");

        System.out.println(p1.resolve(p2));
    }
}

What is printed?

A. /zoo/animals/tiger
B. /animals/tiger
C. The code does not compile
D. If the argument to resolve() is absolute, it is returned as-is
E. resolve() cannot accept an absolute path

Answer: B, D
Explanation

This is a big trap.

base.resolve(other)

If other is absolute, Java returns other.

So:

Path.of("/zoo").resolve(Path.of("/animals/tiger"))

returns:

/animals/tiger

It does not combine two absolute paths.

Question 13 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/zoo");
        System.out.println(p.resolve(""));
    }
}

What is printed?

A. /zoo
B. /zoo/
C. Empty path
D. The code does not compile
E. Resolving an empty path returns the original path

Answer: A, E
Explanation

Resolving an empty path effectively returns the original path.

The printed form is commonly:

/zoo
Question 14 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p1 = Path.of("fish");
        Path p2 = Path.of("birds");

        System.out.println(p1.relativize(p2));
    }
}

What is printed?

A. ../birds
B. fish/birds
C. /birds
D. The code does not compile
E. Both paths are relative, so relativize() is allowed

Answer: A, E
Explanation

From:

fish

to:

birds

You go up from fish, then down to birds:

../birds

Both are relative, so it is allowed.

Question 15 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p1 = Path.of("/fish");
        Path p2 = Path.of("/fish/birds/parrot");

        System.out.println(p1.relativize(p2));
    }
}

What is printed?

A. birds/parrot
B. /birds/parrot
C. ../birds/parrot
D. The code does not compile
E. Both paths are absolute, so relativize() is allowed

Answer: A, E
Explanation

From:

/fish

to:

/fish/birds/parrot

you go down into:

birds/parrot

So the relative path is:

birds/parrot
Question 16 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p1 = Path.of("/fish/birds");
        Path p2 = Path.of("/fish");

        System.out.println(p1.relativize(p2));
    }
}

What is printed?

A. ..
B. ../..
C. birds
D. /fish
E. The code does not compile

Answer: A
Explanation

From:

/fish/birds

to:

/fish

you go up one level:

..
Question 17 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p1 = Path.of("/fish");
        Path p2 = Path.of("birds");

        System.out.println(p1.relativize(p2));
    }
}

What happens?

A. Prints birds
B. Prints /fish/birds
C. Throws IllegalArgumentException
D. The code does not compile
E. One path is absolute and the other is relative

Answer: C, E
Explanation

relativize() requires both paths to be the same type:

both absolute
or
both relative

Here:

Path.of("/fish")  // absolute
Path.of("birds")  // relative

So it throws IllegalArgumentException.

Question 18 — Choose all that apply

Assume Windows.

Given:

Path p1 = Path.of("C:\\fish");
Path p2 = Path.of("D:\\birds");
System.out.println(p1.relativize(p2));

Which statements are true?

A. It prints ..\D:\birds
B. It throws an exception
C. On Windows, absolute paths generally must have the same root or drive to relativize
D. It prints D:\birds
E. It compiles

Answer: B, C, E
Explanation

On Windows, paths on different drives usually cannot be relativized.

The code compiles, but at runtime relativize() throws an exception.

Question 19 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/zoo/./animals/../food");

        System.out.println(p.normalize());
    }
}

What is printed?

A. /zoo/food
B. /zoo/animals/food
C. /food
D. normalize() removes redundant . and ..
E. normalize() accesses the file system

Answer: A, D
Explanation

Start with:

/zoo/./animals/../food

Remove .:

/zoo/animals/../food

Then animals/.. cancels out:

/zoo/food

normalize() does not access the file system.

Question 20 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("a/b/../../c");

        System.out.println(p.normalize());
    }
}

What is printed?

A. c
B. /c
C. ../c
D. The code does not compile
E. normalize() can be used on paths that do not exist

Answer: A, E
Explanation

For:

a/b/../../c

b/.. cancels, then a/.. cancels.

Result:

c

No file system access is required.

Question 21 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("../../fish");

        System.out.println(p.normalize());
    }
}

What is printed?

A. fish
B. ../../fish
C. /fish
D. .. segments that cannot be resolved are preserved
E. The code does not compile

Answer: B, D
Explanation

For a relative path:

../../fish

There are no earlier name elements to cancel.

So the leading .. elements remain.

Result:

../../fish
Question 22 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p1 = Path.of("/zoo/./animals/../food");
        Path p2 = Path.of("/zoo/food");

        System.out.println(p1.equals(p2));
        System.out.println(p1.normalize().equals(p2));
    }
}

What is printed?

A.

true
true

B.

false
true

C.

false
false

D. Path.equals() normalizes automatically
E. normalize() can help compare path text

Answer: B, E
Explanation

Path.equals() does not automatically normalize.

So:

p1.equals(p2)

is:

false

But:

p1.normalize()

becomes:

/zoo/food

So:

p1.normalize().equals(p2)

is:

true
Question 23 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("missing/../file.txt");

        System.out.println(p.normalize());
        System.out.println(p.toRealPath());
    }
}

Assume file.txt does not exist.

Which statements are true?

A. normalize() can print file.txt
B. toRealPath() can throw NoSuchFileException
C. normalize() accesses the file system
D. toRealPath() requires the path to exist
E. The code does not compile

Answer: A, B, D
Explanation

normalize() works on path text only.

missing/../file.txt

normalizes to:

file.txt

But:

p.toRealPath()

requires the actual path to exist.

If it does not exist, it throws NoSuchFileException.

Question 24 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("hospital/records");

        System.out.println(p.toRealPath());
    }
}

Assume:

hospital/records exists as a directory

Which statements are true?

A. toRealPath() returns an absolute path
B. toRealPath() requires the path to exist
C. toRealPath() resolves symbolic links by default
D. toRealPath() never throws IOException
E. toRealPath() is the same as toAbsolutePath()

Answer: A, B, C
Explanation

toRealPath():

converts to absolute path
resolves symbolic links by default
requires the path to exist
throws IOException if invalid

toAbsolutePath() does not require existence.

Question 25 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("hospital/records");

        System.out.println(p.toAbsolutePath());
    }
}

Assume hospital/records does not exist.

Which statements are true?

A. The code compiles
B. It can print an absolute path
C. It throws NoSuchFileException
D. toAbsolutePath() requires the path to exist
E. toAbsolutePath() is a path-text operation, not a file existence check

Answer: A, B, E
Explanation

toAbsolutePath() does not require the path to exist.

It uses the current working directory to make an absolute path.

Question 26 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("link");
        System.out.println(p.toRealPath(LinkOption.NOFOLLOW_LINKS));
    }
}

Assume:

link exists as a symbolic link

Which statements are true?

A. NOFOLLOW_LINKS tells Java not to follow symbolic links
B. toRealPath() can accept LinkOption.NOFOLLOW_LINKS
C. The code does not compile because NOFOLLOW_LINKS is not valid here
D. The path must still exist
E. toRealPath() returns a relative path

Answer: A, B, D
Explanation

toRealPath() accepts:

LinkOption.NOFOLLOW_LINKS

The path must still exist.

The result is an absolute real path.

Question 27 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.copy(
            Path.of("a.txt"),
            Path.of("b.txt"),
            REPLACE_EXISTING
        );
    }
}

Which statements are true?

A. REPLACE_EXISTING is a varargs option
B. The method can accept zero or more copy options
C. REPLACE_EXISTING means overwrite target if allowed
D. REPLACE_EXISTING deletes the source file after copying
E. The code does not compile because options are not allowed in Files.copy()

Answer: A, B, C
Explanation

Many NIO.2 methods use optional varargs parameters:

Files.copy(source, target, options...)

REPLACE_EXISTING allows replacing the target.

It does not delete the source. Copying keeps the source.

Question 28 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.move(
            Path.of("a.txt"),
            Path.of("b.txt"),
            ATOMIC_MOVE
        );
    }
}

Which statements are true?

A. ATOMIC_MOVE means all-or-nothing if supported
B. ATOMIC_MOVE guarantees the operation works on every file system
C. The code compiles
D. If the file system does not support atomic move, an exception may be thrown
E. ATOMIC_MOVE is used with Files.exists()

Answer: A, C, D
Explanation

ATOMIC_MOVE is a StandardCopyOption.

It means the move should be performed as an atomic operation.

But not all file systems support it.

Question 29 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.newBufferedWriter(Path.of("notes.txt"), CREATE, WRITE);
    }
}

Which statements are true?

A. CREATE is a StandardOpenOption
B. WRITE is a StandardOpenOption
C. CREATE creates the file if it does not exist
D. WRITE means open for writing
E. CREATE is a StandardCopyOption

Answer: A, B, C, D
Explanation

These are StandardOpenOption values:

CREATE
READ
WRITE
APPEND
TRUNCATE_EXISTING
CREATE_NEW

CREATE is not a StandardCopyOption.

Question 30 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.newBufferedReader(Path.of("notes.txt"), READ);
    }
}

What happens?

A. The code compiles
B. The code does not compile
C. Files.newBufferedReader() does not take StandardOpenOption.READ
D. READ is used with methods like Files.newInputStream()
E. Files.newBufferedReader() takes charset options, not open options

Answer: B, C, D, E
Explanation

This is a hard trap.

Files.newBufferedReader() has overloads like:

Files.newBufferedReader(Path path)
Files.newBufferedReader(Path path, Charset cs)

It does not accept StandardOpenOption.READ.

For open options, use methods like:

Files.newInputStream(path, READ)
Files.newByteChannel(path, READ)
Question 31 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.newInputStream(Path.of("notes.txt"), READ);
    }
}

Which statements are true?

A. The code compiles
B. READ is a valid open option here
C. READ is required because input streams always require explicit READ
D. The file generally must exist
E. This method can throw IOException

Answer: A, B, D, E
Explanation

Files.newInputStream() accepts open options.

READ is valid, but it is not always required because reading is the default behavior.

If the file does not exist, opening for reading can throw an exception.

Question 32 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.newOutputStream(Path.of("notes.txt"), CREATE, WRITE);
    }
}

Which statements are true?

A. The code compiles
B. CREATE creates the file if it does not exist
C. WRITE opens the file for writing
D. This method can throw IOException
E. This code necessarily appends to the existing file

Answer: A, B, C, D
Explanation

This opens an output stream for writing and creates the file if missing.

It does not necessarily append. To append, use:

APPEND
Question 33 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/a/b/c");

        System.out.println(p.getNameCount());
        System.out.println(p.subpath(0, p.getNameCount()));
    }
}

What is printed?

A.

3
a/b/c

B.

4
/a/b/c

C. subpath(0, p.getNameCount()) excludes the root
D. getNameCount() includes the root
E. The code throws an exception

Answer: A, C
Explanation

Root is not counted as a name.

The names are:

a
b
c

So getNameCount() is 3.

subpath(0, 3) returns:

a/b/c

without root.

Question 34 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/");
        System.out.println(p.getRoot());
        System.out.println(p.getFileName());
        System.out.println(p.getNameCount());
    }
}

What is printed on Unix?

A.

/
null
0

B.

/
/
1

C. Root path has no file name
D. Root path has zero name elements
E. The code does not compile

Answer: A, C, D
Explanation

For the root path:

/

Root:

/

File name:

null

Name count:

0
Question 35 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/");
        System.out.println(p.subpath(0, 1));
    }
}

What happens on Unix?

A. Prints /
B. Prints empty path
C. Throws IllegalArgumentException
D. The code does not compile
E. Root is excluded from subpath names

Answer: C, E
Explanation

Root has zero name elements.

So:

p.subpath(0, 1)

is invalid and throws IllegalArgumentException.

Question 36 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("a/b");
        Path q = p.resolve("../c");

        System.out.println(q);
        System.out.println(q.normalize());
    }
}

What is printed?

A.

a/b/../c
a/c

B.

a/c
a/c

C. resolve() automatically normalizes
D. normalize() removes b/..
E. The code accesses the file system

Answer: A, D
Explanation

resolve() behaves like concatenation.

So:

Path.of("a/b").resolve("../c")

becomes:

a/b/../c

Then:

normalize()

removes:

b/..

Result:

a/c
Question 37 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/a/b");
        Path q = Path.of("/a/b/c/d");

        Path r = p.relativize(q);
        System.out.println(p.resolve(r).normalize());
    }
}

What is printed?

A. /a/b/c/d
B. c/d
C. /c/d
D. The code throws IllegalArgumentException
E. p.resolve(p.relativize(q)) can rebuild q when compatible

Answer: A, E
Explanation

From:

/a/b

to:

/a/b/c/d

relative path is:

c/d

Then:

p.resolve("c/d")

becomes:

/a/b/c/d
Question 38 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/a/b/c");
        Path q = Path.of("/a/d/e");

        Path r = p.relativize(q);
        System.out.println(r);
        System.out.println(p.resolve(r).normalize());
    }
}

What is printed?

A.

../../d/e
/a/d/e

B.

../d/e
/a/b/d/e

C.

d/e
/a/b/c/d/e

D. The code throws IllegalArgumentException
E. The relative path contains ..

Answer: A, E
Explanation

From:

/a/b/c

to:

/a/d/e

You go:

up from c to b
up from b to a
down to d/e

So:

../../d/e

Resolving back:

/a/b/c/../../d/e

Normalizes to:

/a/d/e
Question 39 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("a/../b");
        System.out.println(p.toAbsolutePath());
        System.out.println(p.normalize());
    }
}

Which statements are true?

A. toAbsolutePath() must remove ..
B. normalize() can remove a/..
C. toAbsolutePath() may preserve .. in the printed path
D. Neither method requires the path to exist
E. Both methods throw IOException

Answer: B, C, D
Explanation

toAbsolutePath() converts the path to absolute form but does not guarantee normalization.

normalize() removes redundant name elements.

Neither method checks that the file exists.

Question 40 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("a/../b");
        System.out.println(p.toRealPath());
    }
}

Assume b exists.

Which statements are true?

A. toRealPath() returns an absolute path
B. toRealPath() requires the path to exist
C. toRealPath() can resolve symbolic links
D. toRealPath() is guaranteed to produce the same text as toAbsolutePath()
E. The code does not compile

Answer: A, B, C
Explanation

toRealPath() gives a real absolute path by consulting the file system.

It requires the target to exist and may resolve symbolic links.

It is not just text conversion.

Question 41 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("a/b/c");
        System.out.println(p.getName(0));
        System.out.println(p.subpath(0, 1));
    }
}

What is printed?

A.

a
a

B.

a
a/b

C. getName(0) returns a Path
D. subpath(0, 1) returns a Path
E. The code does not compile because getName() returns String

Answer: A, C, D
Explanation

Both getName() and subpath() return Path objects, not String.

But printing them shows their path text.

p.getName(0)

returns:

a
p.subpath(0, 1)

also returns:

a
Question 42 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("a/b/c");

        System.out.println(p.subpath(1, 1));
    }
}

What happens?

A. Prints empty path
B. Prints b
C. Throws IllegalArgumentException
D. Code does not compile
E. beginIndex must be less than endIndex

Answer: C, E
Explanation

For subpath(begin, end):

begin is inclusive
end is exclusive
begin must be less than end

So:

p.subpath(1, 1)

is invalid.

Question 43 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("a/b/c");

        System.out.println(p.subpath(-1, 2));
    }
}

What happens?

A. Prints a/b
B. Throws IllegalArgumentException
C. Throws checked IOException
D. The code does not compile
E. Negative indexes are invalid

Answer: B, E
Explanation

Path index ranges cannot be negative.

Invalid subpath ranges throw:

IllegalArgumentException
Question 44 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("a");
        Path q = Path.of("a");

        System.out.println(p.relativize(q));
    }
}

What is printed?

A. .
B. Empty path
C. a
D. ..
E. The code throws an exception

Answer: B
Explanation

Relativizing a path against itself returns an empty path.

When printed, it appears as an empty line.

It does not print ".".

Question 45 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("a");
        Path q = Path.of("");

        System.out.println(p.resolve(q));
        System.out.println(q.resolve(p));
    }
}

What is printed?

A.

a
a

B.


a

C. Resolving an empty path against p returns p
D. Resolving p against an empty path returns p
E. The code does not compile

Answer: A, C, D
Explanation

The empty path acts like “nothing to add.”

So:

p.resolve("")

returns:

a

and:

Path.of("").resolve(p)

also returns:

a
Question 46 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("a/b");
        Path q = Path.of("/c/d");

        System.out.println(p.resolve(q));
        System.out.println(q.resolve(p));
    }
}

What is printed?

A.

/c/d
/c/d/a/b

B.

a/b/c/d
/c/d/a/b

C. Absolute argument to resolve() wins
D. Relative argument is appended
E. Both lines print absolute paths

Answer: A, C, D, E
Explanation

First:

p.resolve(q)

Because q is absolute, result is:

/c/d

Second:

q.resolve(p)

Because p is relative, it gets appended:

/c/d/a/b
Question 47 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("a/b");
        Path q = Path.of("a/b/c");

        System.out.println(p.relativize(q));
        System.out.println(q.relativize(p));
    }
}

What is printed?

A.

c
..

B.

/c
/..

C.

../c
..

D. The result of relativize() depends on direction
E. Both paths are relative, so allowed

Answer: A, D, E
Explanation

From:

a/b

to:

a/b/c

relative path is:

c

From:

a/b/c

to:

a/b

relative path is:

..

relativize() is directional.

Question 48 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.LinkOption.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("link");

        System.out.println(Files.exists(p, NOFOLLOW_LINKS));
    }
}

Assume:

link exists as a symbolic link
but its target does not exist

Which statements are true?

A. With NOFOLLOW_LINKS, Java checks the link itself
B. The result may be true because the link exists
C. Without NOFOLLOW_LINKS, Files.exists(p) may return false
D. NOFOLLOW_LINKS is a StandardCopyOption
E. The code does not compile

Answer: A, B, C
Explanation

NOFOLLOW_LINKS tells Java not to follow the symbolic link.

So if the link itself exists, Files.exists(p, NOFOLLOW_LINKS) can be true even if its target is missing.

NOFOLLOW_LINKS belongs to LinkOption.

Question 49 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.LinkOption.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("link");

        System.out.println(Files.isRegularFile(p));
        System.out.println(Files.isRegularFile(p, NOFOLLOW_LINKS));
    }
}

Assume:

link is a symbolic link to an existing regular file

What is likely printed?

A.

true
false

B.

false
true

C. First check follows the symbolic link
D. Second check does not follow the symbolic link
E. The code does not compile

Answer: A, C, D
Explanation

By default:

Files.isRegularFile(p)

follows the symbolic link.

So if the target is a regular file, result is true.

With:

NOFOLLOW_LINKS

Java checks the link itself. The symbolic link itself is not a regular file, so false.

Question 50 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.LinkOption.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("link");

        System.out.println(Files.isDirectory(p));
        System.out.println(Files.isDirectory(p, NOFOLLOW_LINKS));
    }
}

Assume:

link is a symbolic link to an existing directory

What is likely printed?

A.

true
false

B.

false
true

C. The first check follows the link
D. The second check checks the link itself
E. The code requires throws IOException

Answer: A, C, D
Explanation

Default behavior follows symbolic links.

With NOFOLLOW_LINKS, Java checks the symbolic link itself.

A symbolic link is not considered a directory when not followed.

Question 51 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.writeString(Path.of("notes.txt"), "hello", CREATE_NEW);
    }
}

Assume notes.txt already exists.

Which statements are true?

A. The code compiles
B. CREATE_NEW fails if the file already exists
C. CREATE_NEW silently overwrites the file
D. An exception is thrown at runtime
E. CREATE_NEW is a StandardOpenOption

Answer: A, B, D, E
Explanation

CREATE_NEW means:

create a new file only if it does not already exist

If the file already exists, an exception is thrown.

Question 52 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.writeString(Path.of("notes.txt"), "hello", CREATE);
    }
}

Assume notes.txt does not exist.

Which statements are true?

A. The file is created
B. The code compiles
C. CREATE fails if the file already exists
D. CREATE is a StandardCopyOption
E. This method can throw IOException

Answer: A, B, E
Explanation

CREATE creates the file if it does not exist.

Unlike CREATE_NEW, it does not fail just because the file already exists.

Question 53 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.writeString(Path.of("notes.txt"), "A", CREATE, APPEND);
    }
}

Which statements are true?

A. The code compiles
B. APPEND writes at the end of the file
C. APPEND is a StandardCopyOption
D. CREATE creates the file if missing
E. This necessarily replaces all existing file content

Answer: A, B, D
Explanation

APPEND writes at the end.

It does not replace all content.

To replace/truncate existing content, options like TRUNCATE_EXISTING may be used depending on method and context.

Question 54 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.writeString(Path.of("notes.txt"), "A", APPEND);
    }
}

Assume notes.txt does not exist.

What happens?

A. It creates the file automatically
B. It throws an exception
C. APPEND alone does not mean CREATE
D. The code does not compile
E. Adding CREATE would allow creation if missing

Answer: B, C, E
Explanation

APPEND means write at the end of an existing file.

It does not automatically create the file.

To create if missing:

Files.writeString(path, "A", CREATE, APPEND);
Question 55 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;
import static java.nio.file.StandardOpenOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.copy(Path.of("a.txt"), Path.of("b.txt"), CREATE);
    }
}

What happens?

A. The code compiles
B. The code does not compile
C. CREATE is not a valid option for Files.copy(Path, Path, CopyOption...)
D. CREATE is a StandardOpenOption, not a CopyOption
E. REPLACE_EXISTING would be a valid option

Answer: B, C, D, E
Explanation

Files.copy(Path, Path, CopyOption...) expects CopyOption.

Valid examples include:

StandardCopyOption.REPLACE_EXISTING
StandardCopyOption.COPY_ATTRIBUTES
LinkOption.NOFOLLOW_LINKS

CREATE is a StandardOpenOption, not a copy option.

Question 56 — Choose all that apply

Given:

import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;
import static java.nio.file.StandardOpenOption.*;

public class Test {
    public static void main(String[] args) throws Exception {
        Files.newOutputStream(Path.of("out.txt"), REPLACE_EXISTING);
    }
}

What happens?

A. The code compiles
B. The code does not compile
C. REPLACE_EXISTING is not a valid open option
D. REPLACE_EXISTING is a StandardCopyOption
E. CREATE and WRITE would be more appropriate open options

Answer: B, C, D, E
Explanation

Files.newOutputStream() expects OpenOption.

REPLACE_EXISTING is a StandardCopyOption.

So the code does not compile.

Question 57 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/a/b");
        Path q = Path.of("/a/b");

        System.out.println(p.relativize(q).getNameCount());
    }
}

What is printed?

A. 0
B. 1
C. 2
D. The code throws an exception
E. Relativizing equal paths returns an empty path

Answer: B, E
Explanation

This is a subtle one.

Relativizing equal paths returns an empty path.

But for an empty relative path, getNameCount() may return 1 in the default Unix implementation because the empty path has one empty name element.

This is exactly why OCP questions often avoid asking getNameCount() on an empty path unless they want a very hard trap.

The printed path itself appears as an empty line.

Question 58 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("");
        System.out.println(p);
        System.out.println(p.isAbsolute());
        System.out.println(p.toAbsolutePath());
    }
}

Which statements are true?

A. Path.of("") represents the empty path
B. It is relative
C. toAbsolutePath() can resolve it to the current working directory
D. The code does not compile
E. It throws NoSuchFileException

Answer: A, B, C
Explanation

The empty path is a valid relative path.

Its absolute path usually represents the current working directory.

It does not require file existence checking.

Question 59 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/a/b/c");
        System.out.println(p.startsWith("/a"));
        System.out.println(p.endsWith("b/c"));
    }
}

What is printed?

A.

true
true

B.

false
true

C. startsWith() and endsWith() are path element comparisons
D. These methods require the path to exist
E. The code does not compile

Answer: A, C
Explanation

Path.startsWith() and Path.endsWith() compare path structures.

They do not require file-system existence.

Question 60 — Choose all that apply

Given:

import java.nio.file.*;

public class Test {
    public static void main(String[] args) {
        Path p = Path.of("/a/b/c");

        System.out.println(p.startsWith("a"));
        System.out.println(p.endsWith("/b/c"));
    }
}

What is printed on Unix?

A.

false
false

B.

true
true

C. Absolute and relative paths may not match in startsWith()
D. endsWith("/b/c") compares an absolute path suffix
E. The code does not compile

Answer: A, C, D
Explanation

p is absolute: /a/b/c

This does not start with relative:a

Also, endsWith("/b/c") uses an absolute path pattern, which generally does not match the relative suffix of an absolute path.

But:

p.endsWith("b/c") would be true.

Master Trap Table
Topic	Trap	Correct Rule
Files.exists()	Students think it throws IOException	It does not throw checked IOException
Files.isDirectory()	Students think it needs throws IOException	It returns boolean; missing path normally returns false
Files.isRegularFile()	Students think it behaves like Files.size()	It does not throw checked IOException
Files.size()	Students expect 0 for missing path	It throws IOException, often NoSuchFileException
Files.getLastModifiedTime()	Students compare it with File.lastModified()	Files version throws IOException; File version returns long
IOException rule	Students think all Files methods throw checked exceptions	Only methods that declare it require handling
SecurityException	Students ignore unchecked exceptions	Some checks can throw unchecked SecurityException
Varargs options	Students confuse option categories	Copy/move options and open options are not interchangeable
LinkOption.NOFOLLOW_LINKS	Students think links are never followed	Many methods follow symbolic links by default
StandardCopyOption.REPLACE_EXISTING	Students use it with streams/open methods	It is for copy/move-style operations
StandardCopyOption.ATOMIC_MOVE	Students think it always works	It can throw if unsupported
StandardOpenOption.CREATE	Students use it with Files.copy()	It is an open option, not a copy option
StandardOpenOption.READ	Students pass it to newBufferedReader()	newBufferedReader() does not take open options
StandardOpenOption.WRITE	Students think it always creates files	Use with CREATE if the file may be missing
StandardOpenOption.APPEND	Students think it creates missing files	APPEND alone does not mean CREATE
Path immutability	Students think p.resolve(x) changes p	Path methods return new Path objects
getNameCount()	Students count the root	Root is not included
getName(i)	Students expect IndexOutOfBoundsException	Invalid index throws IllegalArgumentException
subpath(begin,end)	Students include the root	Root is excluded
subpath()	Students think end index is included	Begin inclusive, end exclusive
subpath(1,1)	Students expect empty path	Invalid; throws IllegalArgumentException
getFileName()	Students think it checks file existence	It only reads path text
getParent()	Students expect "." for simple filename	Simple filename parent is null
getRoot()	Students expect root on relative path	Relative path root is null
resolve(relative)	Students forget it appends	Relative argument is appended
resolve(absolute)	Students expect concatenation	Absolute argument is returned as-is
resolve("")	Students expect exception	Empty path resolves to the original path
relativize()	Students think order does not matter	It is directional
relativize()	Students mix absolute and relative paths	Both must be absolute or both relative
Windows relativize()	Students ignore drive letters	Different drives generally cannot be relativized
normalize()	Students think it checks existence	It does not access file system
normalize()	Students think all .. disappear	Leading unresolved .. remain
Path.equals()	Students think it normalizes automatically	It compares path structure/text, not real identity
toAbsolutePath()	Students think it requires existence	It does not require the file to exist
toRealPath()	Students think it is same as toAbsolutePath()	It requires existence and resolves real file-system path
toRealPath()	Students forget symbolic links	It resolves symbolic links by default
toRealPath(NOFOLLOW_LINKS)	Students think option invalid	It is valid and avoids following final symbolic link
Files.list()	Students think recursive	It lists direct children only
Files.walk()	Students confuse with list()	walk() traverses recursively
Empty path	Students think invalid	Path.of("") is valid and relative
Root path /	Students expect filename /	Root has no file name and zero name elements
startsWith() / endsWith()	Students think string matching only	They compare path elements and absolute/relative structure matters
Final OCP Memory Rules
Path methods mostly inspect path text.
Files methods often inspect the real file system.
But not every Files method throws IOException.
exists / isDirectory / isRegularFile
= boolean checks
= no checked IOException
= missing path normally false
size / list / copy / move / delete / toRealPath
= real file-system operations
= often throw IOException
resolve()
relative argument → append
absolute argument → return argument
relativize()
both absolute or both relative
direction matters
mixed types throw exception
normalize()
cleans path text
does not check file system
toRealPath()
checks real file system
requires existence
returns absolute path
resolves symbolic links