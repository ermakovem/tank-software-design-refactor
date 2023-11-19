package ru.mipt.bit.platformer.gameLogic.levelListeners;

import ru.mipt.bit.platformer.actionGenerators.ActionGeneratorsHandler;
import ru.mipt.bit.platformer.actionGenerators.ObjectState;
import ru.mipt.bit.platformer.gameLogic.GameObject;
import ru.mipt.bit.platformer.gameLogic.GameObjectState;

public class LevelListenerActionGenerator implements LevelListener {
    // assigns new GameObject with first suitable controller.
    private final ActionGeneratorsHandler actionGeneratorsHandler;

    public LevelListenerActionGenerator(ActionGeneratorsHandler actionGeneratorsHandler) {
        this.actionGeneratorsHandler = actionGeneratorsHandler;
    }

    @Override
    public void add(GameObject gameObject) {
        actionGeneratorsHandler.add(gameObject);
    }

    @Override
    public void parseState(GameObject gameObject) {
        actionGeneratorsHandler.parseState(gameObject, convertState(gameObject.getState()));
    }

    private ObjectState convertState(GameObjectState state) {
        return switch (state) {
            case ALIVE -> ObjectState.ACTIVE;
            case DEAD -> ObjectState.INACTIVE;
            default -> throw new IllegalArgumentException("Unknown GameObjectState");
        };
    }
}
