package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.logic.GameObject;
import ru.mipt.bit.platformer.logic.LevelListener;
import ru.mipt.bit.platformer.logic.Obstacle;
import ru.mipt.bit.platformer.logic.Tank;

public class LevelListenerGraphics implements LevelListener {
    private final GraphicsHandler graphicsHandler;

    //HashMap<GameObject, String> objectToTexturePath = new HashMap<>();
    public LevelListenerGraphics(GraphicsHandler graphicsHandler) {
        this.graphicsHandler = graphicsHandler;
    }
    @Override
    public void addObject(GameObject gameObject) {
        graphicsHandler.addGraphicsObjects(getTexturePath(gameObject), gameObject);
    }
    private String getTexturePath(GameObject gameObject) {
        //mb that should be inside GraphicsHandler
        if (gameObject instanceof Tank) {
            return "images/tank_blue.png";
        }
        if (gameObject instanceof Obstacle) {
            return "images/greenTree.png";
        }
        throw new IllegalCallerException("in class LevelListenerGraphics in fnc " +
                "getTexturePath() gameObject's texture wasn't found");
        //or return "pathToErrorTexture";
    }
}
