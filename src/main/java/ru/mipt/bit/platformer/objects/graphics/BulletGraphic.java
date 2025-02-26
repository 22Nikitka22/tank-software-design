package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.interfaces.MovingModel;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.utils.TileMovement;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.drawTextureRegionUnscaled;

public class BulletGraphic implements MovingGraphic {

    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle boundingRectangle;
    private final MovingModel bulletModel;

    public BulletGraphic(String texturePath, MovingModel bulletModel) {
        this.texture = new Texture(texturePath);
        this.graphics = new TextureRegion(texture);
        this.boundingRectangle = createBoundingRectangle(graphics);
        this.bulletModel = bulletModel;
    }

    @Override
    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, boundingRectangle, bulletModel.getRotation());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void move(Direction direction) {
        bulletModel.move(direction);
    }

    @Override
    public void update(TileMovement tileMovement, float deltaTime) {
        tileMovement.moveRectangleBetweenTileCenters(
                boundingRectangle, bulletModel.getCoordinate(),
                bulletModel.getDestinationCoordinates(), bulletModel.getMovementProgress()
        );
        bulletModel.update(deltaTime);
    }

    @Override
    public MovingModel getModel() {
        return bulletModel;
    }

    @Override
    public Rectangle getRectangle() {
        return boundingRectangle;
    }
}
