package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public interface HasCollision {
    GridPoint2 getDestinationCoordinates();
    GridPoint2 getCoordinates();
}
