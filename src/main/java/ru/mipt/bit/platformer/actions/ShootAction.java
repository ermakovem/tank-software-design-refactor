package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.logic.objects.CanShoot;
import ru.mipt.bit.platformer.logic.objects.GameObject;

public class ShootAction implements Action {

    public ShootAction() {}
    @Override
    public void apply(GameObject gameObject) {
        if (gameObject instanceof CanShoot) {
            ((CanShoot) gameObject).shoot();
        }
    }
}
