package window;

import game.Board;
import game.Dice;
import game.Enemy;
import game.Player;
import main.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    private Thread animationThread;
    private Color[] colors = new Color[] {new Color(199, 66, 66), new Color(65, 158, 52), new Color(52, 57, 158)};
    private Color playerColor = new Color(31, 255, 117);
    private Color enemyColor = new Color(232, 72, 85);
    private Color moveColor = new Color(255, 252, 74);
    private ArrayList<Point> movePoints;
    private ArrayList<Point> trapLocation;
    private boolean diceRoll = false;
    private int gameover = 0;

    private Dice dice;

    public GamePanel() {
        Player player = Manager.getGame().getPlayer();
        movePoints = new ArrayList<Point>();
        trapLocation = new ArrayList<Point>();
        setBackground(Color.gray);
        animationThread = new Thread(this::run);
        animationThread.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                onClick(e.getPoint());
            }
        });

        dice = new Dice(6 ,this,825, 285, 90);
        dice.listener = new Dice.Listener() {
            @Override
            public void onRoll() {
                movePoints = Board.getValidMoves(player.x, player.y, dice.state);
                diceRoll = true;
            }
        };
        repaint();
    }

    private void onClick(Point location) {
        int x = location.x; int y = location.y;
        x = Math.floorDiv(x-105, 90);
        y = Math.floorDiv(y-105, 90);
        if (x < 0 || x > 10 || y < 0 || y > 10) {
            return;
        }
        if (x == 8 && y == 2 && !diceRoll) {
            dice.rollDice();
        }
        Player player = Manager.getGame().getPlayer();
        if (movePoints.contains(new Point(x, y))) {
            if (trapLocation.contains(new Point(x, y))) {
                gameover = 2;
                repaint();
                return;
            }
            player.x = x;
            player.y = y;

            movePoints.clear();
            diceRoll = false;
            repaint();
            enemyTurn();
            return;
        }
        if (player.x == x && player.y == y && !diceRoll && !trapLocation.contains(new Point(x, y))) {
            System.out.println("hello");
            trapLocation.add(new Point(x, y));
            enemyTurn();
        }
    }

    public void enemyTurn() {
        for (var e: (ArrayList<Enemy>)Manager.getGame().getEnemies().clone()) {
            int r = (int)(Math.random() * 2);
            if (r == 0 || trapLocation.contains(new Point(e.x, e.y))) {
                ArrayList<Point> m = Board.getValidMoves(e.x, e.y, (int)(Math.random() * 6d)+1);
                int i = (int)(Math.random() * m.size());
                e.x = m.get(i).x;
                e.y = m.get(i).y;
                if (trapLocation.contains(new Point(e.x, e.y))) {
                    Manager.getGame().getEnemies().remove(e);
                }
            }else {
                trapLocation.add(new Point(e.x, e.y));
            }
        }
        if (Manager.getGame().getEnemies().size() == 0) {
            gameover = 1;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Drawing Board
        if (gameover == 0) {
            g.setColor(Color.darkGray);
            g.fillRect(105,105, 990, 990);
            int c = 0;
            for (int i=0; i<11; i++) {
                g.setColor(colorPicker(c));
                c++;
                g.fillRect(105, 105 + (i * 90), 90, 90);
                g.fillRect(555, 105 + (i * 90), 90, 90);
                g.fillRect(1005, 105 + (i * 90), 90, 90);
                g.setColor(Color.darkGray);
                g.fillRect(110, 110 + (i * 90), 80, 80);
                g.fillRect(560, 110 + (i * 90), 80, 80);
                g.fillRect(1010, 110 + (i * 90), 80, 80);
            }
            c+=2;
            for (int i=0; i<4; i++) {
                g.setColor(colorPicker(c));
                c+=2;
                g.fillRect(195 + (i*90), 105, 90, 90);
                g.setColor(Color.darkGray);
                g.fillRect(200 + (i*90), 110, 80, 80);
                g.setColor(colorPicker(c));
                c+=2;
                g.fillRect(195 + (i*90), 555, 90, 90);
                g.setColor(Color.darkGray);
                g.fillRect(200 + (i*90), 560, 80, 80);
                g.setColor(colorPicker(c));
                c++;
                g.fillRect(195 + (i*90), 1005, 90, 90);
                g.setColor(Color.darkGray);
                g.fillRect(200 + (i*90), 1010, 80, 80);
            }
            c++;
            for (int i=0; i<4; i++) {
                g.setColor(colorPicker(c));
                c+=2;
                g.fillRect(645 + (i*90), 105, 90, 90);
                g.setColor(Color.darkGray);
                g.fillRect(650 + (i*90), 110, 80, 80);
                g.setColor(colorPicker(c));
                c+=2;
                g.fillRect(645 + (i*90), 555, 90, 90);
                g.setColor(Color.darkGray);
                g.fillRect(650 + (i*90), 560, 80, 80);
                g.setColor(colorPicker(c));
                c++;
                g.fillRect(645 + (i*90), 1005, 90, 90);
                g.setColor(Color.darkGray);
                g.fillRect(650 + (i*90), 1010, 80, 80);
            }

            //Draw Player
            g.setColor(playerColor);
            Player player = Manager.getGame().getPlayer();
            g.fillRect(110 + (player.x * 90), 110 + (player.y * 90), 80, 80);

            //Draw Enemies
            g.setColor(enemyColor);
            ArrayList<Enemy> enemies = Manager.getGame().getEnemies();
            for (var e: enemies) {
                g.fillRect(110 + (e.x * 90), 110 + (e.y * 90), 80, 80);
            }

            //Draw Moves
            g.setColor(moveColor);
            for (var p: movePoints) {
                g.fillRect(110 + (p.x * 90), 110 + (p.y * 90), 80, 80);
            }

            //Draw Dice
            dice.draw(g);
        }else if (gameover == 1) {
            setBackground(Color.BLACK);
            g.setColor(new Color(97, 205, 93, 255));
            g.setFont(g.getFont().deriveFont(50f));
            g.drawString("Victory",500 - (g.getFontMetrics().stringWidth("Victory")/4), 500 - (g.getFontMetrics().getHeight()/2));
        }else if (gameover == 2) {
            setBackground(Color.BLACK);
            g.setColor(new Color(205, 109, 93, 255));
            g.setFont(g.getFont().deriveFont(50f));
            g.drawString("Defeat",500 - (g.getFontMetrics().stringWidth("Defeat")/4), 500 - (g.getFontMetrics().getHeight()/2));
        }

    }

    public Color colorPicker(int i) {
        return colors[i%colors.length];
    }


    public void run() {
        while (true) {

            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
