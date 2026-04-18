import java.util.Scanner;
import java.util.Random;
import java.util.*;
class GameBoard {
    private int size=10;
    private char[][] maze={
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
        for (int i=0;i<size;i++) {
            for (int j=0;j<size;j++) {
                if (p.getX()==i && p.getY()==j) {
                    System.out.print("P ");
                }
                else if (g.getX()==i && g.getY()==j) {
                    System.out.print("G ");
                }
                else {
                    System.out.print(maze[i][j] + " ");
                }            }
            System.out.println();
        }    }
    public boolean isWall(int x, int y) {
        return maze[x][y]=='#';
    }
    public void checkFood(Pacman p) {
        if (maze[p.getX()][p.getY()]=='.') {
            maze[p.getX()][p.getY()]=' ';
            p.increaseScore();
        }    }
    public int getSize() {
        return size;
    }
}
class Pacman {
    private int x, y;
    private int score;
    public Pacman(int x, int y) {
        this.x=x;
        this.y=y;
        score=0;
    }
    public void move(int dx, int dy) {
        x +=dx;
        y +=dy;
    }
    public void increaseScore() {
        score++;
    }
    public int getScore() {
        return score;
    }
    public int getX() {return x; }
    public int getY() {return y; }
}
class Ghost {
    private int x, y;
    private Random rand=new Random();
    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void randomMove() {
        int dx=rand.nextInt(3)-1;
        int dy=rand.nextInt(3)-1;
        x += dx;
        y += dy;
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {return x; }
    public int getY() {return y; }
}
class Food {
    private int x, y;
    private boolean eaten;
    public Food(int x,int y) {
        this.x=x;
        this.y=y;
        eaten=false;
    }
    public void eat() {
        eaten=true;
    }
    public boolean isEaten() {
        return eaten;
    }}
public class PacmanGame {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        GameBoard board=new GameBoard();
        Pacman pacman=new Pacman(1, 1);
        Ghost ghost=new Ghost(8, 8);
        boolean running=true;
        while (running) {
           System.out.println("\nScore: " + pacman.getScore());
           board.display(pacman, ghost);
          System.out.print("Move (W/A/S/D): ");
            char move=sc.next().toUpperCase().charAt(0);
            int dx=0,dy=0;
            switch (move) {
                case 'W':dx=-1; break;
                case 'S':dx=1; break;
                case 'A':dy=-1; break;
                case 'D':dy=1; break;
                default: System.out.println("Invalid Input");
            }
            int newX=pacman.getX() + dx;
            int newY=pacman.getY() + dy;
            if (newX >= 0 && newX < board.getSize() &&
                    newY >= 0 && newY < board.getSize() &&
                    !board.isWall(newX, newY)) {
                pacman.move(dx, dy);
            }
            int oldX=ghost.getX();
            int oldY=ghost.getY();
            ghost.randomMove();
            if (ghost.getX() < 0 ||ghost.getX()>=board.getSize() ||
                    ghost.getY()<0 ||ghost.getY()>=board.getSize() ||
                    board.isWall(ghost.getX(), ghost.getY())) {
                ghost.setPosition(oldX, oldY);}
            board.checkFood(pacman);
            if (pacman.getX()==ghost.getX() &&
                    pacman.getY()==ghost.getY()) {
                System.out.println("Game Over!");
                running=false;
            }       }
        System.out.println("Final Score: " + pacman.getScore());
       sc.close();
    }}
