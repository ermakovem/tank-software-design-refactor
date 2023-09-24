package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private CollisionHandler collisionHandler;

    private final InputController inputController = new InputController();

    private Tank tank;
    private Obstacle tree;
    private final List<GameObject> objectsGame = new ArrayList<>();// ArrayList of all game objects

    private GraphicsObject graphicTank;
    private GraphicsObject graphicTree;
    private final List<GraphicsObject> objectsGraphics = new ArrayList<>();// ArrayList of all objects that are rendered

    public GameDesktopLauncher() {
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        //maps keys to tank actions
        keyMap(inputController);

        // create game objects
        createGameObjects();

        // create graphics objects
        createGraphicsObjects();

        //create collision handler
        collisionHandler = new CollisionHandler(objectsGame);

        //load level tiles
        moveRectangleAtTileCenter(getTiledMapTileLayer(), graphicTree.getRectangle(), tree.getCoordinates());
    }

    private void createGraphicsObjects() {
        // create tank
        graphicTank = new GraphicsObject("images/tank_blue.png");
        objectsGraphics.add(graphicTank);

        // create tree
        graphicTree = new GraphicsObject("images/greenTree.png");
        objectsGraphics.add(graphicTree);
    }

    private void createGameObjects() {
        // create tank
        tank = new Tank(new GridPoint2(1, 1), 0.4f);
        objectsGame.add(tank);

        // create tree obstacle
        tree = new Obstacle(new GridPoint2(1, 3));
        objectsGame.add(tree);
    }

    private void keyMap(InputController i) {
        i.mapKeyToAction(UP, Action.UP);
        i.mapKeyToAction(W, Action.UP);
        i.mapKeyToAction(DOWN, Action.DOWN);
        i.mapKeyToAction(S, Action.DOWN);
        i.mapKeyToAction(RIGHT, Action.RIGHT);
        i.mapKeyToAction(D, Action.RIGHT);
        i.mapKeyToAction(LEFT, Action.LEFT);
        i.mapKeyToAction(A, Action.LEFT);
    }

    private TiledMapTileLayer getTiledMapTileLayer() {
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        return groundLayer;
    }

    @Override
    public void render() {
        // clear the screen
        clearScreen();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        //check input controller and change tank state
        tank.handleActions(inputController.checkKeyboard(), collisionHandler.getOccupiedPoints());

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(graphicTank.getRectangle(), tank.getCoordinates(),
                tank.getDestinationCoordinates(), tank.getMovementProgress());

        //updates tank state
        tank.updateState(deltaTime);

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render all objects
        for (int i = 0; i < objectsGraphics.size(); i++) {
            drawTextureRegionUnscaled(batch, objectsGraphics.get(i).getGraphics(), objectsGraphics.get(i).getRectangle(),
                    objectsGame.get(i).getRotation());
        }

        // submit all drawing requests
        batch.end();
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
        for (GraphicsObject object : objectsGraphics) {
            object.dispose();
        }
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
