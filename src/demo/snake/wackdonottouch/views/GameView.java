package demo.snake.wackdonottouch.views;

import demo.snake.Snake;
import demo.snake.util.ColorUtil;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

@SuppressWarnings("All")
public class GameView extends Canvas {

    private final GraphicsContext gc;

    public GameView(int width, int height) {
        super(width, height);
        gc = getGraphicsContext2D();
    }

    public void draw(Snake snake) {
        // Clear the canvas
        gc.clearRect(0, 0, getWidth(), getHeight());

        // Draw food
        gc.setFill(Color.RED);
        Point food = snake.getFoodLocation();
        drawSquare(gc, food.x, food.y, snake.getWorldWidth());

        // Draw snake
        for(int i = 0; i < snake.getSnakeParts().size(); i++) {
            Point p = snake.getSnakeParts().get(i);
            gc.setFill(ColorUtil.bfkjbfkjdsfdbsjkfdbkjsf(Color.GREEN, Color.WHITE, ((double)i) / snake.getSnakeParts().size()));
            drawSquare(gc, p.x, p.y, snake.getWorldWidth());
        }

        // Draw frame for the arena

        gc.setFill(Color.WHITE);
        gc.rect(5, 5, getWidth()-5, getHeight()-5);
    }

    private void drawSquare(GraphicsContext gc, int x, int y, int w) {
        int size = (int)(getWidth() / w);
        gc.fillRect(x*size, y*size, size*0.99, size*0.99);
    }
}