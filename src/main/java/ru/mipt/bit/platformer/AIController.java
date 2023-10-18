package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.logic.GameObject;
import ru.mipt.bit.platformer.logic.Tank;

import java.util.*;

import static java.lang.Math.abs;

public class AIController implements Controller{
    private GameObject gameObject;
    public AIController() {}

    @Override
    public boolean trySetGameObject(GameObject gameObject) {
        //if it is an AI tank
        if (gameObject instanceof Tank && !((Tank)gameObject).isPlayer()) {
            //and there are no GameObject yet
            if (this.gameObject == null) {
                this.gameObject = gameObject;
                return true;
            }
        }
        return false;
    }

    @Override
    public Map.Entry<GameObject, ArrayList<Action>> getActions() {
        Map.Entry<GameObject, ArrayList<Action>> gameObjectToActions =
                new AbstractMap.SimpleEntry<>(gameObject, new ArrayList<>());
        Action moveAction;

        if (gameObject == null) {
            return gameObjectToActions;
        }

        Random random = new Random();
        int direction = abs(random.nextInt() % 4);
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
                throw new IllegalArgumentException("Wrong direction");
        }
        gameObjectToActions.getValue().add(moveAction);
        return gameObjectToActions;
    }
}
