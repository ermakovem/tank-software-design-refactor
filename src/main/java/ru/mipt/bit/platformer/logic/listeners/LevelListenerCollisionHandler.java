package ru.mipt.bit.platformer.logic.listeners;

import ru.mipt.bit.platformer.logic.CollisionHandler;
import ru.mipt.bit.platformer.logic.objects.CanMove;
import ru.mipt.bit.platformer.logic.objects.GameObject;

public class LevelListenerCollisionHandler implements LevelListener{
    private final CollisionHandler collisionHandler;

    public LevelListenerCollisionHandler(CollisionHandler collisionHandler) {
        this.collisionHandler = collisionHandler;
    }

    @Override
    public void add(GameObject gameObject) {
        collisionHandler.add(gameObject);
        if (gameObject instanceof CanMove) {
            ((CanMove) gameObject).addCollisionHandler(collisionHandler);
        }
    }
}
