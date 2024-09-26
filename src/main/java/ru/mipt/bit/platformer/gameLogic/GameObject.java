package ru.mipt.bit.platformer.gameLogic;


public interface GameObject {
    void updateState(float deltaTime);

    GameObjectState getState();

    //Collection<GameObject> getCreatedGameObjects();
}
