package ru.mipt.bit.platformer.game.controllers.input;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.controllers.Controller;
import ru.mipt.bit.platformer.game.controllers.ControllerState;
import ru.mipt.bit.platformer.game.actions.Action;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Tank;

import java.util.*;

public class InputController implements Controller {
    private final HashMap<Integer, HashSet<Action>> keyToAction = new HashMap<>();
    private GameObject gameObject;
    private ControllerState state;

    public InputController() {
        state = ControllerState.INIT;
    }

    public void mapKeyToAction(Integer key, Action action) {
        if (!keyToAction.containsKey(key)) {
            keyToAction.put(key, new HashSet<>());
        }
        keyToAction.get(key).add(action);
    }

    @Override
    public boolean trySet(GameObject gameObject) {
        //if it is player tank
        if (gameObject instanceof Tank && ((Tank) gameObject).isPlayer()) {
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
        Map.Entry<GameObject, ArrayList<Action>> gameToObjectActions =
                new AbstractMap.SimpleEntry<>(gameObject, new ArrayList<>());

        if (gameObject == null) {
            return gameToObjectActions;
        }

        for (Integer key : keyToAction.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                for (Action action : keyToAction.get(key)) {
                    gameToObjectActions.getValue().add(action);
                }
            }
        }
        return gameToObjectActions;
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
