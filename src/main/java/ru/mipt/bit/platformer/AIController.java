package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.logic.GameObject;

import java.util.*;

import static java.lang.Math.abs;

public class AIController implements Controller{
    private GameObject gameObject;
    public AIController() {}

    @Override
    public void addGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public Map.Entry<GameObject, ArrayList<Action>> getActions() {
        Map.Entry<GameObject, ArrayList<Action>> gameObjectToActions =
                new AbstractMap.SimpleEntry<>(gameObject, new ArrayList<>());
        Action moveAction;

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
