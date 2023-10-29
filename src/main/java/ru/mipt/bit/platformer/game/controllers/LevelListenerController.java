package ru.mipt.bit.platformer.game.controllers;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.LevelListener;

public class LevelListenerController implements LevelListener {
    // assigns new GameObject with first suitable controller.
    private final ControllersHandler controllersHandler;

    public LevelListenerController(ControllersHandler controllersHandler) {
        this.controllersHandler = controllersHandler;
    }

    @Override
    public void add(GameObject gameObject) {
        controllersHandler.add(gameObject);
    }

    @Override
    public void parseState(GameObject gameObject) {
        controllersHandler.parseState(gameObject);
    }
}
