import java.util.ArrayList;
import java.util.List;


record Product(
        String name,
        String category,
        double price,
        boolean prime,
        int stock,
        double rating
) {}

public class AmazonBackendWithoutInterfaces {

    public static void main(String[] args) {

        List<Product> allProducts = new ArrayList<>();

        allProducts.add(new Product(
                "iPhone 17",
                "Electronics",
                1499.99,
                true,
                5,
                4.9
        ));

        allProducts.add(new Product(
                "Gaming Chair",
                "Furniture",
                249.99,
                false,
                12,
                4.3
        ));

        allProducts.add(new Product(
                "USB Cable",
                "Electronics",
                9.99,
                true,
                100,
                4.1
        ));

        allProducts.add(new Product(
                "Office Desk",
                "Furniture",
                399.99,
                true,
                3,
                4.7
        ));

        allProducts.add(new Product(
                "Laptop",
                "Electronics",
                2199.99,
                false,
                2,
                4.8
        ));

        // =====================================================
        // PRIME PRODUCTS LIST
        // =====================================================

        List<Product> primeProducts = new ArrayList<>();

        for (Product product : allProducts) {

            if (product.prime()) {
                System.out.println(product);
                primeProducts.add(product);
            }
        }

        // Simulate sending to frontend/UI
        System.out.println("===== PRIME PRODUCTS =====");
        System.out.println(primeProducts);

        System.out.println();

        // =====================================================
        // ELECTRONICS PRODUCTS LIST
        // =====================================================

        List<Product> electronicProducts = new ArrayList<>();

        for (Product product : allProducts) {

            if (product.category().equals("Electronics")) {
                System.out.println(product);
                electronicProducts.add(product);
            }
        }

        System.out.println("===== ELECTRONICS =====");
        System.out.println(electronicProducts);

        System.out.println();

        // =====================================================
        // CHEAP PRODUCTS LIST
        // =====================================================

        List<Product> cheapProducts = new ArrayList<>();

        for (Product product : allProducts) {

            if (product.price() < 100) {

                cheapProducts.add(product);
            }
        }

        System.out.println("===== CHEAP PRODUCTS =====");
        System.out.println(cheapProducts);

        System.out.println();

        // =====================================================
        // HIGH RATED PRODUCTS LIST
        // =====================================================

        List<Product> highRatedProducts = new ArrayList<>();

        for (Product product : allProducts) {

            if (product.rating() >= 4.5) {

                highRatedProducts.add(product);
            }
        }

        System.out.println("===== HIGH RATED PRODUCTS =====");
        System.out.println(highRatedProducts);

        System.out.println();

        // =====================================================
        // LOW STOCK PRODUCTS LIST
        // =====================================================

        List<Product> lowStockProducts = new ArrayList<>();

        for (Product product : allProducts) {

            if (product.stock() <= 5) {

                lowStockProducts.add(product);
            }
        }

        System.out.println("===== LOW STOCK PRODUCTS =====");
        System.out.println(lowStockProducts);
    }
}



// =============================================================
       FULL AMAZON BACKEND USING ANONYMOUS CLASSES 
//=============================================================

import java.util.ArrayList;
import java.util.List;


// =============================================================
// FUNCTIONAL INTERFACE
// =============================================================

interface ProductFilter {

    boolean test(Product product);
}


public class AmazonBackendAnonymousClass {

// =========================================================
    // REUSABLE FILTER ENGINE
    // =========================================================

    static List<Product> filterProducts(
            List<Product> products,
            ProductFilter filter
    ) {

        List<Product> filtered = new ArrayList<>();

        for (Product product : products) {

            if (filter.test(product)) {

                filtered.add(product);
            }
        }

        return filtered;
    }

    public static void main(String[] args) 


        // =====================================================
        // PRIME PRODUCTS
        // =====================================================

        List<Product> primeProducts =
                filterProducts(
                        allProducts,

                        new ProductFilter() {

                            @Override
                            public boolean test(Product product) {

                                return product.prime();
                            }
                        }
                );

        System.out.println("===== PRIME PRODUCTS =====");
        System.out.println(primeProducts);

        System.out.println();

        // =====================================================
        // ELECTRONICS PRODUCTS
        // =====================================================

        List<Product> electronicProducts =
                filterProducts(
                        allProducts,

                        new ProductFilter() {

                            @Override
                            public boolean test(Product product) {

                                return product.category()
                                        .equals("Electronics");
                            }
                        }
                );

        System.out.println("===== ELECTRONICS =====");
        System.out.println(electronicProducts);

        System.out.println();

        // =====================================================
        // CHEAP PRODUCTS
        // =====================================================

        List<Product> cheapProducts =
                filterProducts(
                        allProducts,

                        new ProductFilter() {

                            @Override
                            public boolean test(Product product) {

                                return product.price() < 100;
                            }
                        }
                );

        System.out.println("===== CHEAP PRODUCTS =====");
        System.out.println(cheapProducts);

        System.out.println();

        // =====================================================
        // HIGH RATED PRODUCTS
        // =====================================================

        List<Product> highRatedProducts =
                filterProducts(
                        allProducts,

                        new ProductFilter() {

                            @Override
                            public boolean test(Product product) {

                                return product.rating() >= 4.5;
                            }
                        }
                );

        System.out.println("===== HIGH RATED PRODUCTS =====");
        System.out.println(highRatedProducts);
    }

    
