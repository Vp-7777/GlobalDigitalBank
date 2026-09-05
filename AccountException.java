// Global Digital Bank - Training Program
// Activity 5: Custom Exception Classes

// Base exception for all account-related errors
public class AccountException extends Exception {

    public AccountException(String message) {
        super(message);
    }
}
