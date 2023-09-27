package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CollisionHandler {
    private final List<HasCollision> objectsGame;
    private final HashSet<GridPoint2> occupiedPoints = new HashSet<>();

    public CollisionHandler(List<HasCollision> objectsGame) {
        this.objectsGame = objectsGame;
    }

    private void UpdateMap() {
        occupiedPoints.clear();
        for (HasCollision object : objectsGame) {
            occupiedPoints.add(object.getDestinationCoordinates());
        }
    }

    public boolean isFree(GridPoint2 pointToCheck) {
        return true;
    }

//    public HashSet<GridPoint2> getOccupiedPoints() {
//        UpdateMap();
//        return occupiedPoints;
//    }
}
