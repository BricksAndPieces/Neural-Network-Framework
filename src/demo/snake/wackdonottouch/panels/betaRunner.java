package demo.snake.wackdonottouch.panels;

import demo.snake.simulations.LoadedPanel;
import demo.snake.simulations.TrainingPanel;

public class betaRunner
{

    // todo test collected networks

    // CHANGE THIS TO CHANGE NN MODE
    private static final boolean train = true;

    public static void main(String[] args) {
        if(train) {
            TrainingPanel training = new TrainingPanel(600, 600);
            training.display();
            training.startAI();
        }else {
            LoadedPanel loaded = new LoadedPanel(600, 600, "networks/PerfectSnake");
            loaded.display();
        }
    }
}