package byog.Core;

import byog.TileEngine.TETile;

/**
 * This class extends Hallway class to be a straight hallway.
 */
public class StraightHallway extends Hallway {
    /* Shape parameters of a straight hallway. */
    private Position end1;
    private Position end2;

    public StraightHallway(Position p1, Position p2, TETile[][] world, TETile floor, TETile wall) {
        if (p1 == null || p2 == null || world == null || floor == null || wall == null) {
            throw new IllegalArgumentException("Tried to initialize " + getClass()
                + " with null argument(s).");
        }
        end1 = p1;
        end2 = p2;
        segments = new Segment[1];
        segments[0] = new Segment(end1, end2);
        initialize(world, floor, wall);
    }
}
