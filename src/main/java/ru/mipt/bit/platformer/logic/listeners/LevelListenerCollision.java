package ru.mipt.bit.platformer.logic.listeners;

import ru.mipt.bit.platformer.logic.CollisionHandler;
import ru.mipt.bit.platformer.logic.objects.CanCollide;
import ru.mipt.bit.platformer.logic.objects.CanMove;
import ru.mipt.bit.platformer.logic.objects.GameObject;

public class LevelListenerCollision implements LevelListener{
    private final CollisionHandler collisionHandler;

    public LevelListenerCollision(CollisionHandler collisionHandler) {
        this.collisionHandler = collisionHandler;
    }

    @Override
    public void addObject(GameObject gameObject) {
        if (gameObject instanceof CanCollide) {
            collisionHandler.add(gameObject);
            if (gameObject instanceof CanMove) {
                ((CanMove) gameObject).addCollisionHandler(collisionHandler);
            }
        }
    }
}
