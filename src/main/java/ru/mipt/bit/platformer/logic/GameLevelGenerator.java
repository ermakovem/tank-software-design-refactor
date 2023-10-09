package ru.mipt.bit.platformer.logic;


import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.graphics.LevelListenerGraphics;
import ru.mipt.bit.platformer.logic.GameLevel;
import ru.mipt.bit.platformer.logic.Obstacle;
import ru.mipt.bit.platformer.logic.Tank;

import java.io.*;
import java.util.Random;

import static java.lang.Math.abs;

public class GameLevelGenerator extends GameLevel {
    //idk how to get width and height from config
    // and they probably have to be in collision handler
    private final int tilesWidth = 10;
    private final int tilesHeight = 8;
    public GameLevelGenerator(int amountOfTrees, LevelListenerGraphics levelListener) {
        if (amountOfTrees + 1 > tilesHeight * tilesWidth) {
            throw new IllegalArgumentException("in class GameLevelGenerator in random GameLevelGenerator " +
                    "amount of GameObjects exceed map capacity");
        }

        addLevelListener(levelListener);

        add(new Tank(getRandPoint(), 0.4f));

        while (amountOfTrees > 0) {
            GridPoint2 randPoint = getRandPoint();
            if (collisionHandler.isFree(randPoint)) {
                add(new Obstacle(randPoint));
                amountOfTrees--;
            }
        }
    }

    private GridPoint2 getRandPoint() {
        Random random = new Random();
        return new GridPoint2(abs(random.nextInt() % tilesWidth), abs(random.nextInt() % tilesHeight));
    }
    public GameLevelGenerator(String pathToMapFile, LevelListenerGraphics levelListener) {
        FileReader reader;

        try {
            reader = new FileReader(pathToMapFile);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("in class GameLevelGenerator in path GameLevelGenerator " +
                    "path doesn't contain the file");
        }

        try {
            for (int read = reader.read(); read != -1; read = reader.read()) {
                System.out.println((char) read);
            }
        } catch (IOException e) {
            System.out.println("2");
//            throw new IllegalArgumentException("in class GameLevelGenerator in path GameLevelGenerator " +
//                    "couldn't read next char");
        }

    }
}
