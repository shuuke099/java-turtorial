import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    /*
     GET /users/{id}?lang=en
    */
    @GetMapping("/{id}")
    public UserResponse getOne(
            @PathVariable Long id,
            @RequestParam(defaultValue = "en") String lang
    ) {

        System.out.println("\n[Controller] Incoming request → id=" + id);

        // =================================================
        // STEP 1 — DATABASE
        // =================================================
        Optional<User> userOpt = repo.findById(id);

        // =================================================
        // STEP 2 — LOGGING (isPresent)
        // =================================================
        if (userOpt.isPresent()) {
            System.out.println("[LOG] User found in DB");
        } else {
            System.out.println("[LOG] User NOT found in DB");
        }

        // =================================================
        // STEP 3 — SIDE EFFECT (ifPresent)
        // =================================================
        userOpt.ifPresent(user ->
                System.out.println("[LOG] User name: " + user.getName())
        );

        // =================================================
        // STEP 4 — OPTIONAL VALIDATION (filter)
        // Example: Only allow active users (simulated)
        // =================================================
        Optional<User> validUser =
                userOpt.filter(user -> user.getId() > 0); // example rule

        // =================================================
        // STEP 5 — MAIN BUSINESS FLOW (map + orElseThrow)
        // =================================================
        UserResponse response = validUser
                .map(user -> {

                    // 🔥 "populate / lang" simulation
                    String name =
                            "en".equals(lang)
                                    ? user.getName()
                                    : "Translated-" + user.getName();

                    // =================================================
                    // Optional INSIDE object
                    // =================================================
                    String phone =
                            user.getPhoneNumber().orElse("N/A");

                    // =================================================
                    // SIDE EFFECT inside flow
                    // =================================================
                    user.getPhoneNumber().ifPresent(p ->
                            System.out.println("[SMS] Sending SMS to " + p)
                    );

                    return new UserResponse(
                            user.getId(),
                            name,
                            phone
                    );

                })
                .orElseThrow(() ->
                        new RuntimeException("No document found with that ID"));

        // =================================================
        // STEP 6 — DEFAULT HANDLING (rare case example)
        // =================================================
        User fallbackUser =
                userOpt.orElseGet(() -> {
                    System.out.println("[Fallback] Creating fallback user");
                    return new User(0L, "Guest", null);
                });

        // NOTE: fallbackUser is not used in response — just example usage

        // =================================================
        // STEP 7 — FINAL RESPONSE
        // =================================================
        return response;
    }
}