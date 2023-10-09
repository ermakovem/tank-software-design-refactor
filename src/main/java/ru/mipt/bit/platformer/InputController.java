package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.logic.GameObject;

import java.util.*;

public class InputController {
    private final HashMap<Integer, Map.Entry<Action, GameObject>> keyToActionGameObject = new HashMap<>();
    //there is no pair in java :( Map.Entry<R, L> is analog.
    //so that structure defines key, action and who gets that action

    public InputController() {}

    public void mapKeyToActionObject(Integer key, Action action, GameObject object) {
        keyToActionGameObject.put(key, new AbstractMap.SimpleEntry<>(action, object));
    }

    public ArrayList<Map.Entry<Action, GameObject>> getActions() {//returns all actions that have happened during checking
        ArrayList<Map.Entry<Action, GameObject>> actions = new ArrayList<>();
        for (Integer key : keyToActionGameObject.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                actions.add(keyToActionGameObject.get(key));
            }
        }
        return actions;
    }
}
