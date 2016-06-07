import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 26.04.2016.
 */
public class Authorization extends JFrame {
    static int loggedUserID = 0;
    static int loggedUserType = 0;
    static final String SQL_URL = "jdbc:mysql://localhost:3306/solving_tasks";
    static final String SQL_USER = "root";
    static final String SQL_PASS = "root";
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton okButton;

    public Authorization() {
        super("Authorization");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // First horizontal panel (for login)
        Box box1 = Box.createHorizontalBox();
        JLabel loginLabel = new JLabel("Login:");
        loginField = new JTextField(15);
        box1.add(loginLabel);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(loginField);
        loginField.setText("test");
        loginField.addActionListener(e -> okButton.doClick());

        // Second horizontal panel (for password)
        Box box2 = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        box2.add(passwordLabel);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(passwordField);
        passwordField.setText("test");
        passwordField.addActionListener(e -> okButton.doClick());

        // Third horizontal panel (for buttons)
        Box box3 = Box.createHorizontalBox();
        okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        box3.add(Box.createHorizontalGlue());
        box3.add(okButton);
        box3.add(Box.createHorizontalStrut(12));
        box3.add(cancelButton);

        // Set login panel size equals to password panel size
        loginLabel.setPreferredSize(passwordLabel.getPreferredSize());

        // Place 3 horizontal panels on 1 vertical panel
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12,12,12,12));
        mainBox.add(box1);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box2);
        mainBox.add(Box.createVerticalStrut(17));
        mainBox.add(box3);

        okButton.addActionListener(e -> sendData());
        cancelButton.addActionListener(e -> System.exit(0));

        setContentPane(mainBox);
        pack();
        getRootPane().setDefaultButton(okButton);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Authorization authorization = new Authorization();
    }

    public void sendData() {
        String enteredLogin = loginField.getText();
        String enteredPassword = String.valueOf(passwordField.getPassword());
        String getUserQuery = "SELECT * FROM users WHERE login=? AND `password`=?";

        PreparedStatement preparedStatement;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASS);
            preparedStatement = connection.prepareStatement(getUserQuery);
            preparedStatement.setString(1, enteredLogin);
            preparedStatement.setString(2, enteredPassword);
            rs = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (rs != null && rs.next()) {
                //  WARNING! this trick is may be unsafe
                loggedUserID = rs.getInt("id");
                loggedUserType = rs.getInt("type");
            } else {
                JOptionPane.showMessageDialog(null, "Wrong login or password!");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        switch (loggedUserType) {
            case 1:
                CustomerNewTask.main(new String[]{});
                this.dispose();
                break;
            case 2:
                ExecutorTakeTask.main(new String[]{});
                this.dispose();
                break;
            case 3:
                AdminPanel.main(new String[]{});
                this.dispose();
                break;
            default:
                // There is an error somewhere, no such type of users
                break;
        }
    }
}
