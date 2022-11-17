package game;

import javax.swing.*;
import java.awt.*;

public class Dice {

    private boolean[] activePoints = new boolean[7];

    private JPanel panel;
    private Thread thread;
    private int x, y, size, distance;
    public int state;

    public Listener listener;

    public Dice(int defaultState, JPanel panel, int x, int y, int size) {
        this.x = x; this.y = y; this.size = size; this.panel = panel;
        state = defaultState;
        distance = size/4;
        for (int i=0; i<7; i++) {
            activePoints[i] = false;
        }
        setFace(defaultState);
    }

    public void rollDice() {
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this::run);
            thread.start();
        }
    }

    public void setFace(int face) {
        state = face;
        for (int i=0; i<7; i++) {
            activePoints[i] = false;
        }
        if (face == 1) {
            activePoints[3] = true;
        }else if (face == 2) {
            activePoints[5] = true;
            activePoints[1] = true;
        }else if (face == 3) {
            activePoints[5] = true;
            activePoints[3] = true;
            activePoints[1] = true;
        }else if (face == 4) {
            activePoints[0] = true;
            activePoints[5] = true;
            activePoints[6] = true;
            activePoints[1] = true;
        }else if (face == 5) {
            activePoints[0] = true;
            activePoints[5] = true;
            activePoints[1] = true;
            activePoints[6] = true;
            activePoints[3] = true;
        }else {
            activePoints[0] = true;
            activePoints[2] = true;
            activePoints[5] = true;
            activePoints[1] = true;
            activePoints[4] = true;
            activePoints[6] = true;
        }
    }

    public void run() {
        for (int i=0; i<15; i++) {
            int roll = (int)(Math.random() * 6d)+1;
            setFace(roll);
            panel.repaint();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (listener != null) {
            listener.onRoll();
        }
    }

    public interface Listener {
        public void onRoll();
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);
        g.setColor(Color.BLACK);
        if (activePoints[0]) {
            g.fillRect(x + distance-5, y + distance-5, 10, 10);
        }
        if (activePoints[1]) {
            g.fillRect(x + (distance*3)-5, y + (distance)-5, 10, 10);
        }
        if (activePoints[2]) {
            g.fillRect(x + (distance)-5, y + (distance*2)-5, 10, 10);
        }
        if (activePoints[3]) {
            g.fillRect(x + (distance*2)-5, y + (distance*2)-5, 10, 10);
        }
        if (activePoints[4]) {
            g.fillRect(x + (distance*3)-5, y + (distance*2)-5, 10, 10);
        }
        if (activePoints[5]) {
            g.fillRect(x + (distance)-5, y + (distance*3)-5, 10, 10);
        }
        if (activePoints[6]) {
            g.fillRect(x + (distance*3)-5, y + (distance*3)-5, 10, 10);
        }

    }

}
