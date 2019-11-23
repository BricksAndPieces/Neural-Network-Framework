package demo.snake.util;

import demo.snake.Snake;

import java.awt.*;

@SuppressWarnings("All")
public class SnakeUtil {

    public static double[] getVision(Snake snake) {
        double[] input = new double[10];

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

        // WALLS
        Point p = new Point(snake.getHeadLocation().x + snake.getDirection().getLeft().getX(),
                      snake.getHeadLocation().y + snake.getDirection().getLeft().getY());
        if(!snake.pointWithinWorld(p)) input[4] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getX(),
                            snake.getHeadLocation().y + snake.getDirection().getY());
        if(!snake.pointWithinWorld(p)) input[5] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getRight().getX(),
                            snake.getHeadLocation().y + snake.getDirection().getRight().getY());
        if(!snake.pointWithinWorld(p)) input[6] = 1;

        // BODY
        p = new Point(snake.getHeadLocation().x + snake.getDirection().getLeft().getX(),
                            snake.getHeadLocation().y + snake.getDirection().getLeft().getY());
        if(snake.getBodyLocations().contains(p)) input[7] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getX(),
                      snake.getHeadLocation().y + snake.getDirection().getY());
        if(snake.getBodyLocations().contains(p)) input[8] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getRight().getX(),
                      snake.getHeadLocation().y + snake.getDirection().getRight().getY());
        if(snake.getBodyLocations().contains(p)) input[9] = 1;

        return input;
    }

    public static double[] getVision(Snake snake, int z) {
        double[] input = new double[16];

        for(int i = 0; i < Direction.values().length; i++) {
            Direction d = Direction.values()[i];
            Point p = new Point(snake.getHeadLocation());
            boolean hitBody = false;

            p.translate(d.getX(), d.getY());
            while(snake.pointWithinWorld(p)) {
                if(snake.getFoodLocation().equals(p))
                    input[i*3+0] = snake.getHeadLocation().distance(p) / snake.getWorldWidth();

                if(snake.getBodyLocations().contains(p) && !hitBody) {
                    input[i*3+1] = snake.getHeadLocation().distance(p) / snake.getWorldWidth();
                    hitBody = true;

                }

                p.translate(d.getX(), d.getY());
            }

            input[i*3+2] = snake.getHeadLocation().distance(p) / snake.getWorldWidth();
        }

        switch(snake.getDirection()) {
            case UP: input[12] = 1; break;
            case DOWN: input[13] = 1; break;
            case LEFT: input[14] = 1; break;
            case RIGHT: input[15] = 1; break;
        }

        return input;
    }
}