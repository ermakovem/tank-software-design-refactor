package ru.mipt.bit.platformer.gameLogic.levelListeners;

import ru.mipt.bit.platformer.gameLogic.GameObject;

public interface LevelListener {
    void add(GameObject gameObject);

    void parseState(GameObject gameObject);
}
