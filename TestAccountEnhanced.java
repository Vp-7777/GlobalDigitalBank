// Global Digital Bank - Training Program
// Activity 4: Testing the Enhanced Account Class

public class TestAccountEnhanced {

    // Helper method to format account information for display
    // Format: Account #<num> | <name> (<age> yrs) | <type> | ₹<balance> | <status> | PIN: <Yes/No>
    public static String formatAccount(AccountEnhanced account) {
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
        System.out.println(" ENHANCED ACCOUNT TEST (BOOLEAN RETURNS)");
        System.out.println("============================================================");

        // >>> Test 1: Valid Account Creation
        System.out.println(">>> Test 1: Valid Account Creation");
        AccountEnhanced acc1 = new AccountEnhanced(1001, "John Doe", 25, 1000.0, "Savings");
        System.out.println(formatAccount(acc1));

        // >>> Test 2: Invalid Age (under 18)
        System.out.println("\n>>> Test 2: Invalid Age (under 18)");
        System.out.println("Creating account with age 16");
        AccountEnhanced acc2 = new AccountEnhanced(1002, "Young Kid", 16, 500.0, "Savings");
        System.out.println("Age auto-corrected to: " + acc2.getAge());
        System.out.println(formatAccount(acc2));

        // >>> Test 3: Invalid Account Type
        System.out.println("\n>>> Test 3: Invalid Account Type");
        System.out.println("Creating account with type \"Invalid\"");
        AccountEnhanced acc3 = new AccountEnhanced(1003, "Test User", 25, 500.0, "Invalid");
        System.out.println("Account type defaulted to: " + acc3.getAccountType());
        System.out.println(formatAccount(acc3));

        // >>> Test 4: Minimum Balance Enforcement on Creation
        System.out.println("\n>>> Test 4: Minimum Balance Enforcement on Creation");
        System.out.println("Creating Savings account with ₹300 (below minimum)");
        AccountEnhanced acc4 = new AccountEnhanced(1004, "Bob Wilson", 25, 300.0, "Savings");
        System.out.println("Balance auto-corrected to minimum: ₹" + acc4.getBalance());
        System.out.println(formatAccount(acc4));

        // >>> Test 5: Withdrawal with Minimum Balance
        System.out.println("\n>>> Test 5: Withdrawal with Minimum Balance");
        AccountEnhanced acc5 = new AccountEnhanced(1005, "Alice Brown", 30, 1000.0, "Current");
        acc5.setPin(1111); // Set a valid PIN for Alice
        System.out.println("Initial: " + formatAccount(acc5));

        // Valid withdrawal of ₹200.0
        if (acc5.withdraw(200.0, 1111)) {
            System.out.println("Withdrawing ₹200.0: SUCCESS");
            System.out.println("New balance: ₹" + acc5.getBalance());
        }
        System.out.println("After withdrawal: " + formatAccount(acc5));

        // Invalid withdrawal of ₹900.0 (would cause negative balance)
        if (acc5.withdraw(900.0, 1111)) {
            System.out.println("Withdrawing ₹900.0: SUCCESS");
        } else {
            System.out.println("Withdrawing ₹900.0 (would leave ₹-100): FAILED (Minimum balance violation)");
            System.out.println("Current balance: ₹" + acc5.getBalance());
        }

        // >>> Test 6: Account Status Management
        System.out.println("\n>>> Test 6: Account Status Management");
        AccountEnhanced acc6 = new AccountEnhanced(1006, "Charlie Green", 35, 2000.0, "Savings");
        System.out.println("Initial: " + formatAccount(acc6));

        // Close account
        if (acc6.closeAccount()) {
            System.out.println("Closing account: SUCCESS");
        }
        System.out.println("After close: " + formatAccount(acc6));

        // Deposit to closed account
        if (acc6.deposit(500.0)) {
            System.out.println("Depositing ₹500.0 to closed account: SUCCESS");
        } else {
            System.out.println("Depositing ₹500.0 to closed account: FAILED (Account inactive)");
        }

        // Reopen account
        if (acc6.reopenAccount()) {
            System.out.println("Reopening account: SUCCESS");
        }
        System.out.println("After reopen: " + formatAccount(acc6));

        // >>> Test 7: PIN Protection
        System.out.println("\n>>> Test 7: PIN Protection");
        AccountEnhanced acc7 = new AccountEnhanced(1007, "Diana Prince", 28, 1500.0, "Savings");

        // Set PIN
        if (acc7.setPin(1234)) {
            System.out.println("Setting PIN 1234: SUCCESS");
        }

        // Withdraw with correct PIN
        if (acc7.withdraw(200.0, 1234)) {
            System.out.println("Withdrawing ₹200.0 with correct PIN (1234): SUCCESS");
            System.out.println("New balance: ₹" + acc7.getBalance());
        }

        // Withdraw with incorrect PIN
        if (!acc7.withdraw(100.0, 9999)) {
            System.out.println("Withdrawing ₹100.0 with incorrect PIN (9999): FAILED (Incorrect PIN)");
        }

        // Withdraw on account with PIN not set (using acc1)
        if (!acc1.withdraw(100.0, 1234)) {
            System.out.println("Withdrawing ₹100.0 with PIN not set: FAILED (PIN not set)");
        }

        // >>> Test 8: All Accounts Summary
        System.out.println("\n>>> Test 8: All Accounts Summary");
        System.out.println(formatAccount(acc1));
        System.out.println(formatAccount(acc2));
        System.out.println(formatAccount(acc3));
        System.out.println(formatAccount(acc4));
        System.out.println(formatAccount(acc5));
        System.out.println(formatAccount(acc6));
        System.out.println(formatAccount(acc7));

        // Footer
        System.out.println("============================================================");
        System.out.println(" ENHANCED TEST COMPLETED!");
        System.out.println("============================================================");
    }
}
