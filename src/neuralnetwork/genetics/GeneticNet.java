package neuralnetwork.genetics;

import neuralnetwork.core.Function;
import neuralnetwork.core.Matrix;
import neuralnetwork.core.NeuralNetSettings;
import neuralnetwork.core.NeuralNet;

import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("All")
public class GeneticNet extends NeuralNet implements Comparable<GeneticNet> {

    private double fitness = 0;
    private boolean dead = false;

    public GeneticNet(NeuralNetSettings settings) {
        super(settings);
    }

    public GeneticNet(int[] layers, Function actFunc) {
        super(layers, actFunc);
    }

    public GeneticNet mutate(double chance, Random rng) {
        GeneticNet copy = (GeneticNet) copy();
        for(int i = 0; i < weights.length; i++) {
            copy.weights[i] = weights[i].mutate(chance, rng);
            copy.biases[i] = biases[i].mutate(chance, rng);
        }

        return copy;
    }

    public GeneticNet crossover(GeneticNet o) {
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

    @Override
    public int compareTo(GeneticNet o) {
        return Double.compare(fitness, o.fitness);
    }

    @Override
    public GeneticNet copy() {
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