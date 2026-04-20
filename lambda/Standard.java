import java.util.function.*;

class User {
    String firstName;
    String lastName;
    String email;
    int age;
}

public class Main {
    public static void main(String[] args) {

        String rawFirst = "   Adan   ";
        String rawLast = " MAHAMUD ";
        String rawEmail = "  Adan@Email.COM ";
        String confirmEmail = "adan@email.com";
        String ageInput = "25";

        // 🔥 Built-in interfaces

        Supplier<User> factory = () -> new User();

        UnaryOperator<String> processor =
                input -> input.trim().toLowerCase();

        Function<String, Integer> converter =
                input -> Integer.parseInt(input);

        Predicate<Integer> ageValidator =
                age -> age >= 18;

        BiPredicate<String, String> emailMatcher =
                (e1, e2) -> e1.equals(e2);

        BiFunction<String, String, String> combiner =
                (f, l) -> f + " " + l;

        BinaryOperator<Integer> calculator =
                (a, b) -> a + b;

        Consumer<String> logger =
                msg -> System.out.println(msg);

        // FLOW
        User user = factory.get();

        user.firstName = processor.apply(rawFirst);
        user.lastName = processor.apply(rawLast);
        user.email = processor.apply(rawEmail);

        user.age = converter.apply(ageInput);

        if (!ageValidator.test(user.age)) {
            logger.accept("User is not adult");
            return;
        }

        if (!emailMatcher.test(user.email, confirmEmail)) {
            logger.accept("Emails do not match");
            return;
        }

        String fullName = combiner.apply(user.firstName, user.lastName);

        int totalPoints = calculator.apply(100, 50);

        logger.accept("User Registered Successfully!");
        logger.accept("Name: " + fullName);
        logger.accept("Email: " + user.email);
        logger.accept("Age: " + user.age);
        logger.accept("Points: " + totalPoints);
    }
}



Supplier<User> factory = User::new;
Function<String, Integer> converter = Integer::parseInt;
BiPredicate<String, String> emailMatcher = String::equals;
BinaryOperator<Integer> calculator = Integer::sum;
Consumer<String> logger = System.out::println;