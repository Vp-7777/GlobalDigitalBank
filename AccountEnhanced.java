// Global Digital Bank - Training Program
// Activity 3 & 4: Enhanced Account Class with Validation, Business Rules, and PIN Security

public class AccountEnhanced {

    // Unique account number
    private int accountNumber;

    // Full name of account holder
    private String name;

    // Age of account holder (must be >= 18)
    private int age;

    // Current balance in account
    private double balance;

    // Type of account ("Savings" or "Current")
    private String accountType;

    // Account status ("Active" or "Inactive")
    private String status;

    // 4-digit security PIN (can be null initially)
    private Integer pin;

    // Helper method to get the minimum opening balance based on account type
    // Savings: ₹500, Current: ₹1000
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

        // Enhancement 1: Age Validation (must be >= 18)
        if (age < 18) {
            this.age = 18; // Auto-correct to 18 if under 18
        } else {
            this.age = age;
        }

        // Enhancement 2: Account Type Validation (only "Savings" or "Current")
        if ("Current".equalsIgnoreCase(accountType)) {
            this.accountType = "Current";
        } else {
            this.accountType = "Savings"; // Default to "Savings" if invalid
        }

        // Enhancement 3: Minimum Balance Rules on Creation
        // Savings requires min ₹500, Current requires min ₹1000
        double minBalance = getMinimumBalance();
        if (initialBalance < minBalance) {
            this.balance = minBalance; // Auto-set to minimum balance
        } else {
            this.balance = initialBalance;
        }

        // Default status is Active and PIN is not set yet (null)
        this.status = "Active";
        this.pin = null;
    }

    // Enhancement 5: Account Status Management

    // Closes the account by setting status to "Inactive"
    // Returns true if closed, false if already closed
    public boolean closeAccount() {
        if ("Active".equals(this.status)) {
            this.status = "Inactive";
            return true;
        }
        return false;
    }

    // Reopens the account by setting status to "Active"
    // Returns true if reopened, false if already active
    public boolean reopenAccount() {
        if ("Inactive".equals(this.status)) {
            this.status = "Active";
            return true;
        }
        return false;
    }

    // Enhancement 6: PIN Protection Methods

    // Sets a 4-digit PIN (1000 to 9999)
    // Returns true if valid 4-digit number, false otherwise
    public boolean setPin(int pin) {
        if (pin >= 1000 && pin <= 9999) {
            this.pin = pin;
            return true;
        }
        return false;
    }

    // Verifies if the provided PIN matches the saved PIN
    public boolean verifyPin(int pin) {
        if (this.pin != null && this.pin == pin) {
            return true;
        }
        return false;
    }

    // Checks if a PIN has been set for this account
    public boolean hasPin() {
        return this.pin != null;
    }

    // Deposit money with status check
    // Returns false if account is inactive or amount <= 0
    public boolean deposit(double amount) {
        // Account must be active to deposit
        if (!"Active".equals(this.status)) {
            return false;
        }

        // Amount must be positive
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    // Enhancement 4 & 6: Withdraw with PIN verification and balance check
    // Returns true if successful, false otherwise
    public boolean withdraw(double amount, int pin) {
        // 1. Check account is active
        if (!"Active".equals(this.status)) {
            return false;
        }

        // 2. Verify PIN
        if (!verifyPin(pin)) {
            return false;
        }

        // 3. Amount must be positive
        if (amount <= 0) {
            return false;
        }

        // 4. Balance check (cannot overdraw)
        if (this.balance - amount < 0) {
            return false;
        }

        // Deduct and return success
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
