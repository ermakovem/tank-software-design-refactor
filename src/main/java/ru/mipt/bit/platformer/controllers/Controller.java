package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.logic.objects.GameObject;

import java.util.ArrayList;
import java.util.Map;
//Contract:
// 1. One Controller controls ONE GameObject.
// 2. GameObject can be added after creation with addGameObject(GameObject gameObject) method.
// 3. Method getActions() returns GameObject and array of Actions for the GameObject.
public interface Controller {
    boolean trySetGameObject(GameObject gameObject);
    Map.Entry<GameObject, ArrayList<Action>> getActions();
}
