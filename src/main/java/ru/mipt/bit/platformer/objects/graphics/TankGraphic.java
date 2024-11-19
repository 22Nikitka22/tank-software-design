package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.utils.TileMovement;

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
    public void move(Direction direction) {
        model.move(direction);
    }

    @Override
    public void update(TileMovement tileMovement,  float deltaTime) {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle, model.getCoordinate(),
                model.getDestinationCoordinates(), model.getMovementProgress()
        );
        model.update(deltaTime);
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    public TankModel getModel() {
        return model;
    }
}
