package ru.mipt.bit.platformer.game;

import ru.mipt.bit.platformer.graphics.GraphicsHandler;
import ru.mipt.bit.platformer.graphics.Renderable;
import ru.mipt.bit.platformer.graphics.RenderableState;

public class LevelListenerGraphics implements LevelListener {
    private final GraphicsHandler graphicsHandler;

    public LevelListenerGraphics(GraphicsHandler graphicsHandler) {
        this.graphicsHandler = graphicsHandler;
    }

    @Override
    public void add(GameObject gameObject) {
        if (gameObject instanceof Renderable) {
            graphicsHandler.addGraphicsObjects((Renderable) gameObject, RenderableState.ACTIVE);
        }
    }

    @Override
    public void parseState(GameObject gameObject) {
        if (gameObject instanceof Renderable) {
            graphicsHandler.parseState((Renderable) gameObject, RenderableState.ACTIVE);
        }
    }
}
