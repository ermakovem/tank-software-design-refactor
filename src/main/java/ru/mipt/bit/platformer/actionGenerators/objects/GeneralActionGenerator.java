package ru.mipt.bit.platformer.actionGenerators.objects;

import ru.mipt.bit.platformer.actions.Action;

import java.util.Random;
import java.util.function.Predicate;

public class GeneralActionGenerator implements ActionGenerator {
    private final Class<?> requiredClass;
    private final Action action;
    private final Predicate<Object> predicate;
    private final Random random = new Random();
    private Object object = null;


    public GeneralActionGenerator(Class<?> requiredClass, Action<?> action, Predicate<Object> predicate) {
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
        this.predicate = predicate;
    }

    @Override
    public void apply() {
        if (predicate.test(object) && object != null) {
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
