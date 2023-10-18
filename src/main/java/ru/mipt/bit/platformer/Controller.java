package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.logic.GameObject;

import java.util.ArrayList;
import java.util.Map;
//Contract:
// 1. One Controller controls ONE GameObject.
// 2. GameObject can be added after creation with addGameObject(GameObject gameObject) method.
// 3. Method getActions() returns GameObject and array of Actions for the GameObject.
// 4. If there is no GameObject added yet, getActions returns null. !!!
public interface Controller { //mb <T extends GameObject> ? for listener
    boolean trySetGameObject(GameObject gameObject);
    Map.Entry<GameObject, ArrayList<Action>> getActions();
}
