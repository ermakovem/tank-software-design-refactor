package ru.mipt.bit.platformer.game.objectsWithHelpers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CollisionHandler {
    private final int tilesWidth;
    private final int tilesHeight;
    private final List<Collidable> collisionObjects = new ArrayList<>();
    private final HashSet<GridPoint2> deadGameObjects = new HashSet<>();
    private final HashSet<GridPoint2> occupiedPoints = new HashSet<>();

    public CollisionHandler(int tilesHeight, int tilesWidth) {
        this.tilesWidth = tilesWidth;
        this.tilesHeight = tilesHeight;
    }

    public void add(GameObject gameObject) {
        if (gameObject instanceof Collidable) {
            collisionObjects.add((Collidable) gameObject);
        }
    }

    private void updateMap() {
        occupiedPoints.clear();
        for (GridPoint2 deadGameObject : deadGameObjects) {
            occupiedPoints.add(deadGameObject);
        }
        for (Collidable object : collisionObjects) {
            occupiedPoints.add(object.getCoordinates());
            occupiedPoints.add(object.getDestinationCoordinates());
        }
    }

    public boolean isFree(GridPoint2 pointToCheck) {
        if (pointToCheck.x > tilesWidth - 1 || pointToCheck.y > tilesHeight - 1
                || pointToCheck.x < 0 || pointToCheck.y < 0) {
            return false;
        }
        updateMap();
        return !occupiedPoints.contains(pointToCheck);
    }

    public void parseState(GameObject gameObject) {
        switch (gameObject.getState()) {
            case ALIVE: {
                break;
            }
            case DEAD: {
                if (gameObject instanceof Collidable) {
                    deadGameObjects.add(((Collidable) gameObject).getCoordinates());
                    deadGameObjects.add(((Collidable) gameObject).getDestinationCoordinates());
                    collisionObjects.remove(gameObject);
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("unknown GameObjectState");
            }
        }
    }

    public Collidable getObjectFromCoordinate(GridPoint2 pointToCheck) {
        for (Collidable collisionObject : collisionObjects) {
            if (pointToCheck.equals(collisionObject.getCoordinates())
                    || pointToCheck.equals(collisionObject.getDestinationCoordinates())) {
                return collisionObject;
            }
        }
        return null;
    }
}
