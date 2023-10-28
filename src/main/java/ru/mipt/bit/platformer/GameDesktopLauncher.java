package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.controllers.*;
import ru.mipt.bit.platformer.graphics.GraphicsHandler;
import ru.mipt.bit.platformer.logic.CollisionHandler;
import ru.mipt.bit.platformer.logic.listeners.LevelListenerCollisionHandler;
import ru.mipt.bit.platformer.logic.listeners.LevelListenerGraphics;
import ru.mipt.bit.platformer.logic.GameLevel;
import ru.mipt.bit.platformer.logic.listeners.LevelListener;
import ru.mipt.bit.platformer.logic.listeners.LevelListenerController;
import ru.mipt.bit.platformer.logic.generators.LevelGenerateStrategy;
import ru.mipt.bit.platformer.logic.generators.RandomWithEnemiesLevelGenerator;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private final int tilesHeight = 8;
    private final int tilesWidth = 10;

    private GraphicsHandler graphicsHandler;
    private GameLevel level;
    private ControllersHandler controllers;

    public GameDesktopLauncher() {}

    @Override
    public void create() {
        graphicsHandler = new GraphicsHandler("level.tmx");

        LevelGenerateStrategy randomWithEnemiesLevelGenerator =
                new RandomWithEnemiesLevelGenerator(createLevelListenersAndControllers(),
                tilesWidth, tilesHeight, 10, 2);

        level = randomWithEnemiesLevelGenerator.generate();
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();

        controllers.applyActions();

        level.updateState(deltaTime);

        graphicsHandler.render();
    }

    private ArrayList<LevelListener> createLevelListenersAndControllers() {
        //createControllers
        InputController inputController1 = new InputController();
        inputController1.mapKeyToAction(UP, MoveAction.UP);
        inputController1.mapKeyToAction(W, MoveAction.UP);
        inputController1.mapKeyToAction(DOWN, MoveAction.DOWN);
        inputController1.mapKeyToAction(S, MoveAction.DOWN);
        inputController1.mapKeyToAction(RIGHT, MoveAction.RIGHT);
        inputController1.mapKeyToAction(D, MoveAction.RIGHT);
        inputController1.mapKeyToAction(LEFT, MoveAction.LEFT);
        inputController1.mapKeyToAction(A, MoveAction.LEFT);
        inputController1.mapKeyToAction(SPACE, new ShootAction());

        controllers = new ControllersHandler(inputController1,
                new RandomController(), new RandomController());

        //createLevelListeners
        ArrayList<LevelListener> levelListeners = new ArrayList<>();
        LevelListenerController levelListenerController = new LevelListenerController(controllers);
        LevelListenerGraphics levelListenerGraphics = new LevelListenerGraphics(graphicsHandler);
        LevelListenerCollisionHandler levelListenerCollisionHandler =
                new LevelListenerCollisionHandler(new CollisionHandler(tilesHeight, tilesWidth));

        levelListeners.add(levelListenerController);
        levelListeners.add(levelListenerGraphics);
        levelListeners.add(levelListenerCollisionHandler);
        return levelListeners;
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
