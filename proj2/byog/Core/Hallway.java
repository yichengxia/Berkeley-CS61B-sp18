package byog.Core;

import java.util.ArrayList;
import java.util.List;

import byog.TileEngine.TETile;

/**
 * This class represents a generic hallway composed with adjacent tiles.
 */
public abstract class Hallway {
    /* Inner Segment class */
    class Segment {
        private Position p1;
        private Position p2;
        private Position[] positions;

        Segment(Position p1, Position p2) {
            if (p1.alignedOnY(p2)) {
                if (p1.y() < p2.y()) {
                    this.p1 = p1;
                    this.p2 = p2;
                } else {
                    this.p1 = p2;
                    this.p2 = p1;
                }
                int n = this.p2.y() - this.p1.y() + 1;
                positions = new Position[n];
                for (int i = 0; i < n; i += 1) {
                    positions[i] = new Position(this.p1.x(), this.p1.y() + i);
                }
            } else if (p1.alignedOnX(p2)) {
                if (p1.x() < p2.x()) {
                    this.p1 = p1;
                    this.p2 = p2;
                } else {
                    this.p1 = p2;
                    this.p2 = p1;
                }
                int n = this.p2.x() - this.p1.x() + 1;
                positions = new Position[n];
                for (int i = 0; i < n; i += 1) {
                    positions[i] = new Position(this.p1.x() + i, this.p1.y());
                }
            } else {
                throw new IllegalArgumentException("Tried to initialize " + getClass()
                    + " with non-orthogonal positions.");
            }
        }
    }

    protected Segment[] segments;

    protected TETile[][] world;
    protected TETile floor;
    protected TETile wall;
    
    /**
     * Initialize a Hallway instance with world, floor, and wall.
     * @param world
     * @param floor
     * @param wall
     */
    protected void initialize(TETile[][] worldTile, TETile floorTile, TETile wallTile) {
        world = worldTile;
        floor = floorTile;
        wall = wallTile;
    }

    /**
     * Get hallway list represented by their segment positions.
     * @return list of positions
     */
    public List<Position> getPositions() {
        List<Position> positions = new ArrayList<>();
        for (Segment segment : segments) {
            for (Position p : segment.positions) {
                positions.add(p);
            }
        }
        return positions;
    }
}
