package demo.snake.views;

import demo.snake.SnakeApp;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class OptionsView extends JPanel {

    public OptionsView(SnakeApp app, int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);

        // Labels
        Font font = new Font("TimesRoman", Font.BOLD, 15);
        JLabel speedLabel = new JLabel("Game Speed");
        speedLabel.setForeground(Color.white);
        speedLabel.setFont(font);

        JLabel genLabel = new JLabel("Generation Speed");
        genLabel.setForeground(Color.white);
        genLabel.setFont(font);

        JLabel mutationLabel = new JLabel("Mutation Rate");
        mutationLabel.setForeground(Color.white);
        mutationLabel.setFont(font);

        // Sliders
        JSlider speedSlider = new JSlider(1, 10);
        speedSlider.setValue(1);
        speedSlider.setSnapToTicks(true);
        speedSlider.addChangeListener(e -> {
            if(!speedSlider.getValueIsAdjusting()) {
                int x = 11 - speedSlider.getValue();
                app.setGameSpeed((int)(((10/3.0)*x)+(50/3.0)));
            }
        });

        JSlider genSlider = new JSlider(1, 5);
        genSlider.setValue(1);
        genSlider.setSnapToTicks(true);
        genSlider.addChangeListener(e -> {
            if(!genSlider.getValueIsAdjusting()) {
                app.setGensPerRound(genSlider.getValue()*10);
            }
        });

        JSlider mutationSlider = new JSlider(1, 10);
        mutationSlider.setValue(5);
        mutationSlider.setSnapToTicks(true);
        mutationSlider.addChangeListener(e -> {
            if(!mutationSlider.getValueIsAdjusting()) {
                double mutation = mutationSlider.getValue() / 100.0;
                app.setMutationRate(mutation);
            }
        });

        // Buttons

        JButton loadNet = new JButton("Load Network");
        loadNet.addActionListener(e -> app.loadNetwork());

        JButton saveNet = new JButton("Save Network");
        saveNet.addActionListener(e -> app.saveNetwork());

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> app.quit());

        // Filler
        JPanel filler = new JPanel();
        filler.setPreferredSize(new Dimension(100, 50));
        filler.setBackground(getBackground());

        // Add components to panel
        add(speedLabel);
        add(speedSlider);
        add(genLabel);
        add(genSlider);
        add(mutationLabel);
        add(mutationSlider);
        add(filler);
        add(loadNet);
        add(saveNet);
        add(quit);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g).setStroke(new BasicStroke(4));

        // Draw frame for options
        g.setColor(Color.white);
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);

        // Coloring slider bars
        g.setColor(Color.blue);
        g.drawLine(22, 40, getWidth()-22, 40);
        g.setColor(Color.red);
        g.drawLine(22, 95, getWidth()-22, 95);
        g.setColor(Color.green);
        g.drawLine(22, 150, getWidth()-22, 150);
    }

    public void reset() {
        JSlider[] sliders = Arrays.stream(getComponents()).filter(c -> c instanceof JSlider).toArray(JSlider[]::new);
        sliders[0].setValue(1);
        sliders[1].setValue(1);
        sliders[2].setValue(5);
    }
}