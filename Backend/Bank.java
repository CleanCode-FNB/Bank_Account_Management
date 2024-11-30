import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Bank extends JFrame {
    private User currentUser;  // Hold the current logged-in user
    private JTextArea infoArea;
    private JTextField amountField;
    private JLabel userInfoLabel;

    // Reference to user list from User class (default users)
    private static ArrayList<User> userList = User.loadUserList();  // Load user list from the User class

    public Bank(User user) {
        this.currentUser = user;  // Initialize with the logged-in user

        // Setup JFrame
        setTitle("Bank Operations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // UI Components
        JLabel amountLabel = new JLabel("Enter Amount:");
        amountField = new JTextField(15);

        // Buttons for different actions
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferButton = new JButton("Transfer");
        JButton viewBalanceButton = new JButton("View Balance");
        JButton viewHistoryButton = new JButton("View Action History");
        JButton clearButton = new JButton("Clear");
        JButton logoutButton = new JButton("Logout");

        infoArea = new JTextArea(5, 30);
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);

        // Create user info label to display user details at the top
        userInfoLabel = new JLabel(getUserInfo());
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Add components to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridwidth = 2; // Makes user info span across two columns
        add(userInfoLabel, gbc);  // User info at the top

        gbc.gridwidth = 1;  // Reset grid width for other components

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(amountLabel, gbc);
        gbc.gridx = 1;
        add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(depositButton, gbc);
        gbc.gridx = 1;
        add(withdrawButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(transferButton, gbc);
        gbc.gridx = 1;
        add(viewBalanceButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(viewHistoryButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(clearButton, gbc);
        gbc.gridx = 1;
        add(logoutButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(scrollPane, gbc);

        // Action listeners for buttons
        depositButton.addActionListener(e -> performDeposit());
        withdrawButton.addActionListener(e -> performWithdraw());
        transferButton.addActionListener(e -> performTransfer());
        viewBalanceButton.addActionListener(e -> viewBalance());
        viewHistoryButton.addActionListener(e -> viewActionHistory());
        clearButton.addActionListener(e -> clearInfoArea());
        logoutButton.addActionListener(e -> logout());

        setVisible(true);
    }

    private String getUserInfo() {
        return "<html><b>Name:</b> " + currentUser.getFirstName() + " " + currentUser.getLastName() + " &nbsp;&nbsp;" +
               "<b>Account:</b> " + currentUser.getAccountNumber() + " &nbsp;&nbsp;" +
               "<b>Balance:</b> R" + currentUser.getBalance() + " &nbsp;&nbsp;" +
               "<b>Type:</b> " + currentUser.getAccountType() + "</html>";
    }

    private double getAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    private void performDeposit() {
        double amount = getAmount();
        if (amount > 0) {
            currentUser.updateBalance(amount);
            currentUser.addAction("Deposited R" + amount);
            infoArea.setText("Deposited R" + amount + " successfully.");
            updateUserInfo();
            saveData();  // Save after deposit
        }
    }

    private void performWithdraw() {
        double amount = getAmount();
        if (amount > 0) {
            if (currentUser.getBalance() >= amount) {
                currentUser.updateBalance(-amount);
                currentUser.addAction("Withdrew R" + amount);
                infoArea.setText("Withdrew R" + amount + " successfully.");
                updateUserInfo();
                saveData();  // Save after withdrawal
            } else {
                infoArea.setText("Insufficient balance.");
            }
        }
    }

    private void performTransfer() {
        String recipientAccount = JOptionPane.showInputDialog(this, "Enter recipient account number:");
        double amount = getAmount();

        if (recipientAccount == null || recipientAccount.isEmpty()) {
            infoArea.setText("Transfer cancelled.");
            return;
        }

        if (amount > 0) {
            if (currentUser.getBalance() >= amount) {
                // Update current user's balance
                currentUser.updateBalance(-amount);
                currentUser.addAction("Transferred R" + amount + " to account " + recipientAccount);
                infoArea.setText("Transferred R" + amount + " to account " + recipientAccount + " successfully.");

                // Update recipient's balance and action history
                User recipient = findUserByAccountNumber(recipientAccount);
                if (recipient != null) {
                    recipient.updateBalance(amount);  // Add the received amount to the recipient's current balance
                    recipient.addAction("Received R" + amount + " from account " + currentUser.getAccountNumber());

                    // Show confirmation message
                    JOptionPane.showMessageDialog(this,
                            "Recipient account " + recipient.getAccountNumber() + " has received R" + amount +
                            " from account " + currentUser.getAccountNumber(),
                            "Transfer Successful", JOptionPane.INFORMATION_MESSAGE);

                    updateUserInfo();  // Update sender's information
                    saveData();  // Save after transfer
                } else {
                    JOptionPane.showMessageDialog(this, "Recipient account not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                infoArea.setText("Insufficient balance for the transfer.");
            }
        }
    }

    private User findUserByAccountNumber(String accountNumber) {
        for (User user : userList) {
            if (user.getAccountNumber().equals(accountNumber)) {
                return user;
            }
        }
        return null;
    }

    private void viewBalance() {
        infoArea.setText("Current Balance: R" + currentUser.getBalance());
    }

    private void viewActionHistory() {
        infoArea.setText("Action History:\n" + currentUser.getActionHistory());
    }

    private void clearInfoArea() {
        infoArea.setText("");
    }

    private void logout() {
        // Save the user data when logging out
        saveData();  // Save the updated user list
        new Login();  // Open the login window
        dispose();    // Close the bank window
    }

    private void updateUserInfo() {
        userInfoLabel.setText(getUserInfo());  // Update the user info label when the balance changes
    }

    private void saveData() {
        User.saveUserList(userList);  // Save the updated user list to the file
    }
}
