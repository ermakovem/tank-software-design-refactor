package ru.mipt.bit.platformer.game.graphics.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.graphics.Graphics;
import ru.mipt.bit.platformer.game.graphics.util.TileMovement;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.projectile.Projectile;

import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.drawTextureRegionUnscaled;

public class ProjectileGraphics implements Graphics {
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final Texture texture;
    private final Projectile projectile;
    private final TileMovement tileMovement;
    private final Batch batch;

    public ProjectileGraphics(String imagePath, Projectile projectile, TileMovement tileMovement, Batch batch) {
        this.texture = new Texture(imagePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        this.projectile = projectile;
        this.tileMovement = tileMovement;
        this.batch = batch;
    }

    public void render() {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                projectile.getCoordinates(),
                projectile.getDestinationCoordinates(),
                projectile.getMovementProgress()
        );
        drawTextureRegionUnscaled(batch, graphics, rectangle, projectile.getRotation());
    }

    public void dispose() {
        texture.dispose();
    }
}
