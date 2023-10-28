package ru.mipt.bit.platformer.logic.listeners;

import ru.mipt.bit.platformer.controllers.Controller;
import ru.mipt.bit.platformer.controllers.ControllersHandler;
import ru.mipt.bit.platformer.logic.objects.GameObject;
import ru.mipt.bit.platformer.logic.objects.GameObjectState;

import java.util.ArrayList;

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
