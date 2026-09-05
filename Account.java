// Global Digital Bank - Training Program
// Activity 1: Creating the Account Class (Entity/Model Class)
// Continuation & Enhancement in Activity 5: Introducing Exceptions in the Account Class

public class Account {

    // Constants (Added in Activity 5)
    public static final double MIN_BALANCE_SAVINGS = 500.0;
    public static final double MIN_BALANCE_CURRENT = 1000.0;
    public static final int MIN_AGE = 18;
    public static final int MIN_PIN = 1000;
    public static final int MAX_PIN = 9999;

    // Private Fields (Created in Activity 1, Extended in Activity 5)
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin; // Added in Activity 5 for PIN security

    // Constructor (Activity 1, enhanced with validation exceptions in Activity 5)
    public Account(int accountNumber, String name, int age, double initialBalance, String accountType) {
        // Validate age (must be at least 18)
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("Customer must be at least " + MIN_AGE + " years old. Provided: " + age);
        }

        // Validate account type ("Savings" or "Current")
        if (!"Savings".equalsIgnoreCase(accountType) && !"Current".equalsIgnoreCase(accountType)) {
            throw new IllegalArgumentException("Account type must be 'Savings' or 'Current'. Provided: " + accountType);
        }

        // Standardize account type name
        this.accountType = "Current".equalsIgnoreCase(accountType) ? "Current" : "Savings";

        // Validate initial deposit against required minimum balance
        double minBalance = getMinimumBalance();
        if (initialBalance < minBalance) {
            throw new IllegalArgumentException(this.accountType + " account requires minimum balance of ₹" + minBalance + ". Provided: ₹" + initialBalance);
        }

        // Initialize state
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = initialBalance;
        this.status = "Active";
        this.pin = null;
    }

    // Deposits money into the account (Enhanced in Activity 5 to throw exceptions)
    public void deposit(double amount) throws InvalidAmountException, InactiveAccountException {
        validateActive();

        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive. Provided: ₹" + amount);
        }

        this.balance += amount;
    }

    // Overloaded withdrawal without PIN (for backward compatibility with Activity 1 & 2 tests)
    public void withdraw(double amount) 
            throws InvalidAmountException, InsufficientBalanceException, 
                   MinimumBalanceViolationException, InactiveAccountException, 
                   InvalidPinException {
        if (hasPin()) {
            throw new InvalidPinException("PIN is set. PIN required for withdrawal.");
        }
        performWithdrawal(amount);
    }

    // Withdraws money with PIN verification (Introduced in Activity 5)
    public void withdraw(double amount, int pin) 
            throws InvalidAmountException, InsufficientBalanceException, 
                   MinimumBalanceViolationException, InactiveAccountException, 
                   InvalidPinException {
        validateActive();

        if (!hasPin()) {
            throw new InvalidPinException("PIN not set for this account");
        }

        if (!verifyPin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }

        performWithdrawal(amount);
    }

    // Common withdrawal helper handling business rules
    private void performWithdrawal(double amount) 
            throws InvalidAmountException, InsufficientBalanceException, 
                   MinimumBalanceViolationException, InactiveAccountException {
        validateActive();

        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive. Provided: ₹" + amount);
        }

        if (amount > this.balance) {
            throw new InsufficientBalanceException("Insufficient balance. Available: ₹" + this.balance + ", Requested: ₹" + amount);
        }

        double minBalance = getMinimumBalance();
        if (this.balance - amount < minBalance) {
            throw new MinimumBalanceViolationException("Cannot withdraw. Minimum balance of ₹" + minBalance + " required. Available after withdrawal: ₹" + (this.balance - amount));
        }

        this.balance -= amount;
    }

    // Account Status Management (Added in Activity 5)
    public void closeAccount() {
        if ("Inactive".equals(this.status)) {
            throw new IllegalStateException("Account #" + this.accountNumber + " is already closed.");
        }
        this.status = "Inactive";
    }

    public void reopenAccount() {
        if ("Active".equals(this.status)) {
            throw new IllegalStateException("Account #" + this.accountNumber + " is already active.");
        }
        this.status = "Active";
    }

    // PIN Management (Added in Activity 5)
    public void setPin(int pin) {
        if (pin < MIN_PIN || pin > MAX_PIN) {
            throw new IllegalArgumentException("PIN must be a 4-digit number between " + MIN_PIN + " and " + MAX_PIN + ". Provided: " + pin);
        }
        this.pin = pin;
    }

    public boolean verifyPin(int pin) {
        return this.pin != null && this.pin == pin;
    }

    public boolean hasPin() {
        return this.pin != null;
    }

    // Helper Methods
    public double getMinimumBalance() {
        if ("Current".equalsIgnoreCase(this.accountType)) {
            return MIN_BALANCE_CURRENT;
        }
        return MIN_BALANCE_SAVINGS;
    }

    public void validateActive() throws InactiveAccountException {
        if (!"Active".equals(this.status)) {
            throw new InactiveAccountException("Account is inactive. Please reopen the account or contact support.");
        }
    }

    // Getters (Activity 1)
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getStatus() {
        return status;
    }
}
