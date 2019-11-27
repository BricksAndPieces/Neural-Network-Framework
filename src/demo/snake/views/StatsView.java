package demo.snake.views;

import javax.swing.*;
import java.awt.*;

public class StatsView extends JPanel {

    private static final RenderingHints rendering =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private int generation = 0;
    private int score = 0;
    private int maxScore = 0;

    public StatsView(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
    }

    // todo make it look good lmao

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g).addRenderingHints(rendering);

        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString("Stats", 10, 30);

        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("Generation: " + generation, 10, 70);
        g.drawString("High Score: " + maxScore, 10, 100);
        g.drawString("Score: " + score, 10, 130);


        // todo graph
        g.setColor(Color.green);
        g.drawLine(0, 200, 75, 150);
        g.drawLine(75, 150, 125, 140);
        g.drawLine(125, 140, 200, 100);
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
