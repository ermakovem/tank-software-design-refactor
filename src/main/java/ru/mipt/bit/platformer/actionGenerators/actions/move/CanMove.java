package ru.mipt.bit.platformer.actionGenerators.actions.move;

import ru.mipt.bit.platformer.gameLogic.helpers.CollisionHandler;

import static com.badlogic.gdx.math.MathUtils.clamp;

public interface CanMove {
    static float continueProgress(float previousProgress, float deltaTime, float speed) {
        return clamp(previousProgress + deltaTime / speed, 0f, 1f);
    }

    void addCollisionHandler(CollisionHandler collisionHandler);

    void moveTo(Direction direction);
}
