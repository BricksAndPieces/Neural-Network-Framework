package neuralnetwork.genetics;

import neuralnetwork.core.Function;
import neuralnetwork.core.NeuralNetSettings;
import neuralnetwork.core.NeuralNet;
import neuralnetwork.genetics.interfaces.Simulation;

import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("All")
public class GeneticNet<T extends Simulation<T>> extends NeuralNet implements Comparable<GeneticNet> {

    private double fitness = 0;
    private boolean dead = false;

    private T simulation;

    public GeneticNet(NeuralNetSettings settings) {
        super(settings);
    }

    public GeneticNet(int[] layers, Function actFunc) {
        super(layers, actFunc);
    }

    public GeneticNet<T> mutate(double chance, Random rng) {
        GeneticNet copy = (GeneticNet) copy();
        for(int i = 0; i < weights.length; i++) {
            copy.weights[i] = weights[i].mutate(chance, rng);
            copy.biases[i] = biases[i].mutate(chance, rng);
        }

        return copy;
    }

    public GeneticNet<T> crossover(GeneticNet<T> o) {
        GeneticNet copy = (GeneticNet) copy();
        for(int i = 0; i < weights.length; i++) {
            copy.weights[i] = weights[i].crossover(o.weights[i]);
            copy.biases[i] = biases[i].crossover(o.biases[i]);
        }

        return copy;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public T getSimulation() {
        return simulation;
    }

    public void setSimulation(T simulation) {
        this.simulation = simulation;
    }

    @Override
    public int compareTo(GeneticNet o) {
        return Double.compare(fitness, o.fitness);
    }

    @Override
    public GeneticNet<T> copy() {
        GeneticNet copy = new GeneticNet(getLayers(), getActFunc());
        copy.setWeights(copyWeights());
        copy.setBiases(copyBiases());
        return copy;
    }

    @Override
    public String toString() {
        return "GeneticNet[layers="
               +Arrays.toString(getLayers())
               +",fitness="+fitness
               +",dead="+dead+"]";
    }
}