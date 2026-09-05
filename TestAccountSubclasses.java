// Global Digital Bank - Training Program
// Activity 8: Testing Account Subclasses (SavingsAccount & CurrentAccount)

public class TestAccountSubclasses {

    // Helper method to format account details with PIN status
    public static String formatAccount(Account account) {
        String pinStatus = account.hasPin() ? "Yes" : "No";
        return "Account #" + account.getAccountNumber() + " | " 
                + account.getName() + " (" + account.getAge() + " yrs) | " 
                + account.getAccountType() + " | ₹" 
                + account.getBalance() + " | " 
                + account.getStatus() + " | PIN: " + pinStatus;
    }

    // Helper method to format account details without PIN status
    public static String formatAccountBasic(Account account) {
        return "Account #" + account.getAccountNumber() + " | " 
                + account.getName() + " (" + account.getAge() + " yrs) | " 
                + account.getAccountType() + " | ₹" 
                + account.getBalance() + " | " 
                + account.getStatus();
    }

    public static void main(String[] args) {

        // Header
        System.out.println("============================================================");
        System.out.println(" ACCOUNT SUBCLASSES TEST (SAVINGS & CURRENT)");
        System.out.println("============================================================");

        // >>> Test 1: Creating Accounts
        System.out.println(">>> Test 1: Creating Accounts");
        SavingsAccount acc1 = new SavingsAccount(1001, "John Doe", 25, 1000.0);
        CurrentAccount acc2 = new CurrentAccount(1002, "Jane Smith", 30, 2000.0);
        System.out.println("Savings Account: " + formatAccount(acc1));
        System.out.println("Current Account: " + formatAccount(acc2));

        // >>> Test 2: Account Type and Minimum Balance
        System.out.println("\n>>> Test 2: Account Type and Minimum Balance");
        System.out.println("Savings Account - Type: " + acc1.getAccountType() + ", Minimum Balance: ₹" + acc1.getMinimumBalance());
        System.out.println("Current Account - Type: " + acc2.getAccountType() + ", Minimum Balance: ₹" + acc2.getMinimumBalance());

        // >>> Test 3: Savings Account - Interest Calculation
        System.out.println("\n>>> Test 3: Savings Account - Interest Calculation");
        System.out.println("Savings Account: " + formatAccountBasic(acc1));
        System.out.println("Interest Rate: " + acc1.getInterestRate() + "% per annum");
        System.out.println("Interest for 1 year: ₹" + acc1.calculateInterest(1));
        System.out.println("Interest for 2 years: ₹" + acc1.calculateInterest(2));
        System.out.println("Interest for 5 years: ₹" + acc1.calculateInterest(5));
        System.out.println("After 2 years with interest: Balance would be ₹" + (acc1.getBalance() + acc1.calculateInterest(2)));

        // >>> Test 4: Current Account - Overdraft Feature
        System.out.println("\n>>> Test 4: Current Account - Overdraft Feature");
        System.out.println("Current Account: " + formatAccountBasic(acc2));
        System.out.println("Overdraft Limit: ₹" + acc2.getOverdraftLimit());
        System.out.println("Available Overdraft: ₹" + acc2.getAvailableOverdraft());
        System.out.println("Overdraft Used: ₹" + acc2.getOverdraftUsed());
        System.out.println("Is Using Overdraft: " + acc2.isUsingOverdraft());

        System.out.println("\nWithdrawing ₹1500.0 (goes below minimum balance of ₹1000)");
        System.out.println("Balance before: ₹" + acc2.getBalance());
        try {
            acc2.withdraw(1500.0);
            System.out.println("Withdrawing: ₹1500.0 - SUCCESS");
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println("Balance after: ₹" + acc2.getBalance());
        System.out.println("Overdraft Used: ₹" + acc2.getOverdraftUsed());
        System.out.println("Available Overdraft: ₹" + acc2.getAvailableOverdraft());
        System.out.println("Is Using Overdraft: " + acc2.isUsingOverdraft());

        System.out.println("\nAttempting to withdraw ₹4000.0 (would exceed overdraft)");
        System.out.println("Available funds: ₹" + acc2.getBalance() + " (balance) + ₹" + acc2.getAvailableOverdraft() + " (overdraft) = ₹5000.0");
        try {
            acc2.withdraw(4000.0);
        } catch (InsufficientBalanceException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println("\nRepaying overdraft of ₹500.0");
        System.out.println("Balance before repayment: ₹" + acc2.getBalance());
        System.out.println("Overdraft Used before: ₹" + acc2.getOverdraftUsed());
        try {
            acc2.repayOverdraft(500.0);
            System.out.println("Repaying ₹500.0 - SUCCESS");
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        System.out.println("Balance after repayment: ₹" + acc2.getBalance());
        System.out.println("Overdraft Used after: ₹" + acc2.getOverdraftUsed());
        System.out.println("Is Using Overdraft: " + acc2.isUsingOverdraft());

        // >>> Test 5: Polymorphism - Treating Accounts Uniformly
        System.out.println("\n>>> Test 5: Polymorphism - Treating Accounts Uniformly");
        System.out.println("Processing accounts polymorphically:\n");

        SavingsAccount acc3 = new SavingsAccount(1003, "Bob Wilson", 35, 500.0);
        CurrentAccount acc4 = new CurrentAccount(1004, "Alice Brown", 28, 1500.0);

        Account[] accounts = { acc1, acc2, acc3, acc4 };
        double totalBalance = 0.0;
        for (Account acc : accounts) {
            totalBalance += acc.getBalance();
            System.out.println(formatAccountBasic(acc) + " | Type: " + acc.getAccountType() + ", Min Balance: ₹" + acc.getMinimumBalance());
        }

        System.out.println("\nTotal accounts: " + accounts.length);
        System.out.println("Total balance across all accounts: ₹" + totalBalance);

        // >>> Test 6: Validation - Invalid Creation Attempts
        System.out.println("\n>>> Test 6: Validation - Invalid Creation Attempts");
        System.out.println("Attempting to create SavingsAccount with ₹300 (below minimum)");
        try {
            new SavingsAccount(9991, "Invalid User", 25, 300.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println("Attempting to create CurrentAccount with ₹500 (below minimum)");
        try {
            new CurrentAccount(9992, "Invalid User", 25, 500.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println("Attempting to create SavingsAccount with age 16");
        try {
            new SavingsAccount(9993, "Underage User", 16, 500.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 7: Savings Account - PIN and Operations
        System.out.println("\n>>> Test 7: Savings Account - PIN and Operations");
        SavingsAccount acc5 = new SavingsAccount(1005, "Charlie Green", 40, 2000.0);
        System.out.println("Savings Account: " + formatAccountBasic(acc5));

        acc5.setPin(1234);
        System.out.println("Setting PIN 1234: SUCCESS");

        try {
            acc5.deposit(500.0);
            System.out.println("Depositing ₹500.0: SUCCESS");
            System.out.println("Balance after deposit: ₹" + acc5.getBalance());

            acc5.withdraw(300.0, 1234);
            System.out.println("Withdrawing ₹300.0 with correct PIN: SUCCESS");
            System.out.println("Balance after withdrawal: ₹" + acc5.getBalance());

            System.out.println("Attempting to withdraw ₹2000.0 (would violate minimum balance)");
            acc5.withdraw(2000.0, 1234);
        } catch (MinimumBalanceViolationException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 8: Current Account - Active Status Operations
        System.out.println("\n>>> Test 8: Current Account - Active Status Operations");
        CurrentAccount acc6 = new CurrentAccount(1006, "Diana Prince", 35, 3000.0);
        System.out.println("Current Account: " + formatAccountBasic(acc6));

        acc6.closeAccount();
        System.out.println("Closing account: SUCCESS");

        System.out.println("Attempting to deposit ₹100.0 on closed account");
        try {
            acc6.deposit(100.0);
        } catch (InactiveAccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        acc6.reopenAccount();
        System.out.println("Reopening account: SUCCESS");

        try {
            acc6.deposit(100.0);
            System.out.println("Depositing ₹100.0 after reopen: SUCCESS");
            System.out.println("Balance after deposit: ₹" + acc6.getBalance());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // >>> Test 9: All Accounts Summary
        System.out.println("\n>>> Test 9: All Accounts Summary");
        System.out.println(formatAccount(acc1));
        System.out.println(formatAccount(acc2));
        System.out.println(formatAccount(acc3));
        System.out.println(formatAccount(acc4));
        System.out.println(formatAccount(acc5));
        System.out.println(formatAccount(acc6));

        // Footer
        System.out.println("============================================================");
        System.out.println(" TEST COMPLETED!");
        System.out.println("============================================================");
    }
}
