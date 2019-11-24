package demo.snake;

import demo.snake.simulations.LoadedPanel;
import demo.snake.simulations.TrainingPanel;

public class Runner {

    public static void main(String[] args) {
//        TrainingPanel training = new TrainingPanel(600, 600);
//        training.display();
//        training.startAI();

        LoadedPanel loaded = new LoadedPanel(600, 600, "networks/GoodNetwork3");
        loaded.display();
    }
}