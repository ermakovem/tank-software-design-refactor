package ru.mipt.bit.platformer.game.controllers.random;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.actions.Action;
import ru.mipt.bit.platformer.game.actions.MoveAction;
import ru.mipt.bit.platformer.game.actions.ShootAction;
import ru.mipt.bit.platformer.game.controllers.Controller;
import ru.mipt.bit.platformer.game.controllers.ControllerState;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Tank;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.abs;

public class RandomController implements Controller {
    private GameObject gameObject;
    private ControllerState state;
    private Action moveAction = MoveAction.UP;

    public RandomController() {
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
        Map.Entry<GameObject, ArrayList<Action>> gameObjectToActions =
                new AbstractMap.SimpleEntry<>(gameObject, new ArrayList<>());

        if (gameObject == null) {
            return gameObjectToActions;
        }

        Random random = new Random();
        int direction = abs(random.nextInt() % 8);
        switch (direction) {
            case 0:
                moveAction = MoveAction.UP;
                break;
            case 1:
                moveAction = MoveAction.RIGHT;
                break;
            case 2:
                moveAction = MoveAction.DOWN;
                break;
            case 3:
                moveAction = MoveAction.LEFT;
                break;
            default:
                break;
        }
        gameObjectToActions.getValue().add(moveAction);

        if (random.nextInt() % 100 == 1) {
            gameObjectToActions.getValue().add(new ShootAction());
        }
        return gameObjectToActions;
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