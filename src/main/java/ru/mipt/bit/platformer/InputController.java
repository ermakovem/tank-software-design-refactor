package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Action;

import java.util.*;

public class InputController {
    private final HashMap<Integer, Action> keyToAction = new HashMap<>();

    public InputController() {}

    public void mapKeyToAction(Integer key, Action action) {
        keyToAction.put(key, action);
    }

    public ArrayList<Action> checkKeyboard() {//returns all actions that have happened during checking
        ArrayList<Action> actions = new ArrayList<>();
        for (Integer key : keyToAction.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                actions.add(keyToAction.get(key));
            }
        }
        return actions;
    }
}
