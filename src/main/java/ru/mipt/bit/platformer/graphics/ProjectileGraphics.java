package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.logic.objects.Projectile;
import ru.mipt.bit.platformer.logic.objects.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class ProjectileGraphics implements Graphics{
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final Texture texture;
    private final Projectile tank;
    private final TileMovement tileMovement;
    private final Batch batch;

    public ProjectileGraphics(String imagePath, Projectile tank, TileMovement tileMovement, Batch batch) {
        this.texture = new Texture(imagePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        this.tank = tank;
        this.tileMovement = tileMovement;
        this.batch = batch;
    }

    public void render() {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                tank.getCoordinates(),
                tank.getDestinationCoordinates(),
                tank.getMovementProgress()
        );
        drawTextureRegionUnscaled(batch, graphics, rectangle, tank.getRotation());
    }

    public void dispose() {
        texture.dispose();
    }
}
