import javax.swing.*;
import java.awt.*;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 11.05.2016.
 */
public class CustomerPanel extends JFrame {
    private JButton newTaskButton;
    private JButton showOrdersButton;
    private JPanel rootPanel;
    private JLabel greetingLabel;

    public CustomerPanel() {
        super("Customer panel");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        greetingLabel.setText("Hi, user #" + Authorization.loggedUserID);

        newTaskButton.addActionListener(e -> {
            CustomerNewTask.main(new String[]{});
            this.dispose();
        });

        showOrdersButton.addActionListener(e -> {
            CustomerOrders.main(new String[]{});
            this.dispose();
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Customer panel");
        Container root = new CustomerPanel().rootPanel;
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
