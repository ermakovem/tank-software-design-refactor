package ru.mipt.bit.platformer.logic;


import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.graphics.LevelListenerGraphics;


import java.io.*;
import java.util.*;

import static java.lang.Math.abs;

public class GameLevelGenerator extends GameLevel {
    //idk how to get width and height from config
    // and they probably have to also/only be in collision handler
    private final int tilesWidth = 10;
    private final int tilesHeight = 8;
    public GameLevelGenerator(int amountOfTrees, LevelListenerGraphics levelListenerGraphics) {
        if (amountOfTrees + 1 > tilesHeight * tilesWidth) {
            throw new IllegalArgumentException("in class GameLevelGenerator in random GameLevelGenerator " +
                    "amount of GameObjects exceed map capacity");
        }

        addLevelListener(levelListenerGraphics);

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
    public GameLevelGenerator(String pathToMapFile, LevelListenerGraphics levelListenerGraphics) {
        addLevelListener(levelListenerGraphics);

        List<Integer> objectsMap = new ArrayList<>();

        try {

            FileReader reader = new FileReader(pathToMapFile);
            for (int read = reader.read(); read != -1; read = reader.read()) {
                objectsMap.add(read);
            }
            parseObjectsMap(objectsMap);

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("in class GameLevelGenerator in path GameLevelGenerator " +
                    "path doesn't contain the file");
        } catch (IOException e) {
            throw new IllegalArgumentException("in class GameLevelGenerator in path GameLevelGenerator " +
                    "couldn't read next char");
        }
    }

    //contract: tank is 'X'; obstacle is 'T'; spare space is '_';
    private void parseObjectsMap(List<Integer> objectsMap) {
        int h = 0, w = 0;
        for (int i = 0; i < objectsMap.size(); i++) {
            switch (objectsMap.get(i)) {
                case (int)'X':
                {
                    add(new Tank(new GridPoint2(w++, h), 0.4f));
                    break;
                }
                case (int)'T':
                {
                    add(new Obstacle(new GridPoint2(w++, h)));
                    break;
                }
                case (int)'_':
                {
                    w++;
                    break;
                }
                case 13:
                {
                    h++;
                    w = 0;
                    break;
                }
            }
        }
    }
}
