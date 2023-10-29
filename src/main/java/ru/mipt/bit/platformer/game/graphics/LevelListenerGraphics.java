package ru.mipt.bit.platformer.game.graphics;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.LevelListener;

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
