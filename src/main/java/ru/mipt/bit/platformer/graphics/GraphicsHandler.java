package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.GameObjectState;
import ru.mipt.bit.platformer.graphics.objects.GameObjectGraphics;
import ru.mipt.bit.platformer.graphics.objects.HealthBarGraphics;
import ru.mipt.bit.platformer.graphics.util.TileMovement;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Hittable;

import java.util.*;

import static ru.mipt.bit.platformer.graphics.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.graphics.util.GdxGameUtils.getSingleLayer;

public class GraphicsHandler implements Graphics, Toggleable{
    private final Batch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;
    private final Map<Renderable, Graphics> objectToGraphics = new HashMap<>();
    private final Collection<Graphics> graphicsObjects = new ArrayList<>();
    //TODO: rename
    private final Map<Map.Entry<Class<?>, RenderableState>, String> classStateToPath;

    public GraphicsHandler(String pathGameField, Map<Map.Entry<Class<?>, RenderableState>, String> classStateToPath) {
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

    public void addGraphicsObjects(Renderable renderable, RenderableState state) {
        String path = classStateToPath.get(new AbstractMap.SimpleEntry<>(renderable.getClass(), state));
        if (path != null) {
            Graphics graphics = new GameObjectGraphics(path, renderable, tileMovement, batch);
            graphicsObjects.add(graphics);
            objectToGraphics.put(renderable, graphics);
        }

        //TODO: redo
        if (renderable instanceof Hittable) {
            Graphics graphics = new HealthBarGraphics((Hittable) renderable, renderable, tileMovement, batch);
            graphicsObjects.add(graphics);
            objectToGraphics.put(renderable, graphics);
        }
    }

    public void parseState(Renderable renderable, RenderableState state) {
        switch (state) {
            case INACTIVE: {
                graphicsObjects.remove(objectToGraphics.get(renderable));
                objectToGraphics.remove(renderable);
                addGraphicsObjects(renderable, state);
                break;
            }
            case ACTIVE: {
                break;
            }
            default: {
                throw new IllegalArgumentException("unknown GameObjectState");
            }
        }
    }

    @Override
    public void toggle() {
        for (Graphics graphicsObject : graphicsObjects) {
            if (graphicsObject instanceof Toggleable) {
                ((Toggleable) graphicsObject).toggle();
            }
        }
    }
}