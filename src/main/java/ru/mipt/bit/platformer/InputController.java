package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.logic.GameObject;

import java.util.*;

public class InputController implements Controller {
    private final HashMap<Integer, HashSet<Action>> keyToAction = new HashMap<>();
    private GameObject gameObject;
    //there is no pair in java :( Map.Entry<R, L> is analog.
    //so that structure defines (action; who gets that action)

    public InputController() {
    }

    public void mapKeyToActionObject(Integer key, Action action) {
        if (!keyToAction.containsKey(key)) {
            keyToAction.put(key, new HashSet<>());
        }
        keyToAction.get(key).add(action);
    }

    @Override
    public void addGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public Map.Entry<GameObject, ArrayList<Action>> getActions() {//returns all actions that have happened during checking
        Map.Entry<GameObject, ArrayList<Action>> gameToObjectActions =
                new AbstractMap.SimpleEntry<>(gameObject, new ArrayList<>());
        for (Integer key : keyToAction.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                for (Action action : keyToAction.get(key)) {
                    gameToObjectActions.getValue().add(action);
                }
            }
        }
        return gameToObjectActions;
    }
}
