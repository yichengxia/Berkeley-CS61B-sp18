package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    /**
     * Define class Position as (x, y) tuples.
     */
    public static class Position {
        public int x;
        public int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Computes the width of row i for a size s hexagon.
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }

        return s + 2 * effectiveI;
    }

    /**
     * Computesrelative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position, e.g.
     *   xxxx
     *  xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
     *  xxxxxx
     *   xxxx
     *
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return
     */
    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /** Adds a row of the same tile.
     * @param world the world to draw on
     * @param p the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t the tile to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, new Random());
        }
    }

    /**
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param p the bottom left coordinate of the hexagon
     * @param s the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {

        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        // hexagons have 2*s rows. this code iterates up from the bottom row,
        // which we call row 0.
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;

            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, yi);

            addRow(world, rowStartP, rowWidth, t);

        }
    }

    @Test
    public void testHexRowWidth() {
        assertEquals(3, hexRowWidth(3, 5));
        assertEquals(5, hexRowWidth(3, 4));
        assertEquals(7, hexRowWidth(3, 3));
        assertEquals(7, hexRowWidth(3, 2));
        assertEquals(5, hexRowWidth(3, 1));
        assertEquals(3, hexRowWidth(3, 0));
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }

    /**
     * Demo for size-3 hexagons in lab 5.
     * @param args
     */
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        int width = 30;
        int height = 30;
        ter.initialize(width, height);

        TETile[][] world = new TETile[width][height];

        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // line 1
        addHexagon(world, new Position(13, 0), 3, Tileset.MOUNTAIN);

        // line 2
        addHexagon(world, new Position(8, 3), 3, Tileset.FLOWER);
        addHexagon(world, new Position(18, 3), 3, Tileset.MOUNTAIN);
        
        // line 3
        addHexagon(world, new Position(3, 6), 3, Tileset.GRASS);
        addHexagon(world, new Position(13, 6), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(23, 6), 3, Tileset.SAND);

        // line 4
        addHexagon(world, new Position(8, 9), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(18, 9), 3, Tileset.TREE);

        // line 5
        addHexagon(world, new Position(3, 12), 3, Tileset.GRASS);
        addHexagon(world, new Position(13, 12), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(23, 12), 3, Tileset.TREE);

        // line 6
        addHexagon(world, new Position(8, 15), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(18, 15), 3, Tileset.SAND);

        // line 7
        addHexagon(world, new Position(3, 18), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(13, 18), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(23, 18), 3, Tileset.FLOWER);

        // line 8
        addHexagon(world, new Position(8, 21), 3, Tileset.GRASS);
        addHexagon(world, new Position(18, 21), 3, Tileset.FLOWER);

        // line 9
        addHexagon(world, new Position(13, 24), 3, Tileset.TREE);

        ter.renderFrame(world);
    }
}
