/*
 ============================================================================
  OPTIONAL — COMPLETE STEP-BY-STEP EXPLANATION (REAL-WORLD STYLE)
  This file explains EVERYTHING using comments.
  Nothing is skipped. Nothing is assumed.
 ============================================================================
*/

// import java.util.Optional;

/*
 ============================================================================
 STEP 1 — DOMAIN OBJECT (User)
 ----------------------------------------------------------------------------
 - This is a simple Java class.
 - Represents real data in the system.
 - This is what we WANT to retrieve.
 ============================================================================
*/
class User {

    private long id;
    private String name;
    private Optional<String> phoneNumber; // Optional FIELD (rare, but shown here)

    public User(long id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;

        // ofNullable() is used because phoneNumber MAY be null
        this.phoneNumber = Optional.ofNullable(phoneNumber);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Returning Optional because phone number may not exist
    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }
}

/*
 ============================================================================
 STEP 2 — DATABASE (UNSAFE / LEGACY)
 ----------------------------------------------------------------------------
 - Simulates a database.
 - Database methods often return null.
 - This is where uncertainty begins.
 ============================================================================
*/
class Database {

    public User findUser(long id) {

        // If id == 1, user exists
        if (id == 1) {
            return new User(1, "Adan", "612-555-1234");
        }

        // If id != 1, user does NOT exist
        return null; // DANGEROUS, but realistic
    }
}

/*
 ============================================================================
 STEP 3 — REPOSITORY (WHERE Optional IS CREATED)
 ----------------------------------------------------------------------------
 - Repository hides nulls.
 - Converts null into Optional.empty().
 - This is PROFESSIONAL DESIGN.
 ============================================================================
*/
class UserRepository {

    private Database database = new Database();

    public Optional<User> findById(long id) {

        // Step 1: Call database (may return null)
        User user = database.findUser(id);

        // Step 2: Convert null → Optional.empty()
        //          Non-null → Optional<User>
        return Optional.ofNullable(user);
    }
}

/*
 ============================================================================
 STEP 4 — SERVICE LAYER (BUSINESS LOGIC)
 ----------------------------------------------------------------------------
 - This is where Optional is CONSUMED.
 - This is where decisions are made.
 ============================================================================
*/
class UserService {

    private UserRepository repository = new UserRepository();

    public void processUser(long id) {

        /*
         --------------------------------------------------------------------
         THIS IS THE LINE YOU ASKED ABOUT
         --------------------------------------------------------------------
         - findById returns Optional<User>
         - userOpt is NEVER null
         - The BOX may be empty
         --------------------------------------------------------------------
        */
        Optional<User> userOpt = repository.findById(id);

        /*
         --------------------------------------------------------------------
         1) isPresent() — explicit branching (rare)
         --------------------------------------------------------------------
         - Mostly used for logging / metrics / legacy interop
        */
        if (userOpt.isPresent()) {
            System.out.println("User exists in database");
        } else {
            System.out.println("User does NOT exist in database");
        }

        /*
         --------------------------------------------------------------------
         2) ifPresent() — run logic ONLY if value exists
         --------------------------------------------------------------------
         - No if / else
         - Functional style
        */
        userOpt.ifPresent(user ->
            System.out.println("User name: " + user.getName())
        );

        /*
         --------------------------------------------------------------------
         3) orElse() — cheap default value
         --------------------------------------------------------------------
         - Used when default creation is SIMPLE
        */
        User defaultUser =
            userOpt.orElse(new User(0, "Guest", null));

        /*
         --------------------------------------------------------------------
         4) orElseGet() — expensive default value
         --------------------------------------------------------------------
         - Supplier is executed ONLY if Optional is empty
        */
        User fallbackUser =
            userOpt.orElseGet(() -> {
                System.out.println("Creating fallback user...");
                return new User(-1, "Fallback", null);
            });

        /*
         --------------------------------------------------------------------
         5) orElseThrow() — absence is NOT allowed
         --------------------------------------------------------------------
         - This enforces a business rule
        */
        User requiredUser =
            userOpt.orElseThrow(() ->
                new RuntimeException("User is REQUIRED but not found")
            );

        /*
         --------------------------------------------------------------------
         6) get() — UNSAFE unless guaranteed
         --------------------------------------------------------------------
         - Only allowed when logic GUARANTEES presence
         - Otherwise → bug
        */
        User guaranteedUser = userOpt.get(); // safe ONLY if user exists

        /*
         --------------------------------------------------------------------
         7) map() — transform value if present
         --------------------------------------------------------------------
         - If Optional is empty → stays empty
         - No NullPointerException
        */
        Optional<String> userName =
            userOpt.map(User::getName);

        /*
         --------------------------------------------------------------------
         8) filter() — apply condition if present
         --------------------------------------------------------------------
         - If condition fails → Optional.empty()
        */
        Optional<User> adultUser =
            userOpt.filter(user -> user.getId() >= 18);

        /*
         --------------------------------------------------------------------
         9) map() + orElse() — very common pattern
         --------------------------------------------------------------------
        */
        String name =
            userOpt.map(User::getName)
                   .orElse("Unknown");

        /*
         --------------------------------------------------------------------
         10) Optional INSIDE object
         --------------------------------------------------------------------
         - phone number may or may not exist
         - Use ifPresent() for side effects
        */
        userOpt.ifPresent(user ->
            user.getPhoneNumber()
                .ifPresent(phone ->
                    System.out.println("Sending SMS to " + phone)
                )
        );
    }
}

/*
 ============================================================================
 STEP 5 — APPLICATION ENTRY POINT
 ----------------------------------------------------------------------------
 ============================================================================
*/
public class OptionalFullExample {

    public static void main(String[] args) {

        UserService service = new UserService();

        System.out.println("=== CASE 1: USER EXISTS ===");
        service.processUser(1);

        System.out.println("\n=== CASE 2: USER DOES NOT EXIST ===");
        try {
            service.processUser(99);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
