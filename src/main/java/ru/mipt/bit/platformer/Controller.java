package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.logic.GameObject;

import java.util.ArrayList;
import java.util.Map;

public interface Controller {
    void addGameObject(GameObject gameObject);
    Map.Entry<GameObject, ArrayList<Action>> getActions();
}
