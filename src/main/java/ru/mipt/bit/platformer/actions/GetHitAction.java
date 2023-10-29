package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.logic.objects.GameObject;

public class GetHitAction implements Action {
    private final float damage;

    public GetHitAction(float damage) {
        this.damage = damage;
    }

    @Override
    public void apply(GameObject gameObject) {
        //gameObject.getHit(damage);
    }
}
