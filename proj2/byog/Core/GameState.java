package byog.Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import byog.TileEngine.TETile;

/**
 * This class saves state parameters of a game.
 */
public class GameState implements Serializable {
    /* Position configurations. */
    private List<Position> allowedPositions;
    private List<Position> wallsPositions;
    private Position playerPosition;
    /* Other configurations. */
    private TETile[][] world;
    private TETile playerTile;
    
    public GameState(TETile[][] world, TETile playerTile) {
        this.world = world;
        this.playerTile = playerTile;
    }

    public List<Position> getAllowedPositions() {
        return allowedPositions;
    }

    private void setAllowedPositions(List<Position> allowedPositions) {
        this.allowedPositions = allowedPositions;
    }

    private void setWallsPositions(List<Position> wallsPositions) {
        this.wallsPositions = wallsPositions;
    }

    private void setPlayerPosition(Position playerPosition) {
        this.playerPosition = playerPosition;
    }

    public void setWorld(TETile[][] world) {
        this.world = world;
    }

    public void setPlayerTile(TETile playerTile) {
        this.playerTile = playerTile;
    }

    public Player getPlayer() {
        return new Player(playerPosition, allowedPositions, playerTile, world);
    }

    /**
     * Read a file with it file name and return its game state.
     * @param directory
     * @return a GameState instance if it exist, otherwise null
     */
    public static GameState load(String directory) {
        File file = new File(directory);
        if (file.exists()) {
            try {
                FileInputStream fs = new FileInputStream(file);
                ObjectInputStream os = new ObjectInputStream(fs);
                GameState state = (GameState) os.readObject();
                os.close();
                return state;
            } catch (FileNotFoundException e) {
                System.out.println("File " + directory + "is not found.");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e.toString());
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println(e.toString());
                System.exit(0);
            }
        }
        return null;
    }

    /**
     * Save the game state to directory on disk.
     * @param state
     * @param directory
     */
    public static void save(GameState state, String directory) {
        state.world = null;
        state.playerTile = null;
        File file = new File(directory);
        try {
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(state);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + directory + "is not found.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(0);
        }
    }

    /**
     * Set position state in the world, inclusing allowed positions, walls positions,
     * and player position.
     * @param allowedPositions
     * @param wallsPositions
     * @param playerPosition
     */
    public void setState(List<Position> allowedPositions, List<Position> wallsPositions,
        Position playerPosition) {
        if (allowedPositions == null || wallsPositions == null || playerPosition == null) {
            throw new IllegalArgumentException("Positions to set for game state can not be null.");
        }
        setAllowedPositions(allowedPositions);
        setWallsPositions(wallsPositions);
        setPlayerPosition(playerPosition);
    }
}
