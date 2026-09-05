// Global Digital Bank - Training Program
// Activity 5: Custom Exception Classes

// Thrown when withdrawal would violate minimum balance requirement
public class MinimumBalanceViolationException extends AccountException {

    public MinimumBalanceViolationException(String message) {
        super(message);
    }
}
