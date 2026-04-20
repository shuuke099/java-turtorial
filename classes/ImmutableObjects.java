import java.util.*;

class CitizenProfile {

    private String name;
    private List<String> documents;

    public CitizenProfile(String name, List<String> documents) {
        this.name = name;
        this.documents = documents;
    }

    public List<String> getDocuments() {
        return documents; // ❌ exposes internal state
    }
}

List<String> docs = new ArrayList<>();
docs.add("Passport");

CitizenProfile profile = new CitizenProfile("Ali", docs);

// 😱 Hacker / bug modifies data
profile.getDocuments().clear();
profile.getDocuments().add("Fake Document");

// ==============================

class ImmutableCitizenProfile {

    private final String name;
    private final List<String> documents;

    public ImmutableCitizenProfile(String name, List<String> documents) {
        this.name = name;
        // this.name = Objects.requireNonNull(name);
        this.documents = List.copyOf(documents); // ✅ defensive copy
    }

    public List<String> getDocuments() {
        return documents; // ✅ unmodifiable view
    }
}

// ===============================

import java.util.*;

// ==============================
// 🔐 IMMUTABLE CLASS
// ==============================
public final class CitizenProfile {

    private final String name;
    private final String ssn;
    private final List<String> documents;

    // ==============================
    // 🛡️ CONSTRUCTOR (DEFENSIVE COPY)
    // ==============================
    public CitizenProfile(String name, String ssn, List<String> documents) {

        if (name == null || ssn == null || documents == null || documents.isEmpty()) {
            throw new IllegalArgumentException("Invalid input");
        }

        this.name = name;
        this.ssn = ssn;

        // 🔥 Defensive copy (VERY IMPORTANT)
        this.documents = new ArrayList<>(documents);
    }

    // ==============================
    // 📖 READ-ONLY ACCESS
    // ==============================
    public String getName() {
        return name;
    }

    public String getSsn() {
        return ssn;
    }

    // 🔥 Copy-on-read (safe getter)
    public List<String> getDocuments() {
        return new ArrayList<>(documents);
    }

    // Optional: controlled access
    public int getDocumentCount() {
        return documents.size();
    }

    public String getDocument(int index) {
        return documents.get(index);
    }
}


// ==============================

import java.util.*;

public final class CitizenProfile {

    private final String name;
    private final String ssn;
    private final List<String> documents;

    public CitizenProfile(String name, String ssn, List<String> documents) {

        this.name = Objects.requireNonNull(name);
        this.ssn = Objects.requireNonNull(ssn);
        this.documents = List.copyOf(documents); // 🔥 best
    }

    public String getName() {
        return name;
    }

    public String getSsn() {
        return ssn;
    }

    public List<String> getDocuments() {
        return documents; // safe
    }
}
