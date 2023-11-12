package ru.mipt.bit.platformer.game.controllers;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.HasAction;

import java.lang.reflect.Type;
import java.util.*;

public class ActionGeneratorsHandler {
    private final ArrayList<ActionGenerator> actionGenerators = new ArrayList<>();
    private final HashMap<HasAction, ActionGenerator<HasAction>> objectToController = new HashMap<>();
    private Map<Type[], ActionGenerator> templateTypeToActionGenerator = new HashMap<>();

    public ActionGeneratorsHandler(ActionGenerator... actionGenerators) {
        for (ActionGenerator actionGenerator : actionGenerators) {
            this.actionGenerators.add(actionGenerator);
            templateTypeToActionGenerator.put(actionGenerators.getClass().getTypeParameters(), actionGenerator);

        }
        Collections.addAll(this.actionGenerators, actionGenerators);
        templateTypeToActionGenerator.put(actionGenerators);
    }

    public void applyActions() {
        ArrayList<ActionGenerator<HasAction>> to_be_removed = new ArrayList<>();
        for (ActionGenerator actionGenerator : actionGenerators) {
            switch (actionGenerator.getState()) {
                case INIT: {
                    break;
                }
                case ACTIVE: {
                    actionGenerator.applyActions();
                    break;
                }
                case DEAD: {
                    to_be_removed.add(actionGenerator);
                    gameObjectToController.remove(actionGenerator);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("unknown ControllerState");
                }
            }
        }
        for (ActionGenerator actionGenerator : to_be_removed) {
            actionGenerators.remove(actionGenerator);
        }
    }

    public void add(HasAction hasAction) {
        for (ActionGenerator actionGenerator : actionGenerators) {
            if (actionGenerator.set(gameObject)) {
                gameObjectToController.put(gameObject, actionGenerator);
                break;
            }
        }
    }

    public void parseState(HasAction hasAction) {
        if (gameObjectToController.containsKey(gameObject)) {
            gameObjectToController.get(gameObject).parseState(gameObject);
        }
    }
}
