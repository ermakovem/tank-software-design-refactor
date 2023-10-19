package ru.mipt.bit.platformer.logic.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.logic.CollisionHandler;

public interface CanMove {
    void addCollisionHandler(CollisionHandler collisionHandler);
    void moveTo(GridPoint2 vector);
}
