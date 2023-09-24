package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashMap;

import static com.badlogic.gdx.math.MathUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements GameObject{
    private final GridPoint2 coordinates;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private final float movementSpeed;
    private float rotation = 0f;

    public Tank(GridPoint2 coordinates, float movementSpeed) {
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.movementSpeed = movementSpeed;
    }

    public void handleActions(ArrayList<Action> actions, HashMap<GridPoint2, Boolean> possibleVectors) {
        if (!isEqual(movementProgress, 1f) || actions.isEmpty()) {//we do not handle action if tank is still moving
            return;
        }

        GridPoint2 resultingVector = new GridPoint2(0, 0);
        for (Action action : actions) {
            resultingVector.add(action.getVector());
        }

        if (possibleVectors.get(resultingVector)) {// if we can move there
            movementProgress = 0f;
            destinationCoordinates.add(resultingVector);//set target
        }

        rotation = floor(atan2(resultingVector.y, resultingVector.x) * 180f / PI);// calculate rotation
    }

    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destinationCoordinates);
        }
    }

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

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public float getRotation() {
        return rotation;
    }
}
