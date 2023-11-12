package ru.mipt.bit.platformer.game.actions;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.HasAction;

public interface Action<T> {
    void apply(T t);
}
