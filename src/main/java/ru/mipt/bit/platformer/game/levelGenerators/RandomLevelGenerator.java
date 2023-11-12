package ru.mipt.bit.platformer.game.levelGenerators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameLevel;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.objectsWithHelpers.Collidable;
import ru.mipt.bit.platformer.game.objectsWithHelpers.CollisionHandler;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.obstacle.Obstacle;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class RandomLevelGenerator implements LevelGenerateStrategy {
    private final int tilesWidth;
    private final int tilesHeight;
    private final List<LevelListener> levelListeners;
    private int amountOfTrees;
    private CollisionHandler collisionHandler;
    private GameLevel gameLevel;

    public RandomLevelGenerator(ArrayList<LevelListener> levelListeners,
                                int tilesWidth, int tilesHeight,
                                int amountOfTrees) {
        this.levelListeners = levelListeners;
        this.tilesHeight = tilesHeight;
        this.tilesWidth = tilesWidth;
        this.amountOfTrees = amountOfTrees;
    }

    @Override
    public GameLevel generate() {
        //look for wrong conditions
        if (amountOfTrees + 1 > tilesHeight * tilesWidth) {
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
        tryAdd(new Tank(randCoord, 0.4f, true), randCoord);
        //generate trees
        while (amountOfTrees > 0) {
            randCoord = getRandPoint();
            if (tryAdd(new Obstacle(randCoord), randCoord)) {
                amountOfTrees--;
            }
        }

        return gameLevel;
    }

    private boolean tryAdd(GameObject gameObject, GridPoint2 randCoord) {
        if (collisionHandler.isFree(randCoord)) {
            gameLevel.add(gameObject);
            collisionHandler.add((Collidable) gameObject);
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
