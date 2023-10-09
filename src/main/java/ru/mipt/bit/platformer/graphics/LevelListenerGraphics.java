package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.logic.GameObject;
import ru.mipt.bit.platformer.logic.LevelListener;
import ru.mipt.bit.platformer.logic.Obstacle;
import ru.mipt.bit.platformer.logic.Tank;

public class LevelListenerGraphics implements LevelListener {
    private final GraphicsHandler graphicsHandler;

    public LevelListenerGraphics(GraphicsHandler graphicsHandler) {
        this.graphicsHandler = graphicsHandler;
    }
    @Override
    public void addObject(GameObject gameObject) {
        graphicsHandler.addGraphicsObjects(gameObject);
    }
}
