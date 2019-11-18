package ai.training;

import ai.neuralnetwork.Function;
import ai.neuralnetwork.Matrix;
import ai.neuralnetwork.NeuralNet;

@SuppressWarnings("All")
public class TrainedNet extends NeuralNet {

    private final double learningRate;
    private final Function derivative;

    public TrainedNet(TrainedNetSettings settings) {
        super(settings);
        this.derivative = settings.getDerivative();
        this.learningRate = settings.getLearningRate();
    }

    public TrainedNet(int[] layers, Function actFunc, Function derivative, double learningRate) {
        super(layers, actFunc);
        this.derivative = derivative;
        this.learningRate = learningRate;
    }

    public void train(double[] input, double[] correct) {
        Matrix matrix = new Matrix(input);
        Matrix[] layers = new Matrix[getLayers().length];
        layers[0] = matrix;

        for(int i = 1; i < getLayers().length; i++) {
            matrix = matrix.dotProduct(weights[i-1]).add(biases[i-1]).function(getActFunc());
            layers[i] = matrix;
        }

        Matrix target = new Matrix(correct);
        for(int i = weights.length; i > 0; i--)
        {
            // Calculate Error
            Matrix error = target.subtract(layers[i]);

            // Calculate Gradient
            Matrix gradient = layers[i].function(derivative);
            gradient = gradient.multiply(error);
            gradient = gradient.multiply(learningRate);

            // Calculate Delta
            Matrix delta = layers[i - 1].transpose().dotProduct(gradient);

            // Adjust weights and biases
            biases[i - 1] = biases[i - 1].add(gradient);
            weights[i - 1] = weights[i - 1].add(delta);

            // Reset target for next loop
            target = error.dotProduct(weights[i - 1].transpose()).add(layers[i - 1]);
        }
    }
}