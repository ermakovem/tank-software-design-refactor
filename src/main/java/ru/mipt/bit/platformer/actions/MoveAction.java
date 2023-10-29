package ru.mipt.bit.platformer.actions;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.logic.objects.CanMove;
import ru.mipt.bit.platformer.logic.objects.GameObject;

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
