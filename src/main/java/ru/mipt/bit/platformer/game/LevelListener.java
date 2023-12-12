package ru.mipt.bit.platformer.game;

public interface LevelListener {
    void add(GameObject gameObject);

    void parseState(GameObject gameObject);
}
