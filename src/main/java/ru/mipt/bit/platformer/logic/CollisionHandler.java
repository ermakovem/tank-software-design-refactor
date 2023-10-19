package ru.mipt.bit.platformer.logic;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.logic.objects.CanCollide;
import ru.mipt.bit.platformer.logic.objects.GameObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CollisionHandler {
    private final int tilesWidth;
    private final int tilesHeight;
    private final List<CanCollide> collisionObjects = new ArrayList<>();
    private final HashSet<GridPoint2> occupiedPoints = new HashSet<>();

    public CollisionHandler(int tilesHeight, int tilesWidth) {
        this.tilesWidth = tilesWidth;
        this.tilesHeight = tilesHeight;
    }

    public void add(GameObject gameObject) {
        if (gameObject instanceof CanCollide) {
            collisionObjects.add((CanCollide) gameObject);
        }
    }

    private void updateMap() {
        occupiedPoints.clear();
        for (CanCollide object : collisionObjects) {
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
}
