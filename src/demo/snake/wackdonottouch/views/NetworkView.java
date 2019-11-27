package demo.snake.wackdonottouch.views;

import demo.snake.util.ColorUtil;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import neuralnetwork.core.Matrix;
import neuralnetwork.core.NeuralNet;

import java.awt.*;

@SuppressWarnings("All")
public class NetworkView extends Canvas {

    private final GraphicsContext gc;

    public NetworkView(int width, int height) {
        super(width, height);
        gc = getGraphicsContext2D();
    }

    public void draw(NeuralNet net, double[] inputs, double[] outputs) {
        // Clear the canvas
        gc.clearRect(0, 0, getWidth(), getHeight());

        // Set locations for nodes to be placed
        int nodeSize = (int) (getWidth()/net.getLayers()[0] * 0.5);
        Point[][] nodes = new Point[net.getLayers().length][];
        for(int i = 0; i < nodes.length; i++) {
            nodes[i] = new Point[net.getLayers()[i]];
            for(int j = 0; j < nodes[i].length; j++) {
                // Place node in the correct [x,y] location
                double xLoc = (getWidth()*i/nodes.length)+(getWidth()/nodes.length/2)-(nodeSize/2);
                double yLoc = (getHeight()*j/nodes[i].length)+(getHeight()/nodes[i].length/2)-(nodeSize/2);
                (nodes[i][j] = new Point()).setLocation(xLoc, yLoc);
            }
        }

        for(int i = 0; i < net.getWeights().length; i++) {
            // Draw connections for each layer
            for(int j = 0; j < net.getWeights()[i].rows(); j++) {
                // Draw each connection
                Matrix layer = net.getWeights()[i];
                for(int k = 0; k < net.getWeights()[i].cols(); k++) {
                    // Set color to blue if positive connection and red if negative
                    if(net.getWeights()[i].getCol(k)[j] > 0) gc.setFill(Color.BLUE);
                    else gc.setFill(Color.RED);

                    // Draw connection
                    int offset = nodeSize/2;
                    gc.strokeLine(nodes[i][j].x+offset, nodes[i][j].y+offset, nodes[i+1][k].x+offset, nodes[i+1][k].y+offset);
                }
            }
        }

        // Draw the nodes
        for(int i = 0; i < nodes.length; i++) {
            for(int j = 0; j < nodes[i].length; j++) {
                // Set color of node depending on layer
                if(i == 0) gc.setFill(ColorUtil.bfkjbfkjdsfdbsjkfdbkjsf(Color.WHITE, Color.GREEN, inputs[j])); // Input layer
                else if(i < nodes.length-1) gc.setFill(ColorUtil.bfkjbfkjdsfdbsjkfdbkjsf(Color.WHITE, Color.GREEN, Math.random())); // Hidden layer
                else gc.setFill(ColorUtil.bfkjbfkjdsfdbsjkfdbkjsf(Color.WHITE, Color.GREEN, outputs[j])); // Output layer

                // Draw node
                Point nodeLoc = nodes[i][j];
                gc.fillOval(nodeLoc.x, nodeLoc.y, nodeSize, nodeSize);
            }
        }
    }
}