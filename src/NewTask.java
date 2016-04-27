import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private JTextField deadLineYYYYMMTextField;
    private JPanel taskPanel;
    private JFormattedTextField formattedDateTimeField;


    public NewTask() {
        super("New task");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Subjects
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

        // Dead-line
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = new Date();
        formattedDateTimeField.setValue(dateFormat.format(date));
        try {
            MaskFormatter formatter = new MaskFormatter("####.##.## ##:##");
            formatter.setPlaceholderCharacter('_');
            formatter.install(formattedDateTimeField);
        } catch (ParseException e) {
            System.err.println("Unable to add SSN");
        }

        // Button click
        clickButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(NewTask.this, "Task added.");
            // TODO: 26.04.2016 add task accept
            // TODO: 27.04.2016 check date & time, show message if it's wrong
        });

        setVisible(true);
    }
}
