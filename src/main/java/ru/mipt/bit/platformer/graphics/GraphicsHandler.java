package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.logic.objects.GameObject;
import ru.mipt.bit.platformer.logic.objects.Obstacle;
import ru.mipt.bit.platformer.logic.objects.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GraphicsHandler {
    private final Batch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;
    private final List<TankGraphics> tankGraphicsList = new ArrayList<>();
    private final List<ObstacleGraphics> obstacleGraphicsList = new ArrayList<>();

    public GraphicsHandler(String pathGameField) {
        this.batch = new SpriteBatch();
        this.level = new TmxMapLoader().load(pathGameField);
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = getSingleLayer(level);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public void render(){
        levelRenderer.render();
        batch.begin();

        for (TankGraphics tankGraphics : tankGraphicsList) {
            tankGraphics.render();
        }
        for (ObstacleGraphics obstacleGraphics : obstacleGraphicsList) {
            obstacleGraphics.render();
        }

        batch.end();
    }

    public void dispose(){
        for (TankGraphics tankGraphics : tankGraphicsList) {
            tankGraphics.dispose();
        }
        for (ObstacleGraphics obstacleGraphics : obstacleGraphicsList) {
            obstacleGraphics.dispose();
        }
        level.dispose();
        batch.dispose();
    }

    public void addGraphicsObjects(GameObject gameObject){
        if (gameObject instanceof Tank) {
            TankGraphics tankGraphics =
                    new TankGraphics("images/tank_blue.png", (Tank) gameObject, tileMovement, batch);
            tankGraphicsList.add(tankGraphics);
        }

        if (gameObject instanceof Obstacle) {
            ObstacleGraphics obstacleGraphics  =
                    new ObstacleGraphics("images/greenTree.png", (Obstacle) gameObject, groundLayer, batch);
            obstacleGraphicsList.add(obstacleGraphics);
        }
    }
    public void parseState(GameObject gameObject) {
        switch (gameObject.getState()) {
            case DEAD: {
                //change texture
                break;
            }
            case ALIVE: {
                break;
            }
            default: {
                throw new IllegalArgumentException("unknown GameObjectState");
            }
        }
    }
}