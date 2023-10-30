package ru.mipt.bit.platformer.game.graphics;

import com.badlogic.gdx.math.GridPoint2;

public interface Renderable {
    GridPoint2 getCoordinates();

    GridPoint2 getDestinationCoordinates();

    float getMovementProgress();

    float getRotation();
}
