package ru.mipt.bit.platformer;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.graphics.MapGraphic;
import ru.mipt.bit.platformer.objects.graphics.map.MapFileGraphic;
import ru.mipt.bit.platformer.objects.graphics.map.MapRandomGraphic;
import ru.mipt.bit.platformer.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.utils.ButtonHandler;
import ru.mipt.bit.platformer.utils.TankAIController;
import ru.mipt.bit.platformer.utils.command.HealthBarCommand;
import ru.mipt.bit.platformer.utils.command.MoveTankCommand;
import ru.mipt.bit.platformer.utils.command.ShotCommand;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 1024;

    private final MapGraphic mapGraphic;
    private final ButtonHandler inputHandler;

    private Batch spriteBatch;
    private MovingGraphic playerTank;
    private Collection<MovingGraphic> enemyTanks;

    public GameDesktopLauncher(MapLoader mapLoader) {
        MapModel mapModel = mapLoader.loadMap();
        this.mapGraphic = new MapGraphic(mapModel);
        this.inputHandler = new ButtonHandler();
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        mapGraphic.create(spriteBatch);

        playerTank = mapGraphic.getPlayerTank();
        enemyTanks = mapGraphic.getEnemyTanks();

        Map<Direction, List<Integer>> controls = Map.of(
                Direction.UP, List.of(UP, W),
                Direction.LEFT, List.of(LEFT, A),
                Direction.DOWN, List.of(DOWN, S),
                Direction.RIGHT, List.of(RIGHT, D)
        );

        controls.forEach((direction, keys) ->
                inputHandler.addButtonAction(keys,
                        new MoveTankCommand(playerTank, direction), true));

        inputHandler.addButtonAction(List.of(L), new HealthBarCommand(), false);
        inputHandler.addButtonAction(List.of(SPACE), new ShotCommand(playerTank.getModel()), false);
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();

        inputHandler.checkInput(Gdx.input);
        enemyTanks.forEach(TankAIController::control);

        mapGraphic.render(deltaTime);
        mapGraphic.renderLevel();

        spriteBatch.begin();
        mapGraphic.render(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        mapGraphic.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = createWindowConfig();
        MapLoader mapLoader = createMapLoader("Random");
        new Lwjgl3Application(new GameDesktopLauncher(mapLoader), config);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private static Lwjgl3ApplicationConfiguration createWindowConfig() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
        return config;
    }

    private static MapLoader createMapLoader(String typeMapLoader) {
        switch (typeMapLoader) {
            case "File": return new MapFileGraphic("src/main/resources/level_map.txt");
            case "Random": return new MapRandomGraphic(10, 6, 2);
            default: throw new IllegalArgumentException("There is no such type of map loader");
        }
    }

    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
}
