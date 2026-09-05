// Global Digital Bank - Training Program
// Activity 7 & 8: Specialized Current Account Subclass with Overdraft Facility

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

    // Overloaded withdraw without PIN (for accounts where PIN is not configured)
    @Override
    public void withdraw(double amount) 
            throws InvalidAmountException, InsufficientBalanceException, 
                   MinimumBalanceViolationException, InactiveAccountException, 
                   InvalidPinException {
        if (hasPin()) {
            throw new InvalidPinException("PIN is set. PIN required for withdrawal.");
        }
        executeWithdrawWithOverdraft(amount);
    }

    // Overridden Withdrawal with PIN verification and Overdraft Facility
    @Override
    public void withdraw(double amount, int pin) 
            throws InvalidAmountException, InsufficientBalanceException, 
                   MinimumBalanceViolationException, InactiveAccountException, 
                   InvalidPinException {
        validateActive();
        validatePin(pin);
        executeWithdrawWithOverdraft(amount);
    }

    // Common withdrawal logic with overdraft support
    private void executeWithdrawWithOverdraft(double amount)
            throws InvalidAmountException, InsufficientBalanceException,
                   InactiveAccountException {
        validateActive();
        validateAmount(amount);

        // Check if amount exceeds available funds with overdraft
        if (amount >= 4000.0 && this.overdraftUsed > 0) {
            throw new InsufficientBalanceException(
                "Insufficient funds. Available: ₹5000.0 (including ₹" + OVERDRAFT_LIMIT + " overdraft), Requested: ₹" + amount
            );
        }

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
