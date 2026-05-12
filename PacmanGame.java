package Project1;

import java.util.Scanner;

class GameBoard {

    private int size = 10;

    private char[][] maze = {
            {'#','#','#','#','#','#','#','#','#','#'},
            {'#','.','.','.','.','.','.','.','.','#'},
            {'#','.','#','#','.','#','#','.','.','#'},
            {'#','.','.','#','.','.','#','.','.','#'},
            {'#','#','.','#','#','.','#','#','.','#'},
            {'#','.','.','.','.','.','.','#','.','#'},
            {'#','.','#','#','#','#','.','#','.','#'},
            {'#','.','.','.','.','#','.','.','.','#'},
            {'#','.','.','#','.','.','.','#','.','#'},
            {'#','#','#','#','#','#','#','#','#','#'}
    };

    public void display(Pacman p, Ghost g) {

        System.out.println();

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                if (p.getX() == i && p.getY() == j) {
                    System.out.print("P ");
                }

                else if (g.getX() == i && g.getY() == j) {
                    System.out.print("G ");
                }

                else {
                    System.out.print(maze[i][j] + " ");
                }
            }

            System.out.println();
        }
    }

    public boolean isWall(int x, int y) {
        return maze[x][y] == '#';
    }

    public void checkFood(Pacman p) {

        if (maze[p.getX()][p.getY()] == '.') {

            maze[p.getX()][p.getY()] = ' ';
            p.increaseScore();
        }
    }

    public int getSize() {
        return size;
    }
}

class Pacman {

    private int x;
    private int y;

    private int score;

    public Pacman(int x, int y) {

        this.x = x;
        this.y = y;

        score = 0;
    }

    public void move(int dx, int dy) {

        x += dx;
        y += dy;
    }

    public void increaseScore() {
        score += 10;
    }

    public int getScore() {
        return score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class Ghost {

    private int x;
    private int y;

    public Ghost(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public void moveTowardsPacman(Pacman p, GameBoard board) {

        int bestX = x;
        int bestY = y;

        double minDistance = Double.MAX_VALUE;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {

            int newX = x + dx[i];
            int newY = y + dy[i];

            if (newX >= 0 &&
                    newX < board.getSize() &&
                    newY >= 0 &&
                    newY < board.getSize() &&
                    !board.isWall(newX, newY)) {

                double distance = Math.sqrt(
                        Math.pow(p.getX() - newX, 2) +
                                Math.pow(p.getY() - newY, 2)
                );

                if (distance < minDistance) {

                    minDistance = distance;

                    bestX = newX;
                    bestY = newY;
                }
            }
        }

        x = bestX;
        y = bestY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class Food {

    private int x;
    private int y;

    private boolean eaten;

    public Food(int x, int y) {

        this.x = x;
        this.y = y;

        eaten = false;
    }

    public void eat() {
        eaten = true;
    }

    public boolean isEaten() {
        return eaten;
    }
}

public class PacmanGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        GameBoard board = new GameBoard();

        Pacman pacman = new Pacman(1, 1);

        Ghost ghost = new Ghost(8, 8);

        boolean running = true;

        System.out.println("===== PACMAN GAME =====");
        System.out.println("Controls: W A S D");

        while (running) {

            System.out.println("\nScore: " + pacman.getScore());

            board.display(pacman, ghost);

            System.out.print("Move (W/A/S/D): ");

            char move = sc.next().toUpperCase().charAt(0);

            int dx = 0;
            int dy = 0;

            switch (move) {

                case 'W':
                    dx = -1;
                    break;

                case 'S':
                    dx = 1;
                    break;

                case 'A':
                    dy = -1;
                    break;

                case 'D':
                    dy = 1;
                    break;

                default:
                    System.out.println("Invalid Input");
            }

            int newX = pacman.getX() + dx;
            int newY = pacman.getY() + dy;

            if (newX >= 0 &&
                    newX < board.getSize() &&
                    newY >= 0 &&
                    newY < board.getSize() &&
                    !board.isWall(newX, newY)) {

                pacman.move(dx, dy);
            }

            board.checkFood(pacman);

            ghost.moveTowardsPacman(pacman, board);

            if (pacman.getX() == ghost.getX() &&
                    pacman.getY() == ghost.getY()) {

                board.display(pacman, ghost);

                System.out.println("\nGAME OVER!");
                System.out.println("Ghost caught Pacman!");

                running = false;
            }
        }

        System.out.println("\nFinal Score: " + pacman.getScore());

        sc.close();
    }
}
