package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public interface GraphicObject {
    public GridPoint2 getCoordinates();
    public Rectangle getRectangle();
    public TextureRegion getGraphics();
    public float getRotation();
    public void dispose();
}
