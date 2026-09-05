# Global Digital Bank 🏦

A modular Core Banking System training project built in **Java**, progressively implementing OOP design patterns, entity data modeling, business rules validation, PIN-based authentication, and a custom exception hierarchy.

---

## 📌 Project Structure & Activities

### 1. **Activity 1 & 2: Core Entity & Test Driver**
* `Account.java`: Basic entity class with private fields (`accountNumber`, `name`, `age`, `balance`, `accountType`, `status`), constructor, and boolean returns.
* `TestAccount.java`: Test driver validating account creation, deposit, and withdrawal operations.

### 2. **Activity 3 & 4: Enhanced Model with Business Rules**
* `AccountEnhanced.java`:
  * Age validation (auto-adjusts age $< 18$ to `18`).
  * Account type validation (defaults invalid types to `"Savings"`).
  * Minimum opening balance rules (`₹500` for Savings, `₹1000` for Current).
  * Minimum balance enforcement on withdrawal.
  * Account status management (`closeAccount()`, `reopenAccount()`).
  * 4-digit PIN protection (`setPin`, `verifyPin`, `hasPin`, and `withdraw(amount, pin)`).
* `TestAccountEnhanced.java`: Comprehensive 8-step test suite covering edge cases and validations.

### 3. **Activity 5: Exception Architecture**
* Custom domain exceptions:
  * `AccountException.java` (Base exception)
  * `InvalidAmountException.java`
  * `InsufficientBalanceException.java`
  * `InactiveAccountException.java`
  * `InvalidPinException.java`
  * `MinimumBalanceViolationException.java`
* `Account.java`: Transitioned from boolean returns to throwing specific domain and runtime exceptions (`IllegalArgumentException`, `IllegalStateException`).

---

## 🚀 How to Compile & Run

### Compile all files:
```powershell
javac *.java
```

### Run Basic Account Test:
```powershell
java TestAccount
```

### Run Enhanced Account Test (with UTF-8 support for currency symbol):
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8; java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" TestAccountEnhanced
```
