import javax.swing.*;
import java.sql.*;
import java.util.Date;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 06.05.2016.
 */
public class TakeTask extends JFrame {

    private JPanel rootPanel;
    private JPanel tasksPanel;
    private JButton takeTaskButton;


    public TakeTask() {
        super("Take a task");
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.PAGE_AXIS));
        JTable activeTasks = createTable();
        tasksPanel.add(activeTasks, 0);
        tasksPanel.add(new JScrollPane(activeTasks));

        activeTasks.getColumnModel().getColumn(0).setPreferredWidth(50);
        activeTasks.getColumnModel().getColumn(1).setPreferredWidth(80);
        activeTasks.getColumnModel().getColumn(2).setPreferredWidth(300);
        activeTasks.getColumnModel().getColumn(3).setPreferredWidth(50);
        activeTasks.getColumnModel().getColumn(4).setPreferredWidth(20);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Create a task");
        frame.setContentPane(new TakeTask().rootPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JTable createTable() {
        String[] columnNames = {"Subject", "Task", "Description", "Time left", "Price"};
        ResultSet resultSet = getSqlTable();
        Object[][] data = new Object[10][5];
        int i = 0;
        try {
            while (resultSet.next()) {
                int j = 0;
                data[i][j++] = resultSet.getString("subject");
                data[i][j++] = resultSet.getString("task");
                data[i][j++] = resultSet.getString("description");
                data[i][j++] = timeLeftInSeconds(resultSet.getDate("datetime_end"));
                data[i][j++] = resultSet.getInt("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JTable(data, columnNames);
    }

    private ResultSet getSqlTable() {
        String getUserQuery = "SELECT * FROM orders WHERE is_done=0";

        PreparedStatement preparedStatement;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    Authorization.SQL_URL,
                    Authorization.SQL_USER,
                    Authorization.SQL_PASS
            );
            preparedStatement = connection.prepareStatement(getUserQuery);
            rs = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private int timeLeftInSeconds(Date deadLine) {
        Date now = new Date();
        long diff = deadLine.getTime() - now.getTime();
        return (int) (diff / 1000);
    }
}
