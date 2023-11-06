package ru.mipt.bit.platformer.game.controllers;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.LevelListener;

public class LevelListenerActionGenerators implements LevelListener {
    // assigns new GameObject with first suitable controller.
    private final ActionGeneratorsHandler actionGeneratorsHandler;

    public LevelListenerActionGenerators(ActionGeneratorsHandler actionGeneratorsHandler) {
        this.actionGeneratorsHandler = actionGeneratorsHandler;
    }

    @Override
    public void add(GameObject gameObject) {
        actionGeneratorsHandler.add(gameObject);
    }

    @Override
    public void parseState(GameObject gameObject) {
        actionGeneratorsHandler.parseState(gameObject);
    }
}
