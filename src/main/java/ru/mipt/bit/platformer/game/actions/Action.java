package ru.mipt.bit.platformer.game.actions;

import ru.mipt.bit.platformer.game.GameObject;

public interface Action {
    void apply(GameObject gameObject);
}
