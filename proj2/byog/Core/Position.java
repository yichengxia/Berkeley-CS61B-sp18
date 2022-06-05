package byog.Core;

import java.io.Serializable;

/**
 * This is the class for (x, y) tuples.
 */
public class Position implements Serializable {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get x coordinate.
     * @return x
     */
    public int x() {
        return x;
    }

    /**
     * Get y coordinate.
     * @return y
     */
    public int y() {
        return y;
    }

    /**
     * Compare the x value of two positions.
     * @param p the position to compare
     * @return true if the x value of two positions are equal, otherwise false
     */
    public boolean alignedOnX(Position p) {
        return x == p.x;
    }

    /**
     * Compare the y value of two positions.
     * @param p the position to compare
     * @return true if the y value of two positions are equal, otherwise false
     */
    public boolean alignedOnY(Position p) {
        return y == p.y;
    }
}
