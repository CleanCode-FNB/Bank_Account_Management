public class CreditAccount {
    private String accountHolderName;
    private String accountNumber;
    private double balance;
    private double creditLimit;
    private double interestRate;

    // Constructor
    public CreditAccount(String accountHolderName, String accountNumber, double creditLimit, double interestRate) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    // Deposit money into the account
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }
        balance += amount;
        System.out.println("Deposited (in Rands):  " + amount + ". Current balance (in Rands): " + balance);
    }

    // Withdraw money from the account
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return false;
        } 
        if (balance - amount < -creditLimit) {
            System.out.println("Transaction declined! Exceeds credit limit.");
            return false;
        }
        balance -= amount;
        System.out.println("Withdrew (in Rands): " + amount + ". Current balance (in Rands): " + balance);
        return true;
    }

    // Apply interest to the account
    public void applyInterest() {
        if (balance < 0) { 
            double interest = (-balance) * (interestRate / 100);
            balance -= interest;
            System.out.println("Interest of (in Rands)" + interest + " applied. Current balance (in Rands): " + balance);
        } else {
            System.out.println("No interest applied as the account balance is positive.");
        }
    }

    // Display account details
    public void displayAccountDetails() {
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance (in Rands): " + balance);
        System.out.println("Credit Limit (in Rands): " + creditLimit);
        System.out.println("Interest Rate: " + interestRate + "%"); 
    }
}