package demo.snake;

import javax.swing.*;

public class Runner
{

    public static void main(String[] args) throws Exception {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // todo get this to work
        SwingUtilities.invokeLater(() -> {
            SnakeApp app = new SnakeApp();
            app.display();
            app.startSimulation();
        });
    }
}