# Neural-Network-Framework
Example of Neural Network with Genetic Algorithm
```java
class Example implements Simulation {
    
    public static void main(String[] args){
      Example example = new Example(); // Class that holds the fitness function
      
      int[] layers = {10, 8, 6, 4}; // Layer setup
      Function activation = Function.SIGMOID; // Activation function
      
      // Construct a settings object for the neural network
      NeuralNetSettings settings = new NeuralNetSettings(layers, activation);
      
      int size = 100; // Population size
      Population population = new Population(size, settings);
      
      double mutationChance = 0.05; // Chance of mutation
      
      while(true) {
          population.simulateGeneration(example);
          population.evolveNextGeneration(mutationChance);
          
          // Code to run every generation
          // Add a break function when fitness is acceptable
      }
      
      // Code to run after fitness is acceptable
    }
    
    @Override
    public double calculateFitness(GeneticNet net) {
        // Return your fitness function
    }
}
```