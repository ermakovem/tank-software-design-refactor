package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.logic.objects.GameObject;

public interface Action {
    void apply(GameObject gameObject);
}
