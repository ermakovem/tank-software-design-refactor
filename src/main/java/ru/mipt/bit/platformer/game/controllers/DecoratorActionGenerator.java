package ru.mipt.bit.platformer.game.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DecoratorActionGenerator<T> implements ActionGenerator<T> {
    Collection<ActionGenerator<T>> actionGenerators = new ArrayList<>();

    public DecoratorActionGenerator(ActionGenerator<T> ... actionGenerators) {
        Collections.addAll(this.actionGenerators, actionGenerators);
    }

    @Override
    public void set(T t) {

    }

    @Override
    public void applyActions() {

    }

    @Override
    public ControllerState getState() {
        return null;
    }
}
