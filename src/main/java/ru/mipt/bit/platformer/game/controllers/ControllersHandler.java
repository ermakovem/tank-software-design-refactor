package ru.mipt.bit.platformer.game.controllers;

import ru.mipt.bit.platformer.game.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ControllersHandler {
    private final ArrayList<Controller> controllers = new ArrayList<>();
    private final HashMap<GameObject, Controller> gameObjectToController = new HashMap<>();

    public ControllersHandler(Controller... controllers) {
        Collections.addAll(this.controllers, controllers);
    }

    public void applyActions() {
        ArrayList<Controller> to_be_removed = new ArrayList<>();
        for (Controller controller : controllers) {
            switch (controller.getState()) {
                case INIT: {
                    break;
                }
                case ACTIVE: {
                    controller.applyActions();
                    break;
                }
                case DEAD: {
                    to_be_removed.add(controller);
                    //remove gameObjectToController
                    break;
                }
                default: {
                    throw new IllegalArgumentException("unknown ControllerState");
                }
            }
        }
        for (Controller controller : to_be_removed) {
            controllers.remove(controller);
        }
    }

    public void add(GameObject gameObject) {
        for (Controller controller : controllers) {
            if (controller.trySet(gameObject)) {
                gameObjectToController.put(gameObject, controller);
                break;
            }
        }
    }

    public void parseState(GameObject gameObject) {
        if (gameObjectToController.containsKey(gameObject)) {
            gameObjectToController.get(gameObject).parseState(gameObject);
        }
    }
}
