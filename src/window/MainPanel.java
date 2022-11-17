package window;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        setPreferredSize(Window.size);
        setLayout(new BorderLayout());
    }

    public void setPanel(JPanel panel) {
        removeAll();
        revalidate();
        add(panel, BorderLayout.CENTER);
        repaint();
    }

}
