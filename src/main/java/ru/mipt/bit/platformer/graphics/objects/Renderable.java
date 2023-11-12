package ru.mipt.bit.platformer.graphics.objects;

import com.badlogic.gdx.math.GridPoint2;

public interface Renderable {
    GridPoint2 getCoordinates();

    GridPoint2 getDestinationCoordinates();

    float getMovementProgress();

    float getRotation();
}
