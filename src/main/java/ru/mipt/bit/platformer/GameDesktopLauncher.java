package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private GraphicHandler graphicHandler;
    private final GameLevel level = new GameLevel();
    //private CollisionHandler collisionHandler;
    private InputController inputController;

    private Tank tank;
    private Obstacle tree;

    public GameDesktopLauncher() {
    }

    @Override
    public void create() {
        graphicHandler = new GraphicHandler("level.tmx");

        tank = new Tank(new GridPoint2(1, 3), 0.4f);
        tree = new Obstacle(new GridPoint2(1, 1));

        graphicHandler.addGraphicsObjects("images/tank_blue.png", tank);
        graphicHandler.addGraphicsObjects("images/greenTree.png", tree);

        level.add(tank);
        level.add(tree);

        //also maps keys to tank actions
        createInputController();
    }
    private void createInputController() {
        inputController = new InputController();
        inputController.mapKeyToActionObject(UP, Action.UP, tank);
        inputController.mapKeyToActionObject(W, Action.UP, tank);
        inputController.mapKeyToActionObject(DOWN, Action.DOWN, tank);
        inputController.mapKeyToActionObject(S, Action.DOWN, tank);
        inputController.mapKeyToActionObject(RIGHT, Action.RIGHT, tank);
        inputController.mapKeyToActionObject(D, Action.RIGHT, tank);
        inputController.mapKeyToActionObject(LEFT, Action.LEFT, tank);
        inputController.mapKeyToActionObject(A, Action.LEFT, tank);
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();

        for (Map.Entry<Action, GameObject> action : inputController.readActions()) {
            action.getKey().apply(action.getValue());
        }

        level.updateState(deltaTime);

        graphicHandler.render();
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        //dispose created objects
        graphicHandler.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
