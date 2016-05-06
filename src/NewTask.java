import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 26.04.2016.
 */
public class NewTask extends JFrame {

    private JButton submitButton;
    private JPanel rootPanel;
    private JTextPane descriptionTextArea;
    private JComboBox subjectsComboBox;
    private JComboBox tasksComboBox;
    private JCalendar jCalendar;
    private JPanel tasksPanel;


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


        /* Dead-line date must be the following:
         * tomorrow < date < (now + 90 days)
         */
        Date now = new Date();
        Date tomorrow = new Date(now.getTime() + (long) (24 * 3600 * 1000));
        Date inThreeMonths = new Date(now.getTime() + 90L * 24 * 3600 * 1000);
        jCalendar.setSelectableDateRange(tomorrow, inThreeMonths);


        // "Submit" button click
        submitButton.addActionListener(e -> {
            boolean isNotPast = jCalendar.getDate().compareTo(new Date()) == 1;
            if (checkSubject() && isNotPast) {
                sendData(Main.loggedUserID);
                Object[] options = {"Yes, add another task", "No, quit"};
                int choice = JOptionPane.showOptionDialog(null,
                        "Task added. Would you like to add another task or quit?", "Task added",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (choice == 1) {
                    System.exit(0);
                }
            } else if (!checkSubject()) {
                JOptionPane.showMessageDialog(NewTask.this, "Choose a subject");
            } else {
                JOptionPane.showMessageDialog(NewTask.this, "Choose a dead-line date");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("New task");
        frame.setContentPane(new NewTask().rootPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public boolean checkSubject() {
        return !subjectsComboBox.getSelectedItem().equals("<Choose your subject>");
    }

    private void sendData(int id) {
        String getUserQuery = "INSERT INTO " +
                "orders (id_customer, `subject`, task, description, datetime_start, datetime_end, price) " +
                "VALUES (?, ?, ?, ?, NOW(), ?, ?)";

        PreparedStatement preparedStatement;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    Authorization.SQL_URL,
                    Authorization.SQL_USER,
                    Authorization.SQL_PASS
            );
            preparedStatement = connection.prepareStatement(getUserQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, String.valueOf(subjectsComboBox.getSelectedItem()));
            preparedStatement.setString(3, String.valueOf(tasksComboBox.getSelectedItem()));
            preparedStatement.setString(4, descriptionTextArea.getText());
            preparedStatement.setDate(5, new java.sql.Date(jCalendar.getDate().getTime()));
            preparedStatement.setInt(6, 200);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
