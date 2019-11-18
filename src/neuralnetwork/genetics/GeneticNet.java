package neuralnetwork.genetics;

import neuralnetwork.core.Function;
import neuralnetwork.core.NeuralNetSettings;
import neuralnetwork.core.NeuralNet;

@SuppressWarnings("All")
public class GeneticNet extends NeuralNet implements Comparable<GeneticNet> {

    private double fitness = 0;

    public GeneticNet(NeuralNetSettings settings) {
        super(settings);
    }

    public GeneticNet(int[] layers, Function actFunc) {
        super(layers, actFunc);
    }

    // -------------------------------------------------------------------------------------------------- //

    public static GeneticNet fromNeuralNet(NeuralNet n) {
        GeneticNet g = new GeneticNet(n.getLayers(), n.getActFunc());
        for(int i = 0; i < n.getWeights().length; i++) {
            g.weights[i] = n.getWeights()[i].copy();
            g.biases[i] = n.getBiases()[i].copy();
        }

        return g;
    }

    // -------------------------------------------------------------------------------------------------- //

    @Override
    public int compareTo(GeneticNet o) {
        return Double.compare(fitness, o.fitness);
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}