package ru.mipt.bit.platformer.logic;

import java.util.ArrayList;
import java.util.List;

public class GameLevel {
    private final List<GameObject> gameObjects = new ArrayList<>();
    protected final CollisionHandler collisionHandler = new CollisionHandler();
    private final List<LevelListener> levelListeners = new ArrayList<>();

    //this field and method are temporary. I hope
    private Tank theOnlyTank;
    public Tank getTheTank(){
        return theOnlyTank;
    }

    public GameLevel() {}

    //if we only use LevelGenerator that may be protected
    public void add(GameObject o) {
        //temporary
        if (o instanceof Tank) {
            theOnlyTank = (Tank) o;
        }


        if (o instanceof HasCollision) {
            collisionHandler.add((HasCollision) o);
        }
        if (o instanceof CanMove) {
            ((CanMove) o).addCollisionHandler(collisionHandler);
        }
        for (LevelListener levelListener : levelListeners) {
            levelListener.addObject(o);
        }
        gameObjects.add(o);
    }

    public void updateState(float deltaTime) {
        //collisionHandler.Update();
        for (GameObject gameObject : gameObjects) {
            gameObject.updateState(deltaTime);
        }
    }

    public void addLevelListener(LevelListener levelListener) {
        levelListeners.add(levelListener);
    }
}
