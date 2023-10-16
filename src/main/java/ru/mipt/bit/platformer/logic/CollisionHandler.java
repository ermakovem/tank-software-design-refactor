package ru.mipt.bit.platformer.logic;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CollisionHandler {
    private final int width;
    private final int hight;
    private final List<HasCollision> collisionObjects = new ArrayList<>();
    private final HashSet<GridPoint2> occupiedPoints = new HashSet<>();

    public CollisionHandler(int hight, int width) {
        this.width = width;
        this.hight = hight;
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
        if (pointToCheck.x > width - 1 || pointToCheck.y > hight - 1
        || pointToCheck.x < 0 || pointToCheck.y < 0) {
            return false;
        }
        updateMap();
        return !occupiedPoints.contains(pointToCheck);
    }
}
