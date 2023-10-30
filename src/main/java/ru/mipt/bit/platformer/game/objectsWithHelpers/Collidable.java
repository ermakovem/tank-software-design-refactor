package ru.mipt.bit.platformer.game.objectsWithHelpers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameObject;

public interface Collidable {
    GridPoint2 getDestinationCoordinates();

    GridPoint2 getCoordinates();
}
