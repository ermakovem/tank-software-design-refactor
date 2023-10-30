package ru.mipt.bit.platformer.game.graphics.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.graphics.Graphics;
import ru.mipt.bit.platformer.game.graphics.Renderable;
import ru.mipt.bit.platformer.game.graphics.util.TileMovement;

import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.drawTextureRegionUnscaled;

public class ObjectGraphics implements Graphics {
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final Texture texture;
    private final Renderable object;
    private final TileMovement tileMovement;
    private final Batch batch;

    public ObjectGraphics(String imagePath, Renderable object, TileMovement tileMovement, Batch batch) {
        this.texture = new Texture(imagePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        this.object = object;
        this.tileMovement = tileMovement;
        this.batch = batch;
    }

    public void render() {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                object.getCoordinates(),
                object.getDestinationCoordinates(),
                object.getMovementProgress()
        );
        drawTextureRegionUnscaled(batch, graphics, rectangle, object.getRotation());
    }

    public void dispose() {
        texture.dispose();
    }
}
