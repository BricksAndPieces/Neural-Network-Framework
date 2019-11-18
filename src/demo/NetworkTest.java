package demo;

import ai.neuralnetwork.Function;
import ai.neuralnetwork.Matrix;
import ai.neuralnetwork.NeuralNet;

import java.util.Arrays;

public class NetworkTest {

    public static void main(String[] args) {
        network_forced_outcome();
    }

    private static void network_forced_outcome() {
        int[] layers = {1, 1};
        Function actFunc = Function.LINEAR;
        NeuralNet net = new NeuralNet(layers, actFunc);
        net.setBiases(new Matrix[]{new Matrix(new double[]{0})});
        net.setWeights(new Matrix[]{new Matrix(new double[]{-.5})});
        System.out.println(net.feedForward(new double[]{1})[0]);
    }

    private static void network_output() {
        int[] layers = {10, 8, 8, 6, 6, 5};
        Function activation = Function.SIGMOID;
        NeuralNet net = new NeuralNet(layers, activation);

        double[] inputs = {1, 1, 0, 1, 0, 1, 0, 0, 1, 1};
        double[] outputs = net.feedForward(inputs);
        System.out.println(Arrays.toString(outputs));
    }
}