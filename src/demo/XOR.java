package demo;

import ai.genetics.Population;
import ai.genetics.interfaces.Simulation;
import ai.genetics.GeneticNet;
import ai.neuralnetwork.Function;
import ai.neuralnetwork.Matrix;
import ai.neuralnetwork.NeuralNetSettings;

@SuppressWarnings("All")
public class XOR implements Simulation
{

    public static void main(String[] args) {
        XOR xor = new XOR();
        int[] layers = {2, 2, 1};
        Function actFunc = Function.SIGMOID;
        NeuralNetSettings settings = new NeuralNetSettings(layers, actFunc);
        Population pop = new Population(20, settings);

        double mutationChance = 0.05;

        while(true) {
            if(pop.getGeneration() % 1000 == 0)
            System.out.print("Generation " + pop.getGeneration() + " | ");
            pop.simulateGeneration(xor);
            if(pop.getGeneration() % 1000 == 0)
            System.out.println("Best Fitness: " + pop.getBestFitness());

            if(pop.getBestFitness() >= 3.99)
                break;

            pop.evolveNextGeneration(mutationChance);
        }

        System.out.println("\n-----------------------\n");

        System.out.println("XOR solved in " + pop.getGeneration()  + " Generations");

        System.out.println("\n-----------------------\n");
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                System.out.print("Input: " + i + ", " + j + " | ");
                System.out.print("Expected: " + (i^j) + " | ");
                System.out.println("Network: " + pop.getBestPlayer().feedForward(new double[]{i, j})[0]);
            }
        }

        System.out.println("\n-----------------------\n");
        for(Matrix m : pop.getBestPlayer().getWeights()) {
            System.out.println(m);
        }
        System.out.println("\n-----------------------\n");
        for(Matrix m : pop.getBestPlayer().getBiases()) {
            System.out.println(m);
        }
    }

    @Override
    public double calculateFitness(GeneticNet net) {
        double fitness = 0;
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                double expected = i^j;
                double predict = net.feedForward(new double[]{i,j})[0];
                fitness += 1 - Math.abs(expected - predict);
            }
        }

        return fitness;
    }
}