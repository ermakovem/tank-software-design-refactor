package ru.mipt.bit.platformer.logic;

import com.badlogic.gdx.math.GridPoint2;

public class Obstacle implements GameObject, HasCollision {
    private final GridPoint2 coordinates;

    public Obstacle(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return coordinates;
    }

    @Override
    public void updateState(float deltaTime) {
    }
}
