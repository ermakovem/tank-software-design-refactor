package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.HashMap;

public class CollisionHandler {
    public HashMap<GridPoint2, Boolean> fakeCollisionMaker() {
        HashMap<GridPoint2, Boolean> fakeCollision = new HashMap<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                fakeCollision.put(new GridPoint2(i, j), true);
            }
        }
        return fakeCollision;
    }

    public CollisionHandler() {

    }

    HashMap<GridPoint2, Boolean> possibleVectors = new HashMap<>();
}
