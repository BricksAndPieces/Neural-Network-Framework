package demo.snake;

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

    public static Direction getInstance(int x, int y) {
        for(Direction d : values()) {
            if(d.x == x && d.y == y)
                return d;
        }

        // Invalid inputs
        return null;
    }

    public boolean isOpposite(Direction other) {
        return -x == other.x || -y == other.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}