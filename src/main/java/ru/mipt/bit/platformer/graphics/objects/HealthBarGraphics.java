package ru.mipt.bit.platformer.graphics.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.objectsWithHelpers.objects.tank.Hittable;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class HealthBarGraphics implements Graphics, Toggleable {
    private final Texture textureRedBar;
    private final TextureRegion graphicsRedBar;
    private final Rectangle rectangle;
    private Texture textureGreenBar;
    private TextureRegion graphicsGreenBar;
    private final Renderable renderable;
    private final Hittable hittable;
    private final TileMovement tileMovement;
    private final Batch batch;
    private boolean toggle = true;

    public HealthBarGraphics(Hittable hittable, Renderable renderable, TileMovement tileMovement, Batch batch) {
        this.textureRedBar = createBarTexture(100, Color.RED);
        this.graphicsRedBar = new TextureRegion(textureRedBar);
        this.rectangle = createBoundingRectangle(graphicsRedBar);

        this.textureGreenBar = createBarTexture(100, Color.GREEN);
        this.graphicsGreenBar = new TextureRegion(textureGreenBar);

        this.renderable = renderable;
        this.hittable = hittable;
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
        rectangle.y += 75f;

        textureGreenBar.dispose();
        this.textureGreenBar = createBarTexture(hittable.getHP(), Color.GREEN);
        this.graphicsGreenBar = new TextureRegion(textureGreenBar);
        if (toggle) {
            drawTextureRegionUnscaled(batch, graphicsRedBar, rectangle, 0);
            drawTextureRegionUnscaled(batch, graphicsGreenBar, rectangle, 0);
        }
    }

    public void dispose() {
        textureGreenBar.dispose();
        textureRedBar.dispose();
    }

    private Texture createBarTexture(int health, Color color) {
        Pixmap pixmap = new Pixmap(90 * health / 100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, 90 * health / 100, 20);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    @Override
    public void toggle() {
        toggle = !toggle;
    }
}
