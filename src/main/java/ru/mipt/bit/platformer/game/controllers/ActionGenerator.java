package ru.mipt.bit.platformer.game.controllers;

/**
 * 1. Param <T> - any object that has actions
 * 2. One ActionGenerator controls ONE <T> and generates ONE type of Action<T>.
 * 3. <T> can be added after creation with trySet(T t) method.
 * 4.
 */
//Contract:
// 1. One Controller controls ONE GameObject and generates ONE type of actions.
// 2. GameObject can be added after creation with addGameObject(GameObject gameObject) method.
// 3. Method applyActions() applies actions for assigned <T>
public interface ActionGenerator<T> {
    void set(T t);

    void applyActions();

    ControllerState getState();
}
