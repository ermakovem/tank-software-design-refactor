package ru.mipt.bit.platformer.game.controllers.actions;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.objectsWithHelpers.CanMove;

public enum MoveAction implements Action {
    UP(new GridPoint2(0, 1)),
    DOWN(new GridPoint2(0, -1)),
    RIGHT(new GridPoint2(1, 0)),
    LEFT(new GridPoint2(-1, 0));

    private final GridPoint2 vector;

    MoveAction(GridPoint2 vector) {
        this.vector = vector;
    }

    @Override
    public void apply(GameObject gameObject) {
        if (gameObject instanceof CanMove) {
            ((CanMove) gameObject).moveTo(vector);
        }
    }
}
