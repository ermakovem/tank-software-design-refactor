package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashMap;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private InputController inputController = new InputController();

    private Tank player;
    private Obstacle tree;
    private RenderObject graphicTank;
    private RenderObject graphicTree;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        TiledMapTileLayer groundLayer = getTiledMapTileLayer();

        //maps keys to tank actions
        keyMap(inputController);
        // set player initial position
        player = new Tank(new GridPoint2(1, 1), 0.4f);
        graphicTank = new RenderObject("images/tank_blue.png", player.getRotation());
        // create tree obstacle
        tree = new Obstacle(new GridPoint2(1, 3));
        graphicTree = new RenderObject("images/greenTree.png", 0f);

        moveRectangleAtTileCenter(groundLayer, graphicTree.getRectangle(), tree.getCoordinates());
    }

    private void keyMap(InputController i) {
        i.mapKeyToAction(UP, Action.UP);
        i.mapKeyToAction(W, Action.UP);
        i.mapKeyToAction(DOWN, Action.DOWN);
        i.mapKeyToAction(S, Action.DOWN);
        i.mapKeyToAction(RIGHT, Action.RIGHT);
        i.mapKeyToAction(D, Action.RIGHT);
        i.mapKeyToAction(A, Action.LEFT);
        i.mapKeyToAction(LEFT, Action.LEFT);
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

        HashMap<GridPoint2, Boolean> fakeCollisionHandler = new HashMap<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                fakeCollisionHandler.put(new GridPoint2(i, j), true);
            }
        }

        player.handleActions(inputController.checkKeyboard(), fakeCollisionHandler);

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(graphicTank.getRectangle(), player.getCoordinates(),
                player.getDestinationCoordinates(), player.getMovementProgress());

        player.updateState(deltaTime);

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, graphicTank.getGraphics(), graphicTank.getRectangle(), player.getRotation());

        // render tree obstacle
        drawTextureRegionUnscaled(batch, graphicTree.getGraphics(), graphicTree.getRectangle(), 0f);

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
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        graphicTree.dispose();
        graphicTank.dispose();
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
