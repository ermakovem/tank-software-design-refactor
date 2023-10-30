package ru.mipt.bit.platformer.game.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.GameObjectState;
import ru.mipt.bit.platformer.game.graphics.objects.ObjectGraphics;
import ru.mipt.bit.platformer.game.graphics.objects.ObstacleGraphics;
import ru.mipt.bit.platformer.game.graphics.objects.ProjectileGraphics;
import ru.mipt.bit.platformer.game.graphics.objects.TankGraphics;
import ru.mipt.bit.platformer.game.graphics.util.TileMovement;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.obstacle.Obstacle;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.projectile.Projectile;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Tank;

import java.util.*;

import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.getSingleLayer;

public class GraphicsHandler {
    private final Batch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;
    private final Map<Renderable, Graphics> objectToGraphics = new HashMap<>();
    private final Collection<Graphics> graphicsObjects = new ArrayList<>();
    //TODO: rename
    private final Map<Map.Entry<Class, GameObjectState>, String> classStateToPath;

    public GraphicsHandler(String pathGameField, Map<Map.Entry<Class, GameObjectState>, String> classStateToPath) {
        this.batch = new SpriteBatch();
        this.level = new TmxMapLoader().load(pathGameField);
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = getSingleLayer(level);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        this.classStateToPath = classStateToPath;
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

    public void addGraphicsObjects(Renderable renderable, GameObjectState state) {
        Graphics graphics = new ObjectGraphics(
                classStateToPath.get(new AbstractMap.SimpleEntry<>(renderable.getClass(), state)),
                        renderable, tileMovement, batch);
        graphicsObjects.add(graphics);
        objectToGraphics.put(renderable, graphics);
    }

    public void parseState(Renderable renderable, GameObjectState state) {
        switch (state) {
            case DEAD: {
                graphicsObjects.remove(objectToGraphics.get(renderable));
                objectToGraphics.remove(renderable);
                addGraphicsObjects(renderable, state);
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