package ru.mipt.bit.platformer;

import java.util.ArrayList;
import java.util.Collection;

public class GameLevel {
    private final Collection<GameObject> gameObjects = new ArrayList<>();
    private final Collection<LevelListener> levelListeners = new ArrayList<>();
    private final Collection<GameObject> toBeRemoved = new ArrayList<>();
    private final Collection<GameObject> toBeAdded = new ArrayList<>();

    public GameLevel() {
    }

    public void add(GameObject gameObject) {
        for (LevelListener levelListener : levelListeners) {
            levelListener.add(gameObject);
        }
        gameObjects.add(gameObject);
    }

    public void updateState(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.updateState(deltaTime);
            getNewObjects(gameObject);
            parseState(gameObject);
        }
        handleToBeRemoved();
        handleToBeAdded();
    }

    private void parseState(GameObject gameObject) {
        switch (gameObject.getState()) {
            case DEAD: {
                toBeRemoved.add(gameObject);
                break;
            }
            case ALIVE: {
                break;
            }
            default: {
                throw new IllegalArgumentException("unknown GameObjectState");
            }
        }
        for (LevelListener levelListener : levelListeners) {
            levelListener.parseState(gameObject);
        }
    }

    private void getNewObjects(GameObject gameObject) {
        for (GameObject createdGameObject : gameObject.getCreatedGameObjects()) {
            toBeAdded.add(createdGameObject);
        }
        gameObject.getCreatedGameObjects().clear();
    }

    private void handleToBeRemoved() {
        for (GameObject gameObject : toBeRemoved) {
            gameObjects.remove(gameObject);
        }
        toBeRemoved.clear();
    }

    private void handleToBeAdded() {
        for (GameObject gameObject : toBeAdded) {
            add(gameObject);
        }
        toBeAdded.clear();
    }

    public void addLevelListener(LevelListener levelListener) {
        levelListeners.add(levelListener);
    }
}