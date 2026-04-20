java.util.concurrent.Callable
public interface Callable<V> {
    V call() throws Exception;
}

public class Phase2_Base {

    public static String serveCustomer(String name) {
        System.out.println("➡️ Serving " + name + " on " + Thread.currentThread().getName());
        work();
        return "✅ Done with " + name;
    }

    public static void work() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
    }
}





import java.util.concurrent.*;

class CustomerCallable implements Callable<String> {

    private String name;

    public CustomerCallable(String name) {
        this.name = name;
    }

    @Override
    public String call() {
        return Phase2_Base.serveCustomer(name);
    }
}

public class Stage1_Callable_Class {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<String> f1 = executor.submit(new CustomerCallable("Ali"));
        Future<String> f2 = executor.submit(new CustomerCallable("Fatima"));
        Future<String> f3 = executor.submit(new CustomerCallable("Hassan"));

        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());

        executor.shutdown();
    }
}


public class Stage1_Callable_Class {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<String> f1 = executor.submit(new CustomerCallable("Ali"));
        Future<String> f2 = executor.submit(new CustomerCallable("Fatima"));
        Future<String> f3 = executor.submit(new CustomerCallable("Hassan"));

        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());

        executor.shutdown();
    }
}

// ➡️ Serving Ali on pool-1-thread-1
// ➡️ Serving Fatima on pool-1-thread-2
// ➡️ Serving Hassan on pool-1-thread-3
// ✅ Done with Ali
// ✅ Done with Fatima
// ✅ Done with Hassan


ExecutorService executor = Executors.newFixedThreadPool(3);

Future<String> f1 = executor.submit(new Callable<String>() {
    public String call() {
        return Phase2_Base.serveCustomer("Ali");
    }
});


ExecutorService executor = Executors.newFixedThreadPool(3);

Future<String> f1 = executor.submit(() -> Phase2_Base.serveCustomer("Ali"));
Future<String> f2 = executor.submit(() -> Phase2_Base.serveCustomer("Fatima"));
Future<String> f3 = executor.submit(() -> Phase2_Base.serveCustomer("Hassan"));

System.out.println(f1.get());
System.out.println(f2.get());
System.out.println(f3.get());

executor.shutdown();



Callable<String> c = () -> Phase2_Base.serveCustomer("Ali");

// Or


class CustomerService {

    public static String serveAli() {
        return Phase2_Base.serveCustomer("Ali");
    }
}


ExecutorService executor = Executors.newFixedThreadPool(2);

Future<String> f = executor.submit(CustomerService::serveAli);

System.out.println(f.get());

executor.shutdown();