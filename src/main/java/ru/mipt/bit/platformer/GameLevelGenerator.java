package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public class GameLevelGenerator extends GameLevel{
    GameLevelGenerator(int amountOfTrees) {
        this.add(new Tank(new GridPoint2(1, 3), 0.4f));
    }
    GameLevelGenerator(String pathToMapFile) {
        this.add(new Tank(new GridPoint2(1, 3), 0.4f));
    }
}
