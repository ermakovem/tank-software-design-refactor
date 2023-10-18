package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.logic.GameLevel;

public interface LevelGenerateStrategy {
    GameLevel generate();
}
