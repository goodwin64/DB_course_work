import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 06.05.2016.
 */
public class ExecutorTakeTask extends JFrame {

    private JPanel rootPanel;
    private JPanel tasksPanel;
    private JButton takeTaskButton;
    Timer timer;
    static DefaultTableModel model;

    public ExecutorTakeTask() {
        super("Take a task");
        setContentPane(rootPanel);
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.PAGE_AXIS));
        String[] tableColumnNames = {"Subject", "Task", "Description", "Time left", "Price"};
        String[] databaseColumnNames = {"subject", "task", "description", "datetime_end", "price"};
        JTable activeTasks = createTableFromDB(tableColumnNames, databaseColumnNames);
        model = (DefaultTableModel)activeTasks.getModel();
        tasksPanel.add(activeTasks, 0);
        JScrollPane scrollPane = new JScrollPane(activeTasks);
        tasksPanel.add(scrollPane);
        timer.start();

        activeTasks.getColumnModel().getColumn(0).setPreferredWidth(50);
        activeTasks.getColumnModel().getColumn(1).setPreferredWidth(80);
        activeTasks.getColumnModel().getColumn(2).setPreferredWidth(300);
        activeTasks.getColumnModel().getColumn(3).setPreferredWidth(50);
        activeTasks.getColumnModel().getColumn(4).setPreferredWidth(20);

        //model.setValueAt("hi!", 1, 1);
    }

    class TimerTick implements ActionListener {
        int countdown, rowIndex, colIndex;

        public TimerTick(int countdown, int i, int j) {
            this.countdown = countdown;
            rowIndex = i;
            colIndex = j;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            countdown--;
            model.setValueAt(String.valueOf(secondsToTime(countdown)), rowIndex, colIndex);
            if (countdown <= 0) {
                timer.stop();
                model.setValueAt("Time out", rowIndex, colIndex);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Take a task");
        frame.setContentPane(new ExecutorTakeTask().rootPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JTable createTableFromDB(String[] columnNames, String[] databaseColumnNames) {
        // TODO: 06.05.2016 Reformat this nasty look crutch, move SQL actions into separate method
        String getUserQuery = "SELECT * FROM orders WHERE is_done=0";
        String getSizeQuery = "SELECT COUNT(*) AS total FROM orders WHERE is_done=0";

        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        int tableRowsCount = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    Authorization.SQL_URL,
                    Authorization.SQL_USER,
                    Authorization.SQL_PASS
            );
            Statement statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(getUserQuery);
            resultSet = preparedStatement.executeQuery();

            ResultSet resultSet2 = statement.executeQuery(getSizeQuery);
            resultSet2.next();
            tableRowsCount = resultSet2.getInt("total");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        /* getSqlTable() is not in use because it doesn't return rows count
           this nuance have to be fixed */
        // ResultSet resultSet = getSqlTable();
        int tableColumnsCount = columnNames.length;
        Object[][] data = new Object[tableRowsCount][tableColumnsCount];
        int i = 0;
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    int j = 0;
                    data[i][j++] = resultSet.getString(databaseColumnNames[j-1]);
                    data[i][j++] = resultSet.getString(databaseColumnNames[j-1]);
                    data[i][j++] = resultSet.getString(databaseColumnNames[j-1]);
                    data[i][j] = timeToWrite(
                            timeLeftInSeconds(
                                    resultSet.getDate(databaseColumnNames[j])
                            ), i, j++
                    );
                    data[i++][j++] = resultSet.getInt(databaseColumnNames[j-1]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        return new JTable(model);
    }

    private String timeToWrite(int seconds, int rowIndex, int colIndex) {
        if (seconds <= 0) {
            return "Time out";
        }
        TimerTick tick = new TimerTick(seconds, rowIndex, colIndex);
        timer = new Timer(1000, tick);
        return secondsToTime(seconds);
    }

    private int timeLeftInSeconds(Date deadLine) {
        Date now = new Date();
        long diff = deadLine.getTime() - now.getTime();
        return (int) (diff / 1000);
    }

    public static String secondsToTime(int timeInSeconds) {
        int days = timeInSeconds / 86400;
        int remainder = timeInSeconds - days * 86400;
        int hours = remainder / 3600;
        remainder = remainder - hours * 3600;
        int minutes = remainder / 60;
        remainder = remainder - minutes * 60;
        int seconds = remainder;
        return String.format("%d days %02d:%02d:%02d", days, hours, minutes, seconds);
    }
}
