package ru.mipt.bit.platformer.logic;

import com.badlogic.gdx.math.GridPoint2;

public interface CanMove {
    void addCollisionHandler(CollisionHandler collisionHandler);
    void moveTo(GridPoint2 vector);
}
