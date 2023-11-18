package ru.mipt.bit.platformer;


import java.util.Collection;

public interface GameObject {
    void updateState(float deltaTime);

    GameObjectState getState();

    Collection<GameObject> getCreatedGameObjects();
}
