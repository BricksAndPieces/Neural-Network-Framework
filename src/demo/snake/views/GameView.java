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

        // Population loading
        if(snake == null) {
            g.setFont(new Font("TimesRoman", Font.BOLD, 50)); // todo figure this out
            g.drawString("Simulating population", 100, 100);
        }else {
            int size = getWidth() / snake.getWorldWidth();

            // Food
            if(snake.getFoodLocation() != null) {
                g.setColor(Color.red);
                drawSquare(g, snake.getFoodLocation().x, snake.getFoodLocation().y, size);
            }

            // Snake
            for(int i = 0; i < snake.getSnakeParts().size(); i++) {
                Point p = snake.getSnakeParts().get(i);
                g.setColor(ColorUtil.gradient(Color.GREEN, Color.WHITE, 1-((double)i) / (snake.getSnakeParts().size()*2)));
                drawSquare(g, p.x, p.y, size);
            }
        }

        // Arena frame
        g.setColor(Color.white);
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    private void drawSquare(Graphics g, int x, int y, int size) {
        g.fillRect(x*size, y*size, (int)(size*0.99), (int)(size*0.99));
    }
}
