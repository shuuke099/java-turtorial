Example 1: Copy a binary file using byte[]
Hospital scenario

A hospital needs to copy an X-ray image from the intake folder to the archive folder.

import java.io.*;

public class BinaryCopyExample {
    public static void main(String[] args) throws IOException {
        File source = new File("hospital/intake/xray-image.jpg");
        File destination = new File("hospital/archive/xray-image-copy.jpg");

        try (InputStream in = new BufferedInputStream(new FileInputStream(source));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(destination))) {

            byte[] buffer = new byte[1024];

            int lengthRead;

            while ((lengthRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, lengthRead);
            }
        }
    }
}
Why byte[]?

Because binary files are made of raw bytes.

byte[] buffer = new byte[1024];

This means:Read up to 1024 bytes at a time.

Important rule: use lengthRead
out.write(buffer, 0, lengthRead);

This is important because the last read may not fill the whole buffer.

Example:

File size: 1054 bytes
Buffer size: 1024 bytes

Reads:

First read  → 1024 bytes
Second read → 30 bytes

So the second write should write only:

30 bytes

not the whole 1024-byte buffer.

Example 2: Using readAllBytes()

For smaller binary files, Java can read all bytes at once.

import java.io.*;
import java.nio.file.*;

public class ReadAllBytesExample {
    public static void main(String[] args) throws IOException {
        Path source = Path.of("hospital/intake/xray-image.jpg");
        Path destination = Path.of("hospital/archive/xray-image-copy.jpg");

        byte[] data = Files.readAllBytes(source);

        Files.write(destination, data);
    }
}
Meaning
byte[] data = Files.readAllBytes(source);

means:

Read the entire file into a byte array.

Then:

Files.write(destination, data);

means:

Write those bytes to the destination file.

When to use readAllBytes()

Good for:

small files
small images
small PDFs
small configuration files

Be careful with:

very large videos
large backups
huge medical imaging files

because readAllBytes() loads the whole file into memory.

// No newline handling required

With text files, you may care about lines:

writer.newLine();
System.lineSeparator();
println();

But with binary files, you do not handle new lines.

You should not add:

writer.newLine();

or:

System.lineSeparator();

because that would corrupt binary data.






Using PrintWriter and PrintStream

PrintWriter and PrintStream are higher-level output classes.

They are more convenient than BufferedWriter because they provide easy methods like:

print()
println()
format()
printf()

They are mainly used when you want to write formatted text output.

1. PrintWriter Scenario: Copying a Hospital Text File

Imagine a hospital system needs to copy a patient notes file:

hospital/intake/patient-notes.txt

to:

hospital/archive/patient-notes-copy.txt

The system reads the file line by line using BufferedReader, then writes each line using PrintWriter.

import java.io.*;

public class HospitalPrintWriterCopy {

    void copyTextFile(File src, File dest) throws IOException {
        try (var reader = new BufferedReader(new FileReader(src));
             var writer = new PrintWriter(new FileWriter(dest))) {

            String line;

            while ((line = reader.readLine()) != null) {
                writer.println(line);
            }
        }
    }
}
2. Why use PrintWriter here?

With BufferedWriter, you usually write:

writer.write(line);
writer.newLine();

With PrintWriter, you can simply write:

writer.println(line);

println() writes the text and automatically adds the correct line separator.

3. println() automatically adds a line separator

This:

writer.println(line);

is like saying:

writer.write(line);
writer.write(System.lineSeparator());

But println() is easier and cleaner.

4. Line separator problem

Different operating systems use different line separators.

Operating system	Line separator
Linux/macOS	\n
Windows	\r\n

So instead of manually writing:

"\n"

Java can use the correct system line separator automatically through:

println()

or manually with:

System.lineSeparator()

or:

System.getProperty("line.separator")
5. println() overloads

println() can accept many types:

System.out.println("Patient admitted");
System.out.println(45);
System.out.println(98.6);
System.out.println(true);
System.out.println(new Object());

It accepts:

Type	Example
String	"Patient admitted"
primitives	int, double, boolean, char
objects	new Patient()

Internally, Java converts the value to text using:

String.valueOf()

So this:

System.out.println(45);

is internally converted to text like:

"45"





===========================



