import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class ViewAllUsers extends JFrame {
    private JTable userTable;
    private JScrollPane scrollPane;

    public ViewAllUsers(List<User> userList) {
        // Column names for the table, including username and password
        String[] columns = {"Name", "Surname", "Account Number", "Account Type", "Username", "Password"};

        // Data for the table (convert User objects to an array of strings)
        Object[][] data = new Object[userList.size()][6];
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            data[i][0] = user.getFirstName();
            data[i][1] = user.getLastName();
            data[i][2] = user.getAccountNumber();
            data[i][3] = user.getAccountType();
            data[i][4] = user.getUsername();  // Include username
            data[i][5] = user.getPasswordHash();  // Display hashed password
        }

        // Create the table with data
        userTable = new JTable(data, columns);
        userTable.setFillsViewportHeight(true);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add table to a scroll pane
        scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // Set window properties
        setTitle("All Users");
        setSize(800, 400);  // Increased size to fit more columns
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        List<User> userList = User.loadUserList();  // Load users from default list
        new ViewAllUsers(userList);
    }
}
