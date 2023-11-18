package ru.mipt.bit.platformer.gameLogic.objects.tank;


public enum TankBoosters implements TankInternalStateModifier {
    SPEED_BOOSTER(true, 0.5f);

    private final boolean canShoot;
    private final float movementSpeedModifier;

    TankBoosters(boolean canShoot, float movementSpeedModifier) {
        this.canShoot = canShoot;
        this.movementSpeedModifier = movementSpeedModifier;
    }

    @Override
    public boolean canShoot() {
        return canShoot;
    }

    @Override
    public float getMovementSpeedKoef() {
        return movementSpeedModifier;
    }
}
