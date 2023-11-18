package ru.mipt.bit.platformer.actionGenerators.objects;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.actions.Action;

import java.util.Random;

public class InputActionGenerator implements ActionGenerator {
    private final Class<?> requiredClass;
    private final Action action;
    private final int key;
    private final Random random = new Random();
    private Object object = null;


    public InputActionGenerator(Class<?> requiredClass, Action<?> action, int key) {
        //TODO: there has to be a simpler way :)
        boolean found = false;
        for (Class<?> anInterface : requiredClass.getInterfaces()) {
            if (action.getRequiredInterface() == anInterface) {
                found = true;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("requiredClass cannot do the action");
        }

        this.requiredClass = requiredClass;
        this.action = action;
        this.key = key;
    }

    @Override
    public void apply() {
        if (Gdx.input.isKeyPressed(key) && object != null) {
            action.apply(object);
        }
    }

    @Override
    public boolean set(Object object) {
        if (!requiredClass.isInstance(object) || this.object != null) {
            return false;
        } else {
            this.object = object;
            return true;
        }
    }
}
