package demo.snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("All")
public class Snake {

    private final int worldWidth;
    private final int worldHeight;

    private Direction direction;
    private Direction nextDirection;
    private boolean gameOver;
    private int score = 0;

    private Point food;
    private final List<Point> snakeParts;

    private static final Random rng = new Random();

    public Snake(int width, int height) {
        this.worldWidth = width;
        this.worldHeight = height;
        this.direction = Direction.DOWN;
        this.nextDirection = Direction.DOWN;
        this.gameOver = false;

        this.snakeParts = new ArrayList<>();
        this.snakeParts.add(new Point(width / 2, height / 2));
        generateFood();
    }

    public void move() {
        direction = nextDirection;
        Point futureHead = new Point(snakeParts.get(0).x + direction.getX(), snakeParts.get(0).y + direction.getY());
        if(!pointWithinWorld(futureHead))
            gameOver = true; // Collided with wall

        if(snakeParts.stream().filter(tail -> tail.equals(futureHead)).count() > 0)
            gameOver = true; // Collided with tail

        snakeParts.add(0, futureHead);
        snakeParts.remove(snakeParts.size()-1);

        if(futureHead.equals(food)) {
            snakeParts.add(snakeParts.get(snakeParts.size()-1));
            generateFood();
        }
    }

    public void reset() {
        direction = Direction.DOWN;
        nextDirection = Direction.DOWN;
        gameOver = false;

        snakeParts.clear();
        snakeParts.add(new Point(worldWidth / 2, worldHeight / 2));
        generateFood();
    }

    private void generateFood() {
        boolean validLoc = false;
       while(!validLoc) {
           int locIndex = rng.nextInt(worldWidth * worldHeight);
           Point attempt = new Point(locIndex % worldWidth, locIndex / worldHeight);

           if(!snakeParts.contains(attempt)) {
               validLoc = true;
               food = attempt;
           }
       }
    }

    private boolean pointWithinWorld(Point p) {
        return p.x >= 0 && p.x < worldWidth && p.y >= 0 && p.y < worldHeight;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction d) {
        if(!direction.isOpposite(d))
            nextDirection = d;
    }

    public List<Point> getBodyLocations() {
        return snakeParts.subList(1, snakeParts.size());
    }

    public Point getHeadLocation() {
        return snakeParts.get(0);
    }

    public Point getFoodLocation() {
        return food;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
}