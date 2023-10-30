package ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.GameObjectState;
import ru.mipt.bit.platformer.game.graphics.Renderable;
import ru.mipt.bit.platformer.game.objectsWithHelpers.CanMove;
import ru.mipt.bit.platformer.game.objectsWithHelpers.Collidable;
import ru.mipt.bit.platformer.game.objectsWithHelpers.CollisionHandler;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.projectile.Projectile;

import java.util.ArrayList;
import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.*;
import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.continueProgress;

public class Tank implements GameObject, CanMove, Collidable, CanShoot, Hittable, Renderable {
    private final GridPoint2 coordinates;
    private final float movementSpeed;
    private final int reloadInTicks = 100;
    ArrayList<GameObject> createdGameObjects = new ArrayList<>();
    private final boolean isPlayer;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation = 0f;
    private int currentReload = reloadInTicks;
    private GameObjectState state;
    private float hp = 300;
    private CollisionHandler collisionHandler = new CollisionHandler(10, 8);

    public Tank(GridPoint2 coordinates, float movementSpeed, boolean isPlayer) {
        state = GameObjectState.ALIVE;
        this.coordinates = coordinates;
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.movementSpeed = movementSpeed;
        this.isPlayer = isPlayer;
    }

    @Override
    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isNotMoving()) {
            coordinates.set(destinationCoordinates);
        }
        if (currentReload != reloadInTicks) {
            currentReload++;
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
        if (currentReload == reloadInTicks) {
            currentReload = 0;
            createdGameObjects.add(new Projectile(destinationCoordinates, rotation));
        }
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    private boolean isNotMoving() {
        return isEqual(movementProgress, 1f);
    }

    @Override
    public void getHit(float damage) {
        if (hp - damage <= 0) {
            hp = 0;
            state = GameObjectState.DEAD;
        }
    }
}
