package ru.mipt.bit.platformer.game;


import java.util.Collection;

public interface GameObject {
    void updateState(float deltaTime);

    GameObjectState getState();

    Collection<GameObject> getCreatedGameObjects();
}
