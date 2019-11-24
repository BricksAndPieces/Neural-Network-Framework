package neuralnetwork.core;

import java.io.Serializable;

@SuppressWarnings("All")
public class NeuralNetSettings implements Serializable
{

    private static final long serialVersionUID = 4L;

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