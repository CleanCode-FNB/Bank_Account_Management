import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bank extends JFrame {
    private User currentUser;
    private JTextArea infoArea;
    private JTextField amountField;
    private JLabel userInfoLabel;

    private static ArrayList<User> userList = User.loadUserList();

    public Bank(User user) {
        this.currentUser = user;

        // Setup JFrame
        setTitle("Bank Operations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Section
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel blue
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        userInfoLabel = new JLabel(getUserInfo());
        userInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userInfoLabel.setForeground(Color.WHITE);
        headerPanel.add(userInfoLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Center Section for Controls
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Transaction Section
        JPanel transactionPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Transactions"));

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        amountField = new JTextField(15);

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferButton = new JButton("Transfer");

        transactionPanel.add(amountLabel);
        transactionPanel.add(amountField);
        transactionPanel.add(depositButton);
        transactionPanel.add(withdrawButton);
        transactionPanel.add(transferButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(transactionPanel, gbc);

        // Action Section
        JPanel actionPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton viewBalanceButton = new JButton("View Balance");
        JButton viewHistoryButton = new JButton("View Action History");

        actionPanel.add(viewBalanceButton);
        actionPanel.add(viewHistoryButton);

        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(actionPanel, gbc);

        // Feedback Section
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        feedbackPanel.setBorder(BorderFactory.createTitledBorder("Feedback"));

        infoArea = new JTextArea(10, 40);
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        feedbackPanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(feedbackPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Footer Section
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        footerPanel.setBackground(new Color(240, 240, 240)); // Light gray

        JButton clearButton = new JButton("Clear");
        JButton logoutButton = new JButton("Logout");

        footerPanel.add(clearButton);
        footerPanel.add(logoutButton);

        add(footerPanel, BorderLayout.SOUTH);

        // Action Listeners
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
        return "<html>Welcome, <b>" + currentUser.getFirstName() + " " + currentUser.getLastName() + "</b>!<br>" +
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
        }
        amountField.setText(""); // Clear the text field
    }

    private void performWithdraw() {
        double amount = getAmount();
        if (amount > 0) {
            if (currentUser.getBalance() >= amount) {
                currentUser.updateBalance(-amount);
                currentUser.addAction("Withdrew R" + amount);
                infoArea.setText("Withdrew R" + amount + " successfully.");
                updateUserInfo();
            } else {
                infoArea.setText("Insufficient balance.");
            }
        }
        amountField.setText(""); // Clear the text field
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
                currentUser.updateBalance(-amount);
                currentUser.addAction("Transferred R" + amount + " to account " + recipientAccount);
                infoArea.setText("Transferred R" + amount + " to account " + recipientAccount + " successfully.");

                User recipient = findUserByAccountNumber(recipientAccount);
                if (recipient != null) {
                    recipient.updateBalance(amount);
                    recipient.addAction("Received R" + amount + " from account " + currentUser.getAccountNumber());
                } else {
                    JOptionPane.showMessageDialog(this, "Recipient account not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                updateUserInfo();
            } else {
                infoArea.setText("Insufficient balance for the transfer.");
            }
        }
        amountField.setText(""); // Clear the text field
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
        User.saveUserList(userList);
        new Login();
        dispose();
    }

    private void updateUserInfo() {
        userInfoLabel.setText(getUserInfo());
    }
}



