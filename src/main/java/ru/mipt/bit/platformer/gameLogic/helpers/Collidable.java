package ru.mipt.bit.platformer.gameLogic.helpers;

import com.badlogic.gdx.math.GridPoint2;

public interface Collidable {
    GridPoint2 getDestinationCoordinates();

    GridPoint2 getCoordinates();
}
