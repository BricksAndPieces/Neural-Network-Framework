package demo.snake;

public class Runner {

    public static void main(String[] args) {
        GamePanel panel = new GamePanel(600, 600);
        panel.display();
        panel.startAI();
    }
}