package ru.mipt.bit.platformer.game.controllers;

import ru.mipt.bit.platformer.game.GameObject;

public class GeneralController<T> implements Controller {

    public GeneralController() {
    }

    @Override
    public boolean trySet(GameObject gameObject) {
        return false;
    }

    @Override
    public void applyActions() {

    }

    @Override
    public void parseState(GameObject gameObject) {

    }

    @Override
    public ControllerState getState() {
        return null;
    }
}
