package ru.mipt.bit.platformer.game.controllers.random;

import ru.mipt.bit.platformer.game.actions.Action;
import ru.mipt.bit.platformer.game.controllers.ActionGenerator;
import ru.mipt.bit.platformer.game.controllers.ControllerState;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Tank;

import java.util.Random;

/**
 * arg 'chance' means that chance of action is (1 / 'chance')
 */
public class RandomActionGenerator<T> implements ActionGenerator<T> {
    private final Action<T> action;
    private final int chance;
    private T t;
    private ControllerState state;
    private final Random random = new Random();

    public RandomActionGenerator(Action<T> action, int chance) {
        this.state = ControllerState.INIT;

        this.action = action;
        this.chance = chance;
    }

    @Override
    public void set(T t) {
        this.t = t;
        state = ControllerState.ACTIVE;
    }

    @Override
    public void applyActions() {
        if (random.nextInt() % chance == 0) {
            action.apply(t);
        }
    }

    @Override
    public ControllerState getState() {
        return state;
    }
}