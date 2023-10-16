package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.graphics.GraphicsHandler;
import ru.mipt.bit.platformer.graphics.LevelListenerGraphics;
import ru.mipt.bit.platformer.logic.GameLevel;
import ru.mipt.bit.platformer.logic.GameObject;
import ru.mipt.bit.platformer.logic.Tank;

import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private GraphicsHandler graphicsHandler;
    private GameLevel level;
    private InputController inputController;

    private Tank tank;


    public GameDesktopLauncher() {
    }

    @Override
    public void create() {
        graphicsHandler = new GraphicsHandler("level.tmx");
        LevelGenerator levelGenerator = new LevelGenerator(new LevelListenerGraphics(graphicsHandler));

        //TODO: absolute -> relative
        level = levelGenerator.generatePath("/Programming/GitHub/tank-software-design-refactor" +
                "/src/main/resources/objectsMap/objectsMap.txt");
        //level = levelGenerator.generateRandom(20);

        createInputController();
    }
    private void createInputController() {
        //maps keys to gameObject actions
        inputController = new InputController();
        inputController.mapKeyToActionObject(UP, MoveAction.UP, level.getTheTank());
        inputController.mapKeyToActionObject(W, MoveAction.UP, level.getTheTank());
        inputController.mapKeyToActionObject(DOWN, MoveAction.DOWN, level.getTheTank());
        inputController.mapKeyToActionObject(S, MoveAction.DOWN, level.getTheTank());
        inputController.mapKeyToActionObject(RIGHT, MoveAction.RIGHT, level.getTheTank());
        inputController.mapKeyToActionObject(D, MoveAction.RIGHT, level.getTheTank());
        inputController.mapKeyToActionObject(LEFT, MoveAction.LEFT, level.getTheTank());
        inputController.mapKeyToActionObject(A, MoveAction.LEFT, level.getTheTank());
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();

        for (Map.Entry<Action, GameObject> action : inputController.getActions()) {
            action.getKey().apply(action.getValue());
        }

        level.updateState(deltaTime);

        graphicsHandler.render();
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
        graphicsHandler.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
