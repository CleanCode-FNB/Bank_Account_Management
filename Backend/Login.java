import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, viewUsersButton;

    private static List<User> userList = User.loadUserList();  // Load default users

    public Login() {
        // Setup JFrame
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("Bank Login");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        headerPanel.setBackground(new Color(173, 216, 230)); // Light blue background
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        loginButton = new JButton("Login");
        viewUsersButton = new JButton("View Users");

        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(60, 179, 113)); // Green button
        loginButton.setForeground(Color.WHITE);

        viewUsersButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewUsersButton.setBackground(new Color(70, 130, 180)); // Blue button
        viewUsersButton.setForeground(Color.WHITE);

        buttonPanel.add(loginButton);
        buttonPanel.add(viewUsersButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User loggedInUser = authenticateUser(username, password);
            if (loggedInUser != null) {
                new Bank(loggedInUser);  // Open the bank GUI with the authenticated user
                dispose();  // Close the login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewUsersButton.addActionListener(e -> {
            new ViewAllUsers(userList);  // Show all users
            dispose();  // Close the login window
        });

        setVisible(true);
    }

    private User authenticateUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.validatePassword(password)) {
                return user;  // Return the authenticated user
            }
        }
        return null;  // Return null if no matching user is found
    }

    // Main method
    public static void main(String[] args) {
        new Login();  // Start the Login GUI
    }
}     
