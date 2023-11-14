package ru.mipt.bit.platformer.game.actionGenerators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ActionGeneratorDecorator implements ActionGenerator {
    private Object object;
    private final Collection<ActionGenerator> actionGenerators = new ArrayList<>();
    public ActionGeneratorDecorator(ActionGenerator ... actionGenerators) {
        Collections.addAll(this.actionGenerators, actionGenerators);
    }

    @Override
    public void apply() {
        for (ActionGenerator actionGenerator : actionGenerators) {
            actionGenerator.apply();
        }
    }

    @Override
    public boolean set(Object object) {
        for (ActionGenerator actionGenerator : actionGenerators) {
            if (actionGenerator.set(object)) {
                this.object = object;
                return true;
            }
        }
        return false;
    }
}
