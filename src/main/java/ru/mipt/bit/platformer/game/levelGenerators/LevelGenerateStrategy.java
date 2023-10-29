package ru.mipt.bit.platformer.game.levelGenerators;

import ru.mipt.bit.platformer.game.GameLevel;

public interface LevelGenerateStrategy {
    GameLevel generate();
}
