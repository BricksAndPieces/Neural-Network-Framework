package demo.visual;

import neuralnetwork.core.Function;
import neuralnetwork.training.TrainedNet;

import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("All")
public class Visualizer extends JPanel {

    private static final int[] layers = new int[]{2, 10, 5, 3, 1};
    //private static final int[] layers = new int[]{2, 5, 3, 1};
    private static final Function activation = Function.SIGMOID;
    private static final Function derivative = Function.SIGMOID_DER;
    private static final double learningRate = 0.05;

    private final TrainedNet net = new TrainedNet(layers, activation, derivative, learningRate);
    private final Timer timer = new Timer(50, e -> repaint());

    private final List<Point> groupA = new ArrayList<>();
    private final List<Point> groupB = new ArrayList<>();

    public Visualizer() {
        setBackground(Color.black);
        setPreferredSize(new Dimension(400, 400));

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(!timer.isRunning()) {
                    if(e.isShiftDown()) groupB.add(new Point(e.getX(), e.getY()));
                    else groupA.add(new Point(e.getX(), e.getY()));

                    repaint();
                    requestFocus();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if(!timer.isRunning()) {
                        timer.start();
                    }else {
                        timer.stop();
                        groupA.clear();
                        groupB.clear();
                        repaint();
                    }
                }
            }
        });
    }

    public void display() {
        JFrame frame = new JFrame("Neural Net Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double accuracy = 0;

        if(timer.isRunning()) {
            double correct = 0;

            for(Point p : groupA)
                if(net.feedForward(new double[]{p.x * 1.0 / getWidth(), p.y * 1.0 / getHeight()})[0] < 0.5)
                    correct++;

            for(Point p : groupB)
                if(net.feedForward(new double[]{p.x * 1.0 / getWidth(), p.y * 1.0 / getHeight()})[0] > 0.5)
                    correct++;

            accuracy = correct / (groupA.size() + groupB.size());

            for(int i = 0; i < 10; i++) {
                for(Point p : groupA)
                    net.train(new double[]{p.x * 1.0 / getWidth(), p.y * 1.0 / getHeight()}, new double[]{0});

                for(Point p : groupB)
                    net.train(new double[]{p.x * 1.0 / getWidth(), p.y * 1.0 / getHeight()}, new double[]{1});
            }

            int unit = 2; // make sure even
            for(int x = 0; x < getWidth(); x+=unit) {
                for(int y = 0; y < getHeight(); y+=unit) {
                    double predict = net.feedForward(new double[]{(x+unit/2) * 1.0 / getWidth(), (y+unit/2) * 1.0 / getHeight()})[0];
                    if(predict < 0.5) g.setColor(Color.red.darker().darker());
                    else g.setColor(Color.blue.darker().darker());

                    g.fillRect(x-unit/2, y-unit/2, unit, unit);
                }
            }

            g.setColor(Color.WHITE);
            g.drawString("Accuracy: " + accuracy + "%", 5, 20);
        }

        g.setColor(Color.red);
        for(Point p : groupA)
            g.fillRect(p.x-5, p.y-5, 10, 10);

        g.setColor(Color.blue);
        for(Point p : groupB)
            g.fillRect(p.x-5, p.y-5, 10, 10);

        if(accuracy == 1) {
            timer.stop();
            groupA.clear();
            groupB.clear();
        }
    }

    public void stop() {
        timer.stop();
    }
}