package ru.mipt.bit.platformer.logic.objects;

import com.badlogic.gdx.math.GridPoint2;

public interface CanCollide {
    GridPoint2 getDestinationCoordinates();
    GridPoint2 getCoordinates();
}
