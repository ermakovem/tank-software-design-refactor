package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.logic.GameLevel;
import ru.mipt.bit.platformer.logic.LevelListener;
import ru.mipt.bit.platformer.logic.Obstacle;
import ru.mipt.bit.platformer.logic.Tank;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PathLevelGenerator implements LevelGenerateStrategy {
    private final int tilesWidth;
    private final int tilesHeight;
    private final String filePath;
    private GameLevel gameLevel = new GameLevel();

    public PathLevelGenerator(ArrayList<LevelListener> levelListeners,
                                int tilesWidth, int tilesHeight, String filePath) {
        for (LevelListener levelListener : levelListeners) {
            gameLevel.addLevelListener(levelListener);
        }

        this.tilesHeight = tilesHeight;
        this.tilesWidth = tilesWidth;
        this.filePath = filePath;
    }

    //contract: tank is 'X'; obstacle is 'T'; spare space is '_';
    private void parseObjectsMap(List<Integer> objectsMap) {
        int h = 0, w = 0;
        for (int i = 0; i < objectsMap.size(); i++) {
            switch (objectsMap.get(i)) {
                case (int)'X':
                {
                    gameLevel.tryAdd(new Tank(new GridPoint2(w++, h), 0.4f));
                    break;
                }
                case (int)'T':
                {
                    gameLevel.tryAdd(new Obstacle(new GridPoint2(w++, h)));
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
            if (h > tilesHeight || w > tilesWidth) {
                throw new IllegalArgumentException("file parsing error");
            }
        }
    }

    @Override
    public GameLevel generate() {
        List<Integer> objectsMap = new ArrayList<>();

        try {
            FileReader reader = new FileReader(filePath);
            for (int read = reader.read(); read != -1; read = reader.read()) {
                objectsMap.add(read);
            }
            parseObjectsMap(objectsMap);

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("exception in creating of FileReader");
        } catch (IOException e) {
            throw new IllegalArgumentException("exception in read");
        }
        return gameLevel;
    }
}
