package neuralnetwork.genetics.interfaces;

import neuralnetwork.genetics.GeneticNet;

public interface Simulation<T extends Simulation<T>> {

    T newInstance();

    boolean update(GeneticNet net);

    double calculateFitness(GeneticNet net);
}