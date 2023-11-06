package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.game.GameLevel;
import ru.mipt.bit.platformer.game.GameObjectState;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.actions.MoveAction;
import ru.mipt.bit.platformer.game.actions.ShootAction;
import ru.mipt.bit.platformer.game.controllers.ActionGeneratorsHandler;
import ru.mipt.bit.platformer.game.controllers.LevelListenerActionGenerators;
import ru.mipt.bit.platformer.game.controllers.input.InputActionGenerator;
import ru.mipt.bit.platformer.game.controllers.random.RandomActionGenerator;
import ru.mipt.bit.platformer.game.graphics.GraphicsHandler;
import ru.mipt.bit.platformer.game.graphics.LevelListenerGraphics;
import ru.mipt.bit.platformer.game.graphics.Renderable;
import ru.mipt.bit.platformer.game.levelGenerators.LevelGenerateStrategy;
import ru.mipt.bit.platformer.game.levelGenerators.RandomWithEnemiesLevelGenerator;
import ru.mipt.bit.platformer.game.objectsWithHelpers.CollisionHandler;
import ru.mipt.bit.platformer.game.objectsWithHelpers.LevelListenerCollisionHandler;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.obstacle.Obstacle;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.projectile.Projectile;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Tank;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private final int tilesHeight = 8;
    private final int tilesWidth = 10;

    private GraphicsHandler graphicsHandler;
    private GameLevel level;
    private ActionGeneratorsHandler controllers;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }

    @Override
    public void create() {
        graphicsHandler = new GraphicsHandler("level.tmx", createGraphics());

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

    private Map<Map.Entry<Class, GameObjectState>, String> createGraphics() {
        Map<Map.Entry<Class, GameObjectState>, String> classStateToPath = new HashMap<>();
        classStateToPath.put(new AbstractMap.SimpleEntry<>(Tank.class, GameObjectState.ALIVE),
                "images/tank_blue.png");
        classStateToPath.put(new AbstractMap.SimpleEntry<>(Tank.class, GameObjectState.DEAD),
                "images/destroyed_tank_blue.png");
        classStateToPath.put(new AbstractMap.SimpleEntry<>(Obstacle.class, GameObjectState.ALIVE),
                "images/greenTree.png");
        classStateToPath.put(new AbstractMap.SimpleEntry<>(Projectile.class, GameObjectState.ALIVE),
                "images/projectile.png");

        return classStateToPath;
    }

    private ArrayList<LevelListener> createLevelListenersAndControllers() {
        //createControllers
        InputActionGenerator inputController1 = new InputActionGenerator();
        inputController1.mapKeyToAction(UP, MoveAction.UP);
        inputController1.mapKeyToAction(W, MoveAction.UP);
        inputController1.mapKeyToAction(DOWN, MoveAction.DOWN);
        inputController1.mapKeyToAction(S, MoveAction.DOWN);
        inputController1.mapKeyToAction(RIGHT, MoveAction.RIGHT);
        inputController1.mapKeyToAction(D, MoveAction.RIGHT);
        inputController1.mapKeyToAction(LEFT, MoveAction.LEFT);
        inputController1.mapKeyToAction(A, MoveAction.LEFT);
        inputController1.mapKeyToAction(SPACE, new ShootAction());

        controllers = new ActionGeneratorsHandler(inputController1,
                new RandomActionGenerator(), new RandomActionGenerator());

        //createLevelListeners
        ArrayList<LevelListener> levelListeners = new ArrayList<>();
        LevelListenerActionGenerators levelListenerActionGenerators = new LevelListenerActionGenerators(controllers);
        LevelListenerGraphics levelListenerGraphics = new LevelListenerGraphics(graphicsHandler);
        LevelListenerCollisionHandler levelListenerCollisionHandler =
                new LevelListenerCollisionHandler(new CollisionHandler(tilesHeight, tilesWidth));

        levelListeners.add(levelListenerActionGenerators);
        levelListeners.add(levelListenerGraphics);
        levelListeners.add(levelListenerCollisionHandler);
        return levelListeners;
    }

    @Override
    public void dispose() {
        //dispose created objects
        graphicsHandler.dispose();
    }

    public GameDesktopLauncher() {
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
}
