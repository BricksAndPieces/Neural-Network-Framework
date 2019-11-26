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
public class TrainingPanel extends JPanel implements ActionListener {

    private static final int DELAY_BETWEEN_FRAMES = 50;
    private static final RenderingHints rendering =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);;

    private final Timer timer;

    private final Population<Snake> population;
    private Snake snake;

    int maxScore = 0;

    public TrainingPanel(int width, int height) {
        this.timer = new Timer(DELAY_BETWEEN_FRAMES, this);
        this.snake = snake;

        setFocusable(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(width, height));

        // TODO FIGURE OUT WHY THE SIMULATION JUST STOPS

        //int[] layers = {15, 10, 6, 3};
        //int[] layers = {10, 7, 6, 3};
        //int[] layers = {10, 7, 3};
        //int[] layers = {10, 3};
        //int[] layers = {14, 7, 3};
        //int[] layers = {12, 3};
        int[] layers = {14, 3};
        Function activation = Function.RELU; // very cool
        NeuralNetSettings settings = new NeuralNetSettings(layers, activation);
        snake = new Snake(10,10);
        population = new Population<>(500, snake, settings);
    }

    public void startAI() {
        showGame();
    }

    NeuralNet brain;

    private void showGame() {
        population.simulateGeneration();

        for(int i = 0; i < 49; i++) { // ez to skip some gens
            maxScore = Math.max(maxScore, population.getBestPlayer().getSimulation().getScore());
            population.evolveNextGeneration(0.05);
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

            showGame();
        }
    }

    public void display() {
        JFrame frame = new JFrame("Snake");
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this, BorderLayout.CENTER);

        JButton button = new JButton("Save Network");
        frame.add(button, BorderLayout.SOUTH);
        button.addActionListener((e) -> {
            NetworkStore.writeNetworkToFile(brain, "networks/" + population.getGeneration() +"-"+ brain);
        });

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
        g.drawString("Generation: " + population.getGeneration(), 0, 20);
        g.drawString("High Score: " + maxScore, 0, 50);
        g.drawString("Score: " + score, 0, 80);

        g.drawString("'Billy the snake'", 300, 20);
    }
}