package ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank;

//mb TankInternalStateModifier
public interface TankInternalStateModifier {
    boolean canShoot();
    float getMovementSpeedKoef();
}
