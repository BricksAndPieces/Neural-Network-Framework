package neuralnetwork.genetics;

import neuralnetwork.genetics.interfaces.Simulation;
import neuralnetwork.core.NeuralNetSettings;

import java.util.*;

public class Population<T extends Simulation<T>> {

    private final int popSize;
    private int generation = 0;

    private final NeuralNetSettings settings;
    private List<GeneticNet<T>> networks = new ArrayList<>();
    private final T simulation;

    private static final Random rng = new Random();

    public Population(int popSize, T simulation, NeuralNetSettings settings) {
        this.popSize = popSize;
        this.settings = settings;
        this.simulation = simulation;

        // Add random networks for the initial population
        for(int i = 0; i < popSize; i++) {
            GeneticNet<T> net = new GeneticNet<>(settings);
            net.setSimulation(simulation.newInstance());
            networks.add(net);
        }
    }

    public Population(int popSize, GeneticNet<T> parent) {
        this.popSize = popSize;
        this.settings = new NeuralNetSettings(parent.getLayers(), parent.getActFunc());
        this.simulation = parent.getSimulation();

        networks.add(parent);
        parent.setSimulation(simulation.newInstance());
        for(int i = 0; i < popSize-1; i++) {
            GeneticNet<T> net = parent.copy();
            net.setSimulation(simulation.newInstance());
            networks.add(net);
        }
    }

    public void simulateGeneration() {
        networks.forEach(GeneticNet::simulate);
        sortGeneration();
    }

    public void evolveNextGeneration(double mutationChance) {
        List<GeneticNet<T>> children = new ArrayList<>();

        // Add best players from last generation
        for(int i = 0; i < networks.size()/2; i++)
            children.add(networks.get(i).copy());

        // 10% of networks in next gen are random
        for(int i = 0; i < networks.size()/10; i++)
            children.add(new GeneticNet<>(settings));

        // Rest of population is children from the top 50%
        int topHalf = networks.size();
        while(children.size() < networks.size()) {
            GeneticNet<T> g1 = networks.get((int) (Math.random() * topHalf));
            GeneticNet<T> g2 = networks.get((int) (Math.random() * topHalf));
            GeneticNet<T> child = g1.crossover(g2);
            children.add(child);
        }

        // Mutate next generation
        for(int i = 1; i < children.size(); i++) {
            GeneticNet<T> mutated = children.get(i).mutate(mutationChance, rng);
            children.set(i, mutated);
        }

        generation++;
        networks = children;

        for(GeneticNet<T> g : networks)
            g.setSimulation(simulation.newInstance());
    }

    private void sortGeneration() {
        networks.sort(Collections.reverseOrder());
    }

    public GeneticNet<T> getBestPlayer() {
        return networks.get(0);
    }

    public double getBestFitness() {
        return getBestPlayer().getFitness();
    }

    public int getPopSize() {
        return popSize;
    }

    public int getGeneration() {
        return generation;
    }

    public NeuralNetSettings getSettings() {
        return settings;
    }
}