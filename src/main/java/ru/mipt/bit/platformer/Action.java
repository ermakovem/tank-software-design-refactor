package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.logic.GameObject;

public interface Action {
    void apply(GameObject gameObject);
}
