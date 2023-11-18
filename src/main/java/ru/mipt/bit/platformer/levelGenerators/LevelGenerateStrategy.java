package ru.mipt.bit.platformer.levelGenerators;

import ru.mipt.bit.platformer.GameLevel;

public interface LevelGenerateStrategy {
    GameLevel generate();
}
