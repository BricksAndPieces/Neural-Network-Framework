//package demo.snake;
//
//import demo.snake.util.Direction;
//import neuralnetwork.core.Function;
//import neuralnetwork.core.NeuralNet;
//import neuralnetwork.core.NeuralNetSettings;
//import neuralnetwork.genetics.Population;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class GamePanel extends JPanel implements ActionListener {
//
//    private static final int DELAY_BETWEEN_FRAMES = 100;
//    private static final RenderingHints rendering =
//            new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);;
//
//    private final Timer timer;
//
//    private final Population population;
//    private Snake snake;
//
//    public GamePanel(Snake snake, int width, int height) {
//        this.timer = new Timer(DELAY_BETWEEN_FRAMES, this);
//        this.snake = snake;
//
//        setFocusable(true);
//        setBackground(Color.black);
//        setPreferredSize(new Dimension(width, height));
//
//        int[] layers = {32, 16, 10, 4};
//        Function activation = Function.SIGMOID;
//        NeuralNetSettings settings = new NeuralNetSettings(layers, activation);
//        population = new Population(100, settings);
//        simulatePopulation();
//    }
//
//    private void simulatePopulation() {
//        // todo
//    }
//
//    public void display() {
//        JFrame frame = new JFrame("Snake");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setUndecorated(true);
//
//        frame.add(this, BorderLayout.CENTER);
//        frame.setResizable(false);
//        frame.pack();
//
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
//
//    @Override
//    protected void paintComponent(Graphics graphics)  {
//        super.paintComponent(graphics);
//        Graphics2D g = (Graphics2D)graphics;
//        g.addRenderingHints(rendering);
//
//        drawUnit(g, Color.red, snake.getFoodLocation());
//        drawUnit(g, Color.white, snake.getHeadLocation());
//        for(Point p : snake.getBodyLocations())
//            drawUnit(g, Color.lightGray, p);
//
//        drawSight(g, snake.getHeadLocation());
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(snake.isGameOver()) {
//            timer.stop();
//        }
//
//        snake.move();
//        repaint();
//    }
//
//    private void drawUnit(Graphics g, Color c, Point p) {
//        int width = (int)(((double)getWidth()) / snake.getWorldWidth());
//        int height = (int)((double)getHeight() / snake.getWorldHeight());
//
//        g.setColor(c);
//        g.fillRect(p.x * width, p.y * height, width, height);
//    }
//
//    private void drawSight(Graphics g, Point p) {
//        int width = (int)(((double)getWidth()) / snake.getWorldWidth());
//        int height = (int)((double)getHeight() / snake.getWorldHeight());
//        int x = width * p.x + width/2;
//        int y = height * p.y + height/2;
//
//        int length = 750;
//
//        g.setColor(Color.white);
//        g.drawLine(x,y,x+length,y);
//        g.drawLine(x,y,x+length,y+length);
//        g.drawLine(x,y,x,y+length);
//        g.drawLine(x,y,x-length,y+length);
//        g.drawLine(x,y,x-length,y);
//        g.drawLine(x,y,x-length,y-length);
//        g.drawLine(x,y,x,y-length);
//        g.drawLine(x,y,x+length,y-length);
//    }
//}