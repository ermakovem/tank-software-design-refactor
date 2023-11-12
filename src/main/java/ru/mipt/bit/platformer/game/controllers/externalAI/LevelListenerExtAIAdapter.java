package ru.mipt.bit.platformer.game.controllers.externalAI;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.LevelListener;

public class LevelListenerExtAIAdapter implements LevelListener {
    private final ExternalAIAdapter externalAIAdapter;

    public LevelListenerExtAIAdapter(ExternalAIAdapter externalAIAdapter) {
        this.externalAIAdapter = externalAIAdapter;
    }

    @Override
    public void add(GameObject gameObject) {
        externalAIAdapter.add(gameObject);
    }

    @Override
    public void parseState(GameObject gameObject) {
        //externalAIAdapter.remove(gameObject);
    }
}
