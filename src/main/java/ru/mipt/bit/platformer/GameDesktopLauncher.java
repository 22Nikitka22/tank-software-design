package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mipt.bit.platformer.configuration.Configuration;
import ru.mipt.bit.platformer.objects.graphics.MapGraphic;
import ru.mipt.bit.platformer.utils.ButtonHandler;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 1024;

    private final MapGraphic mapGraphic;
    private final ButtonHandler inputHandler;

    private Batch spriteBatch;

    public GameDesktopLauncher(MapGraphic mapGraphic, ButtonHandler buttonHandler) {
        this.mapGraphic = mapGraphic;
        this.inputHandler = buttonHandler;
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        mapGraphic.initializeGraphics(spriteBatch);
        mapGraphic.initializeGameObjects();
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();
        inputHandler.checkInput(Gdx.input);
        mapGraphic.renderLevel(deltaTime);

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
        ApplicationContext context = new AnnotationConfigApplicationContext(Configuration.class);

        Lwjgl3ApplicationConfiguration config = createWindowConfig();
        new Lwjgl3Application(new GameDesktopLauncher(
                context.getBean(MapGraphic.class),
                context.getBean(ButtonHandler.class)
        ), config);
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

    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
}
