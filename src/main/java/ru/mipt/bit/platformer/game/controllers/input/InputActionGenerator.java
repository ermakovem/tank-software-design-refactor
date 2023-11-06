package ru.mipt.bit.platformer.game.controllers.input;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.actions.Action;
import ru.mipt.bit.platformer.game.controllers.ActionGenerator;
import ru.mipt.bit.platformer.game.controllers.ControllerState;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Tank;

import java.util.*;

public class InputActionGenerator<T> implements ActionGenerator<T> {
    private final HashMap<Integer, HashSet<Action<T>>> keyToAction = new HashMap<>();
    private T t;
    private ControllerState state;

    public InputActionGenerator() {
        state = ControllerState.INIT;
    }

    public void mapKeyToAction(Integer key, Action<T> action) {
        if (!keyToAction.containsKey(key)) {
            keyToAction.put(key, new HashSet<>());
        }
        keyToAction.get(key).add(action);
    }

    private Map.Entry<T, ArrayList<Action<T>>> getActions() {
        Map.Entry<T, ArrayList<Action<T>>> objectToActions =
                new AbstractMap.SimpleEntry<>(t, new ArrayList<>());
        for (Integer key : keyToAction.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                for (Action<T> action : keyToAction.get(key)) {
                    objectToActions.getValue().add(action);
                }
            }
        }
        return objectToActions;
    }

    @Override
    public void set(T t) {
        //reflection if T is smthing..
        this.t = t;
        state = ControllerState.ACTIVE;
    }

    @Override
    public void applyActions() {
        for (Action<T> action : getActions().getValue()) {
            action.apply(getActions().getKey());
        }
    }

    @Override
    public ControllerState getState() {
        return state;
    }
}
