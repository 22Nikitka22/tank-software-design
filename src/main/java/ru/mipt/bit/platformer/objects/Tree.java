package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tree {
    final private Texture texture;
    final private TextureRegion graphics;
    final private Rectangle rectangle;
    final private GridPoint2 coordinates;

    public Tree(String texturePath, GridPoint2 initialPosition, TiledMapTileLayer layer) {
        this.texture = new Texture(texturePath);
        this.graphics = new TextureRegion(texture);
        this.coordinates = new GridPoint2(initialPosition);
        this.rectangle = createBoundingRectangle(graphics);
        moveRectangleAtTileCenter(layer, rectangle, coordinates);
    }

    public void render(Batch batch) { drawTextureRegionUnscaled(batch, graphics, rectangle, 0f); }

    public void dispose() { texture.dispose(); }

    public GridPoint2 getCoordinates() { return coordinates; }
}