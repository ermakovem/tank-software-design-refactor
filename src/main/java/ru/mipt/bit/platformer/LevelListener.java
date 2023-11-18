package ru.mipt.bit.platformer;

public interface LevelListener {
    void add(GameObject gameObject);

    void parseState(GameObject gameObject);
}
