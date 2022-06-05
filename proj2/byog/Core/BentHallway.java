package byog.Core;

import byog.TileEngine.TETile;

/**
 * This class extends Hallway class to be a bent hallway.
 */
public class BentHallway extends Hallway {
    /* Shape parameters of a bent hallway. */
    private Position end1;
    private Position corner;
    private Position end2;

    public BentHallway(Position p1, Position p2, Position p3, TETile[][] world, TETile floor, TETile wall) {
        if (p1 == null || p2 == null || p3 == null || world == null || floor == null || wall == null) {
            throw new IllegalArgumentException("Tried to initialize " + getClass() + " with null argument(s).");
        }
        if (!(p1.alignedOnX(p2) && p2.alignedOnY(p3)) && !(p1.alignedOnY(p2) && p2.alignedOnX(p3))) {
            throw new IllegalArgumentException("Tried to initialize " + getClass() + " with non-orthogonal positions.");
        }
        end1 = p1;
        corner = p2;
        end2 = p3;
        segments = new Segment[2];
        segments[0] = new Segment(end1, corner);
        segments[1] = new Segment(corner, end2);
        initialize(world, floor, wall);
    }

    @Override
    void initialize(TETile[][] world, TETile floor, TETile wall) {
        this.world = world;
        this.floor = floor;
        this.wall = wall;
    }
}
