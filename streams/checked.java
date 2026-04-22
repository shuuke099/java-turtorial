// ===========================================
5. Checked Exceptions and Functional Interfaces
// ===========================================
// Airport Example: Loading inspection records from a file


// import java.io.IOException;
// import java.util.List;
// import java.util.function.Supplier;

static List<String> loadInspectionReport() throws IOException {
    throw new IOException();
 };  

static void good() throws IOException {
    loadInspectionReport().stream().count();
}//This is legal by itself.

Supplier<List<String>> supplier = AdvancedDemo::loadInspectionReport; // DOES NOT COMPILE


Why? Because Supplier<T> does not declare throws IOException.

// Two solutions

// Solution 1: catch and wrap inline
Supplier<List<String>> supplier = () -> {
    try {
        return loadInspectionReport();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
};//Works, but ugly


// Solution 2: create a safe wrapper
static List<String> loadInspectionReportSafe() {
    try {
        return loadInspectionReport();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

Supplier<List<String>> supplier = AdvancedDemo::loadInspectionReportSafe;

Business meaning

In enterprise code:

// file loading
// DB reading
// remote API fetching
often throw checked exceptions.

// But stream/functional code usually wants lambdas that do not throw them directly.

