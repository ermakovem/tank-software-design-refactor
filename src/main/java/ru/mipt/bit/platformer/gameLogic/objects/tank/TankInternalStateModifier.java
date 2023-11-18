package ru.mipt.bit.platformer.gameLogic.objects.tank;

//mb TankInternalStateModifier
public interface TankInternalStateModifier {
    boolean canShoot();
    float getMovementSpeedKoef();
}
