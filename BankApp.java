import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class BankApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, Double> userBalances; // Simulated balance storage
    private String currentUser; // Stores the currently logged-in user
    public String username;

    public BankApp() {
        // Initialize balance storage
        userBalances = new HashMap<>();
        userBalances.put("user", 1000.00); // Dummy user balance
        userBalances.put("recipient", 500.00); // Dummy recipient balance

        // Setup frame
        setTitle("Bank Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add all screens to the card layout
        mainPanel.add(createLoginPage(), "Login");
        mainPanel.add(createWelcomePage(), "Welcome");
        mainPanel.add(createTransactionPage("Deposit Funds", "Deposit", "Welcome"), "Deposit");
        mainPanel.add(createTransactionPage("Withdraw Funds", "Withdraw", "Welcome"), "Withdraw");
        mainPanel.add(createTransferPage(), "Transfer");
        mainPanel.add(createBalancePage(), "CheckBalance");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    // Login Page
    private JPanel createLoginPage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(200, 230, 250));
    
        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(50, 70, 120));
    
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
    
        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Username:"), gbc);
    
        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);
    
        // Password
       // Password
gbc.gridx = 0;
gbc.gridy = 1;
gbc.anchor = GridBagConstraints.EAST;
formPanel.add(new JLabel("Password:"), gbc);

JPasswordField passwordField = new JPasswordField(15);
gbc.gridx = 1;
gbc.anchor = GridBagConstraints.WEST;

// Add a panel to hold password field and toggle button
JPanel passwordPanel = new JPanel(new BorderLayout());
passwordPanel.add(passwordField, BorderLayout.CENTER);

JButton toggleVisibilityButton = new JButton("\uD83D\uDC41"); // Eye icon
toggleVisibilityButton.setPreferredSize(new Dimension(40, passwordField.getPreferredSize().height));
toggleVisibilityButton.setFocusPainted(false);
toggleVisibilityButton.setBorderPainted(false);
toggleVisibilityButton.setContentAreaFilled(false);

passwordPanel.add(toggleVisibilityButton, BorderLayout.EAST);
formPanel.add(passwordPanel, gbc);

// Toggle Password Visibility Action
toggleVisibilityButton.addActionListener(e -> {
    if (passwordField.getEchoChar() == '\u2022') { // Check if password is hidden
        passwordField.setEchoChar((char) 0); // Show password
        toggleVisibilityButton.setText("\uD83D\uDC41\u200D\uD83D\uDD0D"); // Eye with slash icon
    } else {
        passwordField.setEchoChar('\u2022'); // Hide password
        toggleVisibilityButton.setText("\uD83D\uDC41"); // Eye icon
    }
});
    
        // Add vertical spacing
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span both columns
        formPanel.add(Box.createVerticalStrut(20), gbc); // Adds vertical space above the buttons
    
        // Buttons panel with BorderLayout
        JPanel buttonPanel = new JPanel(new BorderLayout(20, 0)); // Horizontal gap set to 20
        JButton loginButton = new JButton("Login");
        JButton clearButton = new JButton("Clear");

    
        
        buttonPanel.add(loginButton, BorderLayout.WEST); // Login button on the left
        // buttonPanel.setBackground(new Color(500, 330, 350));
        buttonPanel.add(clearButton, BorderLayout.EAST); // Clear button on the right
        buttonPanel.setBackground(new Color(200, 230, 250));
    
        // Add a horizontal spacer and button panel
        JPanel alignedButtonPanel = new JPanel(new BorderLayout());
        alignedButtonPanel.add(Box.createHorizontalStrut(50), BorderLayout.WEST); // Spacer to shift buttons right
        alignedButtonPanel.setBackground(new Color(200, 230, 250));
        alignedButtonPanel.add(buttonPanel, BorderLayout.CENTER);
    
        // Add aligned button panel
        gbc.gridx = 0;
        gbc.gridy = 3; // Adjusted to accommodate the vertical spacer
        gbc.gridwidth = 2; // Span both columns
        formPanel.add(alignedButtonPanel, gbc);
    
        // Login Button Action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
    
            if (username.equals("user") && password.equals("pass")) { // Dummy login check
                currentUser = username; // Store the current user
                cardLayout.show(mainPanel, "Welcome");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        // Clear Button Action
        clearButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });
    
        panel.add(title, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }
    
    // Welcome Page
   // Welcome Page
   // Welcome Page
