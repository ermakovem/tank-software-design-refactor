package ru.mipt.bit.platformer.gameLogic.objects.tank;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.actionGenerators.actions.move.CanMove;
import ru.mipt.bit.platformer.actionGenerators.actions.move.Direction;
import ru.mipt.bit.platformer.actionGenerators.actions.shoot.CanShoot;
import ru.mipt.bit.platformer.gameLogic.CanCreateGameObjects;
import ru.mipt.bit.platformer.gameLogic.GameObject;
import ru.mipt.bit.platformer.gameLogic.GameObjectState;
import ru.mipt.bit.platformer.gameLogic.helpers.Collidable;
import ru.mipt.bit.platformer.gameLogic.helpers.CollisionHandler;
import ru.mipt.bit.platformer.gameLogic.objects.Projectile;
import ru.mipt.bit.platformer.graphics.objects.HasHP;
import ru.mipt.bit.platformer.graphics.objects.Renderable;

import java.util.ArrayList;
import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank implements GameObject, CanCreateGameObjects, CanMove, Collidable, CanShoot, Hittable, Renderable, HasHP {
    private final GridPoint2 coordinates;
    private final float movementSpeed;
    private final int reloadInTicks = 100;
    private final TankInternalStatesHandler modifiersHandler = new TankInternalStatesHandler(this);
    ArrayList<GameObject> createdGameObjects = new ArrayList<>();
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private Direction direction = Direction.RIGHT;
    private int currentReload = reloadInTicks;
    private GameObjectState state;
    private int hp = 100;
    private CollisionHandler collisionHandler = new CollisionHandler(10, 8);

    public Tank(GridPoint2 coordinates, float movementSpeed) {
        state = GameObjectState.ALIVE;
        this.coordinates = coordinates;
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void updateState(float deltaTime) {
        modifiersHandler.updateInternalState();

        movementProgress = CanMove.continueProgress(movementProgress, deltaTime,
                movementSpeed * modifiersHandler.getMovementSpeedKoef());
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

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public float getRotation() {
        return direction.getRotation();
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
            }
            this.direction = direction;
        }
    }

    @Override
    public void shoot() {
        if (currentReload == reloadInTicks && modifiersHandler.canShoot()) {
            currentReload = 0;
            createdGameObjects.add(new Projectile(destinationCoordinates, direction));
        }
    }

    private boolean isNotMoving() {
        return isEqual(movementProgress, 1f);
    }

    @Override
    public void getHit(int damage) {
        if (hp - damage <= 0) {
            hp = 0;
            state = GameObjectState.DEAD;
        } else {
            hp -= damage;
        }
    }

    @Override
    public int getHP() {
        return hp;
    }
}
