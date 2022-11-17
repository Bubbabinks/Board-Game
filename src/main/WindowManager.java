package main;

import window.GamePanel;
import window.Window;

public class WindowManager {

    private static Window window;
    private static GamePanel gamePanel;

    public static void init() {
        window = new Window();
        gamePanel = new GamePanel();
        window.setPanel(gamePanel);
    }

}
