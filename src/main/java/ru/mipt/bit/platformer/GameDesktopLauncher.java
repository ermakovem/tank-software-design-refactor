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
import ru.mipt.bit.platformer.util.Obstacle;
import ru.mipt.bit.platformer.util.Player;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Player player;

    private Obstacle tree;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        player = new Player("images/tank_blue.png", 1, 1);

        tree = new Obstacle("images/greenTree.png", 1, 3);

        moveRectangleAtTileCenter(groundLayer, tree.getRectangle(), tree.getCoordinates());
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            if (isEqual(player.getMovementProgress(), 1f)) {
                // check potential player destination for collision with obstacles
                if (!tree.getCoordinates().equals(incrementedY(player.getCoordinates()))) {
                    player.getDestinationCoordinates().y++;
                    player.setMovementProgress(0f);
                }
                player.setRotation(90f);
            }
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            if (isEqual(player.getMovementProgress(), 1f)) {
                if (!tree.getCoordinates().equals(decrementedX(player.getCoordinates()))) {
                    player.getDestinationCoordinates().x--;
                    player.setMovementProgress(0f);
                }
                player.setRotation(-180f);
            }
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            if (isEqual(player.getMovementProgress(), 1f)) {
                if (!tree.getCoordinates().equals(decrementedY(player.getCoordinates()))) {
                    player.getDestinationCoordinates().y--;
                    player.setMovementProgress(0f);
                }
                player.setRotation(-90f);
            }
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            if (isEqual(player.getMovementProgress(), 1f)) {
                if (!tree.getCoordinates().equals(incrementedX(player.getCoordinates()))) {
                    player.getDestinationCoordinates().x++;
                    player.setMovementProgress(0f);
                }
                player.setRotation(0f);
            }
        }

        //calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(player.getRectangle(), player.getCoordinates(),
                player.getDestinationCoordinates(), player.getMovementProgress());

        player.setMovementProgress(continueProgress(player.getMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(player.getMovementProgress(), 1f)) {
            // record that the player has reached his/her destination
            player.setCoordinates(player.getDestinationCoordinates());
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, player.getGraphics(), player.getRectangle(), player.getRotation());

        // render tree obstacle
        drawTextureRegionUnscaled(batch, tree.getGraphics(), tree.getRectangle(), 0f);

        // submit all drawing requests
        batch.end();
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
        tree.dispose();
        player.dispose();
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
