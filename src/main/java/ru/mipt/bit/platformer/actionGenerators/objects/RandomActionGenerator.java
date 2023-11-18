package ru.mipt.bit.platformer.actionGenerators.objects;

import ru.mipt.bit.platformer.actions.*;

import java.util.Random;

import static java.lang.Math.abs;

public class RandomActionGenerator implements ActionGenerator {
    private final Class<?> requiredClass;
    private final Action action;
    private final int chance;
    private final Random random = new Random();
    private Object object = null;


    public RandomActionGenerator(Class<?> requiredClass, Action<?> action, float chance) {
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
        this.chance = (int) (1 / chance);
    }

    @Override
    public void apply() {
        if (random.nextInt() % chance == 0 && object != null) {
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
