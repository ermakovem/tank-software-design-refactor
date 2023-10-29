package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.logic.objects.Obstacle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class ObstacleGraphics implements Graphics{
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final Texture texture;
    private final Batch batch;

    public ObstacleGraphics(String imagePath, Obstacle obstacle, TiledMapTileLayer groundLayer, Batch batch) {
        this.texture = new Texture(imagePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        this.batch = batch;
        moveRectangleAtTileCenter(groundLayer, rectangle, obstacle.getCoordinates());
    }

    public void render() {
        drawTextureRegionUnscaled(batch, graphics, rectangle, 0f);
    }

    public void dispose() {
        texture.dispose();
    }
}