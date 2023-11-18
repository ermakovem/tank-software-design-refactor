package ru.mipt.bit.platformer.actionGenerators.actions;

import ru.mipt.bit.platformer.actionGenerators.Action;

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
