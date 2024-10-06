package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.graphics.interfaces.Obstacle;
import ru.mipt.bit.platformer.objects.models.TreeModel;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.*;

public class TreeGraphic implements Obstacle {
    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final TreeModel treeModel;

    public TreeGraphic(String texturePath, GridPoint2 initialPosition, TiledMapTileLayer layer) {
        this.texture = new Texture(texturePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        this.treeModel = new TreeModel(initialPosition);
        moveRectangleAtTileCenter(layer, rectangle, treeModel.getCoordinates());
    }

    public void render(Batch batch) { drawTextureRegionUnscaled(batch, graphics, rectangle, 0f); }

    public void dispose() { texture.dispose(); }

    public GridPoint2 getCoordinates() { return treeModel.getCoordinates(); }
}
