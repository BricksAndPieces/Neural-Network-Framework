package demo.snake.views;

import javax.swing.*;
import java.awt.*;

public class OptionsView extends JPanel {

    public OptionsView(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);

        add(new JButton("Option1"));
        add(new JButton("Option2"));
        add(new JButton("Option3"));
        add(new JButton("Option4"));
        add(new JButton("Option5"));

    }
}