package ru.mipt.bit.platformer.logic.listeners;

import ru.mipt.bit.platformer.controllers.ExternalAIAdapter;
import ru.mipt.bit.platformer.logic.objects.GameObject;

public class LevelListenerExtAIAdapter implements LevelListener {
    private final ExternalAIAdapter externalAIAdapter;
    public LevelListenerExtAIAdapter(ExternalAIAdapter externalAIAdapter) {
        this.externalAIAdapter = externalAIAdapter;
    }
    @Override
    public void add(GameObject gameObject) {
        externalAIAdapter.add(gameObject);
    }
}
