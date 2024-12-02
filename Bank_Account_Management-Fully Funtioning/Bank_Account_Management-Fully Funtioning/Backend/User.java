import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String accountNumber;
    private String username;
    private String passwordHash;  // Store hashed password
    private String accountType;
    private double balance;
    private List<String> actionHistory;

    // Constructor
    public User(String firstName, String lastName, String accountNumber, String username, String password, String accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.username = username;
        this.passwordHash = hashPassword(password);  // Hash password on user creation
        this.accountType = accountType;
        this.balance = 0.0;
        this.actionHistory = new ArrayList<>();
    }

    // Hashing method for password
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();  // Return hashed password as hex string
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Validate password by comparing hash
    public boolean validatePassword(String password) {
        return this.passwordHash.equals(hashPassword(password));
    }

    // Getters and setters...
    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    public void addAction(String action) {
        actionHistory.add(action);
    }

    public List<String> getActionHistory() {
        return actionHistory;
    }

    // Method to load the user list (default users) or from a file
    public static ArrayList<User> loadUserList() {
        // For simplicity, let's just create default users with fixed passwords (name123)
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Aluncedo", "Langa", "123000001", "aluncedo", "aluncedo123", "Cheque"));
        users.add(new User("Ximiyeto", "Makhubele", "123000002", "ximiyeto", "ximiyeto123", "Cheque"));
        users.add(new User("Senethemba", "Vitsha", "123000003", "senethemba", "senethemba123", "Savings"));
        users.add(new User("Morena", "Macheka", "123000004", "morena", "morena123", "Savings"));
        users.add(new User("Karabo", "Sethuntsa", "123000005", "karabo", "karabo123", "CreditAccount"));
        users.add(new User("Lawrence", "Sibisi", "123000006", "lawrence", "lawrence123", "CreditAccount"));

        return users;  // Return the default users list
    }

    // Method to save user list to a file
    public static void saveUserList(ArrayList<User> userList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
