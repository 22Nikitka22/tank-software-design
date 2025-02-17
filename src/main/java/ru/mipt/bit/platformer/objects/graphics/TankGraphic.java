package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.interfaces.Obstacle;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.utils.TileMovement;

import java.util.Set;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.drawTextureRegionUnscaled;

public class TankGraphic implements MovingGraphic {

    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final TankModel model;

    public TankGraphic(String texturePath, TankModel tankModel) {
        this.texture = new Texture(texturePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        this.model = tankModel;
    }

    @Override
    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, model.getRotation());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void move(Direction direction, Set<Obstacle> obstacles, int rowCount, int columnCount) {
        model.move(direction, obstacles, rowCount, columnCount);
    }

    @Override
    public void update(TileMovement tileMovement,  float deltaTime) {
        model.update(tileMovement, deltaTime, rectangle);
    }

    @Override
    public TankModel getModel() {
        return model;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
}
