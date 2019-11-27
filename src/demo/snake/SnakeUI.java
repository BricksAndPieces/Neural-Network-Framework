package demo.snake;

import demo.snake.util.Direction;
import demo.snake.util.SnakeUtil;
import demo.snake.views.*;
import neuralnetwork.core.Function;
import neuralnetwork.core.NeuralNet;
import neuralnetwork.core.NeuralNetSettings;
import neuralnetwork.genetics.GeneticNet;
import neuralnetwork.genetics.Population;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("All")
public class SnakeUI implements ActionListener {

    private static final String WINDOW_TITLE = "Snake Neural Network";

    private static final int[] layers = {14, 7, 3};
    private static final Function activation = Function.RELU;

    private double mutationRate = 0.05;
    private int populationSize = 100;
    private int gameSize = 10;

    private int timeBetweenFrames = 50;
    private int gensPerRound = 1;

    private Timer gameLoop;

    private NetworkView networkView;
    private InputView inputView;
    private GameView gameView;
    private StatsView statsView;
    private OptionsView optionsView;

    private final Population<Snake> population;
    private NeuralNet network;
    private Snake snake;

    private int maxScore = 0;

    public SnakeUI() {
        gameLoop = setupGameloop();
        population = setupPopulation();
    }

    private Population<Snake> setupPopulation() {
        NeuralNetSettings settings = new NeuralNetSettings(layers, activation);
        return new Population<>(populationSize, new Snake(gameSize, gameSize), settings);
    }

    private void nextGeneration() {
        population.simulateGeneration(); // todo simulate population in background

        for(int i = 0; i < gensPerRound-1; i++) {
            maxScore = Math.max(maxScore, population.getBestPlayer().getSimulation().getScore());
            population.evolveNextGeneration(mutationRate);
            population.simulateGeneration();
        }

        network = population.getBestPlayer();
        snake = ((GeneticNet<Snake>) network).getSimulation().copy();

        gameView.setSnake(snake);
        networkView.setNetwork(network);
        statsView.setGeneration(population.getGeneration());
        statsView.setMaxScore(maxScore);

        // todo put this elsewhere?
        statsView.addGraphPoint(population.getGeneration(), ((GeneticNet<Snake>) network).getSimulation().getScore());

        // todo remove - this is temporary
        System.out.print("Gen: " + population.getGeneration());
        System.out.println(" | Fitness: " + population.getBestFitness());

        gameLoop.start();
        population.evolveNextGeneration(mutationRate);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double[] input = SnakeUtil.getVision(snake);
        double[] output = network.feedForward(input);

        Direction dir = snake.getDirection();
        double max = output[1];

        if(output[0] > max) {
            dir = dir.getLeft();
            max = output[0];
        }if(output[2] > max) {
            dir = snake.getDirection().getRight();
        }

        snake.setDirection(dir);
        snake.move();

        networkView.setInputs(input);
        networkView.setOutputs(output);
        inputView.setInputs(input);
        statsView.setScore(snake.getScore());

        gameView.repaint();
        networkView.repaint();
        inputView.repaint();

        if(snake.isGameOver()) {
            maxScore = Math.max(maxScore, snake.getScore());
            statsView.setMaxScore(maxScore);
            gameLoop.stop();

            nextGeneration();
        }
    }

    private Timer setupGameloop() {
        return new Timer(timeBetweenFrames, this);
    }

    public void display() {
        networkView = new NetworkView(200, 200);
        inputView = new InputView(200, 300);
        gameView = new GameView(600, 600);
        statsView = new StatsView(200, 200);
        optionsView = new OptionsView(200, 400);

        // ---------------------------------------------------------- //

        JFrame frame = new JFrame("Snake");
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .1;
        c.weighty = .1;
        c.insets = new Insets(1,1,1,1);
        c.fill = GridBagConstraints.NONE;
        panel.add(networkView, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 2;
        c.fill = GridBagConstraints.NONE;
        panel.add(inputView, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.fill = GridBagConstraints.NONE;
        panel.add(gameView, c);

        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        //c.fill = GridBagConstraints.BOTH;
        panel.add(statsView, c);

        c.gridy = 1;
        c.gridheight = 2;
        panel.add(optionsView, c);


        panel.setBackground(Color.black);
        panel.setPreferredSize(new Dimension(1010, 630));
        frame.setMinimumSize(panel.getPreferredSize());

        frame.add(panel, BorderLayout.CENTER);
        frame.setJMenuBar(setUpMenu(frame));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JMenuBar setUpMenu(JFrame frame) {
        // TODO Figure this out for later (not a priority)

        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.add("Load Network");
        file.add("Save Network");
        bar.add(file);

        JMenuItem maximize = new JMenuItem("Full Screen");
        maximize.addActionListener(e -> {
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        });

        JMenuItem minimize = new JMenuItem("Exit Full Screen");
        maximize.addActionListener(e -> {
            frame.setExtendedState(frame.getExtendedState() | JFrame.NORMAL);
        });

        JMenu view = new JMenu("View");
        view.add(maximize);
        view.add(minimize);
        bar.add(view);

        return bar;
    }

    public void startSimulation() {
        nextGeneration();
    }
}