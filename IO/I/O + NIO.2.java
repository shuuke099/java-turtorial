import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.stream.Stream;


You are building the storage layer of:

👉 Zeila Market platform

This system is responsible for:

Persisting user data
Managing files & directories
Handling paths across OS
Reading/writing data
Serializing objects
Navigating file systems
Supporting both legacy and modern APIs

// ======================================================
// 🌍 ZEILA STORAGE ENGINE (FULL I/O + NIO.2 SYSTEM)
// ======================================================
public class ZeilaStorageEngine {

    // ======================================================
    // 📁 BASE DIRECTORY
    // ======================================================
    private static final Path BASE = Path.of("data");

    // ======================================================
    // 🧠 1. FILE SYSTEM FUNDAMENTALS
    // ======================================================
    public static void printSystemInfo() {
        System.out.println("File Separator: " +
                System.getProperty("file.separator")); // OS independent
    }

    // ======================================================
    // 🧱 2. PATH CREATION (MULTIPLE WAYS)
    // ======================================================
    public static void createPathsDemo() {

        Path p1 = Path.of("data/users/u1.txt"); // recommended
        Path p2 = Path.of("data", "users", "u1.txt");

        Path p3 = Paths.get("data", "users", "u1.txt"); // legacy helper

        Path p4 = FileSystems.getDefault()
                .getPath("data/users/u1.txt");

        Path p5 = Path.of(URI.create("file:///data/users/u1.txt"));

        System.out.println(p1 + " == " + p2);
    }

    // ======================================================
    // 🧱 3. FILE vs PATH (IMPORTANT)
    // ======================================================
    public static void fileVsPath() {

        File file = new File("data/users/u1.txt");

        Path path = file.toPath(); // convert
        File back = path.toFile();

        System.out.println(file.exists());
    }

    // ======================================================
    // 🧱 4. CREATE DIRECTORIES & FILES
    // ======================================================
    public static void createStructure() throws Exception {

        Path usersDir = BASE.resolve("users");
        Files.createDirectories(usersDir); // safe creation

        Path userFile = usersDir.resolve("u1.txt");

        if (!Files.exists(userFile)) {
            Files.createFile(userFile);
        }
    }

    // ======================================================
    // 🧱 5. WRITE DATA (I/O STREAM BEHAVIOR)
    // ======================================================
    public static void writeUser() throws Exception {

        Path path = BASE.resolve("users/u1.txt");

        Files.writeString(path,
                "Name=Adan\nRole=SELLER\nRegion=MN");

    }

    // ======================================================
    // 🧱 6. READ DATA
    // ======================================================
    public static void readUser() throws Exception {

        Path path = BASE.resolve("users/u1.txt");

        if (Files.exists(path)) {
            String data = Files.readString(path);
            System.out.println(data);
        }
    }

    // ======================================================
    // 🧱 7. ABSOLUTE vs RELATIVE PATH
    // ======================================================
    public static void pathTypes() {

        Path relative = Path.of("data/users/u1.txt");

        Path absolute = Path.of("/data/users/u1.txt");

        System.out.println("Relative: " + relative);
        System.out.println("Absolute: " + absolute);
    }

    // ======================================================
    // 🧱 8. PATH SYMBOLS (., ..)
    // ======================================================
    public static void pathSymbols() {

        Path messy = Path.of("data/users/../logs/app.log");

        Path clean = messy.normalize(); // remove redundancy

        System.out.println("Before: " + messy);
        System.out.println("After: " + clean);
    }

    // ======================================================
    // 🧱 9. DIRECTORY TRAVERSAL
    // ======================================================
    public static void listUsers() throws Exception {

        Path usersDir = BASE.resolve("users");

        try (Stream<Path> stream = Files.list(usersDir)) {
            stream.forEach(System.out::println);
        }
    }

    // ======================================================
    // 🧱 10. FILE ATTRIBUTES
    // ======================================================
    public static void fileAttributes() throws Exception {

        Path path = BASE.resolve("users/u1.txt");

        long size = Files.size(path);
        boolean exists = Files.exists(path);

        System.out.println("Size: " + size);
        System.out.println("Exists: " + exists);
    }

    // ======================================================
    // 🧱 11. SYMBOLIC LINK (ADVANCED)
    // ======================================================
    public static void symbolicLinkDemo() throws Exception {

        Path target = BASE.resolve("users");
        Path link = BASE.resolve("users-link");

        if (!Files.exists(link)) {
            Files.createSymbolicLink(link, target);
        }

        System.out.println("Link → " + link);
    }

    // ======================================================
    // 🧱 12. SERIALIZATION
    // ======================================================
    public static void serializeUser() throws Exception {

        User user = new User("Adan", "SELLER");

        ObjectOutputStream out =
                new ObjectOutputStream(
                        new FileOutputStream("user.ser"));

        out.writeObject(user);
        out.close();
    }

    public static void deserializeUser() throws Exception {

        ObjectInputStream in =
                new ObjectInputStream(
                        new FileInputStream("user.ser"));

        User user = (User) in.readObject();

        System.out.println(user);
        in.close();
    }

    // ======================================================
    // 🧱 13. CONSOLE INPUT
    // ======================================================
    public static void consoleInput() {

        Console console = System.console();

        if (console != null) {
            String name = console.readLine("Enter name: ");
            System.out.println("Hello " + name);
        }
    }

    // ======================================================
    // 🧱 14. FULL FLOW (SYSTEM EXECUTION)
    // ======================================================
    public static void runSystem() throws Exception {

        printSystemInfo();

        createPathsDemo();

        createStructure();

        writeUser();

        readUser();

        pathTypes();

        pathSymbols();

        listUsers();

        fileAttributes();

        serializeUser();

        deserializeUser();
    }

    // ======================================================
    // 🚀 MAIN ENTRY
    // ======================================================
    public static void main(String[] args) throws Exception {
        runSystem();
    }
}

// ======================================================
// 🧾 SERIALIZABLE MODEL
// ======================================================
class User implements Serializable {

    private String name;
    private String role;

    public User(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String toString() {
        return name + " - " + role;
    }
}

File Separator: /

data/users/u1.txt == data/users/u1.txt

Name=Adan
Role=SELLER
Region=MN

Relative: data/users/u1.txt
Absolute: /data/users/u1.txt

Before: data/users/../logs/app.log
After: data/logs/app.log

data/users/u1.txt

Size: 33
Exists: true

Adan - SELLER