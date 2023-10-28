package ru.mipt.bit.platformer.logic.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.logic.CollisionHandler;

import java.util.ArrayList;
import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements GameObject, CanMove, Collidable, CanShoot {
    Collection<GameObject> createdGameObjects = new ArrayList<>();
    private boolean isPlayer = true;
    private final GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private final float movementSpeed;
    private float rotation = 0f;
    private GameObjectState state;
    private CollisionHandler collisionHandler = new CollisionHandler(10 , 8);

    public Tank(GridPoint2 coordinates, float movementSpeed, boolean isPlayer) {
        state = GameObjectState.ALIVE;
        this.coordinates = coordinates;
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.movementSpeed = movementSpeed;
        this.isPlayer = isPlayer;
    }

    public Tank(GridPoint2 coordinates, float movementSpeed) {
        state = GameObjectState.ALIVE;
        this.coordinates = coordinates;
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isNotMoving()) {
            coordinates.set(destinationCoordinates);
        }
    }

    @Override
    public GameObjectState getState() {
        return state;
    }

    @Override
    public Collection<GameObject> getCreatedGameObjects() {
        return createdGameObjects;
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
    public void addCollisionHandler(CollisionHandler collisionHandler) {
        this.collisionHandler = collisionHandler;
    }

    @Override
    public void moveTo(GridPoint2 vector) {
        if (isNotMoving()) {
            if (collisionHandler.isFree(coordinates.cpy().add(vector))) {
                destinationCoordinates = destinationCoordinates.add(vector);
                movementProgress = 0;
            }
            rotation = floor(atan2(vector.y, vector.x) * 180f / PI);
        }
    }

    @Override
    public void shoot() {
        // createdGameObjects.add(new Projectile(rotation, coordinate));
    }

    public boolean isPlayer() {
        return isPlayer;
    }
    private boolean isNotMoving() {
        return isEqual(movementProgress, 1f);
    }
}
