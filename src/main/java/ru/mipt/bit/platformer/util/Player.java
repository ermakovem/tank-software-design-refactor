package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Player implements GraphicObject{
    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private GridPoint2 coordinates;

    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    public Player(String texturePath, int coordinateX, int coordinateY) {
        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        texture = new Texture(texturePath);
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
        // set player initial position
        destinationCoordinates = new GridPoint2(coordinateX, coordinateY);
        coordinates = new GridPoint2(destinationCoordinates);
        rotation = 0f;
    }

    //setters
    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }
    public void setDestinationCoordinates(GridPoint2 destinationCoordinates) {
        this.destinationCoordinates = destinationCoordinates;
    }
    public void setMovementProgress(float movementProgress) {
        this.movementProgress = movementProgress;
    }
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    //getters
    public float getMovementProgress() {
        return movementProgress;
    }
    public float getRotation() {
        return rotation;
    }
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
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
