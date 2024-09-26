package ru.mipt.bit.platformer.actionGenerators.actions.move;

import ru.mipt.bit.platformer.actionGenerators.Action;

public class MoveAction implements Action<CanMove> {
    private final Direction direction;

    public MoveAction(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void apply(CanMove canMove) {
        canMove.moveTo(direction);
    }

    @Override
    public Class<CanMove> getRequiredInterface() {
        return CanMove.class;
    }
}
