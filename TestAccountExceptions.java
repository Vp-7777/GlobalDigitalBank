// Global Digital Bank - Training Program
// Activity 6: Testing the Account Class with Exceptions

public class TestAccountExceptions {

    // Helper method to format account information for display
    // Format: Account #<num> | <name> (<age> yrs) | <type> | ₹<balance> | <status> | PIN: <Yes/No>
    public static String formatAccount(Account account) {
        String pinStatus = account.hasPin() ? "Yes" : "No";
        return "Account #" + account.getAccountNumber() + " | " 
                + account.getName() + " (" + account.getAge() + " yrs) | " 
                + account.getAccountType() + " | ₹" 
                + account.getBalance() + " | " 
                + account.getStatus() + " | PIN: " + pinStatus;
    }

    public static void main(String[] args) {

        // Header
        System.out.println("============================================================");
        System.out.println(" ACCOUNT TEST WITH EXCEPTIONS");
        System.out.println("============================================================");

        // >>> Test 1: Valid Account Creation
        System.out.println(">>> Test 1: Valid Account Creation");
        Account acc1 = null;
        try {
            acc1 = new Account(1001, "John Doe", 25, 1000.0, "Savings");
            System.out.println("SUCCESS: " + formatAccount(acc1));
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 2: Invalid Age (under 18)
        System.out.println("\n>>> Test 2: Invalid Age (under 18)");
        try {
            new Account(1002, "Kid", 16, 500.0, "Savings");
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 3: Invalid Account Type
        System.out.println("\n>>> Test 3: Invalid Account Type");
        try {
            new Account(1003, "User", 25, 500.0, "Invalid");
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 4: Minimum Balance on Creation
        System.out.println("\n>>> Test 4: Minimum Balance on Creation");
        System.out.println("Creating Savings account with ₹300");
        try {
            new Account(1004, "Bob", 25, 300.0, "Savings");
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 5: Valid Deposit and Withdrawal
        System.out.println("\n>>> Test 5: Valid Deposit and Withdrawal");
        Account acc5 = new Account(1005, "Alice Brown", 30, 1000.0, "Current");
        System.out.println("Account: " + formatAccount(acc5));

        try {
            acc5.setPin(1234);
            System.out.println("Setting PIN 1234: SUCCESS");

            acc5.deposit(500.0);
            System.out.println("Depositing ₹500.0: SUCCESS");
            System.out.println("Balance after deposit: ₹" + acc5.getBalance());

            acc5.withdraw(200.0, 1234);
            System.out.println("Withdrawing ₹200.0: SUCCESS");
            System.out.println("Balance after withdrawal: ₹" + acc5.getBalance());
            System.out.println(formatAccount(acc5));
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 6: Invalid Deposit (Negative Amount)
        System.out.println("\n>>> Test 6: Invalid Deposit (Negative Amount)");
        System.out.println("Attempting to deposit ₹-100.0");
        try {
            acc5.deposit(-100.0);
        } catch (InvalidAmountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 7: Insufficient Balance
        System.out.println("\n>>> Test 7: Insufficient Balance");
        Account acc6 = new Account(1006, "Charlie Green", 35, 500.0, "Savings");
        acc6.setPin(1234);
        System.out.println("Account: " + formatAccount(acc6));
        System.out.println("Attempting to withdraw ₹1000.0");
        try {
            acc6.withdraw(1000.0, 1234);
        } catch (InsufficientBalanceException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 8: Minimum Balance Violation
        System.out.println("\n>>> Test 8: Minimum Balance Violation");
        Account acc7 = new Account(1007, "Diana Prince", 28, 1000.0, "Savings");
        acc7.setPin(1234);
        System.out.println("Account: " + formatAccount(acc7));
        System.out.println("Attempting to withdraw ₹600.0");
        try {
            acc7.withdraw(600.0, 1234);
        } catch (MinimumBalanceViolationException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 9: Inactive Account Operations
        System.out.println("\n>>> Test 9: Inactive Account Operations");
        Account acc8 = new Account(1008, "Eve Wilson", 32, 2000.0, "Current");
        System.out.println("Account: " + formatAccount(acc8));

        acc8.closeAccount();
        System.out.println("Closing account: SUCCESS");

        System.out.println("Attempting to deposit ₹100.0 on closed account");
        try {
            acc8.deposit(100.0);
        } catch (InactiveAccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        acc8.reopenAccount();
        System.out.println("Reopening account: SUCCESS");

        try {
            acc8.deposit(100.0);
            System.out.println("Depositing ₹100.0 after reopen: SUCCESS");
            System.out.println("Balance after deposit: ₹" + acc8.getBalance());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 10: PIN Verification
        System.out.println("\n>>> Test 10: PIN Verification");
        Account acc9 = new Account(1009, "Frank Miller", 40, 1500.0, "Savings");
        System.out.println("Account: " + formatAccount(acc9));

        acc9.setPin(1234);
        System.out.println("Setting PIN 1234: SUCCESS");

        try {
            acc9.withdraw(200.0, 1234);
            System.out.println("Withdrawing ₹200.0 with correct PIN: SUCCESS");
            System.out.println("Balance: ₹" + acc9.getBalance());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println("Attempting to withdraw ₹100.0 with incorrect PIN (9999)");
        try {
            acc9.withdraw(100.0, 9999);
        } catch (InvalidPinException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println("Attempting to withdraw ₹100.0 without PIN set");
        Account accNoPin = new Account(1010, "No PIN User", 25, 1000.0, "Savings");
        try {
            accNoPin.withdraw(100.0, 1234);
        } catch (InvalidPinException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 11: All Accounts Summary
        System.out.println("\n>>> Test 11: All Accounts Summary");
        System.out.println(formatAccount(acc1));
        System.out.println(formatAccount(acc5));
        System.out.println(formatAccount(acc6));
        System.out.println(formatAccount(acc7));
        System.out.println(formatAccount(acc8));
        System.out.println(formatAccount(acc9));

        // Footer
        System.out.println("============================================================");
        System.out.println(" TEST COMPLETED!");
        System.out.println("============================================================");
    }
}
