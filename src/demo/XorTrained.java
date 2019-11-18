package demo;

import ai.neuralnetwork.Function;
import ai.training.TrainedNet;

import java.util.Arrays;

public class XorTrained {

    public static void main(String[] args) {
        //percent();

        TrainedNet brain = new TrainedNet(new int[]{ 2, 2, 1}, Function.TANH, Function.TANH_DER, 0.05);

        int[][] xorInputs = { {0, 0}, {0, 1}, {1, 0}, {1, 1} };
        for(int i = 0; i < xorInputs.length * 2000; i++)
        {
            int[] arr = xorInputs[i % 4];
            double[] inputs = { arr[0], arr[1] };
            double[] outputs = { arr[0] ^ arr[1] };
            brain.train(inputs, outputs);
        }

        for(int i = 0; i < xorInputs.length; i++)
        {
            double[] inputs = { xorInputs[i][0], xorInputs[i][1] };
            double[] outputs = brain.feedForward(inputs);
            System.out.println("Test: " + Arrays.toString(inputs) + " -> " + Arrays.toString(outputs));
        }

        System.out.println("\nWeights");
        for(int i = 0; i < brain.getWeights().length; i++) {
            System.out.println(brain.getWeights()[i]);
        }

        System.out.println("Biases");
        for(int i = 0; i < brain.getBiases().length; i++) {
            System.out.println(brain.getBiases()[i]);
        }
    }

    static void percent() {
        int g = 0;
        double correct = 0;

        while(g < 100) {
            TrainedNet brain = new TrainedNet(new int[]{ 2, 2, 1}, Function.TANH, Function.TANH_DER, 0.05);

            int[][] xorInputs = { {0, 0}, {0, 1}, {1, 0}, {1, 1} };
            for(int i = 0; i < xorInputs.length * 2000; i++)
            {
                int[] arr = xorInputs[i % 4];
                double[] inputs = { arr[0], arr[1] };
                double[] outputs = { arr[0] ^ arr[1] };
                brain.train(inputs, outputs);
            }

            for(int i = 0; i < 2; i++) for(int j = 0; j < 2; j++) {
                double expected = i^j;
                double predicted = brain.feedForward(new double[]{i, j})[0];
                if(Math.abs(expected - predicted) < 0.5) correct += 0.25;
            }

            g++;
        }

        System.out.println(correct + "% of answers correct in 400 trials");
    }
}