import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 26.04.2016.
 */
public class NewTask extends JFrame {

    private JButton clickButton;
    private JPanel rootPanel;
    private JTextField subjectTextField;
    private JTextPane textPane1;
    private JTextField taskTextField;
    private JTextField commentTextField;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField deadLineDdMmTextField;
    private JFormattedTextField FormattedTextField;
    private JPanel taskPanel;


    public NewTask() {
        super("New task");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /*
         * Subjects
         */
        String[] subjects = new String[]{"Algebra", "English", "Mechanical drawing"};
        String[] commonTasks = {"Test (A/B/C)", "Course work"};

        Map<String, String[]> specialTasks = new HashMap<>(3);

        specialTasks.put("Algebra", new String[]{
                "Problem", "Task (several problems)", "Theorem proof"
        });
        specialTasks.put("English", new String[]{
                "Essay", "Writing a verse"
        });
        specialTasks.put("Mechanical drawing", new String[]{
                "Figure", "Drawing (several figures)", "Scheme (several drawings)"
        });

        for (String subject : subjects) {
            comboBox1.addItem(subject);
        }
        //comboBox1.setSelectedIndex(1);

        comboBox1.addActionListener(e -> {
            comboBox2.removeAllItems();
            String[] tasks = specialTasks.get(comboBox1.getSelectedItem());
            if (tasks != null) {
                for (String commonTask : commonTasks) {
                    comboBox2.addItem(commonTask);
                }
                for (String specialTask : tasks) {
                    comboBox2.addItem(specialTask);
                }
            }
        });

        /*
         * Dead-line
         */
        // TODO: 27.04.2016 add datetime formatter

        /*
         * Button click
         */
        clickButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(NewTask.this, "Task added.");
            // TODO: 26.04.2016 add task accept
        });

        setVisible(true);
    }
}
