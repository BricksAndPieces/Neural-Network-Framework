package ai.training;

import ai.neuralnetwork.Function;
import ai.neuralnetwork.NeuralNetSettings;

@SuppressWarnings("All")
public class TrainedNetSettings extends NeuralNetSettings {

    private final double learningRate;
    private final Function derivative;

    public TrainedNetSettings(int[] layers, Function actFunc, Function derivative, double learningRate) {
        super(layers, actFunc);
        this.learningRate = learningRate;
        this.derivative = derivative;
    }

    public Function getDerivative() {
        return derivative;
    }

    public double getLearningRate() {
        return learningRate;
    }
}