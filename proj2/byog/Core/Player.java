package byog.Core;

import java.util.List;

import byog.TileEngine.TETile;

/**
 * This class defines a player.
 */
public class Player {
    /* Player configurations. */
    private Position position;
    private List<Position> allowedPositions;
    private TETile playerTile;
    private TETile[][] world;

    public Player(Position position, List<Position> allowePositions, TETile playerTile, TETile[][] world) {
        this.position = position;
        this.allowedPositions = allowePositions;
        this.playerTile = playerTile;
        this.world = world;
    }

    public Position position() {
        return position;
    }

    /**
     * Move the player up.
     */
    public void moveUp() {
        if (canMove(0, 1)) {
            move(0, 1);
        }
    }

    /**
     * Move the player down.
     */
    public void moveDown() {
        if (canMove(0, -1)) {
            move(0, -1);
        }
    }

    /**
     * Move the player left.
     */
    public void moveLeft() {
        if (canMove(-1, 0)) {
            move(-1, 0);
        }
    }

    /**
     * Move the player right.
     */
    public void moveRight() {
        if (canMove(1, 0)) {
            move(1, 0);
        }
    }

    /**
     * Check if the player can move with the increment (dx, xy).
     * @param dx increment on x axis
     * @param dy increment on y axis
     * @return true if the player can move, otherwise false
     */
    private boolean canMove(int dx, int dy) {
        if (position.x() + dx <= 0 || position.x() + dx >= world.length - 1 || position.y() + dy <= 0 ||
            position.y() + dy >= world[0].length - 1) {
            return false;
        }
        Position destination = new Position(position.x() + dx, position.y() + dy);
        return allowedPositions.contains(destination);
    }

    /**
     * Move the position with the increment (dx, xy).
     * @param dx increment on x axis
     * @param dy increment on y axis
     */
    private void move(int dx, int dy) {
        position = new Position(position.x() + dx, position.y() + dy);
    }

    /**
     * Draw the player in the world.
     */
    public void draw() {
        world[position.x()][position.y()] = playerTile;
    }
}
