package ru.mipt.bit.platformer.logic.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.logic.CollisionHandler;
import ru.mipt.bit.platformer.logic.GameLevel;
import ru.mipt.bit.platformer.logic.listeners.LevelListener;
import ru.mipt.bit.platformer.logic.objects.GameObject;
import ru.mipt.bit.platformer.logic.objects.Obstacle;
import ru.mipt.bit.platformer.logic.objects.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class RandomWithEnemiesLevelGenerator implements LevelGenerateStrategy {
    private final int tilesWidth;
    private final int tilesHeight;
    private int amountOfTrees;
    private final List<LevelListener> levelListeners;
    private CollisionHandler collisionHandler;
    private GameLevel gameLevel;
    private int amountOfEnemies;
    public RandomWithEnemiesLevelGenerator(ArrayList<LevelListener> levelListeners,
                                           int tilesWidth, int tilesHeight,
                                           int amountOfTrees, int amountOfEnemies) {
        this.levelListeners = levelListeners;
        this.tilesHeight = tilesHeight;
        this.tilesWidth = tilesWidth;
        this.amountOfTrees = amountOfTrees;
        this.amountOfEnemies = amountOfEnemies;
    }

    @Override
    public GameLevel generate() {
        //look for wrong conditions
        if (amountOfTrees + amountOfEnemies + 1 > tilesHeight * tilesWidth) {
            throw new IllegalArgumentException("amount of trees > map capacity");
        }

        //refresh at each .generate()
        gameLevel = new GameLevel();
        collisionHandler = new CollisionHandler(tilesHeight, tilesWidth);
        //readd LevelListeners
        for (LevelListener levelListener : levelListeners) {
            gameLevel.addLevelListener(levelListener);
        }

        //generate 1 player tank
        GridPoint2 randCoord = getRandPoint();
        tryAdd(new Tank(randCoord, 0.4f), randCoord);
        //generate trees
        while (amountOfTrees > 0) {
            randCoord = getRandPoint();
            if (tryAdd(new Obstacle(randCoord), randCoord)) {
                amountOfTrees--;
            }
        }
        //generate ai tanks
        while (amountOfEnemies > 0) {
            randCoord = getRandPoint();
            if (tryAdd(new Tank(randCoord, 0.4f, false), randCoord)) {
                amountOfEnemies--;
            }
        }
        return gameLevel;
    }

    private boolean tryAdd(GameObject gameObject, GridPoint2 randCoord) {
        if (collisionHandler.isFree(randCoord)) {
            gameLevel.add(gameObject);
            collisionHandler.add(gameObject);
            return true;
        } else {
            return false;
        }
    }
    private GridPoint2 getRandPoint() {
        Random random = new Random();
        return new GridPoint2(abs(random.nextInt() % tilesWidth), abs(random.nextInt() % tilesHeight));
    }
}
