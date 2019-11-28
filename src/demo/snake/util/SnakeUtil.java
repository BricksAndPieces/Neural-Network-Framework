package demo.snake.util;

import demo.snake.Snake;

import java.awt.*;

@SuppressWarnings("All")
public class SnakeUtil {

    public static double[] getVision(Snake snake) {
        double[] input = new double[14];

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

    public static double[] getVision(Snake snake, int r) {
        double[] input = new double[15];

        // todo add diagonal sensors (2 of them)

        Direction[] dirs = {snake.getDirection().getLeft(), snake.getDirection(), snake.getDirection().getRight()};
        for(int i = 0; i < dirs.length; i++) {
            Point p = new Point(snake.getHeadLocation());
            boolean hitBody = false;
            input[i*3] = 1;
            input[i*3+1] = 1;
            while(snake.pointWithinWorld(p)) {
                p.translate(dirs[i].getX(), dirs[i].getY());
                if(p.equals(snake.getFoodLocation()))
                    input[i*3] = (snake.getHeadLocation().distance(p)-1) / snake.getWorldWidth();

                if(!hitBody && snake.getBodyLocations().contains(p)) {
                    input[i*3+1] = (snake.getHeadLocation().distance(p)-1) / snake.getWorldWidth();
                    hitBody = true;
                }
            }

            input[i*3+2] = (snake.getHeadLocation().distance(p)-1) / snake.getWorldWidth();
        }

        int[][] diagonals = {{dirs[0].getX()+dirs[1].getX(),dirs[0].getY()+dirs[1].getY()},
                             {dirs[1].getX()+dirs[2].getX(),dirs[1].getY()+dirs[2].getY()}};
        for(int i = 0; i < diagonals.length; i++) {
            Point p = new Point(snake.getHeadLocation());
            boolean hitBody = false;
            input[i*2+9] = 1;
            input[i*2+10] = 1;
            while(snake.pointWithinWorld(p)) {
                p.translate(diagonals[i][0], diagonals[i][1]);
                if(p.equals(snake.getFoodLocation()))
                    input[i*3+9] =(snake.getHeadLocation().distance(p)-1) / Math.sqrt(Math.pow(snake.getWorldWidth(),2)*2);

                if(!hitBody && snake.getBodyLocations().contains(p)) {
                    input[i*3+10] = (snake.getHeadLocation().distance(p)-1) / Math.sqrt(Math.pow(snake.getWorldWidth(),2)*2);
                    hitBody = true;
                }
            }
            input[i*3+11] = (snake.getHeadLocation().distance(p)-1) / Math.sqrt(Math.pow(snake.getWorldWidth(),2)*2);
        }

        return input;
    }
}