
CLIENT WORD PROBLEM (Real Request)

“We are building a marketplace platform.
When a user signs up, we need a backend process that handles registration properly.

The system should:

Create a new user
Clean the input (remove spaces, lowercase everything)
Convert age from text to number
Make sure the user is at least 18
Make sure email matches confirm email
Combine first and last name into full name
Give the user reward points (base + bonus)
Print/log everything clearly

We want this to be modular and maintainable because later we will scale the system.” 


class User {
    String firstName;
    String lastName;
    String email;
    int age;

    public User() {}
}

// 🧩 1. Create User
interface UserFactory {
    User create();
}

class UserFactoryImpl implements UserFactory {
    public User create() {
        return new User();
    }
}

// 🧩 2. Clean Input
interface StringProcessor {
    String process(String input);
}

class StringProcessorImpl implements StringProcessor {
    public String process(String input) {
        return input.trim().toLowerCase();
    }
}

// 🧩 3. Convert Age
interface StringToIntConverter {
    int convert(String input);
}

class StringToIntConverterImpl implements StringToIntConverter {
    public int convert(String input) {
        return Integer.parseInt(input);
    }
}

// 🧩 4. Validate Age
interface AgeValidator {
    boolean isValid(int age);
}

class AgeValidatorImpl implements AgeValidator {
    public boolean isValid(int age) {
        return age >= 18;
    }
}

// 🧩 5. Validate Emails
interface EmailMatcher {
    boolean match(String email1, String email2);
}

class EmailMatcherImpl implements EmailMatcher {
    public boolean match(String email1, String email2) {
        return email1.equals(email2);
    }
}

// 🧩 6. Combine Names
interface NameCombiner {
    String combine(String first, String last);
}

class NameCombinerImpl implements NameCombiner {
    public String combine(String first, String last) {
        return first + " " + last;
    }
}

// 🧩 7. Add Points
interface PointsCalculator {
    int add(int a, int b);
}

class PointsCalculatorImpl implements PointsCalculator {
    public int add(int a, int b) {
        return a + b;
    }
}

// 🧩 8. Logger
interface Logger {
    void log(String message);
}

class LoggerImpl implements Logger {
    public void log(String message) {
        System.out.println(message);
    }
}

// 🚀 MAIN SYSTEM
public class Main {
    public static void main(String[] args) {

        // Raw input
        String rawFirst = "   Adan   ";
        String rawLast = " MAHAMUD ";
        String rawEmail = "  Adan@Email.COM ";
        String confirmEmail = "adan@email.com";
        String ageInput = "25";

        // Initialize implementations
        UserFactory factory = new UserFactoryImpl();
        StringProcessor processor = new StringProcessorImpl();
        StringToIntConverter converter = new StringToIntConverterImpl();
        AgeValidator ageValidator = new AgeValidatorImpl();
        EmailMatcher emailMatcher = new EmailMatcherImpl();
        NameCombiner combiner = new NameCombinerImpl();
        PointsCalculator calculator = new PointsCalculatorImpl();
        Logger logger = new LoggerImpl();

        // 🔄 FLOW

        // 1. Create user
        User user = factory.create();

        // 2. Clean input
        user.firstName = processor.process(rawFirst);
        user.lastName = processor.process(rawLast);
        user.email = processor.process(rawEmail);

        // 3. Convert age
        user.age = converter.convert(ageInput);

        // 4. Validate age
        if (!ageValidator.isValid(user.age)) {
            logger.log("User is not adult");
            return;
        }

        // 5. Validate email
        if (!emailMatcher.match(user.email, confirmEmail)) {
            logger.log("Emails do not match");
            return;
        }

        // 6. Combine name
        String fullName = combiner.combine(user.firstName, user.lastName);

        // 7. Calculate points
        int totalPoints = calculator.add(100, 50);

        // 8. Log output
        logger.log("User Registered Successfully!");
        logger.log("Name: " + fullName);
        logger.log("Email: " + user.email);
        logger.log("Age: " + user.age);
        logger.log("Points: " + totalPoints);
    }
}





