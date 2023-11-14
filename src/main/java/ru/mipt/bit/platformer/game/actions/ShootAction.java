package ru.mipt.bit.platformer.game.actions;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.CanShoot;

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
