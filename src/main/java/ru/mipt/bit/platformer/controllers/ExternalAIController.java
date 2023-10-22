package ru.mipt.bit.platformer.controllers;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.logic.objects.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExternalAIController implements AI, Controller {
    @Override
    public List<Recommendation> recommend(GameState gameState) {
        return null;
    }

    @Override
    public boolean trySetGameObject(GameObject gameObject) {
        return false;
    }

    @Override
    public Map.Entry<GameObject, ArrayList<Action>> getActions() {
        return null;
    }
}
