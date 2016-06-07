import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    private JLabel taskOptionParam;
    private JFormattedTextField taskFactorTextField;
    private JLabel totalAmountLabel;
    private JTextField priceTextField;
    private JTextField totalAmountTextField;
    private JLabel priceInfoLabel;


    public NewTask() {
        super("New task");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        fillSubjectsComboBox();
        fillTasksComboBox();
        fillTasksCountPriceFields();

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
                sendData(Authorization.loggedUserID);
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
        //frame.setResizable(false);
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

    private void fillSubjectsComboBox() {
        String[] subjects = new String[]{"Algebra", "English", "Mechanical drawing"};

        for (String subject : subjects) {
            subjectsComboBox.addItem(subject);
        }
    }

    private void fillTasksComboBox() {
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
    }

    private void fillTasksCountPriceFields() {
        Map<String, String> tasksQuantity = new HashMap<>(10);
        Map<String, String> pricesInfo = new HashMap<>(10);
        Map<String, Double> pricesValue = new HashMap<>(10);

        tasksQuantity.put("Test (A/B/C)", "Count of test questions:");
        tasksQuantity.put("Course work", "Count of pages A4:");
        tasksQuantity.put("Problem", "Count of problems:");
        tasksQuantity.put("Task (several problems)", "Total count of problems:");
        tasksQuantity.put("Theorem proof", "Count of theorems:");
        tasksQuantity.put("Essay", "Count of pages A5:");
        tasksQuantity.put("Writing a verse", "Count of rows:");
        tasksQuantity.put("Figure", "");    // price is common for any type of figure
        tasksQuantity.put("Drawing (several figures)", "Count of figures:");
        tasksQuantity.put("Scheme (several drawings)", "Total count of figures:");

        pricesInfo.put("Test (A/B/C)", "Price per question:");
        pricesInfo.put("Course work", "Price per A4 page:");
        pricesInfo.put("Problem", "Price per problem:");
        pricesInfo.put("Task (several problems)", "Price per problem:");
        pricesInfo.put("Theorem proof", "Price per theorem:");
        pricesInfo.put("Essay", "Price per A5 page:");
        pricesInfo.put("Writing a verse", "Price per row:");
        pricesInfo.put("Figure", "Price per figure:");
        pricesInfo.put("Drawing (several figures)", "Price per figure:");
        pricesInfo.put("Scheme (several drawings)", "Price per figure:");

        pricesValue.put("Test (A/B/C)", 0.1);
        pricesValue.put("Course work", 0.5);
        pricesValue.put("Problem", 1.0);
        pricesValue.put("Task (several problems)", 1.0);
        pricesValue.put("Theorem proof", 2.5);
        pricesValue.put("Essay", 1.5);
        pricesValue.put("Writing a verse", 2.0);
        pricesValue.put("Figure", 5.0);
        pricesValue.put("Drawing (several figures)", 5.0);
        pricesValue.put("Scheme (several drawings)", 5.0);

        tasksComboBox.addActionListener(e -> {
            Object selectedTaskType = tasksComboBox.getSelectedItem();
            if (selectedTaskType == null) {
                taskFactorTextField.setEditable(false);
                taskOptionParam.setText("");
                priceInfoLabel.setText("");
                priceTextField.setText("");
                totalAmountTextField.setText("");
                return;
            }
            String taskType = String.valueOf(selectedTaskType);
            String info = tasksQuantity.get(taskType);
            String priceInfo = pricesInfo.get(taskType);
            double price = pricesValue.get(taskType);

            taskOptionParam.setText(info);
            priceInfoLabel.setText(priceInfo);
            priceTextField.setText(String.valueOf(price));

            if (!Objects.equals(taskType, "Figure")) {
                taskFactorTextField.setEditable(true);
            } else {
                taskFactorTextField.setText("1");
                taskFactorTextField.setEditable(false);
            }

            setTotalAmountTextField();
        });

        taskFactorTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setTotalAmountTextField();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setTotalAmountTextField();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setTotalAmountTextField();
            }
        });
    }

    private void setTotalAmountTextField() {
        Object enteredTasksCountObj = taskFactorTextField.getText();
        if (enteredTasksCountObj != null) {
            String enteredTasksCount = String.valueOf(enteredTasksCountObj).trim();
            if (!enteredTasksCount.equals("")) {
                double price = Double.parseDouble(priceTextField.getText());
                double taskFactor = Integer.parseInt(enteredTasksCount) * price;
                totalAmountTextField.setText(String.valueOf(taskFactor));
            }
        }
    }
}
