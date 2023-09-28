package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public class Obstacle implements GameObject, HasCollision {
    private final GridPoint2 coordinates;

    public Obstacle(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }
    public GridPoint2 getCoordinates() {
        return coordinates;
    }
    @Override
    public GridPoint2 getDestinationCoordinates() {
        return getCoordinates();
    }
    @Override
    public float getRotation() {
        return 0;
    }

    @Override
    public GridPoint2 updateState(float deltaTime) {
        return null;
    }
}
