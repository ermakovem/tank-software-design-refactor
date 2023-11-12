package ru.mipt.bit.platformer.game.objectsWithHelpers.objects.projectile;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.GameObjectState;
import ru.mipt.bit.platformer.graphics.Renderable;
import ru.mipt.bit.platformer.game.objectsWithHelpers.CanMove;
import ru.mipt.bit.platformer.game.objectsWithHelpers.Collidable;
import ru.mipt.bit.platformer.game.objectsWithHelpers.CollisionHandler;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Hittable;

import java.util.ArrayList;
import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.*;
import static ru.mipt.bit.platformer.graphics.util.GdxGameUtils.continueProgress;

public class Projectile implements GameObject, CanMove, Renderable {
    private final int damage = 35;
    private final GridPoint2 coordinates;
    private final float movementSpeed = 0.1f;
    private final float rotation;
    private final GridPoint2 vector;
    private CollisionHandler collisionHandler;
    private GameObjectState state;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    public Projectile(GridPoint2 coordinates, float rotation) {
        state = GameObjectState.ALIVE;

        this.rotation = rotation;
        this.vector = new GridPoint2(round(cosDeg(rotation)), round(sinDeg(rotation)));
        this.coordinates = new GridPoint2(coordinates);
        this.destinationCoordinates = new GridPoint2(this.coordinates);
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
            } else {
                Collidable collidable = collisionHandler.getObjectFromCoordinate(coordinates.cpy().add(vector));
                if (collidable instanceof Hittable) {
                    ((Hittable) collidable).getHit(damage);
                }
                state = GameObjectState.DEAD;
            }
        }
    }

    @Override
    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isNotMoving()) {
            coordinates.set(destinationCoordinates);
            moveTo(vector);
        }
    }

    @Override
    public GameObjectState getState() {
        return state;
    }

    @Override
    public Collection<GameObject> getCreatedGameObjects() {
        return new ArrayList<>();
    }

    private boolean isNotMoving() {
        return isEqual(movementProgress, 1f);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }
}
