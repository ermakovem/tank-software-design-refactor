package ru.mipt.bit.platformer.game.controllers;

import ru.mipt.bit.platformer.game.GameObject;

//Contract:
// 1. One Controller controls ONE GameObject.
// 2. GameObject can be added after creation with addGameObject(GameObject gameObject) method.
// 3. Method getActions() returns GameObject and array of Actions for the GameObject.
public interface Controller {
    boolean trySet(GameObject gameObject);

    void applyActions();

    void parseState(GameObject gameObject);

    ControllerState getState();
}
