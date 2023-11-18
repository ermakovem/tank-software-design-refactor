package ru.mipt.bit.platformer.gameLogic.objects.tank;

import java.util.ArrayList;
import java.util.Collections;

public class TankInternalStatesHandler implements TankInternalStateModifier{
    private final ArrayList<TankInternalStateModifier> modifiers = new ArrayList<>();
    private final int fullHP;
    private final Tank tank;
    public TankInternalStatesHandler(Tank tank) {
        this.tank = tank;
        this.fullHP = tank.getHP();
        modifiers.add(TankInternalState.LIGHTLY_DAMAGED);
    }

    public void updateInternalState(TankInternalStateModifier ... modifiers) {
        Collections.addAll(this.modifiers, modifiers);

        float percentageOfHP = tank.getHP() * 1.0f / fullHP;
        if (percentageOfHP > 0.7f && percentageOfHP <= 1.0f) {
            this.modifiers.set(0, TankInternalState.LIGHTLY_DAMAGED);
        } else if (percentageOfHP >= 0.15f) {
            this.modifiers.set(0, TankInternalState.MEDIUMLY_DAMAGED);
        } else if (percentageOfHP >= 0.0f) {
            this.modifiers.set(0, TankInternalState.HEAVILY_DAMAGED);
        } else {
            throw new IllegalArgumentException("percentageOfHP is not in range [0.0, 1.1]");
        }
    }

    @Override
    public boolean canShoot() {
        boolean result = true;
        for (TankInternalStateModifier modifier : modifiers) {
            result = result && modifier.canShoot();
        }
        return result;
    }

    @Override
    public float getMovementSpeedKoef() {
        float result = 1.0f;
        for (TankInternalStateModifier modifier : modifiers) {
            result *= modifier.getMovementSpeedKoef();
        }
        return result;
    }
}
