package ru.mipt.bit.platformer.logic;

import ru.mipt.bit.platformer.Action;

import java.util.ArrayList;
import java.util.List;

public class GameLevel {
    private final List<GameObject> gameObjects = new ArrayList<>();
    protected final CollisionHandler collisionHandler = new CollisionHandler(8, 10);
    private final List<LevelListener> levelListeners = new ArrayList<>();

    //this field and method are temporary. I hope
    private Tank theOnlyTank;
    public Tank getTheTank(){
        return theOnlyTank;
    }

    public GameLevel() {}

    //if we only use LevelGenerator that may be protected
    public boolean tryAdd(GameObject o) {

        //we check if the point is already occupied
        if (o instanceof HasCollision) {
            if (collisionHandler.isFree(((HasCollision) o).getCoordinates())) {
                collisionHandler.add((HasCollision) o);
            } else {
                return false;
            }
        }
        if (o instanceof CanMove) {
            ((CanMove) o).addCollisionHandler(collisionHandler);
        }
        for (LevelListener levelListener : levelListeners) {
            levelListener.addObject(o);
        }
        gameObjects.add(o);

        //temporary
        if (o instanceof Tank) {
            theOnlyTank = (Tank) o;
        }

        return true;
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
