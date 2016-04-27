import javax.swing.*;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 26.04.2016.
 */
public class Authorization extends JFrame {
    private JPanel panel1;
    private JPanel Authorization;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField passwordTextField;
    private JCheckBox rememberMeCheckBox;
    private JButton enterButton;
    private JPasswordField passwordField1;


    public Authorization() {
        super("Authorization");

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        enterButton.addActionListener(e -> {
            // TODO: 27.04.2016 check name and pass
            JOptionPane.showMessageDialog(Authorization.this, "Authorization complete!");
        });

        setVisible(true);
    }
}
