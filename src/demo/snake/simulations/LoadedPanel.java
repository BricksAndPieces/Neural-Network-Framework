package demo.snake.simulations;

import demo.snake.Snake;
import demo.snake.util.Direction;
import demo.snake.util.SnakeUtil;
import neuralnetwork.core.Function;
import neuralnetwork.core.NeuralNet;
import neuralnetwork.core.NeuralNetSettings;
import neuralnetwork.genetics.Population;
import neuralnetwork.util.NetworkStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

@SuppressWarnings("All")
public class LoadedPanel extends JPanel implements ActionListener {

    private static final int DELAY_BETWEEN_FRAMES = 100;
    private static final RenderingHints rendering =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);;

    private final Timer timer;
    private final NeuralNet brain;
    private Snake snake;

    int maxScore = 0;

    public LoadedPanel(int width, int height, String fileName) {
        this.timer = new Timer(DELAY_BETWEEN_FRAMES, this);
        this.snake = snake;

        setFocusable(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(width, height));

        // TODO FIGURE OUT WHY THE SIMULATION JUST STOPS

        snake = new Snake(10, 10);
        brain = NetworkStore.getNeuralNetFromFile(fileName);
        timer.start();
    }

    void delayBetweenRestart() {
        try { Thread.sleep(3000); } catch(Exception e) { }
        snake = new Snake(snake.getWorldWidth(), snake.getWorldHeight());
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double[] input = SnakeUtil.getVision(snake); // todo remove int
        double[] output = brain.feedForward(input);
        //System.out.println(Arrays.toString(input) + "     |     " + Arrays.toString(output));

        Direction dir = snake.getDirection().getLeft();
        double max = output[0];

        if(output[1] > max) {
            max = output[1];
            dir = snake.getDirection();
        }
        if(output[2] > max) {
            max = output[2];
            dir = snake.getDirection().getRight();
        }

        snake.setDirection(dir);
        snake.move();
        repaint();

        if(snake.isGameOver()) {
            maxScore = Math.max(maxScore, snake.getScore());
            timer.stop();
            delayBetweenRestart();
        }
    }

    public void display() {
        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);

        frame.add(this, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    boolean text = false;

    @Override
    protected void paintComponent(Graphics graphics)  {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D)graphics;
        g.addRenderingHints(rendering);

        drawUnit(g, Color.red, snake.getFoodLocation());
        drawUnit(g, Color.white, snake.getHeadLocation());
        for(Point p : snake.getBodyLocations())
            drawUnit(g, Color.lightGray, p);

        if(text) drawText(g, snake.getScore());
        else text = true;
    }

    private void drawUnit(Graphics g, Color c, Point p) {
        int width = (int)(((double)getWidth()) / snake.getWorldWidth());
        int height = (int)((double)getHeight() / snake.getWorldHeight());

        g.setColor(c);
        g.fillRect(p.x * width, p.y * height, (int)(width * 0.99), (int)(height * 0.99));
    }

    private void drawText(Graphics g, int score) {
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("High Score: " + maxScore, 0, 20);
        g.drawString("Score: " + score, 0, 50);

        g.drawString("'Billy the snake (Supreme edition)'", 300, 20);
    }
}