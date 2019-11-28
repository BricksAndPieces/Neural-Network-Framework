package demo.snake.views;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("All")
public class InputView extends JPanel {

    private double[] inputs = null;

    public InputView(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // todo set the inputs to the correct location

        if(inputs == null) return;
        int size = getWidth() / 7;

        // Draw head of snake in all three cases
        g.setColor(Color.white);
        drawSquare(g, size * 3, size * 2, size);
        drawSquare(g, size * 3, size * 6, size);
        drawSquare(g, size * 3, size * 9, size);

        // Draw food locations
        g.setColor(inputs[0] == 0 ? Color.lightGray : Color.red);
        drawSquare(g, size * 4, size * 2, size);

        g.setColor(inputs[1] == 0 ? Color.lightGray : Color.red);
        drawSquare(g, size * 2, size * 2, size);

        g.setColor(inputs[2] == 0 ? Color.lightGray : Color.red);
        drawSquare(g, size * 3, size * 1, size);

        g.setColor(inputs[3] == 0 ? Color.lightGray : Color.red);
        drawSquare(g, size * 3, size * 3, size);

        // Wall collisions
        g.setColor(inputs[4] == 0 ? Color.lightGray : Color.red);
        drawSquare(g, size * 4, size * 6, size);

        g.setColor(inputs[5] == 0 ? Color.lightGray : Color.red);
        drawSquare(g, size * 3, size * 5, size);

        g.setColor(inputs[6] == 0 ? Color.lightGray : Color.red);
        drawSquare(g, size * 2, size * 6, size);

        // Body collisions
        g.setColor(inputs[7] == 0 ? Color.lightGray : Color.red);
        drawSquare(g, size * 4, size * 9, size);

        g.setColor(inputs[8] == 0 ? Color.lightGray : Color.red);
        drawSquare(g, size * 3, size * 8, size);

        g.setColor(inputs[9] == 0 ? Color.lightGray : Color.red);
        drawSquare(g, size * 2, size * 9, size);
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
    }

    private void drawSquare(Graphics g, int x, int y, int size) {
        g.fillRect(x, y, (int)(size*0.99), (int)(size*0.99));
    }
}