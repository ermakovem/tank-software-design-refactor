package ru.mipt.bit.platformer.gameLogic.levelListeners;

import ru.mipt.bit.platformer.gameLogic.GameObject;
import ru.mipt.bit.platformer.gameLogic.GameObjectState;
import ru.mipt.bit.platformer.gameLogic.levelListeners.LevelListener;
import ru.mipt.bit.platformer.graphics.GraphicsHandler;
import ru.mipt.bit.platformer.graphics.objects.Renderable;
import ru.mipt.bit.platformer.graphics.RenderableState;

public class LevelListenerGraphics implements LevelListener {
    private final GraphicsHandler graphicsHandler;

    public LevelListenerGraphics(GraphicsHandler graphicsHandler) {
        this.graphicsHandler = graphicsHandler;
    }

    @Override
    public void add(GameObject gameObject) {
        if (gameObject instanceof Renderable) {
            graphicsHandler.addGraphicsObjects((Renderable) gameObject, convertState(gameObject.getState()));
        }
    }

    @Override
    public void parseState(GameObject gameObject) {
        if (gameObject instanceof Renderable) {
            graphicsHandler.parseState((Renderable) gameObject, convertState(gameObject.getState()));
        }
    }

    private RenderableState convertState(GameObjectState state) {
        return switch (state) {
            case ALIVE -> RenderableState.ACTIVE;
            case DEAD -> RenderableState.INACTIVE;
            default -> throw new IllegalArgumentException("Unknown GameObjectState");
        };
    }
}
