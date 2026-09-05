// Global Digital Bank - Training Program
// Activity 5: Custom Exception Classes

// Thrown when an invalid amount is provided (negative or zero)
public class InvalidAmountException extends AccountException {

    public InvalidAmountException(String message) {
        super(message);
    }
}
