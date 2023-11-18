package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.objectsWithHelpers.objects.tank.CanShoot;

public class ShootAction implements Action<CanShoot> {
    @Override
    public void apply(CanShoot canShoot) {
        canShoot.shoot();
    }

    @Override
    public Class<?> getRequiredInterface() {
        return CanShoot.class;
    }
}
