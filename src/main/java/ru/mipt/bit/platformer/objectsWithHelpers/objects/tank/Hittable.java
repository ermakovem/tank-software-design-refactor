package ru.mipt.bit.platformer.objectsWithHelpers.objects.tank;

public interface Hittable {
    void getHit(int damage);
    int getHP();
}
