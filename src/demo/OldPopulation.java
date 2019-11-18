package demo;

import ai.genetics.interfaces.Simulation;
import ai.genetics.GeneticNet;
import ai.neuralnetwork.NeuralNetSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("All")
public class OldPopulation
{

    private final int popSize;
    private final NeuralNetSettings settings;
    private int generation = 1;

    private List<GeneticNet> networks = new ArrayList<>();
    private static final Random rng = new Random();

    public OldPopulation(int popSize, NeuralNetSettings settings) {
        this.popSize = popSize;
        this.settings = settings;

        for(int i = 0; i < popSize; i++)
            networks.add(new GeneticNet(settings));
    }

    public void simulateGeneration(Simulation e) {
        networks.forEach(n -> n.setFitness(e.calculateFitness(n)));
    }

    public void evolveNextGeneration(double mutationChance, double valChangeChance) {
        sortPopulation();
        List<GeneticNet> children = new ArrayList<>();

        // Add best network from last gen
        children.add(GeneticNet.fromNeuralNet(getBestNetwork().copy()));

        // Add a new completely random net
        children.add(new GeneticNet(settings));

        // Create children based on fitness of the parents of the top half
        while(children.size() < networks.size()) {
            GeneticNet parent1 = selectByFitness();
            GeneticNet parent2 = selectByFitness();

            children.add(GeneticNet.fromNeuralNet(parent1.crossover(parent2)));
        }

        // Mutate some children
        for(int i = 2; i < children.size(); i++) {
            children.set(i, GeneticNet.fromNeuralNet(children.get(i).mutate(mutationChance, rng)));
        }

        generation++;
        networks = children;
    }

    private GeneticNet selectByFitness() {
        double sum = 0;
        for(GeneticNet n : networks.subList(0, networks.size()/2))
            sum += n.getFitness();

        double rand = Math.random() * sum;
        for(GeneticNet n : networks.subList(0, networks.size()/2)) {
            sum -= n.getFitness();
            if(rand > sum)
                return n;
        }

        // This should never happen
        return null;
    }

    private void sortPopulation() {
        networks.sort(Collections.reverseOrder());
    }

    public GeneticNet getBestNetwork() {
        return networks.get(0);
    }

    public int getGeneration() {
        return generation;
    }

    public int size() {
        return popSize;
    }
}