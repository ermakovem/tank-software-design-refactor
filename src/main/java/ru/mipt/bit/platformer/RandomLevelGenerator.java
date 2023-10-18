package ru.mipt.bit.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.logic.GameLevel;
import ru.mipt.bit.platformer.logic.LevelListener;
import ru.mipt.bit.platformer.logic.Obstacle;
import ru.mipt.bit.platformer.logic.Tank;

import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class RandomLevelGenerator implements LevelGenerateStrategy{
    private final int tilesWidth;
    private final int tilesHeight;
    private int amountOfTrees;
    private final List<LevelListener> levelListeners;
    public RandomLevelGenerator(ArrayList<LevelListener> levelListeners,
                                int tilesWidth, int tilesHeight, int amountOfTrees) {
        this.levelListeners = levelListeners;
        this.tilesHeight = tilesHeight;
        this.tilesWidth = tilesWidth;
        this.amountOfTrees = amountOfTrees;
    }

    @Override
    public GameLevel generate() {
        GameLevel gameLevel = new GameLevel();

        for (LevelListener levelListener : levelListeners) {
            gameLevel.addLevelListener(levelListener);
        }

        if (amountOfTrees + 1 > tilesHeight * tilesWidth) {
            throw new IllegalArgumentException("amount of trees > map capacity");
        }

        gameLevel.tryAdd(new Tank(getRandPoint(), 0.4f));

        while (amountOfTrees > 0) {
            GridPoint2 randPoint = getRandPoint();
            if (gameLevel.tryAdd(new Obstacle(randPoint))) {
                amountOfTrees--;
            }
        }
        return gameLevel;
    }

    private GridPoint2 getRandPoint() {
        Random random = new Random();
        return new GridPoint2(abs(random.nextInt() % tilesWidth), abs(random.nextInt() % tilesHeight));
    }
}
