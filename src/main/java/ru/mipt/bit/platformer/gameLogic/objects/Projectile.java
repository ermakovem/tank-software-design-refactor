package ru.mipt.bit.platformer.gameLogic.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.actionGenerators.actions.move.CanMove;
import ru.mipt.bit.platformer.actionGenerators.actions.move.Direction;
import ru.mipt.bit.platformer.gameLogic.GameObject;
import ru.mipt.bit.platformer.gameLogic.GameObjectState;
import ru.mipt.bit.platformer.gameLogic.helpers.Collidable;
import ru.mipt.bit.platformer.gameLogic.helpers.CollisionHandler;
import ru.mipt.bit.platformer.gameLogic.objects.tank.Hittable;
import ru.mipt.bit.platformer.graphics.objects.Renderable;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Projectile implements GameObject, CanMove, Renderable {
    private final int damage = 35;
    private final GridPoint2 coordinates;
    private final float movementSpeed = 0.1f;
    private final Direction direction;
    private CollisionHandler collisionHandler;
    private GameObjectState state;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    public Projectile(GridPoint2 coordinates, Direction direction) {
        state = GameObjectState.ALIVE;

        this.direction = direction;
        this.coordinates = new GridPoint2(coordinates);
        this.destinationCoordinates = new GridPoint2(this.coordinates);
    }

    @Override
    public void addCollisionHandler(CollisionHandler collisionHandler) {
        this.collisionHandler = collisionHandler;
    }

    @Override
    public void moveTo(Direction direction) {
        if (isNotMoving()) {
            if (collisionHandler.isFree(coordinates.cpy().add(direction.getVector()))) {
                destinationCoordinates = destinationCoordinates.add(direction.getVector());
                movementProgress = 0;
            } else {
                Collidable collidable = collisionHandler.getObjectFromCoordinate(coordinates.cpy().add(direction.getVector()));
                if (collidable instanceof Hittable) {
                    ((Hittable) collidable).getHit(damage);
                }
                state = GameObjectState.DEAD;
            }
        }
    }

    @Override
    public void updateState(float deltaTime) {
        movementProgress = CanMove.continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isNotMoving()) {
            coordinates.set(destinationCoordinates);
            moveTo(direction);
        }
    }

    @Override
    public GameObjectState getState() {
        return state;
    }

    private boolean isNotMoving() {
        return isEqual(movementProgress, 1f);
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public float getRotation() {
        return direction.getRotation();
    }
}
