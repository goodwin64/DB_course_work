import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 26.04.2016.
 */
public class NewTask extends JFrame {

    private JButton submitButton;
    private JPanel rootPanel;
    private JTextField subjectTextField;
    private JTextPane taskTextArea;
    private JTextField taskTextField;
    private JTextField commentTextField;
    private JComboBox subjectsComboBox;
    private JComboBox tasksComboBox;
    private JTextField deadlineTextField;
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
            subjectsComboBox.addItem(subject);
        }

        subjectsComboBox.addActionListener(e -> {
            tasksComboBox.removeAllItems();
            String[] tasks = specialTasks.get(subjectsComboBox.getSelectedItem());
            if (tasks != null) {
                for (String commonTask : commonTasks) {
                    tasksComboBox.addItem(commonTask);
                }
                for (String specialTask : tasks) {
                    tasksComboBox.addItem(specialTask);
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
        submitButton.addActionListener(e -> {
            /* Entered date must be the following:
             * now < date < (now + 3 month)
             */
            Date enteredDate = new Date();
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                enteredDate = format.parse(formattedDateTimeField.getText());
            } catch (ParseException pe) {
                throw new IllegalArgumentException();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 3);
            Date inThreeMonths = calendar.getTime();

            boolean isPast = new Date().compareTo(enteredDate) == 1;
            boolean isFuture = inThreeMonths.compareTo(enteredDate) == -1;

            if (isPast || isFuture) {
                JOptionPane.showMessageDialog(NewTask.this, "Wrong date!");
            } else {
                JOptionPane.showMessageDialog(NewTask.this, "Task added.");
                // TODO: 26.04.2016 add task accept
            }
        });

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
