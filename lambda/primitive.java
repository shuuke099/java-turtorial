import java.util.function.*;

public class Main {
    public static void main(String[] args) {

        double price = 99.99;
        int quantity = 2;
        double discount = 10.5;

        // 🟡 1. BooleanSupplier → fraud check
        BooleanSupplier fraudCheck = () -> Math.random() > 0.2;

        // 🟢 2. DoubleBinaryOperator → total = price * quantity
        DoubleBinaryOperator totalCalculator =
                (p, q) -> p * q;

        // 🟢 3. DoubleUnaryOperator → apply discount
        DoubleUnaryOperator applyDiscount =
                total -> total - discount;

        // 🟢 4. DoublePredicate → validate total
        DoublePredicate isValid =
                total -> total > 50;

        // 🟢 5. DoubleToIntFunction → convert to int (e.g. points)
        DoubleToIntFunction toPoints =
                total -> (int) total;

        // 🟢 6. ObjDoubleConsumer → log result
        ObjDoubleConsumer<String> logger =
                (msg, value) -> System.out.println(msg + ": " + value);

        // 🔄 FLOW

        if (!fraudCheck.getAsBoolean()) {
            System.out.println("Fraud detected ❌");
            return;
        }

        double total = totalCalculator.applyAsDouble(price, quantity);

        total = applyDiscount.applyAsDouble(total);

        if (!isValid.test(total)) {
            System.out.println("Order too small ❌");
            return;
        }

        int points = toPoints.applyAsInt(total);

        logger.accept("Final Total", total);
        logger.accept("Reward Points", points);
    }
}






import java.util.function.*;

public class Main {
    public static void main(String[] args) {

        int price = 100;
        int quantity = 2;
        int discount = 10;

        // 🟡 1. BooleanSupplier (same)
        BooleanSupplier fraudCheck = () -> Math.random() > 0.2;

        // 🟢 2. IntBinaryOperator → total = price * quantity
        IntBinaryOperator totalCalculator =
                (p, q) -> p * q;

        // 🟢 3. IntUnaryOperator → apply discount
        IntUnaryOperator applyDiscount =
                total -> total - discount;

        // 🟢 4. IntPredicate → validate total
        IntPredicate isValid =
                total -> total > 50;

        // 🟢 5. IntUnaryOperator → points (no conversion needed)
        IntUnaryOperator toPoints =
                total -> total;

        // 🟢 6. ObjIntConsumer → log
        ObjIntConsumer<String> logger =
                (msg, value) -> System.out.println(msg + ": " + value);

        // 🔄 FLOW

        if (!fraudCheck.getAsBoolean()) {
            System.out.println("Fraud detected ❌");
            return;
        }

        int total = totalCalculator.applyAsInt(price, quantity);

        total = applyDiscount.applyAsInt(total);

        if (!isValid.test(total)) {
            System.out.println("Order too small ❌");
            return;
        }

        int points = toPoints.applyAsInt(total);

        logger.accept("Final Total", total);
        logger.accept("Reward Points", points);
    }
}