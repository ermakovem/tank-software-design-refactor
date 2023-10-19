package ru.mipt.bit.platformer.logic;

import ru.mipt.bit.platformer.logic.listeners.LevelListener;
import ru.mipt.bit.platformer.logic.listeners.LevelListenerCollisionHandler;
import ru.mipt.bit.platformer.logic.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GameLevel {
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final List<LevelListener> levelListeners = new ArrayList<>();

    public GameLevel(int tilesHeight, int tilesWidth) {
        addLevelListener(new LevelListenerCollisionHandler(new CollisionHandler(tilesHeight, tilesWidth)));
    }

    public void add(GameObject o) {
        for (LevelListener levelListener : levelListeners) {
            levelListener.add(o);
        }
        gameObjects.add(o);
    }

    public void updateState(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.updateState(deltaTime);
        }
    }

    public void addLevelListener(LevelListener levelListener) {
        levelListeners.add(levelListener);
    }
}
