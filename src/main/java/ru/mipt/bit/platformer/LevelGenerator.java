package ru.mipt.bit.platformer;


import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.graphics.LevelListenerGraphics;
import ru.mipt.bit.platformer.logic.GameLevel;
import ru.mipt.bit.platformer.logic.LevelListener;
import ru.mipt.bit.platformer.logic.Obstacle;
import ru.mipt.bit.platformer.logic.Tank;


import java.io.*;
import java.util.*;

import static java.lang.Math.abs;

//не понял зачем нужен интрефейс левел генератор и левелинфо
public class LevelGenerator {
    private final int tilesWidth = 10;
    private final int tilesHeight = 8;
    GameLevel level = new GameLevel();
    public LevelGenerator() {}
    public LevelGenerator(LevelListener... levelListeners) {
        for (LevelListener levelListener : levelListeners) {
            level.addLevelListener(levelListener);
        }
    }


    public GameLevel generateRandom(int amountOfTrees) {
        if (amountOfTrees + 1 > tilesHeight * tilesWidth) {
            throw new IllegalArgumentException("amount of trees > map capacity");
        }

        level.tryAdd(new Tank(getRandPoint(), 0.4f));

        while (amountOfTrees > 0) {
            GridPoint2 randPoint = getRandPoint();
            if (level.tryAdd(new Obstacle(randPoint))) {
                amountOfTrees--;
            }
        }
        return level;
    }

    private GridPoint2 getRandPoint() {
        Random random = new Random();
        return new GridPoint2(abs(random.nextInt() % tilesWidth), abs(random.nextInt() % tilesHeight));
    }
    public GameLevel generatePath(String pathToMapFile) {

        List<Integer> objectsMap = new ArrayList<>();

        try {
            FileReader reader = new FileReader(pathToMapFile);
            for (int read = reader.read(); read != -1; read = reader.read()) {
                objectsMap.add(read);
            }
            parseObjectsMap(objectsMap);

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("exception in creating of FileReader");
        } catch (IOException e) {
            throw new IllegalArgumentException("exception in read");
        }
        return level;
    }

    //contract: tank is 'X'; obstacle is 'T'; spare space is '_';
    private void parseObjectsMap(List<Integer> objectsMap) {
        int h = 0, w = 0;
        for (int i = 0; i < objectsMap.size(); i++) {
            switch (objectsMap.get(i)) {
                case (int)'X':
                {
                    level.tryAdd(new Tank(new GridPoint2(w++, h), 0.4f));
                    break;
                }
                case (int)'T':
                {
                    level.tryAdd(new Obstacle(new GridPoint2(w++, h)));
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
