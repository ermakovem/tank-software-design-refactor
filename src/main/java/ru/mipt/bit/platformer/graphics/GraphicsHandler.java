package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.actionGenerators.actions.toggle.Toggleable;
import ru.mipt.bit.platformer.graphics.objects.*;
import ru.mipt.bit.platformer.graphics.util.TileMovement;

import java.util.*;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.graphics.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.graphics.util.GdxGameUtils.getSingleLayer;

public class GraphicsHandler implements Graphics, Toggleable {
    private final Batch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;
    private final Map<Renderable, HashSet<Graphics>> objectToGraphics = new HashMap<>();
    private final Collection<Graphics> graphicsObjects = new ArrayList<>();
    //TODO: rename
    private final Map<Map.Entry<Class<?>, RenderableState>, String> classStateToTexturePath;
    private final Map<Class<?>, HashSet<Class<? extends Graphics>>> classToGraphics;

    public GraphicsHandler(String pathGameField,
                           Map<Map.Entry<Class<?>, RenderableState>, String> classStateToTexturePath,
                           Map<Class<?>, HashSet<Class<? extends Graphics>>> classToGraphics) {
        this.batch = new SpriteBatch();
        this.level = new TmxMapLoader().load(pathGameField);
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.tileMovement = new TileMovement(getSingleLayer(level), Interpolation.smooth);

        this.classToGraphics = classToGraphics;
        this.classStateToTexturePath = classStateToTexturePath;
    }

    public void render() {
        clearScreen();

        levelRenderer.render();
        batch.begin();
        for (Graphics graphicsObject : graphicsObjects) {
            graphicsObject.render();
        }
        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
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
            graphics = new HealthBarGraphics((HasHP) renderable, renderable, tileMovement, batch);
        } else {
            throw new IllegalArgumentException("unknown Class<? extends Graphics>");
        }
        return graphics;
    }

    public void parseState(Renderable renderable, RenderableState state) {
        switch (state) {
            case INACTIVE: {
                for (Graphics graphics : objectToGraphics.get(renderable)) {
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
                throw new IllegalArgumentException("unknown RenderableState");
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