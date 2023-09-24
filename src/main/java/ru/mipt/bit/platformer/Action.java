package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public enum Action {
    UP(new GridPoint2(0, 1)),
    DOWN(new GridPoint2(0, -1)),
    RIGHT(new GridPoint2(1, 0)),
    LEFT(new GridPoint2(-1, 0));

    private GridPoint2 vector = new GridPoint2(0, 0);

    Action(GridPoint2 vector) {
        this.vector = vector;
    }

    public GridPoint2 getVector() {
        return vector;
    }
}