void copyReader(Reader in, Writer out) throws IOException {
    int batchSize = 1024;
    char[] buffer = new char[batchSize];

    int lengthRead;

    while ((lengthRead = in.read(buffer, 0, batchSize)) > 0) {
        out.write(buffer, 0, lengthRead);
        out.flush();
    }
}
Same idea

Byte version:

InputStream  → byte[]
OutputStream → byte[]

Char version:

Reader → char[]
Writer → char[]
Example use
try (
        Reader reader = new FileReader("patient-notes.txt");
        Writer writer = new FileWriter("backup-notes.txt")
) {
    copyReader(reader, writer);
}
Small note

Usually, you do not need to call flush() inside every loop. Better performance:

void copyReader(Reader in, Writer out) throws IOException {
    int batchSize = 1024;
    char[] buffer = new char[batchSize];

    int lengthRead;

    while ((lengthRead = in.read(buffer, 0, batchSize)) > 0) {
        out.write(buffer, 0, lengthRead);
    }

    out.flush();
}
lengthRead = in.read(buffer, 0, batchSize)) > 0... in here some times i can reduce the redundant batchsize ... char[] buffer = new char[1024]; .. like this?

Yes. You can remove the separate batchSize variable and use buffer.length.

Better version:

void copyReader(Reader in, Writer out) throws IOException {
    char[] buffer = new char[1024];

    int lengthRead;

    while ((lengthRead = in.read(buffer, 0, buffer.length)) > 0) {
        out.write(buffer, 0, lengthRead);
    }

    out.flush();
}

Even shorter:

void copyReader(Reader in, Writer out) throws IOException {
    char[] buffer = new char[1024];

    int lengthRead;

    while ((lengthRead = in.read(buffer)) > 0) {
        out.write(buffer, 0, lengthRead);
    }

    out.flush();
}
Why this works

This:

in.read(buffer)

means:

in.read(buffer, 0, buffer.length)

So yes, this is redundant:

int batchSize = 1024;
char[] buffer = new char[batchSize];

in.read(buffer, 0, batchSize);

You can simply write:

char[] buffer = new char[1024];

in.read(buffer);

Best exam-friendly version:

while ((lengthRead = in.read(buffer)) > 0) {
    out.write(buffer, 0, lengthRead);
}






readAllBytes() version does not need try-with-resources
import java.io.*;
import java.nio.file.*;

void copyBinaryFile(File src, File dest) throws IOException {
    byte[] data = Files.readAllBytes(src.toPath());

    Files.write(dest.toPath(), data);
}

Why?

Because you are not opening the stream yourself.

Java opens and closes the file internally.

2. Buffered stream version does need try-with-resources
import java.io.*;

void copyBinaryFile(File src, File dest) throws IOException {
    try (
            var in = new BufferedInputStream(new FileInputStream(src));
            var out = new BufferedOutputStream(new FileOutputStream(dest))
    ) {
        byte[] buffer = new byte[1024];

        int lengthRead;

        while ((lengthRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, lengthRead);
        }
    }
}








. Read and write as String
File: patient.txt
Patient: Ali
Room: ICU
Status: Stable
Code
Path input = Path.of("patient.txt");
Path output = Path.of("copy.txt");

String s = Files.readString(input);

System.out.println(s);

Files.writeString(output, s);
Console output
Patient: Ali
Room: ICU
Status: Stable
New file: copy.txt
Patient: Ali
Room: ICU
Status: Stable
2. Read and write as byte[]
File: report.pdf

Imagine the file is a real PDF.

Code
Path input = Path.of("report.pdf");
Path output = Path.of("copy.pdf");

byte[] b = Files.readAllBytes(input);

System.out.println(b.length);

Files.write(output, b);
Console output

Example:

24580

That means the PDF file has 24,580 bytes.

New file
copy.pdf

The new file is a copied PDF. You normally do not print PDF bytes as text.

3. Read and write as List<String>
File: patients.txt
Ali
Fatima
Hassan
Code
Path input = Path.of("patients.txt");
Path output = Path.of("copy.txt");

List<String> lines = Files.readAllLines(input);

System.out.println(lines);

Files.write(output, lines);
Console output
[Ali, Fatima, Hassan]
New file: copy.txt
Ali
Fatima
Hassan
Remember
readString()

prints the file as normal text.

readAllBytes()

prints numbers/byte size, not readable file content.

readAllLines()

prints a Java List, like:

[Ali, Fatima, Hassan]