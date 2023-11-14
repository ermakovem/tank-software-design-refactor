package ru.mipt.bit.platformer.game.actionGenerators;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.GameObjectState;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.graphics.RenderableState;

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
        switch (state) {
            case ALIVE: {
                return ObjectState.ACTIVE;
            }
            case DEAD: {
                return ObjectState.INACTIVE;
            }
            default:
                throw new IllegalArgumentException("Unknown GameObjectState");
        }
    }
}
