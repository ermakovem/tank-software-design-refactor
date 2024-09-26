package ru.mipt.bit.platformer.gameLogic.levelGenerators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.gameLogic.GameLevel;
import ru.mipt.bit.platformer.gameLogic.levelListeners.LevelListener;
import ru.mipt.bit.platformer.gameLogic.objects.Obstacle;
import ru.mipt.bit.platformer.gameLogic.objects.tank.Tank;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO: redo
public class PathLevelGenerator implements LevelGenerateStrategy {
    private final int tilesWidth;
    private final int tilesHeight;
    private final String filePath;
    private final ArrayList<LevelListener> levelListeners;

    public PathLevelGenerator(ArrayList<LevelListener> levelListeners,
                              int tilesWidth, int tilesHeight, String filePath) {
        this.levelListeners = levelListeners;
        this.tilesHeight = tilesHeight;
        this.tilesWidth = tilesWidth;
        this.filePath = filePath;
    }

    @Override
    public GameLevel generate() {
        GameLevel gameLevel = new GameLevel();
        List<Integer> objectsMap = new ArrayList<>();

        for (LevelListener levelListener : levelListeners) {
            gameLevel.addLevelListener(levelListener);
        }

        try {
            FileReader reader = new FileReader(filePath);
            for (int read = reader.read(); read != -1; read = reader.read()) {
                objectsMap.add(read);
            }
            parseObjectsMap(objectsMap, gameLevel);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("exception in creating of FileReader");
        } catch (IOException e) {
            throw new IllegalArgumentException("exception in read");
        }
        return gameLevel;
    }

    //contract: tank is 'X'; obstacle is 'T'; spare space is '_';
    private void parseObjectsMap(List<Integer> objectsMap, GameLevel gameLevel) {
        int h = 0, w = 0;
        for (Integer character : objectsMap) {
            switch (character) {
                case (int) 'X': {
                    gameLevel.add(new Tank(new GridPoint2(w++, h), 0.4f));
                    break;
                }
                case (int) 'T': {
                    gameLevel.add(new Obstacle(new GridPoint2(w++, h)));
                    break;
                }
                case (int) '_': {
                    w++;
                    break;
                }
                case 13: {
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


}
