// Global Digital Bank - Training Program
// Activity 5: Custom Exception Classes

// Thrown when PIN is incorrect or not set
public class InvalidPinException extends AccountException {

    public InvalidPinException(String message) {
        super(message);
    }
}
