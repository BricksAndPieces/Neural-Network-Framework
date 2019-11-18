package neuralnetwork.genetics;

import neuralnetwork.genetics.interfaces.Simulation;
import neuralnetwork.core.NeuralNet;
import neuralnetwork.core.NeuralNetSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Population {

    private int popSize;
    private int generation = 1;

    private final NeuralNetSettings settings;
    private List<GeneticNet> networks = new ArrayList<>();
    private static final Random rng = new Random();

    public Population(int popSize, NeuralNetSettings settings) {
        this.popSize = popSize;
        this.settings = settings;

        // Add random networks for the initial population
        for(int i = 0; i < popSize; i++)
            networks.add(new GeneticNet(settings));
    }

    public void simulateGeneration(Simulation sim) {
        networks.forEach(n -> n.setFitness(sim.calculateFitness(n)));
    }

//    public void evolveNextGeneration(double mutationChance) {
//        sortGeneration();
//        List<GeneticNet> children = new ArrayList<>();
//        children.add(GeneticNet.fromNeuralNet(getBestPlayer().copy()));
//
//        for(int i = 0; i < children.size()*0.9; i++)
//            children.add(GeneticNet.fromNeuralNet(networks.get(i).mutate(mutationChance, rng)));
//
//        while(children.size() < networks.size())
//            children.add(new GeneticNet(settings));
//
//        generation++;
//        networks = children;
//    }

    public void evolveNextGeneration(double mutationChance) {
        sortGeneration();
        List<GeneticNet> children = new ArrayList<>();

        // Add best players from last generation
        for(int i = 0; i < networks.size()/2; i++)
            children.add(GeneticNet.fromNeuralNet(networks.get(i).copy()));

        // 10% of networks in next gen are random
        for(int i = 0; i < networks.size()/10; i++)
            children.add(new GeneticNet(settings));

        // Rest of population is children from the top 50%
        int topHalf = networks.size();
        while(children.size() < networks.size()) {
            GeneticNet g1 = networks.get((int) (Math.random() * topHalf));
            GeneticNet g2 = networks.get((int) (Math.random() * topHalf));
            GeneticNet child = GeneticNet.fromNeuralNet(g1.crossover(g2));
            children.add(child);
        }

        // Mutate next generation
        for(int i = 1; i < children.size(); i++) {
            NeuralNet mutated = children.get(i).mutate(mutationChance, rng);
            children.set(i, GeneticNet.fromNeuralNet(mutated));
        }

        generation++;
        networks = children;
    }

    private void sortGeneration() {
        networks.sort(Collections.reverseOrder());
    }

    public GeneticNet getBestPlayer() {
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