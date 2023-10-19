package ru.mipt.bit.platformer.logic;

import ru.mipt.bit.platformer.logic.listeners.LevelListener;
import ru.mipt.bit.platformer.logic.listeners.LevelListenerCollision;
import ru.mipt.bit.platformer.logic.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GameLevel {
    private final List<GameObject> gameObjects = new ArrayList<>();
    //private final CollisionHandler collisionHandler;
    private final List<LevelListener> levelListeners = new ArrayList<>();

    public GameLevel(int tilesHeight, int tilesWidth) {
        addLevelListener(new LevelListenerCollision(new CollisionHandler(tilesHeight, tilesWidth)));
    }

    public boolean add(GameObject o) {
//        //TODO: migrate that inside a generator and make void
//        if (o instanceof CanCollide) {
//            if (collisionHandler.isFree(((CanCollide) o).getCoordinates())) {
//                collisionHandler.add((CanCollide) o);
//            } else {
//                return false;
//            }
//        }
//
//        //should I use level listener to add to a collision handler?
//        if (o instanceof CanMove) {
//            ((CanMove) o).addCollisionHandler(collisionHandler);
//        }

        for (LevelListener levelListener : levelListeners) {
            levelListener.addObject(o);
        }
        gameObjects.add(o);

        return true;
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
