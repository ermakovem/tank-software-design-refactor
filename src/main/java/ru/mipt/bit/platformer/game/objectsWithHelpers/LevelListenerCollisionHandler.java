package ru.mipt.bit.platformer.game.objectsWithHelpers;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.LevelListener;

public class LevelListenerCollisionHandler implements LevelListener {
    private final CollisionHandler collisionHandler;

    public LevelListenerCollisionHandler(CollisionHandler collisionHandler) {
        this.collisionHandler = collisionHandler;
    }

    @Override
    public void add(GameObject gameObject) {
        if (gameObject instanceof Collidable) {
            collisionHandler.add((Collidable) gameObject);
        }
        if (gameObject instanceof CanMove) {
            ((CanMove) gameObject).addCollisionHandler(collisionHandler);
        }
    }

    @Override
    public void parseState(GameObject gameObject) {
        if (gameObject instanceof Collidable) {
            collisionHandler.parseState((Collidable) gameObject, gameObject.getState());
        }
    }
}
