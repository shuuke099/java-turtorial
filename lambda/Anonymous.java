class User {
    String firstName;
    String lastName;
    String email;
    int age;
}

// Interfaces (same)
interface UserFactory {
    User create();
}

interface StringProcessor {
    String process(String input);
}

interface StringToIntConverter {
    int convert(String input);
}

interface AgeValidator {
    boolean isValid(int age);
}

interface EmailMatcher {
    boolean match(String email1, String email2);
}

interface NameCombiner {
    String combine(String first, String last);
}

interface PointsCalculator {
    int add(int a, int b);
}

interface Logger {
    void log(String message);
}

// 🚀 MAIN
public class Main {
    public static void main(String[] args) {

        String rawFirst = "   Adan   ";
        String rawLast = " MAHAMUD ";
        String rawEmail = "  Adan@Email.COM ";
        String confirmEmail = "adan@email.com";
        String ageInput = "25";

        // 🔁 Anonymous classes

        UserFactory factory = new UserFactory() {
            public User create() {
                return new User();
            }
        };

        StringProcessor processor = new StringProcessor() {
            public String process(String input) {
                return input.trim().toLowerCase();
            }
        };

        StringToIntConverter converter = new StringToIntConverter() {
            public int convert(String input) {
                return Integer.parseInt(input);
            }
        };

        AgeValidator ageValidator = new AgeValidator() {
            public boolean isValid(int age) {
                return age >= 18;
            }
        };

        EmailMatcher emailMatcher = new EmailMatcher() {
            public boolean match(String e1, String e2) {
                return e1.equals(e2);
            }
        };

        NameCombiner combiner = new NameCombiner() {
            public String combine(String f, String l) {
                return f + " " + l;
            }
        };

        PointsCalculator calculator = new PointsCalculator() {
            public int add(int a, int b) {
                return a + b;
            }
        };

        Logger logger = new Logger() {
            public void log(String message) {
                System.out.println(message);
            }
        };

        // FLOW
        User user = factory.create();

        user.firstName = processor.process(rawFirst);
        user.lastName = processor.process(rawLast);
        user.email = processor.process(rawEmail);

        user.age = converter.convert(ageInput);

        if (!ageValidator.isValid(user.age)) {
            logger.log("User is not adult");
            return;
        }

        if (!emailMatcher.match(user.email, confirmEmail)) {
            logger.log("Emails do not match");
            return;
        }

        String fullName = combiner.combine(user.firstName, user.lastName);

        int totalPoints = calculator.add(100, 50);

        logger.log("User Registered Successfully!");
        logger.log("Name: " + fullName);
        logger.log("Email: " + user.email);
        logger.log("Age: " + user.age);
        logger.log("Points: " + totalPoints);
    }
}