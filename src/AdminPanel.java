import javax.swing.*;
import java.awt.*;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 11.05.2016.
 */
public class AdminPanel extends JFrame {
    private JButton showUsersButton;
    private JButton showOrdersButton;
    private JPanel rootPanel;
    private JLabel greetingLabel;

    public AdminPanel() {
        super("Administrator panel");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        greetingLabel.setText("Hi, user #" + Authorization.loggedUserID);

        showUsersButton.addActionListener(e -> {
            AdminUsersList.main(new String[]{});
            // TODO: 11.05.2016 show users list (table?)
        });

        showOrdersButton.addActionListener(e -> {
            // TODO: 11.05.2016 show orders list (table?)
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("New task");
        Container root = new AdminPanel().rootPanel;
        frame.setContentPane(root);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // menu
        root.setLayout(new BoxLayout(root, BoxLayout.PAGE_AXIS));
        root.add(new CustomerMenu(frame)); // TODO: 11.05.2016 change menu

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
