package ru.mipt.bit.platformer.controllers;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.logic.objects.GameObject;
import ru.mipt.bit.platformer.logic.objects.Tank;

import java.util.*;

public class InputController implements Controller {
    private final HashMap<Integer, HashSet<Action>> keyToAction = new HashMap<>();
    private GameObject gameObject;

    public InputController() {}

    public void mapKeyToActionObject(Integer key, Action action) {
        if (!keyToAction.containsKey(key)) {
            keyToAction.put(key, new HashSet<>());
        }
        keyToAction.get(key).add(action);
    }

    @Override
    public boolean trySet(GameObject gameObject) {
        //if it is player tank
        if (gameObject instanceof Tank && ((Tank)gameObject).isPlayer()) {
            //and there are no GameObject yet
            if (this.gameObject == null) {
                this.gameObject = gameObject;
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
}
