package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Obstacle {
    private Rectangle obstacleRectangle;
    protected GridPoint2 obstacleCoordinates;
    private Texture obstacleTexture;
    private TextureRegion obstacleGraphics;

    public Obstacle(String texturePath, int coordinateX, int coordinateY) {
        obstacleTexture = new Texture(texturePath);
        obstacleGraphics = new TextureRegion(obstacleTexture);
        obstacleCoordinates = new GridPoint2(coordinateX, coordinateY);
        obstacleRectangle = createBoundingRectangle(obstacleGraphics);
    }

    public GridPoint2 getObstacleCoordinates() {
        return obstacleCoordinates;
    }
    public Rectangle getObstacleRectangle() {
        return obstacleRectangle;
    }
    public Texture getObstacleTexture() {
        return obstacleTexture;
    }
    public TextureRegion getObstacleGraphics() {
        return obstacleGraphics;
    }
    public void dispose() {
        obstacleTexture.dispose();
    }
}