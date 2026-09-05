// Global Digital Bank - Training Program
// Activity 2: Testing the Account Class
// Note: Updated in Activity 5 to demonstrate exception-safe execution using try-catch blocks

public class TestAccount {

    // Helper method to format account information for display
    public static String formatAccount(Account account) {
        return "Account #" + account.getAccountNumber() + " | " 
                + account.getName() + " (" + account.getAge() + " yrs) | " 
                + account.getAccountType() + " | ₹" 
                + account.getBalance() + " | " 
                + account.getStatus();
    }

    public static void main(String[] args) {

        // Header
        System.out.println("==================================================");
        System.out.println(" GLOBAL DIGITAL BANK - ACCOUNT TEST");
        System.out.println("==================================================");

        // 1. Creating First Account
        System.out.println(">>> 1. Creating Account");
        Account acc1 = new Account(1001, "John Doe", 25, 1000.0, "Savings");
        System.out.println("Account created!");
        System.out.println(formatAccount(acc1));

        // 2. Deposit Money Tests
        System.out.println(">>> 2. Deposit Money");
        
        // Valid deposit of 500.0
        double depositAmount1 = 500.0;
        try {
            acc1.deposit(depositAmount1);
            System.out.println("Depositing ₹" + depositAmount1 + ": SUCCESS");
            System.out.println("New balance: ₹" + acc1.getBalance());
        } catch (Exception e) {
            System.out.println("Depositing ₹" + depositAmount1 + ": FAILED (" + e.getMessage() + ")");
        }

        // Invalid deposit of -100.0 (negative amount)
        double depositAmount2 = -100.0;
        try {
            acc1.deposit(depositAmount2);
            System.out.println("Depositing ₹" + depositAmount2 + ": SUCCESS");
            System.out.println("New balance: ₹" + acc1.getBalance());
        } catch (Exception e) {
            System.out.println("Depositing ₹" + depositAmount2 + ": FAILED (Invalid amount)");
        }

        // 3. Withdraw Money Tests
        System.out.println(">>> 3. Withdraw Money");

        // Valid withdrawal of 200.0
        double withdrawAmount1 = 200.0;
        try {
            acc1.withdraw(withdrawAmount1);
            System.out.println("Withdrawing ₹" + withdrawAmount1 + ": SUCCESS");
            System.out.println("New balance: ₹" + acc1.getBalance());
        } catch (Exception e) {
            System.out.println("Withdrawing ₹" + withdrawAmount1 + ": FAILED (" + e.getMessage() + ")");
            System.out.println("Current balance: ₹" + acc1.getBalance());
        }

        // Insufficient balance withdrawal of 2000.0
        double withdrawAmount2 = 2000.0;
        try {
            acc1.withdraw(withdrawAmount2);
            System.out.println("Withdrawing ₹" + withdrawAmount2 + ": SUCCESS");
            System.out.println("New balance: ₹" + acc1.getBalance());
        } catch (Exception e) {
            System.out.println("Withdrawing ₹" + withdrawAmount2 + ": FAILED (Insufficient balance)");
            System.out.println("Current balance: ₹" + acc1.getBalance());
        }

        // 4. Creating Another Account
        System.out.println(">>> 4. Creating Another Account");
        Account acc2 = new Account(1002, "Jane Smith", 30, 2000.0, "Current");
        System.out.println(formatAccount(acc2));

        // 5. Display All Accounts
        System.out.println(">>> 5. All Accounts");
        System.out.println(formatAccount(acc1));
        System.out.println(formatAccount(acc2));

        // Footer
        System.out.println("==================================================");
        System.out.println(" TEST COMPLETED!");
        System.out.println("==================================================");
    }
}
