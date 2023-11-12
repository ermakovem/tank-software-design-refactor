package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.game.GameLevel;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.actions.MoveAction;
import ru.mipt.bit.platformer.game.actions.ShootAction;
import ru.mipt.bit.platformer.game.controllers.ControllersHandler;
import ru.mipt.bit.platformer.game.controllers.LevelListenerController;
import ru.mipt.bit.platformer.game.controllers.input.InputController;
import ru.mipt.bit.platformer.game.controllers.random.RandomController;
import ru.mipt.bit.platformer.graphics.GraphicsHandler;
import ru.mipt.bit.platformer.game.LevelListenerGraphics;
import ru.mipt.bit.platformer.game.levelGenerators.LevelGenerateStrategy;
import ru.mipt.bit.platformer.game.levelGenerators.RandomWithEnemiesLevelGenerator;
import ru.mipt.bit.platformer.game.objectsWithHelpers.CollisionHandler;
import ru.mipt.bit.platformer.game.objectsWithHelpers.LevelListenerCollisionHandler;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.obstacle.Obstacle;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.projectile.Projectile;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Tank;
import ru.mipt.bit.platformer.graphics.RenderableState;
import ru.mipt.bit.platformer.graphics.objects.GameObjectGraphics;
import ru.mipt.bit.platformer.graphics.objects.Graphics;
import ru.mipt.bit.platformer.graphics.objects.HealthBarGraphics;

import java.util.*;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private final int tilesHeight = 8;
    private final int tilesWidth = 10;

    private GraphicsHandler graphicsHandler;
    private GameLevel level;
    private ControllersHandler controllers;

    public GameDesktopLauncher() {
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }

    @Override
    public void create() {
        graphicsHandler = new GraphicsHandler("level.tmx", createClassToPath(), createClassToGraphics());

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

    private Map<Map.Entry<Class<?>, RenderableState>, String> createClassToPath() {
        Map<Map.Entry<Class<?>, RenderableState>, String> classStateToPath = new HashMap<>();
        classStateToPath.put(new AbstractMap.SimpleEntry<>(Tank.class, RenderableState.ACTIVE),
                "images/tank_blue.png");
        classStateToPath.put(new AbstractMap.SimpleEntry<>(Tank.class, RenderableState.INACTIVE),
                "images/destroyed_tank_blue.png");
        classStateToPath.put(new AbstractMap.SimpleEntry<>(Obstacle.class, RenderableState.ACTIVE),
                "images/greenTree.png");
        classStateToPath.put(new AbstractMap.SimpleEntry<>(Projectile.class, RenderableState.ACTIVE),
                "images/projectile.png");

        return classStateToPath;
    }

    private Map<Class<?>, HashSet<Class<? extends Graphics>>> createClassToGraphics() {
        Map<Class<?>, HashSet<Class<? extends Graphics>>> classToGraphics = new HashMap<>();
        classToGraphics.put(Tank.class, new HashSet<>());
        classToGraphics.get(Tank.class).add(GameObjectGraphics.class);
        classToGraphics.get(Tank.class).add(HealthBarGraphics.class);
        classToGraphics.put(Obstacle.class, new HashSet<>());
        classToGraphics.get(Obstacle.class).add(GameObjectGraphics.class);
        classToGraphics.put(Projectile.class, new HashSet<>());
        classToGraphics.get(Projectile.class).add(GameObjectGraphics.class);
        return classToGraphics;
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
}
