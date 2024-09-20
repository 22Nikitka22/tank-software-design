package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank {

    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final float movementSpeed;

    private final GridPoint2 currentCoordinates;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    public Tank(String texturePath, GridPoint2 initialCoordinates, float movementSpeed) {
        this.texture = new Texture(texturePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.currentCoordinates = new GridPoint2(initialCoordinates);
        this.movementSpeed = movementSpeed;
        this.rotation = 0f;
    }

    public void move(TileMovement tileMovement, float deltaTime, GridPoint2 treeCoordinates) {
        if (isEqual(movementProgress, 1f)) {
            if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
                destinationCoordinates.y++;
                rotation = 90f;
                movementProgress = 0f;
            }
            if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
                destinationCoordinates.x--;
                rotation = -180f;
                movementProgress = 0f;
            }
            if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
                destinationCoordinates.y--;
                rotation = -90f;
                movementProgress = 0f;
            }
            if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
                destinationCoordinates.x++;
                rotation = 0f;
                movementProgress = 0f;
            }
        }

        if (!treeCoordinates.equals(destinationCoordinates)) {
            tileMovement.moveRectangleBetweenTileCenters(rectangle, currentCoordinates, destinationCoordinates, movementProgress);

            movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
            if (isEqual(movementProgress, 1f)) {
                currentCoordinates.set(destinationCoordinates);
            }
        }
    }

    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    public void dispose() {
        texture.dispose();
    }
}
