package ru.mipt.bit.platformer;

import java.util.ArrayList;
import java.util.List;

public class GameLevel {
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final CollisionHandler collisionHandler;

    public GameLevel(CollisionHandler collisionHandler) {
        this.collisionHandler = collisionHandler;
    }

    public void add(GameObject o) {
        if (o instanceof HasCollision) {
            collisionHandler.add((HasCollision) o);
        }
        if (o instanceof CanMove) {
            ((CanMove) o).addCollisionHandler(collisionHandler);
        }
        gameObjects.add(o);
    }
    public void updateState(float deltaTime) {
        //collisionHandler.Update();
        for (GameObject gameObject : gameObjects) {
            gameObject.updateState(deltaTime);
        }
    }
}
