package neuralnetwork.core;

import neuralnetwork.util.Copyable;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("All")
public class NeuralNet implements Copyable<NeuralNet>, Serializable {

    private static final long serialVersionUID = 1L;

    protected Matrix[] weights; // Multi dimensional - Each matrix contains all weights between two layers
    protected Matrix[] biases; // One dimensional - Each matrix contains a list of biases for each 'output' node

    private final Function actFunc;
    private final int[] layers;

    public NeuralNet(NeuralNetSettings settings) {
        this(settings.getLayers(), settings.getActFunc());
    }

    public NeuralNet(int[] layers, Function actFunc) {
        this.actFunc = actFunc;
        this.layers = layers;

        // Construct net of weights
        weights = new Matrix[layers.length-1];
        for(int i = 0; i < weights.length; i++) {
            weights[i] = new Matrix(layers[i], layers[i+1]);
            weights[i] = weights[i].randomize();
        }

        // Construct net of biases
        biases = new Matrix[layers.length-1];
        for(int i = 0; i < biases.length; i++) {
            biases[i] = new Matrix(1, layers[i+1]);
            biases[i] = biases[i].randomize();
        }
    }

    public double[] feedForward(double[] input) {
        Matrix matrix = new Matrix(input);
        for(int i = 0; i < weights.length-1; i++)
            matrix = matrix.dotProduct(weights[i]).add(biases[i]).function(actFunc); // todo add 2nd function to settings

        matrix = matrix.dotProduct(weights[weights.length-1]).add(biases[biases.length-1]).function(Function.SIGMOID);
        double[] output = new double[layers[layers.length-1]];
        for(int i = 0; i < output.length; i++)
            output[i] = matrix.getData()[0][i];

        return output;
    }

    public Matrix[] getWeights() {
        return weights;
    }

    public void setWeights(Matrix[] weights) {
        this.weights = weights;
    }

    public Matrix[] getBiases() {
        return biases;
    }

    public void setBiases(Matrix[] biases) {
        this.biases = biases;
    }

    public Function getActFunc() {
        return actFunc;
    }

    public int[] getLayers() {
        return layers;
    }

    public Matrix[] copyWeights() {
        Matrix[] copy = new Matrix[weights.length];
        for(int i = 0; i < weights.length; i++)
            copy[i] = weights[i].copy();

        return copy;
    }

    public Matrix[] copyBiases() {
        Matrix[] copy = new Matrix[biases.length];
        for(int i = 0; i < biases.length; i++)
            copy[i] = biases[i].copy();

        return copy;
    }

    @Override
    public NeuralNet copy() {
        NeuralNet n = new NeuralNet(layers, actFunc);
        n.setWeights(copyWeights());
        n.setBiases(copyBiases());
        return n;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof NeuralNet)) return false;
        return Arrays.equals(weights, ((NeuralNet) obj).weights) &&
               Arrays.equals(biases, ((NeuralNet) obj).biases);
    }

    @Override
    public String toString() {
        return "NeuralNet[layers="+Arrays.toString(layers)+"]";
    }
}