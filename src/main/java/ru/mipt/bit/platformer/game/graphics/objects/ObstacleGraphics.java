package ru.mipt.bit.platformer.game.graphics.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.graphics.Graphics;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.obstacle.Obstacle;

import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.*;

public class ObstacleGraphics implements Graphics {
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