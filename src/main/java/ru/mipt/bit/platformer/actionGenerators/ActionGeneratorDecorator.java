package ru.mipt.bit.platformer.actionGenerators;

import ru.mipt.bit.platformer.actionGenerators.generalActionGenerator.ActionGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

//somewhat decorator
public class ActionGeneratorDecorator implements ActionGenerator {
    private final Collection<ActionGenerator> actionGenerators = new ArrayList<>();
    private Object object;

    public ActionGeneratorDecorator(ActionGenerator... actionGenerators) {
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
            if (!actionGenerator.set(object)) {
                this.object = object;
                return false;
            }
        }
        return true;
    }
}
