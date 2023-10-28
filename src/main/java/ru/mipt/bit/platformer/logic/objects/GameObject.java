package ru.mipt.bit.platformer.logic.objects;


import java.util.Collection;

public interface GameObject {
    void updateState(float deltaTime);
    GameObjectState getState();
    Collection<GameObject> getCreatedGameObjects();
}
