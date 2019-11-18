package demo;

import ai.genetics.interfaces.Simulation;
import ai.genetics.GeneticNet;
import ai.neuralnetwork.Function;
import ai.neuralnetwork.NeuralNetSettings;

@SuppressWarnings("All")
public class SimpleTest implements Simulation
{
    public static void main(String[] args)
    {
        SimpleTest st = new SimpleTest();
        int[] layers = {1, 1, 1, 1};
        Function actFunc = Function.RELU;
        NeuralNetSettings settings = new NeuralNetSettings(layers, actFunc);
        OldPopulation pop = new OldPopulation(25, settings);

        double mutationChance = 0.05;
        double valChangeChance = 0.005;

        while(true) {
            if(pop.getGeneration() % 1 == 0)
                System.out.print("Generation " + pop.getGeneration() + " | ");
            pop.simulateGeneration(st);
            if(pop.getGeneration() % 1 == 0)
                System.out.println("Best Fitness: " + pop.getBestNetwork().getFitness());

            if(pop.getBestNetwork().getFitness() >= 2.99)
                break;

            pop.evolveNextGeneration(mutationChance, valChangeChance);
        }

        System.out.println("\n" + pop.getBestNetwork().feedForward((new double[]{1}))[0]);
    }

    @Override
    public double calculateFitness(GeneticNet net)
    {
        return 3 - Math.abs(3 - net.feedForward(new double[]{1})[0]);
    }
}
