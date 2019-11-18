package neuralnetwork.genetics.interfaces;

import neuralnetwork.genetics.GeneticNet;

// TODO Rename it to something that makes more sense
public interface Simulation {

    double calculateFitness(GeneticNet net);
}