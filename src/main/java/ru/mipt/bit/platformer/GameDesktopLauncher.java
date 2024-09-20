package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.objects.Level;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;
    private Level level;
    private Tank tank;
    private Tree tree;
    private TileMovement tileMovement;

    @Override
    public void create() {
        batch = new SpriteBatch();
        level = new Level("level.tmx", batch);
        tileMovement = new TileMovement(level.getLayer(), Interpolation.smooth);
        tank = new Tank("images/tank_blue.png", new GridPoint2(1, 1), 1f);
        tree = new Tree("images/greenTree.png", new GridPoint2(1, 3), level.getLayer());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        tank.move(tileMovement, deltaTime, tree.getCoordinates());

        level.render();

        batch.begin();
        tank.render(batch);
        tree.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        tree.dispose();
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
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
