package window;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

public class Window {

    public static final Dimension size = new Dimension(1200,1200);

    private JFrame frame;
    private MainPanel mainPanel;

    public Window() {
        //Setting up looks and feel
        FlatDarculaLaf.setup();

        frame = new JFrame("Board Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        mainPanel = new MainPanel();
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setPanel(JPanel panel) {
        mainPanel.setPanel(panel);
    }

}
