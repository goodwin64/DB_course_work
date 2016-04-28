import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 26.04.2016.
 */
public class Authorization extends JFrame {
    JTextField loginField;
    JPasswordField passwordField;

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

        // Second horizontal panel (for password)
        Box box2 = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        box2.add(passwordLabel);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(passwordField);

        // Third horizontal panel (for buttons)
        Box box3 = Box.createHorizontalBox();
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        box3.add(Box.createHorizontalGlue());
        box3.add(ok);
        box3.add(Box.createHorizontalStrut(12));
        box3.add(cancel);

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

        setContentPane(mainBox);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
