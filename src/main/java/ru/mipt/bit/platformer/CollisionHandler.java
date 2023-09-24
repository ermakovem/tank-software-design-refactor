package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CollisionHandler {
    private final List<GameObject> objectsGame;
    private final HashSet<GridPoint2> occupiedPoints = new HashSet<>();

    public CollisionHandler(List<GameObject> objectsGame) {
        this.objectsGame = objectsGame;
    }

    private void UpdateMap() {
        occupiedPoints.clear();
        for (GameObject object : objectsGame) {
            occupiedPoints.add(object.getDestinationCoordinates());
        }
    }

    public HashSet<GridPoint2> getOccupiedPoints() {
        UpdateMap();
        return occupiedPoints;
    }
}