// =============================================================
         FULL AMAZON BACKEND — USING LAMBDAS
// =============================================================

import java.util.ArrayList;
import java.util.List;

// =============================================================
// FUNCTIONAL INTERFACE
// =============================================================

interface ProductFilter {
    boolean test(Product product);
}


public class AmazonBackendLambda {
    // =========================================================
    // REUSABLE FILTER ENGINE
    // =========================================================

    static List<Product> filterProducts(
            List<Product> products,
            ProductFilter filter
    ) {

        List<Product> filtered = new ArrayList<>();

        for (Product product : products) {

            if (filter.test(product)) {

                filtered.add(product);
            }
        }

        return filtered;
    }


    public static void main(String[] args) {


        // =====================================================
        // PRIME PRODUCTS
        // =====================================================

        List<Product> primeProducts =
                filterProducts(
                        allProducts,
                        product -> product.prime()
                );

        System.out.println("===== PRIME PRODUCTS =====");
        System.out.println(primeProducts);

        System.out.println();

        // =====================================================
        // ELECTRONICS PRODUCTS
        // =====================================================

        List<Product> electronics =
                filterProducts(
                        allProducts,
                        product ->
                                product.category()
                                       .equals("Electronics")
                );

        System.out.println("===== ELECTRONICS =====");
        System.out.println(electronics);

        System.out.println();

        // =====================================================
        // CHEAP PRODUCTS
        // =====================================================

        List<Product> cheapProducts =
                filterProducts(
                        allProducts,
                        product -> product.price() < 100
                );

        System.out.println("===== CHEAP PRODUCTS =====");
        System.out.println(cheapProducts);

        System.out.println();

        // =====================================================
        // HIGH RATED PRODUCTS
        // =====================================================

        List<Product> highRatedProducts =
                filterProducts(
                        allProducts,
                        product -> product.rating() >= 4.5
                );

        System.out.println("===== HIGH RATED PRODUCTS =====");
        System.out.println(highRatedProducts);

        System.out.println();

        // =====================================================
        // LOW STOCK PRODUCTS
        // =====================================================

        List<Product> lowStockProducts =
                filterProducts(
                        allProducts,
                        product -> product.stock() <= 5
                );

        System.out.println("===== LOW STOCK PRODUCTS =====");
        System.out.println(lowStockProducts);
    }

    
}








import java.util.ArrayList;
import java.util.List;

public class AmazonBackendMethodReference {

    public static void main(String[] args) {

        // =====================================================
        // PRIME PRODUCTS
        // Product::prime
        // Equivalent Lambda:
        // product -> product.prime()
        // =====================================================

        List<Product> primeProducts =
                filterProducts(
                        allProducts,
                        Product::prime
                );

        System.out.println("===== PRIME PRODUCTS =====");

        System.out.println(primeProducts);

        System.out.println();

        // =====================================================
        // ELECTRONICS PRODUCTS
        // ProductUtils::isElectronics
        // Equivalent Lambda:
        // product -> ProductUtils.isElectronics(product)
        // =====================================================

        List<Product> electronicsProducts =
                filterProducts(
                        allProducts,
                        ProductUtils::isElectronics
                );

        System.out.println("===== ELECTRONICS PRODUCTS =====");

        System.out.println(electronicsProducts);

        System.out.println();

        // =====================================================
        // CHEAP PRODUCTS
        // ProductUtils::isCheap
        // Equivalent Lambda:
        // product -> ProductUtils.isCheap(product)
        // =====================================================

        List<Product> cheapProducts =
                filterProducts(
                        allProducts,
                        ProductUtils::isCheap
                );

        System.out.println("===== CHEAP PRODUCTS =====");

        System.out.println(cheapProducts);

        System.out.println();

        // =====================================================
        // HIGH RATED PRODUCTS
        // ProductUtils::isHighlyRated
        // Equivalent Lambda:
        // product -> ProductUtils.isHighlyRated(product)
        // =====================================================

        List<Product> highRatedProducts =
                filterProducts(
                        allProducts,
                        ProductUtils::isHighlyRated
                );

        System.out.println("===== HIGH RATED PRODUCTS =====");

        System.out.println(highRatedProducts);

        System.out.println();

        // =====================================================
        // LOW STOCK PRODUCTS
        // ProductUtils::isLowStock
        // Equivalent Lambda:
        // product -> ProductUtils.isLowStock(product)
        // =====================================================

        List<Product> lowStockProducts =
                filterProducts(
                        allProducts,
                        ProductUtils::isLowStock
                );

        System.out.println("===== LOW STOCK PRODUCTS =====");

        System.out.println(lowStockProducts);
    }

// =============================================================
// UTILITY METHODS
// =============================================================

class ProductUtils {

    // =========================================================
    // ELECTRONICS FILTER
    // =========================================================

    static boolean isElectronics(Product product) {

        return product.category()
                      .equals("Electronics");
    }

    // =========================================================
    // CHEAP PRODUCT FILTER
    // =========================================================

    static boolean isCheap(Product product) {

        return product.price() < 100;
    }

    // =========================================================
    // HIGH RATED FILTER
    // =========================================================

    static boolean isHighlyRated(Product product) {

        return product.rating() >= 4.5;
    }

    // =========================================================
    // LOW STOCK FILTER
    // =========================================================

    static boolean isLowStock(Product product) {

        return product.stock() <= 5;
    }
}