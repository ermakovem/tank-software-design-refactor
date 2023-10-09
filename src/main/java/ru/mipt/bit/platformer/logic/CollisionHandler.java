package ru.mipt.bit.platformer.logic;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CollisionHandler {
    private final List<HasCollision> collisionObjects = new ArrayList<>();
    private final HashSet<GridPoint2> occupiedPoints = new HashSet<>();

    public CollisionHandler() {
    }

    public void add(HasCollision object) {
        collisionObjects.add(object);
    }

    private void updateMap() {
        occupiedPoints.clear();
        for (HasCollision object : collisionObjects) {
            occupiedPoints.add(object.getCoordinates());
            occupiedPoints.add(object.getDestinationCoordinates());
        }
    }

    public boolean isFree(GridPoint2 pointToCheck) {
        updateMap();
        return !occupiedPoints.contains(pointToCheck);
    }
}
