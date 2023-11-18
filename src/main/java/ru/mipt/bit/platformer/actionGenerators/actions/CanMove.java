package ru.mipt.bit.platformer.actionGenerators.actions;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.gameLogic.helpers.CollisionHandler;

public interface CanMove {
    void addCollisionHandler(CollisionHandler collisionHandler);

    void moveTo(GridPoint2 vector);
}
