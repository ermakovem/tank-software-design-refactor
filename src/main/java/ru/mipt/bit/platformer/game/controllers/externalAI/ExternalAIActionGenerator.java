package ru.mipt.bit.platformer.game.controllers.externalAI;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.actions.Action;
import ru.mipt.bit.platformer.game.controllers.ActionGenerator;
import ru.mipt.bit.platformer.game.controllers.ControllerState;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Tank;

import java.util.ArrayList;
import java.util.Map;

public class ExternalAIActionGenerator implements ActionGenerator {
    private final ExternalAIAdapter externalAIAdapter;
    private GameObject gameObject;
    private ControllerState state;

    public ExternalAIActionGenerator(ExternalAIAdapter externalAIAdapter) {
        this.externalAIAdapter = externalAIAdapter;
        state = ControllerState.INIT;
    }

    @Override
    public boolean trySet(GameObject gameObject) {
        //if it is an AI tank
        if (gameObject instanceof Tank && !((Tank) gameObject).isPlayer()) {
            //and there are no GameObject yet
            if (this.gameObject == null) {
                this.gameObject = gameObject;
                state = ControllerState.ACTIVE;
                return true;
            }
        }
        return false;
    }

    private Map.Entry<GameObject, ArrayList<Action>> getActions() {
        return externalAIAdapter.getActions(gameObject);
    }

    @Override
    public void applyActions() {
        for (Action action : getActions().getValue()) {
            action.apply(getActions().getKey());
        }
    }

    @Override
    public void parseState(GameObject gameObject) {
        if (gameObject != this.gameObject) {
            throw new IllegalArgumentException("parsing state of wrong GameObject");
        }
        switch (gameObject.getState()) {
            case DEAD: {
                state = ControllerState.DEAD;
                break;
            }
            case ALIVE: {
                break;
            }
            default: {
                throw new IllegalArgumentException("unknown GameObjectState");
            }
        }
    }

    @Override
    public ControllerState getState() {
        return state;
    }
}
