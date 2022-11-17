package game;

import main.Manager;

import java.awt.*;
import java.util.ArrayList;

public class Board {

    private static boolean board[][] = new boolean[11][11];
    private static boolean redSquares[][] = new boolean[11][11];

    public static void init() {
        for (int x=0; x<11; x++) {
            for (int y=0; y<11; y++) {
                if (x == 0 || x == 5 || x == 10 || y == 0 || y == 5 || y == 10) {
                    board[x][y] = true;
                }else {
                    board[x][y] = false;
                }
            }
        }
        for (int x=0; x<11; x++) {
            for (int y=0; y<11; y++) {
                redSquares[x][y] = false;
            }
        }
        redSquares[0][0] = true;
        redSquares[2][0] = true;
        redSquares[5][0] = true;
        redSquares[7][0] = true;
        redSquares[10][0] = true;
        redSquares[0][3] = true;
        redSquares[5][3] = true;
        redSquares[10][3] = true;
        redSquares[1][5] = true;
        redSquares[4][5] = true;
        redSquares[6][5] = true;
        redSquares[9][5] = true;
        redSquares[0][6] = true;
        redSquares[5][6] = true;
        redSquares[10][6] = true;
        redSquares[0][9] = true;
        redSquares[5][9] = true;
        redSquares[10][9] = true;
        redSquares[3][10] = true;
        redSquares[8][10] = true;
    }

    public static ArrayList<Point> getValidMoves(int x, int y, int totalMoves) {
        ArrayList<Point> points = new ArrayList<Point>();
        Face face = new Face(x, y, x, y, 0);
        ArrayList<Face> faces = new ArrayList<Face>();
        faces.add(face);
        for (int i=1; i<totalMoves; i++) {
            for (var f: (ArrayList<Face>)faces.clone()) {
                for (var p: f.getPoints()) {
                    faces.add(new Face(p.x, p.y, f.x, f.y, i+1));
                }
                faces.remove(f);
            }
        }
        for (var f: (ArrayList<Face>)faces.clone()) {
            for (var p: f.getPoints()) {
                points.add(p);
            }
        }
        Player player = Manager.getGame().getPlayer();
        ArrayList<Enemy> enemies = Manager.getGame().getEnemies();
        for (var p: (ArrayList<Point>)points.clone()) {
            if (p.x == player.x && p.y == player.y) {
                points.remove(p);
            }
            for (var e: enemies) {
                if (p.x == e.x && p.y == e.y) {
                    points.remove(p);

                }
            }
        }
        return points;
    }

    public static boolean checkRedSquare(int x, int y) {
        return redSquares[x][y];
    }

    private static class Face {

        public int x, y, px, py, step;

        public Face(int x, int y, int px, int py, int step) {
            this.x = x;
            this.y = y;
            this.px = px;
            this.py = py;
            this.step = step;
        }

        public ArrayList<Point> getPoints() {
            ArrayList<Point> points = new ArrayList<Point>();
            Point temp = null;
            if (!(x + 1 == px && y == py) && (x + 1 < 11 && x + 1 >= 0) && board[x + 1][y]) {
                points.add(new Point(x + 1, y));
            }
            if (!(x - 1 == px && y == py) && (x - 1 < 11 && x - 1 >= 0) && board[x - 1][y]) {
                points.add(new Point(x - 1, y));
            }
            if (!(x == px && y + 1 == py) && (y + 1 < 11 && y + 1 >= 0) && board[x][y + 1]) {
                points.add(new Point(x, y + 1));
            }
            if (!(x == px && y - 1 == py) && (y - 1 < 11 && y - 1 >= 0) && board[x][y - 1]) {
                points.add(new Point(x, y - 1));
            }
            return points;
        }
    }

}
