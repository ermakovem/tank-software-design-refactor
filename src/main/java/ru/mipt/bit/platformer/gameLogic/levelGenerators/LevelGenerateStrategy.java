package ru.mipt.bit.platformer.gameLogic.levelGenerators;

import ru.mipt.bit.platformer.gameLogic.GameLevel;

public interface LevelGenerateStrategy {
    GameLevel generate();
}
