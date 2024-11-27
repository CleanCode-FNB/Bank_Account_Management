import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class BankApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, Double> userBalances; // Simulated balance storage
    private String currentUser; // Stores the currently logged-in user

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
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Password:"), gbc);
    
        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);
    
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
    private JPanel createWelcomePage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(180, 220, 240));
    
        // Dashboard Panel
        JPanel dashboardPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        dashboardPanel.setOpaque(false);
    
        // Dashboard Labels
        dashboardPanel.add(new JLabel("Name:", SwingConstants.RIGHT));
        JLabel nameLabel = new JLabel("John Doe"); // Replace with dynamic data if needed
        dashboardPanel.add(nameLabel);
    
        dashboardPanel.add(new JLabel("Account Number:", SwingConstants.RIGHT));
        JLabel accountNumberLabel = new JLabel("123456789"); // Replace with dynamic data if needed
        dashboardPanel.add(accountNumberLabel);
    
        dashboardPanel.add(new JLabel("Account Holder:", SwingConstants.RIGHT));
        JLabel accountHolderLabel = new JLabel("Standard User"); // Replace with dynamic data if needed
        dashboardPanel.add(accountHolderLabel);
    
        // Title Label
        JLabel label = new JLabel("Welcome "  , SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(50, 100, 150));
    
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.setOpaque(false);
    
        String[] buttons = {"Deposit", "Withdraw", "Transfer", "Check Balance", "Logout"};
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.addActionListener(e -> {
                if (text.equals("Logout")) {
                    cardLayout.show(mainPanel, "Login");
                } else {
                    cardLayout.show(mainPanel, text);
                }
            });
            buttonPanel.add(button);
        }

        // Add components to the panel
        panel.add(label, BorderLayout.NORTH); // Page title at the top
        panel.add(dashboardPanel, BorderLayout.CENTER); // Dashboard in the center
        panel.add(buttonPanel, BorderLayout.SOUTH); // Buttons at the bottom
    
        return panel;
    }

    // Helper Method for Transaction Pages (Deposit / Withdraw)
    private JPanel createTransactionPage(String titleText, String buttonText, String backPage) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 240, 255));

        JLabel label = new JLabel(titleText, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(50, 100, 150));

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

        // Action Button
        JButton actionButton = new JButton(buttonText);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(actionButton, gbc);

        actionButton.addActionListener(e -> {
            String amountText = amountField.getText();
            JOptionPane.showMessageDialog(this, buttonText + " Successful: $" + amountText, "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, backPage);
        });

        panel.add(label, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }

    // Check Balance Page
    private JPanel createBalancePage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 240, 255));

        JLabel balanceLabel = new JLabel("Your Balance: $", SwingConstants.CENTER);
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
                balanceLabel.setText("Your Balance: $" + balance);
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

                    JOptionPane.showMessageDialog(this, "Transfer Successful to " + recipient + ": $" + amount, "Success", JOptionPane.INFORMATION_MESSAGE);
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