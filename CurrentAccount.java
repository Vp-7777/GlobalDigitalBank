// Global Digital Bank - Training Program
// Activity 7: Specialized Account Subclasses (Inheritance & Polymorphism)

public class CurrentAccount extends Account {

    // Constants
    public static final double MINIMUM_BALANCE = 1000.0;
    public static final String ACCOUNT_TYPE = "Current";
    public static final double OVERDRAFT_LIMIT = 5000.0;

    // Instance Fields
    private double overdraftUsed;

    // Constructor
    public CurrentAccount(int accountNumber, String name, int age, double initialBalance) {
        super(accountNumber, name, age, initialBalance);
        this.overdraftUsed = 0.0;
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

    // Overridden Withdrawal supporting Overdraft Facility
    @Override
    public void withdraw(double amount, int pin) 
            throws InvalidAmountException, InsufficientBalanceException, 
                   MinimumBalanceViolationException, InactiveAccountException, 
                   InvalidPinException {
        // Parent validations
        validateActive();
        validatePin(pin);
        validateAmount(amount);

        // Check available funds including remaining overdraft
        double availableBalance = getBalance() - getMinimumBalance() + OVERDRAFT_LIMIT - overdraftUsed;
        if (amount > availableBalance) {
            throw new InsufficientBalanceException(
                "Insufficient funds. Available: ₹" + availableBalance +
                " (including ₹" + OVERDRAFT_LIMIT + " overdraft), Requested: ₹" + amount
            );
        }

        // Apply overdraft if balance falls below minimum required
        double newBalance = getBalance() - amount;
        if (newBalance < getMinimumBalance()) {
            double overdraftAmount = getMinimumBalance() - newBalance;
            this.overdraftUsed += overdraftAmount;
        }

        setBalance(newBalance);
    }

    // Current-Specific Methods
    public double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }

    public double getOverdraftUsed() {
        return overdraftUsed;
    }

    public double getAvailableOverdraft() {
        return OVERDRAFT_LIMIT - overdraftUsed;
    }

    public boolean isUsingOverdraft() {
        return overdraftUsed > 0;
    }

    // Repays used overdraft funds
    public void repayOverdraft(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Repayment amount must be positive");
        }
        if (amount > this.overdraftUsed) {
            throw new IllegalArgumentException("Amount exceeds overdraft used (₹" + this.overdraftUsed + ")");
        }

        this.overdraftUsed -= amount;
        setBalance(getBalance() + amount);
    }
}
