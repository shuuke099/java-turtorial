import java.time.LocalDate;
import java.util.UUID;

public interface PaymentProcessor{
    // Constants 
    String SYSTEM_NAME = "Zeila Payment GateWay";
    int MAX_RETRY_ATTEMPS = 3;

    // ABASTRACT METHODS
    String processPayment(Double amount , String currency, String userId);
    
}