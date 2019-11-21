package neuralnetwork.genetics.interfaces;

import neuralnetwork.genetics.GeneticNet;

public interface Simulation<T extends Simulation<T>> {

    T newInstance();

    double simulate(GeneticNet net);
}