package ru.mipt.bit.platformer.actionGenerators.generalActionGenerator;

import ru.mipt.bit.platformer.actionGenerators.Action;

import java.util.Arrays;
import java.util.function.Predicate;

public class GeneralActionGenerator implements ActionGenerator {
    private final Class<?> requiredClass;
    private final Action action;
    private final Predicate<Object> predicate;
    private Object object = null;


    public GeneralActionGenerator(Class<?> requiredClass, Action action, Predicate<Object> predicate) {

        checkIfClassSupportsAction(requiredClass, action);

        this.requiredClass = requiredClass;
        this.action = action;
        this.predicate = predicate;
    }

    private void checkIfClassSupportsAction(Class<?> requiredClass, Action action) {
        if (!Arrays.stream(requiredClass.getInterfaces()).toList().contains(action.getRequiredInterface())) {
            throw new IllegalArgumentException("requiredClass cannot do the action");
        }
    }

    @Override
    public void apply() {
        if (object != null && predicate.test(object)) {
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
