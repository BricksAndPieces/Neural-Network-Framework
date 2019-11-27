package demo.snake.util;

import java.awt.*;

public class ColorUtil {

    public static Color gradient(Color c1, Color c2, double x) {
        int r = (int) (c1.getRed() * x + c2.getRed() * (1 - x));
        int g = (int) (c1.getGreen() * x + c2.getGreen() * (1 - x));
        int b = (int) (c1.getBlue() * x + c2.getBlue() * (1 - x));

        return new Color(r, g, b);
    }

    // Ignore till im able to delete it
    public static javafx.scene.paint.Color bfkjbfkjdsfdbsjkfdbkjsf(javafx.scene.paint.Color c1, javafx.scene.paint.Color c2, double x) {
        int r = (int) (c1.getRed()   * x + c2.getRed()   * (1 - x));
        int g = (int) (c1.getGreen() * x + c2.getGreen() * (1 - x));
        int b = (int) (c1.getBlue()  * x + c2.getBlue()  * (1 - x));

        return javafx.scene.paint.Color.color(r, g, b);
    }
}