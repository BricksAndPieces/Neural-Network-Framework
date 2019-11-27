package demo.snake;

import javax.swing.*;

public class SnakeApp {

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        SwingUtilities.invokeLater(() -> {
            SnakeUI app = new SnakeUI();
            app.display();
            app.startSimulation();
        });
    }
}