import javax.swing.*;
import java.awt.*;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 11.05.2016.
 */
public class AdminUsersList extends JFrame {
    private JTable table1;
    private JPanel rootPanel;

    public AdminUsersList() {
        super("Users list");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // TODO: 20.05.2016 IMPLEMENTATION 
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("New task");
        Container root = new AdminUsersList().rootPanel;
        frame.setContentPane(root);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // menu
        root.setLayout(new BoxLayout(root, BoxLayout.PAGE_AXIS));
        root.add(new CustomerMenu(frame)); // TODO: 11.05.2016 fix menu 

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
