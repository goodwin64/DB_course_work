import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 07.05.2016.
 */
public class CustomerMenu extends JMenuBar {
    public CustomerMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem myOrdersItem = new JMenuItem("My orders");
        fileMenu.add(myOrdersItem);
        myOrdersItem.addActionListener(e -> CustomerOrders.main(new String[]{}));

        JMenuItem newTaskItem = new JMenuItem("New task");
        fileMenu.add(newTaskItem);
        newTaskItem.addActionListener(e -> CustomerNewTask.main(new String[]{}));
        fileMenu.addSeparator();

        JMenuItem closeItem = new JMenuItem("Close", new ImageIcon("CloseIcon.jpg"));
        fileMenu.add(closeItem);
        closeItem.addActionListener(e -> System.exit(0));
        closeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));

        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem helpItem = helpMenu.add("Info");
        // TODO: 07.05.2016 add "user help" info window

        JMenuItem aboutItem = helpMenu.add("About program");
        // TODO: 07.05.2016 add "about" window
    }
}
