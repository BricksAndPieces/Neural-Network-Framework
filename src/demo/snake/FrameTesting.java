package demo.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrameTesting {

    public static void main(String[] args) {

        JPanel network = new JPanel();
        network.setBackground(Color.yellow);
        network.setPreferredSize(new Dimension(200, 200));

        JPanel inputs = new JPanel();
        inputs.setBackground(Color.red);
        inputs.setPreferredSize(new Dimension(200, 400));

        JPanel game = new JPanel();
        game.setBackground(Color.green);
        game.setPreferredSize(new Dimension(600, 600));
        game.setMinimumSize(game.getPreferredSize());

        JPanel stats = new JPanel();
        stats.setBackground(Color.blue);
        stats.setPreferredSize(new Dimension(200, 200));

        JPanel options = new JPanel();
        options.setBackground(Color.CYAN);
        options.setPreferredSize(new Dimension(200, 400));

        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(200, 600));
        left.add(network, BorderLayout.NORTH);
        left.add(inputs, BorderLayout.SOUTH);

        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(200, 600));
        right.add(stats, BorderLayout.NORTH);
        right.add(options, BorderLayout.SOUTH);

        // ---------------------------------------------------------- //

        JFrame frame = new JFrame("Snake");
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .1;
        c.weighty = .1;
        c.insets = new Insets(1,1,1,1);
        c.fill = GridBagConstraints.BOTH;
        panel.add(network, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 2;
        panel.add(inputs, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.fill = GridBagConstraints.NONE;
        panel.add(game, c);

        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        panel.add(stats, c);

        c.gridy = 1;
        c.gridheight = 2;
        panel.add(options, c);

        // todo get the panels scale with window

        panel.setBackground(Color.black);
        panel.setPreferredSize(new Dimension(1010, 630));
        frame.setMinimumSize(panel.getPreferredSize());

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e)
            {
                System.out.println(frame.getSize());
            }
        });

        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}