package demo.visual;

import javax.swing.*;

@SuppressWarnings("All")
public class Runner {

    public static void main(String[] args) {
        Visualizer visualizer = new Visualizer();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> visualizer.stop()));
        SwingUtilities.invokeLater(() -> {
            visualizer.display();
        });
    }
}