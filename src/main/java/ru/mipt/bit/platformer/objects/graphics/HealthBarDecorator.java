package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.interfaces.HealthModel;
import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.interfaces.Obstacle;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.models.HealthBarModel;
import ru.mipt.bit.platformer.utils.GdxGameUtils;
import ru.mipt.bit.platformer.utils.TileMovement;

import java.util.Set;

public class HealthBarDecorator implements MovingGraphic {

    private static final int HEALTH_BAR_WIDTH = 90;
    private static final int HEALTH_BAR_HEIGHT = 20;
    private static final int HEALTH_BAR_Y_OFFSET = 90;

    private final MovingGraphic wrapped;

    public HealthBarDecorator(MovingGraphic wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void render(Batch batch) {
        wrapped.render(batch);
        renderHealthBar(batch);
    }

    @Override
    public void dispose() {
        wrapped.dispose();
    }

    @Override
    public void move(Direction direction, Set<Obstacle> obstacles, int rowCount, int columnCount) {
        wrapped.move(direction, obstacles, rowCount, columnCount);
    }

    @Override
    public void update(TileMovement tileMovement, float deltaTime) {
        wrapped.update(tileMovement, deltaTime);
    }

    @Override
    public HealthModel getModel() {
        return wrapped.getModel();
    }

    @Override
    public Rectangle getRectangle() {
        return wrapped.getRectangle();
    }

    private void renderHealthBar(Batch batch) {
        if (!HealthBarModel.getVisible()) {
            return;
        }

        float relativeHealth = getModel().getHealth();
        TextureRegion healthBarTexture = createHealthBarTexture(relativeHealth);
        Rectangle healthBarRectangle = createHealthBarRectangle();
        GdxGameUtils.drawTextureRegionUnscaled(batch, healthBarTexture, healthBarRectangle, 0f);
    }

    private TextureRegion createHealthBarTexture(float relativeHealth) {
        Pixmap pixmap = new Pixmap(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(0, 0, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
        pixmap.setColor(Color.GREEN);
        pixmap.fillRectangle(0, 0, (int) (HEALTH_BAR_WIDTH * relativeHealth), HEALTH_BAR_HEIGHT);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegion(texture);
    }

    private Rectangle createHealthBarRectangle() {
        Rectangle rectangle = new Rectangle(wrapped.getRectangle());
        rectangle.y += HEALTH_BAR_Y_OFFSET;
        return rectangle;
    }
}
