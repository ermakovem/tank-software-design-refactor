package ru.mipt.bit.platformer.logic.listeners;

import ru.mipt.bit.platformer.logic.objects.GameObject;
import ru.mipt.bit.platformer.logic.objects.GameObjectState;

public interface LevelListener {
    void add(GameObject gameObject);
    void parseState(GameObject gameObject);
}
