package demo.snake;

import java.awt.event.KeyEvent;

public enum Direction {

    UP(0,1),
    DOWN(0,-1),
    LEFT(-1,0),
    RIGHT(1,0);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isOpposite(Direction other) {
        return -x == other.x || -y == other.y;
    }

    public static Direction convertFromKeyCode(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_S :
            case KeyEvent.VK_DOWN : return UP;
            case KeyEvent.VK_W :
            case KeyEvent.VK_UP : return DOWN;
            case KeyEvent.VK_A :
            case KeyEvent.VK_LEFT : return LEFT;
            case KeyEvent.VK_D :
            case KeyEvent.VK_RIGHT : return RIGHT;
        }

        return null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}