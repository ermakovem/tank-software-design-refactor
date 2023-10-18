package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.logic.GameObject;
import ru.mipt.bit.platformer.logic.LevelListener;

import java.util.ArrayList;

public class LevelListenerController implements LevelListener {
    // assigns new GameObject with first suitable controller.
    private final ArrayList<Controller> controllers;

    public LevelListenerController(ArrayList<Controller> controllers) {
        this.controllers = controllers;
    }

    @Override
    public void addObject(GameObject gameObject) {
        for (Controller controller : controllers) {
            if (controller.trySetGameObject(gameObject)) {
                break;
            }
        }
    }
}
