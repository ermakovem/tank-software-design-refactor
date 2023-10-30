package ru.mipt.bit.platformer.game.objectsWithHelpers;

import com.badlogic.gdx.math.GridPoint2;

public interface Collidable {
    GridPoint2 getDestinationCoordinates();

    GridPoint2 getCoordinates();
}
