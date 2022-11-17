package main;

import game.Board;
import game.Game;

public class Manager {

    private static Game game;

    public static void main(String[] args) {
        Board.init();
        game = new Game();
        WindowManager.init();
    }

    public static Game getGame() {
        return game;
    }

}