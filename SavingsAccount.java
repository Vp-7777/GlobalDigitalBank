// Global Digital Bank - Training Program
// Activity 7: Specialized Account Subclasses (Inheritance & Polymorphism)

public class SavingsAccount extends Account {

    // Constants
    public static final double MINIMUM_BALANCE = 500.0;
    public static final String ACCOUNT_TYPE = "Savings";
    public static final double INTEREST_RATE = 4.0; // 4% per annum

    // Constructor calling parent constructor
    public SavingsAccount(int accountNumber, String name, int age, double initialBalance) {
        super(accountNumber, name, age, initialBalance);
    }

    // Abstract Method Implementations
    @Override
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    @Override
    public String getAccountType() {
        return ACCOUNT_TYPE;
    }

    // Calculates annual simple interest
    public double calculateInterest(int years) {
        if (years < 0) {
            throw new IllegalArgumentException("Years must be non-negative");
        }
        return getBalance() * (INTEREST_RATE / 100.0) * years;
    }

    public double getInterestRate() {
        return INTEREST_RATE;
    }
}
