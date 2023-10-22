package ru.mipt.bit.platformer.controllers;

import org.awesome.ai.AI;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.logic.GameLevel;
import ru.mipt.bit.platformer.logic.objects.GameObject;
import ru.mipt.bit.platformer.logic.objects.Tank;

import java.util.ArrayList;
import java.util.Map;

public class ExternalAIController implements Controller {
    private GameObject gameObject;
    private final ExternalAIAdapter externalAIAdapter;

    public ExternalAIController(ExternalAIAdapter externalAIAdapter) {
        this.externalAIAdapter = externalAIAdapter;
    }

    @Override
    public boolean trySetGameObject(GameObject gameObject) {
        //if it is an AI tank
        if (gameObject instanceof Tank && !((Tank)gameObject).isPlayer()) {
            //and there are no GameObject yet
            if (this.gameObject == null) {
                this.gameObject = gameObject;
                return true;
            }
        }
        return false;
    }

    private Map.Entry<GameObject, ArrayList<Action>> getActions() {
        return externalAIAdapter.getActions(gameObject);
    }

    @Override
    public void applyActions() {
        for (Action action : getActions().getValue()) {
            action.apply(getActions().getKey());
        }
    }
}
