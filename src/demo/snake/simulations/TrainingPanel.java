package demo.snake.simulations;

import demo.snake.Snake;
import demo.snake.wackdonottouch.panels.BetaInputView;
import demo.snake.wackdonottouch.panels.BetaNetworkView;
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
        int[] layers = {14, 7, 3};
        Function activation = Function.RELU; // very cool
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

        for(int i = 0; i < 49; i++) { // ez to skip some gens
            maxScore = Math.max(maxScore, population.getBestPlayer().getSimulation().getScore());
            population.evolveNextGeneration(0.05);
            population.simulateGeneration();
        }

        brain = population.getBestPlayer();
        snake = population.getBestPlayer().getSimulation().copy();
        System.out.print("Gen: " + population.getGeneration());
        System.out.println(" | Fitness: " + population.getBestFitness());

        networkView.setNetwork(brain);
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

        networkView.setInputs(input);
        networkView.setOutputs(output);
        inputView.setInputs(input);

//        networkView.repaint();
//        inputView.repaint();
        container.repaint();
        repaint();

        if(snake.isGameOver()) {
            maxScore = Math.max(maxScore, snake.getScore());
            timer.stop();

            showGame();
        }
    }

    BetaNetworkView networkView = new BetaNetworkView();
    BetaInputView inputView = new BetaInputView();
    JPanel container = new JPanel();

    public void display() {
        JFrame frame = new JFrame("Snake");
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container.setPreferredSize(new Dimension(200, 600));
        container.setBackground(Color.black);
        container.add(networkView, BorderLayout.CENTER);
        container.add(inputView, BorderLayout.AFTER_LINE_ENDS);
        container.add(new JButton("Save Network"), BorderLayout.SOUTH);
        container.add(new JButton("Load Network"), BorderLayout.SOUTH); // todo figure this part out later

        frame.add(this, BorderLayout.CENTER);
        frame.add(container, BorderLayout.WEST);

//        JButton button = new JButton("Save Network");
//        frame.add(button, BorderLayout.SOUTH);
//        button.addActionListener((e) -> {
//            NetworkStore.writeNetworkToFile(brain, "networks/" + population.getGeneration() +"-"+ brain);
//        });

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    boolean firstPaint = true;

    @Override
    protected void paintComponent(Graphics graphics)  {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D)graphics;
        g.addRenderingHints(rendering);

        drawUnit(g, Color.red, snake.getFoodLocation());
        drawUnit(g, Color.green, snake.getHeadLocation());
        for(int i = 0; i < snake.getBodyLocations().size(); i++) {
            drawUnit(g, colorBetween(Color.green,Color.lightGray, ((double)i)/snake.getBodyLocations().size()), snake.getBodyLocations().get(i));
        }

        if(firstPaint) firstPaint = false;
        else {
//            double[] input = SnakeUtil.getVision(snake); // todo remove int
//            double[] output = brain.feedForward(input);
//            NetworkGraphics.draw(g, brain, input, output, 0, 100, 200, 200);

            drawText(g, snake.getScore());
        }

        g.setColor(Color.white);
        g.drawRect(0, 0, (int) (getWidth()*0.9999), (int) (getHeight()));
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
        g.drawString("Generation: " + population.getGeneration(), 2, 20);
        g.drawString("High Score: " + maxScore, 2, 50);
        g.drawString("Score: " + score, 2, 80);

        g.drawString("'Billy the snake'", 300, 20);
    }

    private Color colorBetween(Color c1, Color c2, double loc) {
        int r = (int) (Math.min(c1.getRed(), c2.getRed()) + loc * Math.abs(c1.getRed() - c2.getRed()));
        int g = (int) (Math.min(c1.getGreen(), c2.getGreen()) + loc * Math.abs(c1.getGreen() - c2.getGreen()));
        int b = (int) (Math.min(c1.getBlue(), c2.getBlue()) + loc * Math.abs(c1.getBlue() - c2.getBlue()));
        return new Color(r,g,b);
    }
}