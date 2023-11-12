package ru.mipt.bit.platformer.game.objectsWithHelpers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.HasAction;

public interface CanMove {
    void addCollisionHandler(CollisionHandler collisionHandler);

    void moveTo(GridPoint2 vector);
}
