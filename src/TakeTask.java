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
        // TODO: 06.05.2016 Reformat this nasty look crutch, move SQL actions into separate method
        String getUserQuery = "SELECT * FROM orders WHERE is_done=0";
        String getSizeQuery = "SELECT COUNT(*) AS total FROM orders WHERE is_done=0";

        PreparedStatement preparedStatement1;
        ResultSet resultSet1 = null;
        int tableRowsCount = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    Authorization.SQL_URL,
                    Authorization.SQL_USER,
                    Authorization.SQL_PASS
            );
            Statement statement = connection.createStatement();
            preparedStatement1 = connection.prepareStatement(getUserQuery);
            resultSet1 = preparedStatement1.executeQuery();

            ResultSet resultSet2 = statement.executeQuery(getSizeQuery);
            resultSet2.next();
            tableRowsCount = resultSet2.getInt("total");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String[] columnNames = {"Subject", "Task", "Description", "Time left", "Price"};
        /* getSqlTable() is not in use because it doesn't return rows count
           this nuance have to be fixed */
        // ResultSet resultSet = getSqlTable();
        Object[][] data = new Object[tableRowsCount][5];
        int i = 0;
        try {
            if (resultSet1 != null) {
                while (resultSet1.next()) {
                    int j = 0;
                    data[i][j++] = resultSet1.getString("subject");
                    data[i][j++] = resultSet1.getString("task");
                    data[i][j++] = resultSet1.getString("description");
                    data[i][j++] = timeLeftInSeconds(resultSet1.getDate("datetime_end"));
                    data[i++][j++] = resultSet1.getInt("price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JTable(data, columnNames);
    }

    private int timeLeftInSeconds(Date deadLine) {
        Date now = new Date();
        long diff = deadLine.getTime() - now.getTime();
        return (int) (diff / 1000);
    }
}
