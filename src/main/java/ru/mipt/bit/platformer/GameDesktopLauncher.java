package ru.mipt.bit.platformer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.Level;
import ru.mipt.bit.platformer.objects.graphics.map.MapFileGraphic;
import ru.mipt.bit.platformer.objects.graphics.map.MapRandomGraphic;
import ru.mipt.bit.platformer.objects.graphics.TankGraphic;
import ru.mipt.bit.platformer.objects.graphics.TreeGraphic;
import ru.mipt.bit.platformer.objects.graphics.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.graphics.interfaces.Obstacle;
import ru.mipt.bit.platformer.objects.graphics.interfaces.Tank;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.utils.ButtonHandler;
import ru.mipt.bit.platformer.utils.TankAIController;
import ru.mipt.bit.platformer.utils.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 1024;
    private static final String TANK_PLAYER_PATH = "images/tank_blue.png";
    private static final String TANK_PATH = "images/tank_green.png";
    private static final String TREE_PATH = "images/greenTree.png";
    private static final String LEVEL_FILE = "level.tmx";

    private final MapModel gameMap;
    private final ButtonHandler inputHandler;

    private Batch spriteBatch;
    private Level gameLevel;
    private TileMovement tileMovement;
    private Tank playerTank;
    private Collection<Tank> enemyTanks;
    private Collection<Obstacle> trees;


    public GameDesktopLauncher(MapLoader mapLoader) {
        this.gameMap = mapLoader.loadMap();
        this.inputHandler = new ButtonHandler();
    }

    @Override
    public void create() {
        initializeGraphics();
        initializeGameObjects();
        setupInputHandling();
    }

    @Override
    public void render() {
        clearScreen();
        float deltaTime = Gdx.graphics.getDeltaTime();
        updateGame(deltaTime);
        renderGame();
    }

    @Override
    public void dispose() {
        trees.forEach(Obstacle::dispose);
        enemyTanks.forEach(Tank::dispose);
        playerTank.dispose();
        gameLevel.dispose();
        spriteBatch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = createWindowConfig();
        MapLoader mapLoader = createMapLoader("File");
        new Lwjgl3Application(new GameDesktopLauncher(mapLoader), config);
    }

    private void initializeGraphics() {
        spriteBatch = new SpriteBatch();
        gameLevel = new Level(LEVEL_FILE, spriteBatch);
        tileMovement = new TileMovement(gameLevel.getLayer(), Interpolation.smooth);
    }

    private void initializeGameObjects() {
        playerTank = new TankGraphic(TANK_PLAYER_PATH, gameMap.getPlayerCoordinates());

        enemyTanks = gameMap.getTanksCoordinates().stream()
                .map(coordinates -> new TankGraphic(TANK_PATH, coordinates))
                .collect(Collectors.toList());

        trees = gameMap.getTreesCoordinates().stream()
                .map(coordinates -> new TreeGraphic(TREE_PATH, coordinates, gameLevel.getLayer()))
                .collect(Collectors.toList());
    }

    private void setupInputHandling() {
        Map<Direction, List<Integer>> controls = Map.of(
                Direction.UP, List.of(UP, W),
                Direction.LEFT, List.of(LEFT, A),
                Direction.DOWN, List.of(DOWN, S),
                Direction.RIGHT, List.of(RIGHT, D)
        );

        controls.forEach((direction, keys) ->
                inputHandler.addButtonAction(keys, () ->
                        playerTank.move(direction, gameMap.getObstaclesCoordinates(),
                                gameMap.getRowCount(), gameMap.getColumnCount())));
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void updateGame(float deltaTime) {
        inputHandler.checkInput(Gdx.input);

        enemyTanks.forEach(tank -> TankAIController.control(tank,
                gameMap.getObstaclesCoordinates(),
                gameMap.getRowCount(),
                gameMap.getColumnCount()));

        playerTank.update(tileMovement, deltaTime);
        enemyTanks.forEach(tank -> tank.update(tileMovement, deltaTime));
    }

    private void renderGame() {
        gameLevel.render();
        spriteBatch.begin();
        trees.forEach(tree -> tree.render(spriteBatch));
        enemyTanks.forEach(tank -> tank.render(spriteBatch));
        playerTank.render(spriteBatch);
        spriteBatch.end();
    }

    private static Lwjgl3ApplicationConfiguration createWindowConfig() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
        return config;
    }

    private static MapLoader createMapLoader(String typeMapLoader) {
        switch (typeMapLoader) {
            case "File": return new MapFileGraphic("src/main/resources/level_map.txt");
            case "Random": return new MapRandomGraphic(10, 8, 1);
            default: throw new IllegalArgumentException("There is no such type of map loader");
        }
    }

    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
}
