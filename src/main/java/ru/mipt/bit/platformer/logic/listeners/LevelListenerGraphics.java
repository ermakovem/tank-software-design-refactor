package ru.mipt.bit.platformer.logic.listeners;

import ru.mipt.bit.platformer.graphics.GraphicsHandler;
import ru.mipt.bit.platformer.logic.objects.GameObject;
import ru.mipt.bit.platformer.logic.objects.GameObjectState;

public class LevelListenerGraphics implements LevelListener {
    private final GraphicsHandler graphicsHandler;

    public LevelListenerGraphics(GraphicsHandler graphicsHandler) {
        this.graphicsHandler = graphicsHandler;
    }
    @Override
    public void add(GameObject gameObject) {
        graphicsHandler.addGraphicsObjects(gameObject);
    }

    @Override
    public void parseState(GameObject gameObject) {
        graphicsHandler.parseState(gameObject);
    }
}
