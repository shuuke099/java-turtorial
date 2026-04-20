package marketplace.app;

import marketplace.dto.*;
import marketplace.service.*;
import marketplace.pricing.*;

import java.util.List;

public class MarketplaceApp {

    public static void main(String[] args) {

        OrderProcessor processor = new OrderProcessor();

        ProductData laptop = new ProductData("P1", "Laptop", 1000);
        ProductData ebook = new ProductData("P2", "Ebook", 50);
        ProductData service = new ProductData("P3", "Consulting", 200);

        Order order = new Order(
                "ORD-1001",
                List.of(
                        new OrderItem(laptop, 1),
                        new OrderItem(ebook, 2),
                        new OrderItem(service, 1)
                )
        );

        processor.process(order, DiscountStrategy.BLACK_FRIDAY);
    }
}