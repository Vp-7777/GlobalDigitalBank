// Global Digital Bank - Training Program
// Activity 3: Enhancing the Account Class (Validation, Business Rules, and PIN Security)

public class AccountEnhanced {

    // Private Fields
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;

    // Helper to determine minimum opening balance
    private double getMinimumBalance() {
        if ("Current".equalsIgnoreCase(this.accountType)) {
            return 1000.0;
        }
        return 500.0;
    }

    // Constructor with self-correcting validation rules
    public AccountEnhanced(int accountNumber, String name, int age, double initialBalance, String accountType) {
        this.accountNumber = accountNumber;
        this.name = name;

        // Auto-correct age to 18 if under 18
        if (age < 18) {
            this.age = 18;
        } else {
            this.age = age;
        }

        // Validate account type (default to "Savings" if invalid)
        if ("Current".equalsIgnoreCase(accountType)) {
            this.accountType = "Current";
        } else {
            this.accountType = "Savings";
        }

        // Enforce minimum initial balance
        double minBalance = getMinimumBalance();
        if (initialBalance < minBalance) {
            this.balance = minBalance;
        } else {
            this.balance = initialBalance;
        }

        this.status = "Active";
        this.pin = null;
    }

    // Account Status Management
    public boolean closeAccount() {
        if ("Active".equals(this.status)) {
            this.status = "Inactive";
            return true;
        }
        return false;
    }

    public boolean reopenAccount() {
        if ("Inactive".equals(this.status)) {
            this.status = "Active";
            return true;
        }
        return false;
    }

    // PIN Management
    public boolean setPin(int pin) {
        if (pin >= 1000 && pin <= 9999) {
            this.pin = pin;
            return true;
        }
        return false;
    }

    public boolean verifyPin(int pin) {
        return this.pin != null && this.pin == pin;
    }

    public boolean hasPin() {
        return this.pin != null;
    }

    // Deposit operation with active status validation
    public boolean deposit(double amount) {
        if (!"Active".equals(this.status)) {
            return false;
        }

        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    // Withdraw operation with PIN check and balance enforcement
    public boolean withdraw(double amount, int pin) {
        if (!"Active".equals(this.status)) {
            return false;
        }

        if (!verifyPin(pin)) {
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        if (this.balance - amount < 0) {
            return false;
        }

        this.balance -= amount;
        return true;
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

    public String getAccountType() {
        return accountType;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        if (age < 18) {
            this.age = 18;
        } else {
            this.age = age;
        }
    }
}
