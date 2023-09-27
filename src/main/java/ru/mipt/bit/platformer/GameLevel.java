package ru.mipt.bit.platformer;

import java.util.ArrayList;
import java.util.List;

public class GameLevel {
    List<GameObject> gameObjects = new ArrayList<>();
    //CollisionHandler collisionHandler = new CollisionHandler();

    public GameLevel() {}

    public void add(GameObject o) {
        //if
        gameObjects.add(o);
    }
    public void updateState(float deltaTime) {
        collisionHandler.Update();
        for (GameObject gameObject : gameObjects) {
            gameObject.updateState();
        }
    }
}
