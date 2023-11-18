package ru.mipt.bit.platformer.gameLogic.helpers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.gameLogic.GameObjectState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class CollisionHandler {
    private final int tilesWidth;
    private final int tilesHeight;
    private final Collection<Collidable> collisionObjects = new ArrayList<>();
    private final HashSet<GridPoint2> deadGameObjects = new HashSet<>();
    private final HashSet<GridPoint2> occupiedPoints = new HashSet<>();

    public CollisionHandler(int tilesHeight, int tilesWidth) {
        this.tilesWidth = tilesWidth;
        this.tilesHeight = tilesHeight;
    }

    public void add(Collidable collidable) {
        collisionObjects.add(collidable);
    }

    private void updateMap() {
        occupiedPoints.clear();

        occupiedPoints.addAll(deadGameObjects);

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

    public void parseState(Collidable collidable, GameObjectState state) {
        switch (state) {
            case ALIVE: {
                break;
            }
            case DEAD: {
                deadGameObjects.add(collidable.getCoordinates());
                deadGameObjects.add(collidable.getDestinationCoordinates());
                collisionObjects.remove(collidable);
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
