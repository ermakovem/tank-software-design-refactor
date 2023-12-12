package ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank;


//mb private inside a handler
public enum TankInternalState implements TankInternalStateModifier{
    LIGHTLY_DAMAGED(true, 1.0f),
    MEDIUMLY_DAMAGED(true, 2.0f),
    HEAVILY_DAMAGED(false, 3.0f);

    final boolean canShoot;
    final float movementSpeedKoef;

    TankInternalState(boolean canShoot, float movementSpeedKoef) {
        this.canShoot = canShoot;
        this.movementSpeedKoef = movementSpeedKoef;
    }

    @Override
    public boolean canShoot() {
        return canShoot;
    }

    @Override
    public float getMovementSpeedKoef() {
        return movementSpeedKoef;
    }
}
