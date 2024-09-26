package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.actionGenerators.ActionGeneratorDecorator;
import ru.mipt.bit.platformer.actionGenerators.ActionGeneratorsHandler;
import ru.mipt.bit.platformer.actionGenerators.actions.move.Direction;
import ru.mipt.bit.platformer.actionGenerators.actions.move.MoveAction;
import ru.mipt.bit.platformer.actionGenerators.actions.shoot.ShootAction;
import ru.mipt.bit.platformer.actionGenerators.actions.toggle.ToggleAction;
import ru.mipt.bit.platformer.actionGenerators.generalActionGenerator.ActionGenerator;
import ru.mipt.bit.platformer.actionGenerators.generalActionGenerator.GeneralActionGenerator;
import ru.mipt.bit.platformer.actionGenerators.predicates.InputIsKeyPressedPredicate;
import ru.mipt.bit.platformer.actionGenerators.predicates.RandomPredicate;
import ru.mipt.bit.platformer.gameLogic.GameLevel;
import ru.mipt.bit.platformer.gameLogic.helpers.CollisionHandler;
import ru.mipt.bit.platformer.gameLogic.levelGenerators.LevelGenerateStrategy;
import ru.mipt.bit.platformer.gameLogic.levelGenerators.RandomWithEnemiesLevelGenerator;
import ru.mipt.bit.platformer.gameLogic.levelListeners.LevelListener;
import ru.mipt.bit.platformer.gameLogic.levelListeners.LevelListenerActionGenerator;
import ru.mipt.bit.platformer.gameLogic.levelListeners.LevelListenerCollisionHandler;
import ru.mipt.bit.platformer.gameLogic.levelListeners.LevelListenerGraphics;
import ru.mipt.bit.platformer.gameLogic.objects.Obstacle;
import ru.mipt.bit.platformer.gameLogic.objects.Projectile;
import ru.mipt.bit.platformer.gameLogic.objects.tank.Tank;
import ru.mipt.bit.platformer.graphics.GraphicsHandler;
import ru.mipt.bit.platformer.graphics.RenderableState;
import ru.mipt.bit.platformer.graphics.objects.GameObjectGraphics;
import ru.mipt.bit.platformer.graphics.objects.Graphics;
import ru.mipt.bit.platformer.graphics.objects.HealthBarGraphics;

import java.util.*;
import java.util.function.Predicate;

import static com.badlogic.gdx.Input.Keys.*;

public class GameDesktopLauncher implements ApplicationListener {
    private final int tilesHeight = 8;
    private final int tilesWidth = 10;

    private GraphicsHandler graphics;
    private GameLevel level;
    private ActionGeneratorsHandler actionGenerator;

    public GameDesktopLauncher() {
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }

    @Override
    public void create() {
        graphics = new GraphicsHandler("level.tmx", createClassToTexturePath(), createClassToGraphics());

        createActionGenerators();

        LevelGenerateStrategy randomWithEnemiesLevelGenerator =
                new RandomWithEnemiesLevelGenerator(createLevelListeners(),
                        tilesWidth, tilesHeight, 10, 2);

        level = randomWithEnemiesLevelGenerator.generate();
    }

    @Override
    public void render() {
        actionGenerator.apply();

        level.updateState(Gdx.graphics.getDeltaTime());

        graphics.render();
    }

    private Map<Map.Entry<Class<?>, RenderableState>, String> createClassToTexturePath() {
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

    private ArrayList<LevelListener> createLevelListeners() {
        ArrayList<LevelListener> levelListeners = new ArrayList<>();
        LevelListenerActionGenerator levelListenerActionGenerator = new LevelListenerActionGenerator(actionGenerator);
        LevelListenerGraphics levelListenerGraphics = new LevelListenerGraphics(graphics);
        LevelListenerCollisionHandler levelListenerCollisionHandler =
                new LevelListenerCollisionHandler(new CollisionHandler(tilesHeight, tilesWidth));

        levelListeners.add(levelListenerActionGenerator);
        levelListeners.add(levelListenerGraphics);
        levelListeners.add(levelListenerCollisionHandler);
        return levelListeners;
    }

    private void createActionGenerators() {
        Collection<ActionGenerator> actionGenerators = new ArrayList<>();
        actionGenerators.add(new ActionGeneratorDecorator(
                new GeneralActionGenerator(Tank.class, new ShootAction(), new RandomPredicate(0.001f)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.UP), new RandomPredicate(0.1f)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.DOWN), new RandomPredicate(0.1f)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.RIGHT), new RandomPredicate(0.1f)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.LEFT), new RandomPredicate(0.1f))));
        actionGenerators.add(new ActionGeneratorDecorator(
                new GeneralActionGenerator(Tank.class, new ShootAction(), new RandomPredicate(0.001f)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.UP), new RandomPredicate(0.1f)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.DOWN), new RandomPredicate(0.1f)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.RIGHT), new RandomPredicate(0.1f)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.LEFT), new RandomPredicate(0.1f))));
        actionGenerators.add(new ActionGeneratorDecorator(
                new GeneralActionGenerator(Tank.class, new ShootAction(), new InputIsKeyPressedPredicate(SPACE)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.UP), new InputIsKeyPressedPredicate(W)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.DOWN), new InputIsKeyPressedPredicate(S)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.RIGHT), new InputIsKeyPressedPredicate(D)),
                new GeneralActionGenerator(Tank.class, new MoveAction(Direction.LEFT), new InputIsKeyPressedPredicate(A))));
        actionGenerators.add(new GeneralActionGenerator(GraphicsHandler.class, new ToggleAction(), new Predicate<Object>() {
            @Override
            public boolean test(Object object) {
                return Gdx.input.isKeyJustPressed(L);
            }
        }));

        actionGenerator = new ActionGeneratorsHandler(actionGenerators);
        actionGenerator.add(graphics);//!!!!
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
        graphics.dispose();
    }
}
