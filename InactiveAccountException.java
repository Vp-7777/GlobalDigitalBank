// Global Digital Bank - Training Program
// Activity 5: Custom Exception Classes

// Thrown when operation is attempted on an inactive account
public class InactiveAccountException extends AccountException {

    public InactiveAccountException(String message) {
        super(message);
    }
}
