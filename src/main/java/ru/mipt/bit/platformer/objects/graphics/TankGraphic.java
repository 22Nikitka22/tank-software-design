package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.utils.TileMovement;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.drawTextureRegionUnscaled;

public class TankGraphic {
    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final TankModel model;

    public TankGraphic(String texturePath, GridPoint2 initialPosition) {
        this.texture = new Texture(texturePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        this.model = new TankModel(initialPosition);
    }

    public void render(Batch batch) { drawTextureRegionUnscaled(batch, graphics, rectangle, model.getRotation()); }

    public void dispose() { texture.dispose(); }

    public void move(Direction direction, GridPoint2 obstacleCoordinates) { model.move(direction, obstacleCoordinates); }

    public void update(TileMovement tileMovement,  float deltaTime) { model.update(tileMovement, deltaTime, rectangle); }
}
