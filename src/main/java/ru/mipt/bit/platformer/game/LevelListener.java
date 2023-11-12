package ru.mipt.bit.platformer.game;

public interface LevelListener extends Listener<GameObject>{
    void add(GameObject gameObject);

    void parseState(GameObject gameObject);
}
