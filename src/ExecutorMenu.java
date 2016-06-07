import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 07.05.2016.
 */
public class ExecutorMenu {
    public ExecutorMenu(JFrame frame) {
        //створюємо рядок меню
        JMenuBar menuBar = new JMenuBar();
        //додаємо рядок меню у фрейм
        frame.setJMenuBar(menuBar);

        //Меню "Файл"
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        //додаємо пункти в меню файл
        JMenuItem openItem = new JMenuItem("Відкрити");
        fileMenu.add(openItem);
        //додаємо розділювач
        fileMenu.addSeparator();


        JMenuItem closeItem = new JMenuItem("Закрити", new ImageIcon("CloseIcon.jpg"));
        fileMenu.add(closeItem);
        //додаємо обробник подій використавши безіменний внутрішній клас
        closeItem.addActionListener(e -> System.exit(0));


        // додаємо гарячу клавішу Ctrl-X до пункту "Закрити"
        closeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));

        // меню "Вигляд"
        JMenu viewMenu = new JMenu("Вигляд");
        menuBar.add(viewMenu);

        // меню "Допомога"
        JMenu helpMenu = new JMenu("Допомога");
        menuBar.add(helpMenu);
        // можна використати метод add()
        // для додавання пунктів в меню зразу ж при створенні
        JMenuItem helpItem = helpMenu.add("Довідка");
        JMenuItem aboutItem = helpMenu.add("Про програму");
    }
}
