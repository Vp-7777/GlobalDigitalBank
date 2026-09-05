// Global Digital Bank - Training Program
// Activity 1: Creating the Account Class (Entity/Model Class)
// Continuation in Activity 5: Introducing Exceptions in the Account Class
// Continuation in Activity 7: Creating Account Subclasses (Converting to Abstract Class)

public abstract class Account {

    // Constants (Added in Activity 5)
    public static final int MIN_AGE = 18;
    public static final int MIN_PIN = 1000;
    public static final int MAX_PIN = 9999;

    // Private Fields
    private int accountNumber;
    private String name;
    private int age;
    protected double balance; // Protected so specialized subclasses can access/update
    private String status;
    private Integer pin;

    // Abstract Methods (Introduced in Activity 7 for Polymorphism)
    public abstract double getMinimumBalance();
    public abstract String getAccountType();

    // Constructor (Activity 7: 4 parameters, delegates min balance & type to subclass)
    public Account(int accountNumber, String name, int age, double initialBalance) {
        // Validate age (must be at least 18)
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("Customer must be at least " + MIN_AGE + " years old. Provided: " + age);
        }

        // Initialize core fields before polymorphic balance validation
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.status = "Active";
        this.pin = null;
        this.balance = initialBalance;

        // Validate minimum balance (delegated to subclass via abstract getMinimumBalance())
        double minBalance = getMinimumBalance();
        if (initialBalance < minBalance) {
            throw new IllegalArgumentException(getAccountType() + " account requires minimum balance of ₹" + minBalance + ". Provided: ₹" + initialBalance);
        }
    }

    // Factory method for backward-compatible instantiation in earlier test suites
    public static Account create(int accountNumber, String name, int age, double initialBalance, String accountType) {
        if ("Savings".equalsIgnoreCase(accountType)) {
            return new SavingsAccount(accountNumber, name, age, initialBalance);
        } else if ("Current".equalsIgnoreCase(accountType)) {
            return new CurrentAccount(accountNumber, name, age, initialBalance);
        } else {
            throw new IllegalArgumentException("Account type must be 'Savings' or 'Current'. Provided: " + accountType);
        }
    }

    // Deposits money into the account
    public void deposit(double amount) throws InvalidAmountException, InactiveAccountException {
        validateActive();

        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive. Provided: ₹" + amount);
        }

        this.balance += amount;
    }

    // Overloaded withdrawal without PIN (for backward compatibility)
    public void withdraw(double amount) 
            throws InvalidAmountException, InsufficientBalanceException, 
                   MinimumBalanceViolationException, InactiveAccountException, 
                   InvalidPinException {
        if (hasPin()) {
            throw new InvalidPinException("PIN is set. PIN required for withdrawal.");
        }
        performWithdrawal(amount);
    }

    // Standard withdrawal with PIN verification
    public void withdraw(double amount, int pin) 
            throws InvalidAmountException, InsufficientBalanceException, 
                   MinimumBalanceViolationException, InactiveAccountException, 
                   InvalidPinException {
        validateActive();
        validatePin(pin);
        validateAmount(amount);
        performWithdrawal(amount);
    }

    // Common withdrawal execution helper
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

    // Protected Validation Helpers for Subclasses
    protected void validateActive() throws InactiveAccountException {
        if (!"Active".equals(this.status)) {
            throw new InactiveAccountException("Account is inactive. Please reopen the account or contact support.");
        }
    }

    protected void validatePin(int pin) throws InvalidPinException {
        if (!hasPin()) {
            throw new InvalidPinException("PIN not set for this account");
        }
        if (!verifyPin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }
    }

    protected void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive. Provided: ₹" + amount);
        }
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    // Account Status Management
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

    // PIN Management
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

    // Getters
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

    public String getStatus() {
        return status;
    }
}
