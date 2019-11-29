package demo.snake.views;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Optimized :)
public class StatsView extends JPanel {

    private static final RenderingHints rendering =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private JLabel genLabel = new JLabel();
    private JLabel highScoreLabel = new JLabel();
    private JLabel scoreLabel = new JLabel();

    private Graph graphPanel;
    private final List<Point> graph = new ArrayList<>();

    public StatsView(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.black);

        Font font = new Font("TimesRoman", Font.BOLD, 20);
        genLabel.setForeground(Color.white);
        genLabel.setFont(font);
        highScoreLabel.setForeground(Color.white);
        highScoreLabel.setFont(font);
        scoreLabel.setForeground(Color.white);
        scoreLabel.setFont(font);

        JPanel stats = new JPanel();
        stats.setLayout(new BoxLayout(stats, BoxLayout.PAGE_AXIS));
        stats.setBackground(Color.black);
        stats.add(genLabel);
        stats.add(highScoreLabel);
        stats.add(scoreLabel);
        stats.add(new JLabel()); // empty to take up space
        add(stats);

        graph.add(new Point());
        graphPanel = new Graph();
        add(graphPanel);
    }

    public void setGeneration(int generation) {
        genLabel.setText("Generation: " + generation);
    }

    public void setMaxScore(int maxScore) {
        highScoreLabel.setText("Highscore: " + maxScore);
    }

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void addGraphPoint(int gen, int score) {
        graph.add(new Point(gen, score));
        graphPanel.repaint();
    }

    public void reset() {
        graph.clear();
        graphPanel.repaint();
    }

    private class Graph extends JPanel {

        private Graph() {
            setBackground(Color.black);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ((Graphics2D)g).addRenderingHints(rendering);

            g.setColor(Color.white);
            g.drawRect(0, 0, getWidth()-1, getHeight()-1);

            if(graph.size() < 2) return;
            g.setColor(Color.green);

            double xUnit = ((double) getWidth())/graph.get(graph.size()-1).x;
            for(int i = 0; i < graph.size()-1; i++) {
                Point from = graph.get(i);
                Point to = graph.get(i+1);

                double scale = ((double)getHeight()) / 100;
                g.drawLine((int)(from.x*xUnit), (int)(getHeight()-from.y*scale), (int)(to.x * xUnit), (int)(getHeight()-to.y*scale));
            }
        }
    }
}