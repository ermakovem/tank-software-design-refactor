package ru.mipt.bit.platformer.actionGenerators.predicates;

import com.badlogic.gdx.Gdx;

import java.util.function.Predicate;

public class InputIsKeyJustPressedPredicate implements Predicate<Object> {
    private final int key;
    public InputIsKeyJustPressedPredicate(int key) {
        this.key = key;
    }

    @Override
    public boolean test(Object object) {
        return Gdx.input.isKeyJustPressed(key);
    }
}