private JPanel createWelcomePage() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(180, 220, 240));

    // Top Panel for Welcome Text and Logout Button
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(new Color(180, 220, 240));

    JLabel welcomeLabel = new JLabel("Welcome, Lundi!", SwingConstants.CENTER);
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
    welcomeLabel.setForeground(new Color(50, 100, 150));
    topPanel.add(welcomeLabel, BorderLayout.CENTER);

    JButton logoutButton = new JButton("Logout");
    logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
    logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
    topPanel.add(logoutButton, BorderLayout.EAST);

    // Dashboard Panel
    JPanel dashboardPanel = new JPanel(new GridLayout(3, 2, 10, 10));
    dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
    dashboardPanel.setOpaque(false);

    // Dashboard Labels
    dashboardPanel.add(new JLabel("Account Holder:", SwingConstants.LEFT));
    JLabel nameLabel = new JLabel("Lundi Langa", SwingConstants.LEFT); // Replace with dynamic data if needed
    dashboardPanel.add(nameLabel);

    dashboardPanel.add(new JLabel("Account Number:", SwingConstants.LEFT));
    JLabel accountNumberLabel = new JLabel("123456789", SwingConstants.LEFT); // Replace with dynamic data if needed
    dashboardPanel.add(accountNumberLabel);

    dashboardPanel.add(new JLabel("Account Type:", SwingConstants.LEFT));

    // Add Dropdown for Account Type
    String[] accountTypes = {"SAVINGS", "CHEQUE"};
    JComboBox<String> accountTypeDropdown = new JComboBox<>(accountTypes);
    dashboardPanel.add(accountTypeDropdown);

    // Buttons Panel
    JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
    buttonPanel.setOpaque(false);

    String[] buttons = {"Deposit", "Withdraw", "Transfer", "Check Balance"};
    for (String text : buttons) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.addActionListener(e -> cardLayout.show(mainPanel, text));
        buttonPanel.add(button);
    }

    // Add components to the panel
    panel.add(topPanel, BorderLayout.NORTH); // Welcome text and logout at the top
    panel.add(dashboardPanel, BorderLayout.CENTER); // Dashboard in the center
    panel.add(buttonPanel, BorderLayout.SOUTH); // Buttons at the bottom

    return panel;
}

    // Helper Method for Transaction Pages (Deposit / Withdraw)
    private JPanel createTransactionPage(String titleText, String buttonText, String backPage) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 240, 255));
    
        // Top Panel with Back Button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(230, 240, 255));
        
        JButton backButton = new JButton("← ");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        backButton.setBackground(new Color(200, 220, 240));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, backPage));
        
        JLabel titleLabel = new JLabel(titleText, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(50, 100, 150));
    
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
    
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
    
        // Display Current Balance
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Current Balance:"), gbc);
    
        JLabel balanceLabel = new JLabel();
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(balanceLabel, gbc);
    
        // Account Type Selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Account Type:"), gbc);
    
        String[] accountTypes = {"SAVINGS", "CHEQUE"};
        JComboBox<String> accountTypeDropdown = new JComboBox<>(accountTypes);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(accountTypeDropdown, gbc);
    
        // Amount to Deposit
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Amount:"), gbc);
    
        JTextField amountField = new JTextField(10);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(amountField, gbc);
    
        // Account Number to Deposit To
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Account Number:"), gbc);
    
        JTextField accountNumberField = new JTextField(10);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(accountNumberField, gbc);
    
        // Action Button
        JButton actionButton = new JButton(buttonText);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(actionButton, gbc);
    
        actionButton.addActionListener(e -> {
            String amountText = amountField.getText();
            String accountNumber = accountNumberField.getText();
            String accountType = (String) accountTypeDropdown.getSelectedItem();
    
            // Validate input and perform deposit logic
            if (!amountText.isEmpty() && !accountNumber.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountText);
                    if (amount > 0) {
                        double currentBalance = userBalances.getOrDefault(currentUser, 0.0);
                        userBalances.put(currentUser, currentBalance + amount);
    
                        JOptionPane.showMessageDialog(this, "Deposit Successful: R" + amount + "\nTo Account: " + accountNumber + " (" + accountType + ")", "Success", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(mainPanel, backPage);
                    } else {
                        JOptionPane.showMessageDialog(this, "Amount must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount entered!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        // Update balance label dynamically
        panel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & e.DISPLAYABILITY_CHANGED) != 0 && panel.isDisplayable()) {
                Double balance = userBalances.getOrDefault(currentUser, 0.0);
                balanceLabel.setText("R" + balance);
            }
        });
    
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }
    

    // Check Balance Page
    private JPanel createBalancePage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 240, 255));

        JLabel balanceLabel = new JLabel("Your Balance: R", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setForeground(new Color(50, 100, 150));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Welcome");
        });

        panel.add(balanceLabel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        // Update balance dynamically
        panel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & e.DISPLAYABILITY_CHANGED) != 0 && panel.isDisplayable()) {
                Double balance = userBalances.getOrDefault(currentUser, 0.0);
                balanceLabel.setText("Your Balance: R" + balance);
            }
        });

        return panel;
    }

    // Transfer Page
    private JPanel createTransferPage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 240, 255));

        JLabel titleLabel = new JLabel("Transfer Funds", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(50, 100, 150));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Amount
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Amount:"), gbc);

        JTextField amountField = new JTextField(10);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(amountField, gbc);

        // Recipient
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Recipient:"), gbc);

        JTextField recipientField = new JTextField(10);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(recipientField, gbc);

        // Action Button
        JButton transferButton = new JButton("Transfer");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(transferButton, gbc);

        transferButton.addActionListener(e -> {
            String amountText = amountField.getText();
            String recipient = recipientField.getText();

            // Check transfer logic
            if (userBalances.containsKey(recipient)) {
                double amount = Double.parseDouble(amountText);
                double senderBalance = userBalances.get(currentUser);
                if (senderBalance >= amount) {
                    userBalances.put(currentUser, senderBalance - amount);
                    userBalances.put(recipient, userBalances.get(recipient) + amount);

                    JOptionPane.showMessageDialog(this, "Transfer Successful to " + recipient + ": R" + amount, "Success", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "Welcome");
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient funds!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Recipient not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankApp app = new BankApp();
            app.setVisible(true);
        });
    }
}