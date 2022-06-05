package byog.Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import byog.TileEngine.TETile;

/**
 * This class generates a random world, containing world instance and many methods.
 */
public class RandomWorldGenerator {
    /* Default wall size. */
    private static final int WALL_SIZE = 1;

    private TETile[][] world;
    private TETile floor;
    private TETile wall;
    private Random random;

    public RandomWorldGenerator(TETile[][] world, TETile floor, TETile wall, Random random) {
        this.world = world;
        this.floor = floor;
        this.wall = wall;
        this.random = random;
    }

    /**
     * Get the allowed position list of non-overlapping rooms and hallways.
     * @param minDim
     * @param maxDim
     * @param diffWH
     * @param maxRooms
     * @param maxTries
     * @return list of positions
     */
    public List<Position> getPositions(int minDim, int maxDim, int diffWH, int maxRooms, int maxTries) {
        // Get rooms
        List<Room> rooms = new ArrayList<>();
        if (maxDim + diffWH + 2 * WALL_SIZE >= world.length || maxDim + diffWH + 2 * WALL_SIZE >= world[0].length) {
            throw new RuntimeException("Room size is too big to fit world frame.");
        }
        int[] widths = getRandomUniformArray(minDim, maxDim + 1, maxTries);
        int[] heights = getRandomUniformArray(minDim - diffWH, maxDim + diffWH + 1, maxTries);
        for (int i = 0; i < maxRooms; i += 1) {
            int x = RandomUtils.uniform(random, WALL_SIZE, world.length - (maxDim + diffWH + WALL_SIZE));
            int y = RandomUtils.uniform(random, WALL_SIZE, world[0].length - (maxDim + diffWH + WALL_SIZE));
            for (int j = 0; j < maxTries; j += 1) {
                Room room = new Room(new Position(x, y), widths[i], heights[i], world, floor, wall);
                List<Room> overlapping = rooms.stream().filter(r -> r.overlapOnX(room) && r.overlapOnY(room)).collect(Collectors.toList());
                if (overlapping.isEmpty()) {
                    rooms.add(room);
                    break;
                }
            }
        }
        // Get hallways
        List<Hallway> hallways = new ArrayList<>();
        List<Room> connected = new ArrayList<>();
        Room[] shuffledRooms = new Room[rooms.size()];
        shuffledRooms = rooms.toArray(shuffledRooms);
        RandomUtils.shuffle(random, shuffledRooms);
        rooms = Arrays.asList(shuffledRooms);
        connected.add(rooms.get(0));
        for (int i = 1; i < rooms.size(); i += 1) {
            Room room = rooms.get(i);
            if (room.overlapOnX(connected.get(i - 1))) {
                hallways.add(connectAlongY(room, connected.get(i - 1)));
            } else if (room.overlapOnY(connected.get(i - 1))) {
                hallways.add(connectAlongX(room, connected.get(i - 1)));
            } else if (RandomUtils.bernoulli(random)) {
                hallways.add(connectRight(room, connected.get(i - 1)));
            } else {
                hallways.add(connectLeft(room, connected.get(i - 1)));
            }
            connected.add(i, room);
        }
        // Get positions
        List<Position> positions = new ArrayList<>();
        for (Room room : rooms) {
            room.getPositions().forEach(p -> {
                if (!positions.contains(p)) {
                    positions.add(p);
                }
            });
        }
        for (Hallway hallway : hallways) {
            hallway.getPositions().forEach(p -> {
                if (!positions.contains(p)) {
                    positions.add(p);
                }
            });
        }
        return positions;
    }

    /**
     * Get random uniform array of length n within the range [min, max).
     * @param min
     * @param max
     * @param n
     * @return random uniform int array
     */
    private int[] getRandomUniformArray(int min, int max, int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i += 1) {
            arr[i] = RandomUtils.uniform(random, min, max);
        }
        return arr;
    }

    /**
     * Connect two rooms along x axis and return the connected hallway.
     * @param room1
     * @param room2
     * @return connected hallway
     */
    private Hallway connectAlongX(Room room1, Room room2) {
        Room low, high, left, right;
        if (room1.y() < room2.y()) {
            low = room1;
            high = room2;
        } else {
            low = room2;
            high = room1;
        }
        if (room1.x() + room1.width() < room2.x()) {
            left = room1;
            right = room2;
        } else {
            left = room2;
            right = room1;
        }
        int y = RandomUtils.uniform(random, high.y(), low.y() + low.height());
        return new StraightHallway(new Position(left.x() + left.width(), y), new Position(right.x() - 1, y), world, floor, wall);
    }

    /**
     * Connect two rooms along y axis and return the connected hallway.
     * @param room1
     * @param room2
     * @return connected hallway
     */
    private Hallway connectAlongY(Room room1, Room room2) {
        Room low, high, left, right;
        if (room1.y() + room1.height() < room2.y()) {
            low = room1;
            high = room2;
        } else {
            low = room2;
            high = room1;
        }
        if (room1.x() < room2.x()) {
            left = room1;
            right = room2;
        } else {
            left = room2;
            right = room1;
        }
        int x = RandomUtils.uniform(random, right.x(), left.x() + left.width());
        return new StraightHallway(new Position(x, low.y() + low.height()), new Position(x, high.y() - 1), world, floor, wall);
    }

    /**
     * Connect two rooms with a bent hallway from left to right and return the connected hallway.
     * @param room1
     * @param room2
     * @return connected hallway
     */
    private Hallway connectLeft(Room room1, Room room2) {
        Room left, right;
        if (room1.x() < room2.x()) {
            left = room1;
            right = room2;
        } else {
            left = room2;
            right = room1;
        }
        int x1 = right.x() - 1;
        int x2 = RandomUtils.uniform(random, left.x(), left.x() + left.width());
        int y2 = RandomUtils.uniform(random, right.y(), right.y() + right.height());
        int y3 = right.y() < left.y() ? (left.y() - 1) : (left.y() + left.height());
        return new BentHallway(new Position(x1, y2), new Position(x2, y2), new Position(x2, y3), world, floor, wall);
    }

    /**
     * Connect two rooms with a bent hallway from right to left and return the connected hallway.
     * @param room1
     * @param room2
     * @return connected hallway
     */
    private Hallway connectRight(Room room1, Room room2) {
        Room left, right;
        if (room1.x() < room2.x()) {
            left = room1;
            right = room2;
        } else {
            left = room2;
            right = room1;
        }
        int x1 = left.x() + left.width();
        int x2 = RandomUtils.uniform(random, right.x(), right.x() + right.width());
        int y2 = RandomUtils.uniform(random, left.y(), left.y() + left.height());
        int y3 = left.y() < right.y() ? (right.y() - 1) : (right.y() + right.height());
        return new BentHallway(new Position(x1, y2), new Position(x2, y2), new Position(x2, y3), world, floor, wall);
    }

    /**
     * Get a new Walls instance
     * @param positions
     * @return Walls instance
     */
    public Walls getWalls(List<Position> positions) {
        return new Walls(positions, world, floor, wall);
    }
}
