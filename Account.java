// Global Digital Bank - Training Program
// Activity 5 & 6: Account Class with Exception Handling

public class Account {

    // ===== Constants =====
    public static final double MIN_BALANCE_SAVINGS = 500.0;
    public static final double MIN_BALANCE_CURRENT = 1000.0;
    public static final int MIN_AGE = 18;
    public static final int MIN_PIN = 1000;
    public static final int MAX_PIN = 9999;

    // ===== Private Fields =====
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;

    // ===== Constructor =====
    public Account(int accountNumber, String name, int age, double initialBalance, String accountType) {
        // Validate age (must be >= 18)
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("Customer must be at least " + MIN_AGE + " years old. Provided: " + age);
        }

        // Validate account type (must be "Savings" or "Current")
        if (!"Savings".equalsIgnoreCase(accountType) && !"Current".equalsIgnoreCase(accountType)) {
            throw new IllegalArgumentException("Account type must be 'Savings' or 'Current'. Provided: " + accountType);
        }

        // Standardize account type casing
        this.accountType = "Current".equalsIgnoreCase(accountType) ? "Current" : "Savings";

        // Validate minimum balance based on account type
        double minBalance = getMinimumBalance();
        if (initialBalance < minBalance) {
            throw new IllegalArgumentException(this.accountType + " account requires minimum balance of ₹" + minBalance + ". Provided: ₹" + initialBalance);
        }

        // Initialize fields
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = initialBalance;
        this.status = "Active";
        this.pin = null;
    }

    // ===== Business Methods =====

    // Deposits money into the account
    public void deposit(double amount) throws InvalidAmountException, InactiveAccountException {
        // Check if account is active
        validateActive();

        // Check if amount is positive
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive. Provided: ₹" + amount);
        }

        // Add amount to balance
        this.balance += amount;
    }

    // Overloaded withdraw without PIN (for accounts where PIN has not been set)
    public void withdraw(double amount) throws InvalidAmountException, 
                                               InsufficientBalanceException, 
                                               MinimumBalanceViolationException, 
                                               InactiveAccountException, 
                                               InvalidPinException {
        if (hasPin()) {
            throw new InvalidPinException("PIN is set. PIN required for withdrawal.");
        }
        performWithdrawal(amount);
    }

    // Withdraws money with PIN verification
    public void withdraw(double amount, int pin) throws InvalidAmountException, 
                                                        InsufficientBalanceException, 
                                                        MinimumBalanceViolationException, 
                                                        InactiveAccountException, 
                                                        InvalidPinException {
        // 1. Check if account is active
        validateActive();

        // 2. Check if PIN is set
        if (!hasPin()) {
            throw new InvalidPinException("PIN not set for this account");
        }

        // 3. Verify PIN
        if (!verifyPin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }

        performWithdrawal(amount);
    }

    // Common withdrawal execution helper
    private void performWithdrawal(double amount) throws InvalidAmountException,
                                                         InsufficientBalanceException,
                                                         MinimumBalanceViolationException,
                                                         InactiveAccountException {
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

    // ===== Account Status Management =====

    // Closes the account
    public void closeAccount() {
        if ("Inactive".equals(this.status)) {
            throw new IllegalStateException("Account #" + this.accountNumber + " is already closed.");
        }
        this.status = "Inactive";
    }

    // Reopens the account
    public void reopenAccount() {
        if ("Active".equals(this.status)) {
            throw new IllegalStateException("Account #" + this.accountNumber + " is already active.");
        }
        this.status = "Active";
    }

    // ===== PIN Management =====

    // Sets a 4-digit PIN
    public void setPin(int pin) {
        if (pin < MIN_PIN || pin > MAX_PIN) {
            throw new IllegalArgumentException("PIN must be a 4-digit number between " + MIN_PIN + " and " + MAX_PIN + ". Provided: " + pin);
        }
        this.pin = pin;
    }

    // Verifies if the PIN matches
    public boolean verifyPin(int pin) {
        return this.pin != null && this.pin == pin;
    }

    // Checks if a PIN is set
    public boolean hasPin() {
        return this.pin != null;
    }

    // ===== Helper Methods =====

    // Returns minimum balance based on account type
    public double getMinimumBalance() {
        if ("Current".equalsIgnoreCase(this.accountType)) {
            return MIN_BALANCE_CURRENT;
        }
        return MIN_BALANCE_SAVINGS;
    }

    // Validates that the account is active
    public void validateActive() throws InactiveAccountException {
        if (!"Active".equals(this.status)) {
            throw new InactiveAccountException("Account is inactive. Please reopen the account or contact support.");
        }
    }

    // ===== Getters =====
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
