package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.controllers.*;
import ru.mipt.bit.platformer.graphics.GraphicsHandler;
import ru.mipt.bit.platformer.logic.listeners.LevelListenerExtAIAdapter;
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
    private GraphicsHandler graphicsHandler;
    private GameLevel level;
    private final ArrayList<Controller> controllers = new ArrayList<>();

    public GameDesktopLauncher() {}

    @Override
    public void create() {
        graphicsHandler = new GraphicsHandler("level.tmx");

        LevelGenerateStrategy randomWithEnemiesLevelGenerator =
                new RandomWithEnemiesLevelGenerator(createLevelListeners(),
                10, 8, 10, 2);

        level = randomWithEnemiesLevelGenerator.generate();
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();

        for (Controller controller : controllers) {
            //TODO: controller.applyActions();
            for (Action action : controller.getActions().getValue()) {
                action.apply(controller.getActions().getKey());
            }
        }

        level.updateState(deltaTime);

        graphicsHandler.render();
    }

    private ArrayList<LevelListener> createLevelListeners() {
        //createControllers
        InputController inputController1 = new InputController();
        inputController1.mapKeyToActionObject(UP, MoveAction.UP);
        inputController1.mapKeyToActionObject(W, MoveAction.UP);
        inputController1.mapKeyToActionObject(DOWN, MoveAction.DOWN);
        inputController1.mapKeyToActionObject(S, MoveAction.DOWN);
        inputController1.mapKeyToActionObject(RIGHT, MoveAction.RIGHT);
        inputController1.mapKeyToActionObject(D, MoveAction.RIGHT);
        inputController1.mapKeyToActionObject(LEFT, MoveAction.LEFT);
        inputController1.mapKeyToActionObject(A, MoveAction.LEFT);

        controllers.add(inputController1);
        controllers.add(new RandomController());
        ExternalAIAdapter externalAIAdapter = new ExternalAIAdapter(10, 8, new NotRecommendingAI());
        controllers.add(new ExternalAIController(externalAIAdapter));

        //createLevelListeners
        ArrayList<LevelListener> levelListeners = new ArrayList<>();
        LevelListenerController levelListenerController = new LevelListenerController(controllers);
        LevelListenerGraphics levelListenerGraphics = new LevelListenerGraphics(graphicsHandler);
        LevelListenerExtAIAdapter levelListenerExtAIAdapter = new LevelListenerExtAIAdapter(externalAIAdapter);

        levelListeners.add(levelListenerController);
        levelListeners.add(levelListenerGraphics);
        levelListeners.add(levelListenerExtAIAdapter);
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
