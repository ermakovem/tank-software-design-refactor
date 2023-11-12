package ru.mipt.bit.platformer.graphics.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.graphics.Graphics;
import ru.mipt.bit.platformer.graphics.Renderable;
import ru.mipt.bit.platformer.graphics.util.TileMovement;

import static ru.mipt.bit.platformer.graphics.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.graphics.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GameObjectGraphics implements Graphics {
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final Texture texture;
    private final Renderable renderable;
    private final TileMovement tileMovement;
    private final Batch batch;

    public GameObjectGraphics(String imagePath, Renderable renderable, TileMovement tileMovement, Batch batch) {
        this.texture = new Texture(imagePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        this.renderable = renderable;
        this.tileMovement = tileMovement;
        this.batch = batch;
    }

    public void render() {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                renderable.getCoordinates(),
                renderable.getDestinationCoordinates(),
                renderable.getMovementProgress()
        );
        drawTextureRegionUnscaled(batch, graphics, rectangle, renderable.getRotation());
    }

    public void dispose() {
        texture.dispose();
    }
}
