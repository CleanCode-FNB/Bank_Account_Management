public class Savings extends Account {
    public Savings(String accountHolderName, String accountNumber) {
        super(accountHolderName,accountNumber);
    }
    // Common methods
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount + " successfully.");
        } else {
            System.out.println("Deposit amount must be positive!");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Insufficient balance or invalid amount.");
            return false;
        }
    }
public boolean transfer(Account targetAccount, double amount) {
    if (this != targetAccount && amount > 0 && amount <= this.balance) {
        // Withdraw the amount from the source account (current account)
        boolean success = this.withdraw(amount);
        if (success) {
            // Deposit the amount into the target account
            targetAccount.deposit(amount);
            System.out.println("Transferred " + amount + " from " + this.accountNumber + " to " + targetAccount.accountNumber);
            return true;
        }
    } else {
        System.out.println("Invalid transfer: Ensure sufficient balance or different target account.");
    }
    return false;
}



    public double getBalance() {
        return balance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }
public void accountDetails(){
    System.out.println(accountHolderName);
    System.out.println(accountNumber);
    System.out.println(balance);
}
}
