package neuralnetwork.genetics.interfaces;

import neuralnetwork.genetics.GeneticNet;

public interface Simulation {

    boolean update(GeneticNet net);

    double calculateFitness(GeneticNet net);
}