package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.badlogic.gdx.math.MathUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements GameObject, CanMove, HasCollision{
    private final GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private final float movementSpeed;
    private float rotation = 0f;

    public Tank(GridPoint2 coordinates, float movementSpeed) {
        this.coordinates = coordinates;
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isntMoving()) {
            coordinates.set(destinationCoordinates);
        }
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    public void moveTo(GridPoint2 vector) {
        if (isntMoving()) {
            destinationCoordinates.add(vector);
            rotation = floor(atan2(vector.y, vector.x) * 180f / PI);
            movementProgress = 0;
        }
    }

    private boolean isntMoving() {
        return isEqual(movementProgress, 1f);
    }
}
