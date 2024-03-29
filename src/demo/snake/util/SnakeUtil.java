package demo.snake.util;

import demo.snake.Snake;

import java.awt.*;

@SuppressWarnings("All")
public class SnakeUtil {

    public static double[] getVision(Snake snake) {
        double[] input = new double[14];

        // Food
        if(snake.getFoodLocation() != null) {
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
        }

        // WALLS
        Point p = new Point(snake.getHeadLocation().x + snake.getDirection().getLeft().getX(),
                      snake.getHeadLocation().y + snake.getDirection().getLeft().getY());
        if(!snake.pointWithinWorld(p)) input[4] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getX() + snake.getDirection().getLeft().getX(),
                      snake.getHeadLocation().y + snake.getDirection().getY() + snake.getDirection().getLeft().getY());
        if(!snake.pointWithinWorld(p)) input[5] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getX(),
                            snake.getHeadLocation().y + snake.getDirection().getY());
        if(!snake.pointWithinWorld(p)) input[6] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getX() + snake.getDirection().getRight().getX(),
                      snake.getHeadLocation().y + snake.getDirection().getY() + snake.getDirection().getRight().getY());
        if(!snake.pointWithinWorld(p)) input[7] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getRight().getX(),
                            snake.getHeadLocation().y + snake.getDirection().getRight().getY());
        if(!snake.pointWithinWorld(p)) input[8] = 1;

        // BODY
        p = new Point(snake.getHeadLocation().x + snake.getDirection().getLeft().getX(),
                            snake.getHeadLocation().y + snake.getDirection().getLeft().getY());
        if(snake.getBodyLocations().contains(p) && !snake.getBodyLocations().get(snake.getBodyLocations().size()-1).equals(p)) input[9] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getX() + snake.getDirection().getLeft().getX(),
                      snake.getHeadLocation().y + snake.getDirection().getY() + snake.getDirection().getLeft().getY());
        if(snake.getBodyLocations().contains(p) && !snake.getBodyLocations().get(snake.getBodyLocations().size()-1).equals(p)) input[10] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getX(),
                      snake.getHeadLocation().y + snake.getDirection().getY());
        if(snake.getBodyLocations().contains(p) && !snake.getBodyLocations().get(snake.getBodyLocations().size()-1).equals(p)) input[11] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getX() + snake.getDirection().getRight().getX(),
                      snake.getHeadLocation().y + snake.getDirection().getY() + snake.getDirection().getRight().getY());
        if(snake.getBodyLocations().contains(p) && !snake.getBodyLocations().get(snake.getBodyLocations().size()-1).equals(p)) input[12] = 1;

        p = new Point(snake.getHeadLocation().x + snake.getDirection().getRight().getX(),
                      snake.getHeadLocation().y + snake.getDirection().getRight().getY());
        if(snake.getBodyLocations().contains(p) && !snake.getBodyLocations().get(snake.getBodyLocations().size()-1).equals(p)) input[13] = 1;

        return input;
    }
}