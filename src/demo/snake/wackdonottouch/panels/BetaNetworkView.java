package demo.snake.wackdonottouch.panels;

import neuralnetwork.core.NeuralNet;
import neuralnetwork.util.NetworkGraphics;

import javax.swing.*;
import java.awt.*;

public class BetaNetworkView extends JPanel {

    private NeuralNet network = null;
    private double[] inputs = null;
    private double[] outputs = null;

    public BetaNetworkView() {
        setBackground(Color.black);
        setPreferredSize(new Dimension(200, 200));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(network != null && inputs != null && outputs != null)
            NetworkGraphics.draw(g, network, inputs, outputs, 0, 0, (int) (getWidth() * 0.99), (int) (getWidth() * 0.99));
    }

    public void setNetwork(NeuralNet network) {
        this.network = network;
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
    }

    public void setOutputs(double[] outputs) {
        this.outputs = outputs;
    }
}