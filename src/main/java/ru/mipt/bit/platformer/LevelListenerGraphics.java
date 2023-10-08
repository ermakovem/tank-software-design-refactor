package ru.mipt.bit.platformer;

import org.lwjgl.system.CallbackI;
import ru.mipt.bit.platformer.*;

import java.util.HashMap;

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
