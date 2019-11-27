package demo.snake.views;

import demo.snake.util.ColorUtil;
import neuralnetwork.core.Matrix;
import neuralnetwork.core.NeuralNet;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("All")
public class NetworkView extends JPanel {

    private static final RenderingHints rendering =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private NeuralNet network = null;
    private double[] inputs = null;
    private double[] outputs = null;

    public NetworkView(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g).addRenderingHints(rendering);
        if(network == null || inputs == null || outputs == null)
            return; // todo figure out better solution

        // Set locations for nodes to be placed
        int nodeSize = (int) (getWidth() / network.getLayers()[0] * 0.5);
        Point[][] nodes = new Point[network.getLayers().length][];
        for(int i = 0; i < nodes.length; i++) {
            nodes[i] = new Point[network.getLayers()[i]];
            for(int j = 0; j < nodes[i].length; j++) {
                // Place node in the correct [x,y] location
                int xLoc = (getWidth()*i/nodes.length)+(getWidth()/nodes.length/2)-(nodeSize/2);
                int yLoc = (getHeight()*j/nodes[i].length)+(getHeight()/nodes[i].length/2)-(nodeSize/2);
                nodes[i][j] = new Point(xLoc, yLoc);
            }
        }

        for(int i = 0; i < network.getWeights().length; i++) {
            // Draw connections for each layer
            for(int j = 0; j < network.getWeights()[i].rows(); j++) {
                // Draw each connection
                Matrix layer = network.getWeights()[i];
                for(int k = 0; k < network.getWeights()[i].cols(); k++) {
                    // Set color to blue if positive connection and red if negative
                    if(network.getWeights()[i].getCol(k)[j] > 0) g.setColor(Color.blue);
                    else g.setColor(Color.red);

                    // Draw connection
                    int offset = nodeSize/2;
                    g.drawLine(nodes[i][j].x+offset, nodes[i][j].y+offset, nodes[i+1][k].x+offset, nodes[i+1][k].y+offset);
                }
            }
        }

        // Draw the nodes
        for(int i = 0; i < nodes.length; i++) {
            for(int j = 0; j < nodes[i].length; j++) {
                // Set color of node depending on layer
                if(i == 0) g.setColor(ColorUtil.gradient(Color.white, Color.green, inputs[j])); // Input layer
                else if(i < nodes.length-1) ColorUtil.gradient(Color.white, Color.green, Math.random()); // Hidden layer
                else g.setColor(ColorUtil.gradient(Color.white, Color.green, outputs[j])); // Output layer

                // Draw node
                Point nodeLoc = nodes[i][j];
                g.fillOval(nodeLoc.x, nodeLoc.y, nodeSize, nodeSize);
            }
        }
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