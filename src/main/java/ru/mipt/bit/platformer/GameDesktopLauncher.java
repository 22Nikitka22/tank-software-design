package ru.mipt.bit.platformer;

import java.util.Collection;
import java.util.List;
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
import ru.mipt.bit.platformer.objects.graphics.MapFileGraphic;
import ru.mipt.bit.platformer.objects.graphics.MapRandomGraphic;
import ru.mipt.bit.platformer.objects.graphics.TankGraphic;
import ru.mipt.bit.platformer.objects.graphics.TreeGraphic;
import ru.mipt.bit.platformer.objects.graphics.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.graphics.interfaces.Obstacle;
import ru.mipt.bit.platformer.objects.graphics.interfaces.Player;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.utils.ButtonHandler;
import ru.mipt.bit.platformer.utils.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;

    private Level level;
    private TileMovement tileMovement;
    private final MapModel gameMap;

    private Player tank;
    private Collection<Obstacle> trees;

    private final ButtonHandler handler = new ButtonHandler();

    public GameDesktopLauncher(MapLoader mapLoader) { this.gameMap = mapLoader.loadMap(); }

    @Override
    public void create() {
        batch = new SpriteBatch();

        level = new Level("level.tmx", batch);
        tileMovement = new TileMovement(level.getLayer(), Interpolation.smooth);

        tank = new TankGraphic("images/tank_blue.png", gameMap.getPlayerCoordinates());
        trees = gameMap.getTreesCoordinates().stream()
                .map(treeCoordinates -> new TreeGraphic("images/greenTree.png", treeCoordinates, level.getLayer()))
                .collect(Collectors.toList());

        handler.add(List.of(UP, W), () -> tank.move(Direction.UP, gameMap.getTreesCoordinates()));
        handler.add(List.of(LEFT, A), () -> tank.move(Direction.LEFT, gameMap.getTreesCoordinates()));
        handler.add(List.of(DOWN, S), () -> tank.move(Direction.DOWN, gameMap.getTreesCoordinates()));
        handler.add(List.of(RIGHT, D), () -> tank.move(Direction.RIGHT, gameMap.getTreesCoordinates()));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        handler.check(Gdx.input);
        tank.update(tileMovement, deltaTime);

        level.render();

        batch.begin();
        trees.forEach(tree -> tree.render(batch));
        tank.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        trees.forEach(Obstacle::dispose);
        tank.dispose();
        level.dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);

        MapLoader mapLoader = getMapLoader(args);

        new Lwjgl3Application(new GameDesktopLauncher(mapLoader), config);
    }

    private static MapLoader getMapLoader(String[] args) {
        String typeMapLoader = args[0];

        if (typeMapLoader.equals("File")) {
            return new MapFileGraphic("/Users/nikitabulanov/tank-software-design/src/main/resources/level_map.txt");
        } else if (typeMapLoader.equals("Ranndom")) {
            return new MapRandomGraphic(10, 10);
        } else {
            throw new RuntimeException("There is no such type of map loader");
        }
    }
}
