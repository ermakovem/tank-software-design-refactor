package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public interface CanMove {
    void moveTo(GridPoint2 vector);
}
