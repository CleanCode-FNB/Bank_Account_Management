import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, viewUsersButton;

    private static List<User> userList = User.loadUserList();  // Load default users

    public Login() {
        // Setup JFrame
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(8);
        passwordField = new JPasswordField(8);

        loginButton = new JButton("Login");
        viewUsersButton = new JButton("View Users");

        // Set font and size for buttons and labels
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        loginButton.setFont(new Font("Arial", Font.PLAIN, 12));
        viewUsersButton.setFont(new Font("Arial", Font.PLAIN, 12));

        // Layout setup
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        gbc.gridy = 3;
        add(viewUsersButton, gbc);

        // Login button action
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

        // View all users button action
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
