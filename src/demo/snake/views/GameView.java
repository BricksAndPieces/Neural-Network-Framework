package demo.snake.views;

import demo.snake.Snake;
import demo.snake.util.ColorUtil;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {

    private Snake snake = null;

    public GameView(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Arena frame
        g.setColor(Color.white);
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);

        if(snake != null) {
            int size = getWidth() / snake.getWorldWidth();

            // Food drawing
            if(snake.getFoodLocation() != null) {
                g.setColor(Color.red);
                drawSquare(g, snake.getFoodLocation().x, snake.getFoodLocation().y, size);
            }

            // Head drawing
            if(snake.getBodyLocations().isEmpty()) {
                g.setColor(Color.green);
                drawSquare(g, snake.getHeadLocation().x, snake.getHeadLocation().y, size);
                return;
            }

            // Body drawing
            ((Graphics2D)g).setStroke(new BasicStroke((int)(size*0.95)));
            for(int i = 0; i < snake.getSnakeParts().size()-1; i++) {
                Point p1 = snake.getSnakeParts().get(i);
                Point p2 = snake.getSnakeParts().get(i+1);
                g.setColor(ColorUtil.gradient(Color.GREEN, Color.WHITE, 1-(i / (snake.getSnakeParts().size()*1.0))));
                g.drawLine(p1.x*size+size/2, p1.y*size+size/2, p2.x*size+size/2, p2.y*size+size/2);
            }
        }
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    private void drawSquare(Graphics g, int x, int y, int size) {
        g.fillRect(x*size, y*size, (int)(size*0.99), (int)(size*0.99));
    }
}