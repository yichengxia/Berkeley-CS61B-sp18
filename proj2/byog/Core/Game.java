package byog.Core;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /* Game configurations. */
    private static final String DIRECTORY = "./world.ser";
    private static final int MIN_DIM = 4;
    private static final int MAX_DIM = 4;
    private static final int DIFF_WIDTH_HEIGHT = 1;
    private static final int MAX_ROOMS = 30;
    private static final int MAX_TRIES = 30;
    private static final int HUD_HEIGHT = 2;
    private static final int WINDOW_WIDTH = WIDTH;
    private static final int WINDOW_HEIGHT = HEIGHT + HUD_HEIGHT;
    private static final int TITLE_FONT_SIZE = 40;
    private static final int COMMAND_FONT_SIZE = 30;
    private static final int HUD_FONT_SIZE = 16;

    /* Initial display titles. */
    private static final String TITLE = "CS61B: THE GAME";
    private static final String TITLE_NEW_GAME = "New Game (N)";
    private static final String TITLE_LOAD_GAME = "Load Game (L)";
    private static final String TITLE_QUIT = "Quit (Q)";

    /* Game world parameters. */
    private TETile[][] world;
    private GameState state;
    private List<Position> positions;
    private Walls walls;
    private Player player;

    public Game() {
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        state = new GameState(world, Tileset.PLAYER);
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        input = input.toUpperCase();
        char key = input.charAt(0);
        input = input.substring(1);
        long seed = getSeed(input);
        String commands = getCommands(input);
        Random random = new Random(seed);
        RandomWorldGenerator rwg = new RandomWorldGenerator(world, Tileset.FLOOR, Tileset.WALL, random);
        switch(key) {
            case Command.NEW_GAME:
                positions = rwg.getPositions(MIN_DIM, MAX_DIM, DIFF_WIDTH_HEIGHT, MAX_ROOMS, MAX_TRIES);
                walls = rwg.getWalls(positions);
                player = new Player(positions.get(RandomUtils.uniform(random, 0, positions.size())), positions, Tileset.PLAYER, world);
                break;
            case Command.LOAD_GAME:
                state = GameState.load(DIRECTORY);
                state.setWorld(world);
                state.setPlayerTile(Tileset.PLAYER);
                positions = state.getAllowedPositions();
                player = state.getPlayer();
                walls = rwg.getWalls(positions);
                break;
            default:
                return world;
        }
        movePlayer(commands);
        drawAtPositions(Tileset.FLOOR);
        walls.draw();
        player.draw();
        return world;
    }

    /**
     * Get seed number from input string.
     * @param input
     * @return seed
     */
    private long getSeed(String input) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? Long.parseLong(input.substring(matcher.start(), matcher.end())) : 0;
    }

    /**
     * Get commands from input string.
     * @param input
     * @return commands string
     */
    private String getCommands(String input) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? input.substring(matcher.end()) : input;
    }

    /**
     * Move player around the world according to the commands.
     * @param commands
     */
    private void movePlayer(String commands) {
        for (char c : commands.toCharArray()) {
            switch (c) {
                case Command.UP:
                    player.moveUp();
                    break;
                case Command.DOWN:
                    player.moveDown();
                    break;
                case Command.LEFT:
                    player.moveLeft();
                    break;
                case Command.RIGHT:
                    player.moveRight();
                    break;
                case Command.QUIT:
                    int i = commands.indexOf(c);
                    if (commands.charAt(i + 1) == Command.QUIT) {
                        state.setState(positions, walls.getPositions(), player.position());
                        GameState.save(state, DIRECTORY);
                        return;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Draw certain tiles at positions.
     * @param tile
     */
    private void drawAtPositions(TETile tile) {
        for (Position p : positions) {
            world[p.x()][p.y()] = tile;
        }
    }

    /**
     * Display the quitting message and the game.
     * @param message
     */
    private void quit(String message) {
        diaplay(message);
        StdDraw.pause(1000);
        System.exit(0);
    }

    /**
     * Display a message.
     * @param message
     */
    private void diaplay(String message) {
        Font font = StdDraw.getFont();
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font.deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        StdDraw.text(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, message);
        StdDraw.setFont(font);
        StdDraw.show();
    }
}
