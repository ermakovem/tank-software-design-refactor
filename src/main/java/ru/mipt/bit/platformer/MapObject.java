package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public interface MapObject {
    GridPoint2 destinationCoordinates = null;
    void getDestinationCoordinates();
}
