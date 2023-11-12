package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import org.lwjgl.opengl.GLXSGIMakeCurrentRead;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Tank;
import ru.mipt.bit.platformer.graphics.objects.*;
import ru.mipt.bit.platformer.game.util.TileMovement;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Hittable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static ru.mipt.bit.platformer.game.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.game.util.GdxGameUtils.getSingleLayer;

public class GraphicsHandler implements Graphics, Toggleable{
    private final Batch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;
    private final Map<Renderable, HashSet<Graphics>> objectToGraphics = new HashMap<>();
    private final Collection<Graphics> graphicsObjects = new ArrayList<>();
    //TODO: rename
    private final Map<Map.Entry<Class<?>, RenderableState>, String> classStateToTexturePath;
    private Map<Class<?>, HashSet<Class<? extends Graphics>>> classToGraphics = new HashMap<>();

    public GraphicsHandler(String pathGameField,
                           Map<Map.Entry<Class<?>, RenderableState>, String> classStateToTexturePath,
                           Map<Class<?>, HashSet<Class<? extends Graphics>>> classToGraphics){
        this.batch = new SpriteBatch();
        this.level = new TmxMapLoader().load(pathGameField);
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = getSingleLayer(level);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        this.classToGraphics = classToGraphics;
        this.classStateToTexturePath = classStateToTexturePath;
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
        String path = classStateToTexturePath.get(new AbstractMap.SimpleEntry<>(renderable.getClass(), state));
        HashSet<Class<? extends Graphics>> graphicsClasses = classToGraphics.get(renderable.getClass());
        objectToGraphics.put(renderable, new HashSet<>());
        if (path != null && graphicsClasses != null) {
            for (Class<? extends Graphics> graphicsClass : graphicsClasses) {
                Graphics graphics = getGraphics(renderable, graphicsClass, path);
                graphicsObjects.add(graphics);
                objectToGraphics.get(renderable).add(graphics);
            }
        }
    }

    private Graphics getGraphics(Renderable renderable, Class<? extends Graphics> graphicsClass, String path) {
        Graphics graphics;
        //TODO: redo with reflections
        if (graphicsClass.equals(GameObjectGraphics.class)) {
            graphics = new GameObjectGraphics(path, renderable, tileMovement, batch);
        } else if (graphicsClass.equals(HealthBarGraphics.class)) {
            graphics = new HealthBarGraphics((Hittable) renderable, renderable,tileMovement, batch);
        } else {
            throw new IllegalArgumentException("unknown Class<? extends Graphics>");
        }
        return graphics;
    }

    public void parseState(Renderable renderable, RenderableState state) {
        switch (state) {
            case INACTIVE: {
                for(Graphics graphics : objectToGraphics.get(renderable)) {
                    graphicsObjects.remove(graphics);
                }
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