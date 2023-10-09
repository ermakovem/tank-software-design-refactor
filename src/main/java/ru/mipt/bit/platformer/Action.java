package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.logic.CanMove;
import ru.mipt.bit.platformer.logic.GameObject;

public enum Action {
    UP(new GridPoint2(0, 1)),
    DOWN(new GridPoint2(0, -1)),
    RIGHT(new GridPoint2(1, 0)),
    LEFT(new GridPoint2(-1, 0)),
    SHOOT(true);

    private GridPoint2 vector = new GridPoint2();
    private boolean shoot = false;

    Action(boolean shoot) {
        this.shoot = shoot;
    }
    Action(GridPoint2 vector) {
        this.vector = vector;
    }

    public void apply(GameObject object) {
//        if (shoot && object instanceof CanShoot) {
//            object.shoot();
//        }
        if (object instanceof CanMove) {
            ((CanMove) object).moveTo(vector);
        }
    }
}
