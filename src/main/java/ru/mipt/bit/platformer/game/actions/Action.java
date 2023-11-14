package ru.mipt.bit.platformer.game.actions;

import ru.mipt.bit.platformer.game.GameObject;

public interface Action<T> {
    void apply(T t);

    Class<?> getRequiredInterface();
}
