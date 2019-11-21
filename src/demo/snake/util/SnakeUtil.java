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

    public static double[] getVision(Snake snake) {
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
                    vision[i*directions.length+0] = 1; // binary

                // Body distance
                if(!hitBody && snake.getBodyLocations().contains(p)) {
                    vision[i * directions.length + 1] = 1 / snake.getHeadLocation().distance(p);
                    hitBody = true;
                }
            }

            // Wall distance
            vision[i*directions.length+2] = 1 / snake.getHeadLocation().distance(p);
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
}