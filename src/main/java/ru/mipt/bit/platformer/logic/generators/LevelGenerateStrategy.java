package ru.mipt.bit.platformer.logic.generators;

import ru.mipt.bit.platformer.logic.GameLevel;

public interface LevelGenerateStrategy {
    GameLevel generate();
}
