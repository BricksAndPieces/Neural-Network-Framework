package neuralnetwork.core;

@SuppressWarnings("All")
public class NeuralNetSettings {

    private final int[] layers;
    private final Function actFunc;

    public NeuralNetSettings(int[] layers, Function actFunc) {
        this.layers = layers;
        this.actFunc = actFunc;
    }

    public int[] getLayers() {
        return layers;
    }

    public Function getActFunc() {
        return actFunc;
    }
}