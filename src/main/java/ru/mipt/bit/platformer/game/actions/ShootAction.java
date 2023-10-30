package ru.mipt.bit.platformer.game.actions;

import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.objectsWithHelpers.objects.tank.CanShoot;

public class ShootAction implements Action {

    public ShootAction() {
    }

    @Override
    public void apply(GameObject gameObject) {
        if (gameObject instanceof CanShoot) {
            ((CanShoot) gameObject).shoot();
        }
    }
}
