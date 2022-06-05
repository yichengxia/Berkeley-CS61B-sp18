package byog.Core;

import java.util.ArrayList;
import java.util.List;

import byog.TileEngine.TETile;

/**
 * This class defines a room.
 */
public class Room {
    /* Lower left corner coordinate. */
    private int x;
    private int y;
    /* Size parameters. */
    private int width;
    private int height;
    /* Other configurations. */
    private TETile[][] world;
    private TETile floor;
    private TETile wall;

    public Room(int x, int y, int width, int height, TETile[][] world, TETile floor, TETile wall) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.world = world;
        this.floor = floor;
        this.wall = wall;
        if (x < 0) {
            throw new RuntimeException("Room size out of bounds: x=" + x);
        }
        if (y < 0) {
            throw new RuntimeException("Room size out of bounds: y=" + y);
        }
        if (world.length < x + width) {
            throw new RuntimeException("Room size out of bounds: x=" + x + ", width=" + width);
        }
        if (world[0].length < y + height) {
            throw new RuntimeException("Room size out of bounds: y=" + y + ", height=" + height);
        }
    }

    public Room(Position p, int width, int height, TETile[][] world, TETile floor, TETile wall) {
        this(p.x(), p.y(), width, height, world, floor, wall);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    /**
     * Compare the x coordinates of two rooms.
     * @param r the room to compare
     * @return true if the x coordinates of two positions overlap, otherwise false
     */
    public boolean overlapOnX(Room r) {
        return (x <= r.x && r.x <= x + width - 1) || (r.x <= x && x <= r.x + r.width - 1);
    }

    /**
     * Compare the y coordinates of two rooms.
     * @param r the room to compare
     * @return true if the y coordinates of two positions overlap, otherwise false
     */
    public boolean overlapOnY(Room r) {
        return (y <= r.y && r.y <= y + height - 1) || (r.y <= y && y <= r.y + r.height - 1);
    }

    /**
     * Get room list represented by their left corner positions.
     * @return list of positions
     */
    public List<Position> getPositions() {
        List<Position> positions = new ArrayList<>();
        for (int i = x; i < x + width; i += 1) {
            for (int j = y; j < y + height; j += 1) {
                positions.add(new Position(i, j));
            }
        }
        return positions;
    }
}
