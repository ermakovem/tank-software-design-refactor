package ru.mipt.bit.platformer.logic.objects;

import com.badlogic.gdx.math.GridPoint2;

public class Obstacle implements GameObject, Collidable {
    private final GridPoint2 coordinates;
    private final GameObjectState state;

    public Obstacle(GridPoint2 coordinates) {
        this.coordinates = coordinates;
        state = GameObjectState.ALIVE;
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

    @Override
    public GameObjectState getState() {
        return state;
    }
}
