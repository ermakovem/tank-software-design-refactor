package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Obstacle implements GraphicObject{
    private final Rectangle rectangle;
    protected final GridPoint2 coordinates;
    private final Texture texture;
    private TextureRegion graphics;

    public Obstacle(String texturePath, int coordinateX, int coordinateY) {
        texture = new Texture(texturePath);
        graphics = new TextureRegion(texture);
        coordinates = new GridPoint2(coordinateX, coordinateY);
        rectangle = createBoundingRectangle(graphics);
    }
    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }
    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
    @Override
    public TextureRegion getGraphics() {
        return graphics;
    }
    @Override
    public void dispose() {
        texture.dispose();
    }
}