package ru.mipt.bit.platformer.logic;

import com.badlogic.gdx.math.GridPoint2;

public interface HasCollision {
    GridPoint2 getDestinationCoordinates();
    GridPoint2 getCoordinates();
}
