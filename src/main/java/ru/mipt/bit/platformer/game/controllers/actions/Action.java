package ru.mipt.bit.platformer.game.controllers.actions;

import ru.mipt.bit.platformer.game.GameObject;

public interface Action {
    void apply(GameObject gameObject);
}
