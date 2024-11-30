import javax.swing.JOptionPane;

public class CreditAccount extends Account {
    private final double creditLimit;  // Credit limit
    private final double interestRate; // Interest rate

    // Updated Constructor
    public CreditAccount(String accountHolderName, String accountNumber, double creditLimit, double interestRate) {
        super(accountHolderName, accountNumber);
        this.creditLimit = creditLimit;  // Initialize credit limit
        this.interestRate = interestRate;  // Initialize interest rate
    }

    // Withdraw money from the account
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            JOptionPane.showMessageDialog(null, "Withdrawal amount must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if withdrawal exceeds available credit (balance + credit limit)
        if (getBalance() - amount < -creditLimit) {
            JOptionPane.showMessageDialog(null,
                    "Transaction declined! Insufficient funds. Exceeds credit limit of R" + creditLimit,
                    "Transaction Declined", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Reduce the balance (direct update since it's a credit account)
        super.balance -= amount;

        JOptionPane.showMessageDialog(null,
                "Withdrew R" + amount + ". Current balance: R" + getBalance(),
                "Withdrawal Successful", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    // Apply interest to the account
    public void applyInterest() {
        if (getBalance() < 0) {
            double interest = (-getBalance()) * (interestRate / 100);
            super.balance -= interest;  // Deduct interest from the balance
            JOptionPane.showMessageDialog(null,
                    "Interest of R" + interest + " applied. Current balance: R" + getBalance(),
                    "Interest Applied", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "No interest applied as the account balance is positive.",
                    "Interest Not Applied", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Display account details
    @Override
    public void accountDetails() {
        JOptionPane.showMessageDialog(null,
                "Account Holder: " + accountHolderName +
                        "\nAccount Number: " + accountNumber +
                        "\nBalance: R" + getBalance() +
                        "\nCredit Limit: R" + creditLimit +
                        "\nInterest Rate: " + interestRate + "%",
                "Credit Account Details", JOptionPane.INFORMATION_MESSAGE);
    }

    // Getter for credit limit
    public double getCreditLimit() {
        return creditLimit;
    }

    // Getter for interest rate
    public double getInterestRate() {
        return interestRate;
    }
}
