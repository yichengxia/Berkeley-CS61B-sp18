package byog.Core;

import java.util.ArrayList;
import java.util.List;

import byog.TileEngine.TETile;

/**
 * This class defines walls.
 */
public class Walls {
    /* Position, room, and hallway configurations. */
    private List<Position> wallPositions;
    private List<Position> allPositions; // positions for both rooms and walls
    private List<Room> rooms;
    private List<Hallway> hallways;

    /* Other configurations. */
    private TETile[][] world;
    private TETile floor;
    private TETile wall;

    public Walls(List<Position> positions, TETile[][] world, TETile floor, TETile wall) {
        wallPositions = new ArrayList<>();
        allPositions = positions;
        rooms = null;
        hallways = null;
        this.world = world;
        this.floor = floor;
        this.wall = wall;
        initWalls();
    }

    /**
     * Initialize all walls in the world.
     */
    private void initWalls() {
        /* 1. Loop left-right and bottom-up to add right and upper walls and top-right corners. */
        for (int x = 1; x < world.length; x += 1) {
            for (int y = 1; y < world[0].length; y += 1) {
                Position current = new Position(x, y);
                Position below = new Position(x, y - 1);
                Position left = new Position(x - 1, y);
                Position belowLeft = new Position(x - 1, y - 1);
                if (allPositions.contains(current)) {
                    continue;
                }
                if (allPositions.contains(left)) {
                    addWall(x, y);
                }
                if (allPositions.contains(below)) {
                    addWall(x, y);
                }
                if (wallPositions.contains(left) && wallPositions.contains(below)
                    && allPositions.contains(belowLeft)) {
                    addWall(x, y);
                }
            }
        }
        /* 2. Loop right-left and top-down to add left and lower walls and bottom-left corners. */
        for (int x = world.length - 2; x >= 0; x -= 1) {
            for (int y = world[0].length - 2; y >= 0; y -= 1) {
                Position current = new Position(x, y);
                Position above = new Position(x, y + 1);
                Position right = new Position(x + 1, y);
                Position aboveRight = new Position(x + 1, y + 1);
                if (allPositions.contains(current) || wallPositions.contains(current)) {
                    continue;
                }
                if (allPositions.contains(right)) {
                    addWall(x, y);
                }
                if (allPositions.contains(above)) {
                    addWall(x, y);
                }
                if (wallPositions.contains(right) && wallPositions.contains(above)
                    && allPositions.contains(aboveRight)) {
                    addWall(x, y);
                }
            }
        }
        /* 3. Loop left-right and bottom-up to add top-left and bottom-right corners. */
        for (int x = 1; x < world.length; x += 1) {
            for (int y = 1; y < world[0].length; y += 1) {
                Position current = new Position(x, y);
                Position below = new Position(x, y - 1);
                Position left = new Position(x - 1, y);
                Position belowLeft = new Position(x - 1, y - 1);
                if (!wallPositions.contains(current)) {
                    continue;
                }
                if (allPositions.contains(below) && !allPositions.contains(left)
                    && wallPositions.contains(belowLeft)) {
                    addWall(x - 1, y);
                }
                if (!allPositions.contains(below) && allPositions.contains(left)
                    && wallPositions.contains(belowLeft)) {
                    addWall(x, y - 1);
                }
            }
        }
    }

    /**
     * Add wall at (x, y) in the world.
     * @param x
     * @param y
     */
    private void addWall(int x, int y) {
        Position position = new Position(x, y);
        wallPositions.add(position);
    }

    /**
     * Get wall positions.
     * @return wall positions
     */
    public List<Position> getPositions() {
        return wallPositions;
    }

    /**
     * Draw the walls in the world.
     */
    public void draw() {
        for (Position p : wallPositions) {
            world[p.x()][p.y()] = wall;
        }
    }
}
