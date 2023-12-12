package ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank;

public interface Hittable {
    void getHit(int damage);
    int getHP();
}
