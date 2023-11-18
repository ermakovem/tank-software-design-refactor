package ru.mipt.bit.platformer.actions;

public interface Action<T> {
    void apply(T t);

    Class<?> getRequiredInterface();
}
