package ru.mipt.bit.platformer.actionGenerators.actions;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.actionGenerators.Action;

public enum MoveAction implements Action<CanMove> {
    UP(new GridPoint2(0, 1)),
    DOWN(new GridPoint2(0, -1)),
    RIGHT(new GridPoint2(1, 0)),
    LEFT(new GridPoint2(-1, 0));

    private final GridPoint2 vector;

    MoveAction(GridPoint2 vector) {
        this.vector = vector;
    }

    @Override
    public void apply(CanMove canMove) {
            canMove.moveTo(vector);
    }

    @Override
    public Class<?> getRequiredInterface() {
        return CanMove.class;
    }
}
