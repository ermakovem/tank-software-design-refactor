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
        if (gameObject instanceof Renderable) {
            graphicsHandler.addGraphicsObjects((Renderable) gameObject, gameObject.getState());
        }
    }

    @Override
    public void parseState(GameObject gameObject) {
        if (gameObject instanceof Renderable) {
            graphicsHandler.parseState((Renderable) gameObject, gameObject.getState());
        }
    }
}
