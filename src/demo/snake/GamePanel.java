package demo.snake;

import demo.snake.util.Direction;
import demo.snake.util.SnakeUtil;
import neuralnetwork.core.Function;
import neuralnetwork.core.NeuralNet;
import neuralnetwork.core.NeuralNetSettings;
import neuralnetwork.genetics.Population;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

@SuppressWarnings("All")
public class GamePanel extends JPanel implements ActionListener {

    private static final int DELAY_BETWEEN_FRAMES = 100;
    private static final RenderingHints rendering =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);;

    private final Timer timer;

    private final Population<Snake> population;
    private Snake snake;

    int maxScore = 0;

    public GamePanel(int width, int height) {
        this.timer = new Timer(DELAY_BETWEEN_FRAMES, this);
        this.snake = snake;

        setFocusable(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(width, height));

        //int[] layers = {32, 20, 12, 4};
        //int[] layers = {8, 6, 4};
        int[] layers = {7, 6, 4};
        Function activation = Function.SIGMOID;
        NeuralNetSettings settings = new NeuralNetSettings(layers, activation);
        snake = new Snake(10,10);
        population = new Population<>(100, snake, settings);
    }

    public void startAI() {
        showGame();
    }

    NeuralNet brain;

    private void showGame() {
        population.simulateGeneration();

        for(int i = 0; i < 50; i++) {
            population.evolveNextGeneration(0.05); // ez to skip some gens
            population.simulateGeneration();
        }

        brain = population.getBestPlayer();
        snake = population.getBestPlayer().getSimulation().copy();
        System.out.print("Gen: " + population.getGeneration());
        System.out.println(" | Fitness: " + population.getBestFitness());

        timer.start();
        population.evolveNextGeneration(0.05);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double[] input = SnakeUtil.getVision(snake, 1);
        double[] output = brain.feedForward(input);

//        Direction dir = null;
//        double max = Double.MIN_VALUE;
//        for(int i = 0; i < output.length; i++) {
//            if(output[i] > max && !Direction.values()[i].isOpposite(snake.getDirection())) {
//                max = output[i];
//                dir = Direction.values()[i];
//            }
//        }

        Direction dir = snake.getDirection().getLeft();
        double max = output[0];

        if(output[1] > max) {
            max = output[1];
            dir = snake.getDirection();
        }else if(output[2] > max) {
            max = output[2];
            dir = snake.getDirection().getRight();
        }

        snake.setDirection(dir);
        snake.move();
        repaint();

        if(snake.isGameOver()) {
            maxScore = Math.max(maxScore, snake.getScore());
            timer.stop();
            showGame();
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

    @Override
    protected void paintComponent(Graphics graphics)  {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D)graphics;
        g.addRenderingHints(rendering);

        if(maxScore != 0)
            drawScores(g, snake.getScore());

        drawUnit(g, Color.red, snake.getFoodLocation());
        drawUnit(g, Color.white, snake.getHeadLocation());
        for(Point p : snake.getBodyLocations())
            drawUnit(g, Color.lightGray, p);

        //drawSight(g, snake.getHeadLocation());
    }

    private void drawUnit(Graphics g, Color c, Point p) {
        int width = (int)(((double)getWidth()) / snake.getWorldWidth());
        int height = (int)((double)getHeight() / snake.getWorldHeight());

        g.setColor(c);
        g.fillRect(p.x * width, p.y * height, (int)(width * 0.99), (int)(height * 0.99));
    }

    private void drawScores(Graphics g, int score) {
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("High Score: " + maxScore, 0, 20);
        g.drawString("Score: " + score, 0, 50);
    }

//    private void drawSight(Graphics g, Point p) {
//        int width = (int)(((double)getWidth()) / snake.getWorldWidth());
//        int height = (int)((double)getHeight() / snake.getWorldHeight());
//        int x = width * p.x + width/2;
//        int y = height * p.y + height/2;
//
//        int length = 750;
//
//        g.setColor(Color.white);
//        g.drawLine(x,y,x+length,y);
//        g.drawLine(x,y,x+length,y+length);
//        g.drawLine(x,y,x,y+length);
//        g.drawLine(x,y,x-length,y+length);
//        g.drawLine(x,y,x-length,y);
//        g.drawLine(x,y,x-length,y-length);
//        g.drawLine(x,y,x,y-length);
//        g.drawLine(x,y,x+length,y-length);
//    }
}