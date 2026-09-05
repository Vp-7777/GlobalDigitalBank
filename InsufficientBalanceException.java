// Global Digital Bank - Training Program
// Activity 5: Custom Exception Classes

// Thrown when attempting to withdraw more than available balance
public class InsufficientBalanceException extends AccountException {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
