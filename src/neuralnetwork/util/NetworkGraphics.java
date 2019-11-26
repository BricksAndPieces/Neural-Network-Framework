package neuralnetwork.util;

import neuralnetwork.core.Matrix;
import neuralnetwork.core.NeuralNet;

import java.awt.*;

@SuppressWarnings("All")
public class NetworkGraphics {

    public static void draw(Graphics g, NeuralNet net, double[] inputs, double[] outputs, int x, int y, int w, int h) {
        // Draw frame for the Neural Net Drawing
        g.setColor(Color.white);
        g.drawRect(x,y,w,h);

        // Set locations for nodes to be placed
        int nodeSize = (int) (w / net.getLayers()[0] * 0.5);
        Point[][] nodes = new Point[net.getLayers().length][];
        for(int i = 0; i < nodes.length; i++) {
            nodes[i] = new Point[net.getLayers()[i]];
            for(int j = 0; j < nodes[i].length; j++) {
                // Place node in the correct [x,y] location
                int xLoc = (x+w*i/nodes.length)+(w/nodes.length/2)-(nodeSize/2);
                int yLoc = (y+h*j/nodes[i].length)+(h/nodes[i].length/2)-(nodeSize/2);
                nodes[i][j] = new Point(xLoc, yLoc);
            }
        }

        for(int i = 0; i < net.getWeights().length; i++) {
            // Draw connections for each layer
            for(int j = 0; j < net.getWeights()[i].rows(); j++) {
                // Draw each connection
                Matrix layer = net.getWeights()[i];
                for(int k = 0; k < net.getWeights()[i].cols(); k++) {
                    // Set color to blue if positive connection and red if negative
                    if(net.getWeights()[i].getCol(k)[j] > 0) g.setColor(Color.blue);
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
                if(i == 0) g.setColor(colorBetween(Color.white, Color.green, inputs[j])); // Input layer
                else if(i < nodes.length-1) g.setColor(Color.white); // Hidden layer
                else g.setColor(colorBetween(Color.white, Color.green, outputs[j])); // Output layer

                // Draw node
                Point nodeLoc = nodes[i][j];
                g.fillOval(nodeLoc.x, nodeLoc.y, nodeSize, nodeSize);
            }
        }
    }

    private static Color colorBetween(Color c1, Color c2, double loc) {
        int r = (int) (Math.min(c1.getRed(), c2.getRed()) + loc * Math.abs(c1.getRed() - c2.getRed()));
        int g = (int) (Math.min(c1.getGreen(), c2.getGreen()) + loc * Math.abs(c1.getGreen() - c2.getGreen()));
        int b = (int) (Math.min(c1.getBlue(), c2.getBlue()) + loc * Math.abs(c1.getBlue() - c2.getBlue()));
        return new Color(r,g,b);
    }
}