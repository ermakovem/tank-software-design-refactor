package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public interface GameObject {
    GridPoint2 getDestinationCoordinates(); // for collisionHandler
    float getRotation();// for render
}
