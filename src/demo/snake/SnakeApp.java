package demo.snake;

import demo.snake.util.Direction;
import demo.snake.util.SnakeUtil;
import demo.snake.views.*;
import neuralnetwork.core.Function;
import neuralnetwork.core.NeuralNet;
import neuralnetwork.core.NeuralNetSettings;
import neuralnetwork.genetics.GeneticNet;
import neuralnetwork.genetics.Population;
import neuralnetwork.util.NetworkStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

@SuppressWarnings("All")
public class SnakeApp implements ActionListener {

    private static final String WINDOW_TITLE = "Snake Neural Network";

    private static final int[] layers = {14, 7, 3};
    private static final Function activation = Function.RELU;

    private double mutationRate = 0.05;
    private int populationSize = 100;
    private int gameSize = 10;

    private int timeBetweenFrames = 50;
    private int gensPerRound = 1;
    private Timer gameLoop;

    private JFrame frame = null;
    private JPanel panel = null;

    private NetworkView networkView;
    private InputView inputView;
    private GameView gameView;
    private StatsView statsView;
    private OptionsView optionsView;

    private Population<Snake> population;
    private NeuralNet network;
    private Snake snake;

    private int maxScore = 0;

    public SnakeApp() {
        gameLoop = setupGameloop();
        population = setupPopulation();
    }

    private Population<Snake> setupPopulation() {
        NeuralNetSettings settings = new NeuralNetSettings(layers, activation);
        return new Population<>(populationSize, new Snake(gameSize, gameSize), settings);
    }

    private void nextGeneration() {
        population.simulateGeneration(); // todo simulate population in background (then i can increase pop size) TIME TO FIX THIS

        for(int i = 0; i < gensPerRound-1; i++) {
            population.evolveNextGeneration(mutationRate);
            population.simulateGeneration();
        }

        network = population.getBestPlayer();
        snake = ((GeneticNet<Snake>) network).getSimulation().copy();

        gameView.setSnake(snake);
        networkView.setNetwork(network);
        statsView.setGeneration(population.getGeneration());
        statsView.setMaxScore(maxScore);

        networkView.repaint();

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

        inputView.setInputs(input);
        statsView.setScore(snake.getScore());

        gameView.repaint();
        inputView.repaint();

        if(snake.isGameOver()) {
            if(snake.getScore() == 99) {
                System.out.println("99: " + snake.getFoodLocation());
            }
            if(snake.getScore()==100) {
                System.out.println(snake.getFoodLocs().size());
                System.out.println("100: " + snake.getFoodLocation());
                System.out.println(snake.getSnakeParts().size());
                System.out.println(new HashSet<Point>(snake.getSnakeParts()).size());
            }

            maxScore = Math.max(maxScore, snake.getScore());
            statsView.addGraphPoint(population.getGeneration(), snake.getScore());
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
        optionsView = new OptionsView(this, 200, 350);

        // ---------------------------------------------------------- //

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .1;
        c.weighty = .1;
        c.insets = new Insets(1,1,1,1);
        panel.add(networkView, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 2;
        panel.add(inputView, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 3;
        panel.add(gameView, c);

        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(statsView, c);

        c.gridy = 1;
        c.gridheight = 2;
        panel.add(optionsView, c);


        panel.setBackground(Color.black);
        panel.setPreferredSize(new Dimension(1010, 630));

        frame = new JFrame(WINDOW_TITLE);
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(panel.getPreferredSize());
        frame.setSize(panel.getPreferredSize());

        frame.add(panel, BorderLayout.CENTER);
        frame.setJMenuBar(setUpMenu(frame));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JMenuBar setUpMenu(JFrame frame) {
        JMenuBar bar = new JMenuBar();

        JMenuItem load = new JMenuItem("Load Network");
        load.addActionListener(e -> loadNetwork());

        JMenuItem save = new JMenuItem("Save Network");
        save.addActionListener(e -> saveNetwork());

        JMenu file = new JMenu("File");
        file.add(load);
        file.add(save);
        bar.add(file);

        JMenuItem maximize = new JMenuItem("Full Screen");
        maximize.addActionListener(e -> {
            fullscreen();
        });

        JMenuItem minimize = new JMenuItem("Exit Full Screen");
        minimize.addActionListener(e -> {
            regularSize();
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

    private void regularSize() {
        frame.dispose();
        frame.setUndecorated(false);
        panel.setSize(panel.getMinimumSize());
        frame.setSize(panel.getMinimumSize());
        frame.setExtendedState(JFrame.NORMAL);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void fullscreen() {
        frame.dispose();
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public void quit() {
        // TODO Are you sure? save pop up
        int result  = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?");
        if(result == JOptionPane.YES_OPTION) {
            System.out.println("Quiting App");
            gameLoop.stop();
            frame.dispose();
            System.exit(0);
        }
    }

    public void loadNetwork() {
        JFileChooser fc = new JFileChooser(); // Open in documents todo
        int result = fc.showOpenDialog(frame);
        if(result == JFileChooser.APPROVE_OPTION) {
            GeneticNet<Snake> net;
            try { net = (GeneticNet<Snake>) NetworkStore.getNeuralNetFromFile(fc.getSelectedFile()); }
            catch(IllegalArgumentException e) {
                System.out.println("No that is a bad file");
                return; // todo make popup
            }

            gameLoop.stop();
            population = new Population<Snake>(populationSize, net);
            optionsView.reset();
            statsView.reset();
            timeBetweenFrames = 50;
            gensPerRound = 1;
            mutationRate = 0.05;
            maxScore = 0;
            gameLoop = setupGameloop();
            startSimulation();
        }
    }

    public void saveNetwork() {
        JFileChooser fc = new JFileChooser(); // Open in Documents todo
        int result = fc.showSaveDialog(frame);
        if(result == JFileChooser.APPROVE_OPTION) {
            NetworkStore.writeNetworkToFile(network, fc.getSelectedFile());
            System.out.println("Should of saved file");
        }
    }

    public void setGameSpeed(int timeBetweenFrames) {
        boolean playing = gameLoop.isRunning();
        gameLoop.stop();

        this.timeBetweenFrames = timeBetweenFrames;
        gameLoop = setupGameloop();

        if(playing)
            gameLoop.start();
    }

    public void setGensPerRound(int gensPerRound) {
        this.gensPerRound = gensPerRound;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }
}