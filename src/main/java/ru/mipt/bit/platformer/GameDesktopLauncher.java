package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.actionGenerators.*;
import ru.mipt.bit.platformer.actionGenerators.objects.ActionGenerator;
import ru.mipt.bit.platformer.actionGenerators.objects.InputActionGenerator;
import ru.mipt.bit.platformer.actionGenerators.objects.RandomActionGenerator;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.actions.ToggleAction;
import ru.mipt.bit.platformer.graphics.GraphicsHandler;
import ru.mipt.bit.platformer.levelGenerators.LevelGenerateStrategy;
import ru.mipt.bit.platformer.levelGenerators.RandomWithEnemiesLevelGenerator;
import ru.mipt.bit.platformer.objectsWithHelpers.CollisionHandler;
import ru.mipt.bit.platformer.objectsWithHelpers.LevelListenerCollisionHandler;
import ru.mipt.bit.platformer.objectsWithHelpers.objects.obstacle.Obstacle;
import ru.mipt.bit.platformer.objectsWithHelpers.objects.projectile.Projectile;
import ru.mipt.bit.platformer.objectsWithHelpers.objects.tank.Tank;
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
    private ActionGeneratorsHandler actionGeneratorsHandler;
    //private ControllersHandler controllers;

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
                new RandomWithEnemiesLevelGenerator(createLevelListenersAndActionGenerators(),
                        tilesWidth, tilesHeight, 10, 2);

        level = randomWithEnemiesLevelGenerator.generate();
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();

        actionGeneratorsHandler.apply();

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

    private ArrayList<LevelListener> createLevelListenersAndActionGenerators() {
        //createActionGenerators
        Collection<ActionGenerator> actionGenerators = new ArrayList<>();
        actionGenerators.add(new ActionGeneratorDecorator(new RandomActionGenerator(Tank.class, new ShootAction(), 0.01f),
                new RandomActionGenerator(Tank.class, MoveAction.UP, 0.25f),
                new RandomActionGenerator(Tank.class, MoveAction.DOWN, 0.25f),
                new RandomActionGenerator(Tank.class, MoveAction.RIGHT, 0.25f),
                new RandomActionGenerator(Tank.class, MoveAction.LEFT, 0.25f)));
        actionGenerators.add(new ActionGeneratorDecorator(new RandomActionGenerator(Tank.class, new ShootAction(), 0.01f),
                new RandomActionGenerator(Tank.class, MoveAction.UP, 0.25f),
                new RandomActionGenerator(Tank.class, MoveAction.DOWN, 0.25f),
                new RandomActionGenerator(Tank.class, MoveAction.RIGHT, 0.25f),
                new RandomActionGenerator(Tank.class, MoveAction.LEFT, 0.25f)));
        actionGenerators.add(new ActionGeneratorDecorator(new InputActionGenerator(Tank.class, new ShootAction(), SPACE),
                new InputActionGenerator(Tank.class, MoveAction.UP, W),
                new InputActionGenerator(Tank.class, MoveAction.DOWN, S),
                new InputActionGenerator(Tank.class, MoveAction.LEFT, A),
                new InputActionGenerator(Tank.class, MoveAction.RIGHT, D)));
        actionGenerators.add(new InputActionGenerator(GraphicsHandler.class, new ToggleAction(), L));

        actionGeneratorsHandler = new ActionGeneratorsHandler(actionGenerators);
        actionGeneratorsHandler.add(graphicsHandler);//!!!!

        //createLevelListeners
        ArrayList<LevelListener> levelListeners = new ArrayList<>();
        LevelListenerActionGenerator levelListenerActionGenerator = new LevelListenerActionGenerator(actionGeneratorsHandler);
        LevelListenerGraphics levelListenerGraphics = new LevelListenerGraphics(graphicsHandler);
        LevelListenerCollisionHandler levelListenerCollisionHandler =
                new LevelListenerCollisionHandler(new CollisionHandler(tilesHeight, tilesWidth));

        levelListeners.add(levelListenerActionGenerator);
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
