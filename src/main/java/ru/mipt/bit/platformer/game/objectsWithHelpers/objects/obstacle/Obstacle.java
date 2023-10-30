package ru.mipt.bit.platformer.game.objectsWithHelpers.objects.obstacle;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.GameObjectState;
import ru.mipt.bit.platformer.game.graphics.Renderable;
import ru.mipt.bit.platformer.game.objectsWithHelpers.Collidable;

import java.util.ArrayList;
import java.util.Collection;

public class Obstacle implements GameObject, Collidable, Renderable {
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
    public float getMovementProgress() {
        return 1f;
    }

    @Override
    public float getRotation() {
        return 0f;
    }

    @Override
    public void updateState(float deltaTime) {
    }

    @Override
    public GameObjectState getState() {
        return state;
    }

    @Override
    public Collection<GameObject> getCreatedGameObjects() {
        return new ArrayList<>();
    }
}
