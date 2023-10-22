package ru.mipt.bit.platformer.logic.listeners;

import ru.mipt.bit.platformer.graphics.GraphicsHandler;
import ru.mipt.bit.platformer.logic.objects.GameObject;

public class LevelListenerGraphics implements LevelListener {
    private final GraphicsHandler graphicsHandler;

    public LevelListenerGraphics(GraphicsHandler graphicsHandler) {
        this.graphicsHandler = graphicsHandler;
    }
    @Override
    public void add(GameObject gameObject) {
        graphicsHandler.addGraphicsObjects(gameObject);
    }
}
