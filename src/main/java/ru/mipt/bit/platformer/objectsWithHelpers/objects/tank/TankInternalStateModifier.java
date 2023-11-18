package ru.mipt.bit.platformer.objectsWithHelpers.objects.tank;

//mb TankInternalStateModifier
public interface TankInternalStateModifier {
    boolean canShoot();
    float getMovementSpeedKoef();
}
