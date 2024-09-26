package ru.mipt.bit.platformer.actionGenerators.predicates;

import com.badlogic.gdx.Gdx;

import java.util.function.Predicate;

public class InputIsKeyPressedPredicate implements Predicate<Object> {
    private final int key;

    public InputIsKeyPressedPredicate(int key) {
        this.key = key;
    }

    @Override
    public boolean test(Object object) {
        return Gdx.input.isKeyPressed(key);
    }
}
