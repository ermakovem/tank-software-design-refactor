package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class RenderObject {
    private final float rotation;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final Texture texture;

    public RenderObject(String imagePath, float rotation) {
        this.rotation = rotation;
        this.texture = new Texture(imagePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
    }

    public float getRotation() {
        return rotation;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void dispose() {
        texture.dispose();
    }
}
