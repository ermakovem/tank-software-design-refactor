package ru.mipt.bit.platformer.actionGenerators.actions.move;

import com.badlogic.gdx.math.GridPoint2;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;

/**
 * rotation - angle in deg (0 deg = RIGHT)
 * vector - 2d vector
 */

public enum Direction {
    UP(new GridPoint2(0, 1)),
    DOWN(new GridPoint2(0, -1)),
    RIGHT(new GridPoint2(1, 0)),
    LEFT(new GridPoint2(-1, 0));
    private final GridPoint2 vector;

    Direction(GridPoint2 vector) {
        this.vector = vector;
    }

    public GridPoint2 getVector() {
        return vector;
    }

    public float getRotation() {
        return (float) (atan2(vector.y, vector.x) * 180f / PI);
    }
}
