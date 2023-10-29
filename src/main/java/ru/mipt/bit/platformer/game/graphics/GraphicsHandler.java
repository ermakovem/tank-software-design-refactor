package ru.mipt.bit.platformer.game.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.graphics.util.TileMovement;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.obstacle.Obstacle;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.projectile.Projectile;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Tank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.getSingleLayer;

public class GraphicsHandler {
    private final Batch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;
    private final HashMap<GameObject, Graphics> objectToGraphics = new HashMap<>();
    private final Collection<Graphics> graphicsObjects = new ArrayList<>();

    public GraphicsHandler(String pathGameField) {
        this.batch = new SpriteBatch();
        this.level = new TmxMapLoader().load(pathGameField);
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = getSingleLayer(level);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public void render() {
        levelRenderer.render();
        batch.begin();
        for (Graphics graphicsObject : graphicsObjects) {
            graphicsObject.render();
        }

        batch.end();
    }

    public void dispose() {
        for (Graphics graphicsObject : graphicsObjects) {
            graphicsObject.dispose();
        }
        level.dispose();
        batch.dispose();
    }

    public void addGraphicsObjects(GameObject gameObject) {
        Graphics graphics;
        if (gameObject instanceof Tank) {
            graphics =
                    new TankGraphics("images/tank_blue.png", (Tank) gameObject, tileMovement, batch);
        } else if (gameObject instanceof Obstacle) {
            graphics =
                    new ObstacleGraphics("images/greenTree.png", (Obstacle) gameObject, groundLayer, batch);
        } else if (gameObject instanceof Projectile) {
            graphics =
                    new ProjectileGraphics("images/projectile.png", (Projectile) gameObject, tileMovement, batch);
        } else {
            throw new IllegalArgumentException("unknown GameObject");
        }
        graphicsObjects.add(graphics);
        objectToGraphics.put(gameObject, graphics);
    }

    public void parseState(GameObject gameObject) {
        switch (gameObject.getState()) {
            case DEAD: {
                if (gameObject instanceof Projectile) {
                    graphicsObjects.remove(objectToGraphics.get(gameObject));
                    objectToGraphics.remove(gameObject);
                } else if (gameObject instanceof Tank) {
                    graphicsObjects.remove(objectToGraphics.get(gameObject));
                    objectToGraphics.remove(gameObject);
                    Graphics graphics =
                            new TankGraphics("images/destroyed_tank_blue.png", (Tank) gameObject, tileMovement, batch);
                    graphicsObjects.add(graphics);
                    objectToGraphics.put(gameObject, graphics);
                }
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