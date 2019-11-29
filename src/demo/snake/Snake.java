package demo.snake;

import demo.snake.util.Direction;
import demo.snake.util.SnakeUtil;
import neuralnetwork.genetics.GeneticNet;
import neuralnetwork.genetics.interfaces.Simulation;
import neuralnetwork.util.Copyable;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("All")
public class Snake implements Simulation<Snake>, Copyable<Snake>, Serializable
{
    private static final long serialVersionUID = 6L;

    private final int worldWidth;
    private final int worldHeight;

    private Direction direction = Direction.DOWN;
    private Direction nextDirection = Direction.DOWN;
    private boolean gameOver = false;
    private int totalMoves = 0;
    private int score = 0;

    private Point food;
    private int movesTillDead;

    private final List<Point> snakeParts = new ArrayList<>();
    private final List<Point> replayFood = new ArrayList<>();
    private final List<Point> foodLocs = new ArrayList<>();

    private static final Random rng = new Random();

    public Snake(int width, int height) {
        this.worldWidth = width;
        this.worldHeight = height;
        this.direction = Direction.DOWN;
        this.nextDirection = Direction.DOWN;
        this.gameOver = false;
        this.movesTillDead = width*height/2;

        this.snakeParts.add(new Point(width / 2, height / 2));
        generateFood();
    }

    public void move() {
        totalMoves++;
        direction = nextDirection;
        if(movesTillDead-- <= 0) {
            gameOver = true;
            return;
        }

        Point futureHead = new Point(snakeParts.get(0).x + direction.getX(), snakeParts.get(0).y + direction.getY());
        if(!pointWithinWorld(futureHead))
            gameOver = true; // Collided with wall

        if(snakeParts.stream().filter(tail -> tail.equals(futureHead)).count() > 0 && !futureHead.equals(snakeParts.get(snakeParts.size()-1)))
            gameOver = true; // Collided with tail

        snakeParts.add(0, futureHead);
        snakeParts.remove(snakeParts.size()-1);

        if(futureHead.equals(food)) {
            snakeParts.add(snakeParts.get(snakeParts.size()-1));
            score++;
            movesTillDead += worldWidth*worldHeight/2;
            generateFood();
        }
    }

    private void generateFood() {
        if(!replayFood.isEmpty()) {
            food = replayFood.get(0);
            replayFood.remove(0);
            foodLocs.add(food);
            return;
        }

        int i = worldWidth * worldHeight * 2;
        boolean validLoc = false;
        while(!validLoc) {
            i--;
            int locIndex = rng.nextInt(worldWidth * worldHeight);
            Point attempt = new Point(locIndex % worldWidth, locIndex / worldHeight);

            if(!snakeParts.contains(attempt)) {
                validLoc = true;
                food = attempt;
                foodLocs.add(food);
            }

            if(i < 0) {
                gameOver = snakeWon();
                food = null;
                return;
            }
        }
    }

    private boolean snakeWon() {
        boolean snakeWon = true;
        for(int w = 0; w < worldWidth; w++) {
            for(int h = 0; h < worldHeight; h++) {
                if(!snakeParts.contains(new Point(w,h)))
                    return false;
            }
        }

        return true;
    }

    public boolean pointWithinWorld(Point p) {
        return p.x >= 0 && p.x < worldWidth && p.y >= 0 && p.y < worldHeight;
    }

    @Override
    public Snake newInstance()
    {
        return new Snake(worldWidth, worldHeight);
    }

    @Override
    public double calculateFitness(GeneticNet net) {
        while(!gameOver) {
            double[] input = SnakeUtil.getVision(this); // todo remove int
            double[] output = net.feedForward(input);

            Direction dir = getDirection().getLeft();
            double max = output[0];

            if(output[1] > max) {
                max = output[1];
                dir = getDirection();
            }
            if(output[2] > max) {
                max = output[2];
                dir = getDirection().getRight();
            }

            setDirection(dir);
            move();
        }

        int applesEaten = foodLocs.size() - 1;
        double fitness = totalMoves + (Math.pow(2, applesEaten)+Math.pow(applesEaten, 2.1)*500)
                         - (Math.pow(applesEaten, 2.1)*Math.pow(totalMoves*0.25, 1.3));
        return fitness;
    }

    @Override
    public Snake copy() {
        Snake copy = new Snake(worldWidth, worldHeight);
        copy.replayFood.addAll(foodLocs);
        copy.foodLocs.clear();
        copy.generateFood();
        return copy;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction d) {
        if(!direction.isOpposite(d))
            nextDirection = d;
    }

    public List<Point> getSnakeParts() {
        return snakeParts;
    }

    public List<Point> getBodyLocations() {
        return snakeParts.subList(1, snakeParts.size());
    }

    public Point getHeadLocation() {
        return snakeParts.get(0);
    }

    public List<Point> getFoodLocs() {
        return foodLocs;
    }

    public Point getFoodLocation() {
        return food;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getTotalMoves() {
        return totalMoves;
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