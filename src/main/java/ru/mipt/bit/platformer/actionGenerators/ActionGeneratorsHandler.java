package ru.mipt.bit.platformer.actionGenerators;

import ru.mipt.bit.platformer.actionGenerators.generalActionGenerator.ActionGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ActionGeneratorsHandler {
    private final Map<Object, Collection<ActionGenerator>> ownerToActionGenerator = new HashMap<>();
    private Collection<ActionGenerator> actionGenerators = new ArrayList<>();

    public ActionGeneratorsHandler(Collection<ActionGenerator> actionGenerators) {
        this.actionGenerators = actionGenerators;
    }

    public void apply() {
        for (ActionGenerator actionGenerator : actionGenerators) {
            actionGenerator.apply();
        }
    }

    public void add(Object object) {
        for (ActionGenerator actionGenerator : actionGenerators) {
            if (actionGenerator.set(object)) {
                ownerToActionGenerator.put(object, actionGenerators);
                return;
            }
        }
    }

    public void parseState(Object object, ObjectState state) {
//        switch (state) {
//            case INACTIVE: {
//                for (ActionGenerator actionGenerator : ownerToActionGenerator.get(object)) {
//                    System.out.println(object);
//                    actionGenerators.remove(actionGenerator);
//                }
//                ownerToActionGenerator.remove(object);
//                break;
//            }
//            case ACTIVE: {
//                break;
//            }
//            default: {
//                throw new IllegalArgumentException("unknown ObjectState");
//            }
//        }
    }
}
