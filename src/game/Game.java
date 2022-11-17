package game;

import java.util.ArrayList;

public class Game {

    private Player player;
    private ArrayList<Enemy> enemies;

    public Game() {
        player = new Player();
        enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy());
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

}
