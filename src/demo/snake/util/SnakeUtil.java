package demo.snake.util;

import demo.snake.Snake;
import demo.snake.util.Direction;

import java.awt.*;

@SuppressWarnings("All")
public class SnakeUtil {

    /**
     * 3 inputs for each direction of vision
     * 8 directions of vision
     * 24 total inputs for vision
     *
     * 4 inputs for head direction
     * 4 inputs for tail direction
     *
     * 32 total intputs
     */

    private static int[][] directions = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};

    public static double[] getVision(Snake snake, boolean x) {
        double[] vision = new double[32]; // todo set num to correct num
        for(int i = 0; i < directions.length; i++) {
            Point p = new Point(snake.getHeadLocation());
            boolean hitBody = false;
            while(snake.pointWithinWorld(p)) {
                // Shift vision
                p.translate(directions[i][0], directions[i][1]);

                // Food distance
                if(snake.getFoodLocation().equals(p))
                    //vision[i*directions.length+0] = 1 / snake.getHeadLocation().distance(p); // distance
                    vision[i*3+0] = 1; // binary

                // Body distance
                if(!hitBody && snake.getBodyLocations().contains(p)) {
                    vision[i * 3 + 1] = 1 / snake.getHeadLocation().distance(p);
                    hitBody = true;
                }
            }

            // Wall distance
            vision[i*3+2] = 1 / (snake.getHeadLocation().distance(p)+1);
        }

        // Head direction
        for(int i = 0; i < Direction.values().length; i++) {
            if(Direction.values()[i] == snake.getDirection()) {
                vision[24+i] = 1;
                break;
            }
        }

        // Tail direction
        if(snake.getBodyLocations().isEmpty()) {
            for(int i = 0; i < Direction.values().length; i++) {
                if(Direction.values()[i] == snake.getDirection()) {
                    vision[28 + i] = 1;
                    break;
                }
            }
        }else {
            Point last = snake.getSnakeParts().get(snake.getSnakeParts().size()-1);
            Point sencondToLast = snake.getSnakeParts().get(snake.getSnakeParts().size()-2);
            Point p = new Point(last.x - sencondToLast.x, last.y - sencondToLast.y);
            Direction tail = Direction.getInstance(p.x, p.y);
            for(int i = 0; i < Direction.values().length; i++) {
                if(Direction.values()[i] == tail) {
                    vision[28 + i] = 1;
                    break;
                }
            }
        }

        return vision;
    }

    public static double[] getVision(Snake snake) {
        double[] input = new double[8];
        if(snake.getFoodLocation().x > snake.getHeadLocation().x) input[0] = 1;
        if(snake.getFoodLocation().x < snake.getHeadLocation().x) input[1] = 1;
        if(snake.getFoodLocation().y > snake.getHeadLocation().y) input[2] = 1;
        if(snake.getFoodLocation().y < snake.getHeadLocation().y) input[3] = 1;

        int i = 4;
        for(Direction d : Direction.values()) {
            Point p = new Point(snake.getHeadLocation().x+d.getX(), snake.getHeadLocation().y+d.getY());
            if(!snake.pointWithinWorld(p)) input[i] = 1;
            else if(snake.getBodyLocations().contains(p)) input[i] = 1;
            i++;
        }

        return input;
    }

    public static double[] getVision(Snake snake, int i) {
        double[] input = new double[7];

        // Direction of apple
        // todo change from world space to first person
        if(snake.getDirection() == Direction.DOWN) {
            if(snake.getFoodLocation().x > snake.getHeadLocation().x) input[0] = 1;
            if(snake.getFoodLocation().x < snake.getHeadLocation().x) input[1] = 1;
            if(snake.getFoodLocation().y > snake.getHeadLocation().y) input[2] = 1;
            if(snake.getFoodLocation().y < snake.getHeadLocation().y) input[3] = 1;
        }else if(snake.getDirection() == Direction.UP) {
            if(snake.getFoodLocation().x < snake.getHeadLocation().x) input[0] = 1;
            if(snake.getFoodLocation().x > snake.getHeadLocation().x) input[1] = 1;
            if(snake.getFoodLocation().y < snake.getHeadLocation().y) input[2] = 1;
            if(snake.getFoodLocation().y > snake.getHeadLocation().y) input[3] = 1;
        }else if(snake.getDirection() == Direction.LEFT) {
            if(snake.getFoodLocation().y > snake.getHeadLocation().y) input[0] = 1;
            if(snake.getFoodLocation().y < snake.getHeadLocation().y) input[1] = 1;
            if(snake.getFoodLocation().x > snake.getHeadLocation().x) input[2] = 1;
            if(snake.getFoodLocation().x < snake.getHeadLocation().x) input[3] = 1;
        }else if(snake.getDirection() == Direction.RIGHT) {
            if(snake.getFoodLocation().y < snake.getHeadLocation().y) input[0] = 1;
            if(snake.getFoodLocation().y > snake.getHeadLocation().y) input[1] = 1;
            if(snake.getFoodLocation().x < snake.getHeadLocation().x) input[2] = 1;
            if(snake.getFoodLocation().x > snake.getHeadLocation().x) input[3] = 1;
        }

        Point p = new Point(snake.getHeadLocation().x + snake.getDirection().getLeft().getX(),
                      snake.getHeadLocation().y + snake.getDirection().getLeft().getY());
        if(snake.pointWithinWorld(p) || snake.getBodyLocations().contains(p)) input[4] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getX(),
                            snake.getHeadLocation().y + snake.getDirection().getY());
        if(snake.pointWithinWorld(p) || snake.getBodyLocations().contains(p)) input[5] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getRight().getX(),
                            snake.getHeadLocation().y + snake.getDirection().getRight().getY());
        if(snake.pointWithinWorld(p) || snake.getBodyLocations().contains(p)) input[6] = 1;

        return input;
    }
}