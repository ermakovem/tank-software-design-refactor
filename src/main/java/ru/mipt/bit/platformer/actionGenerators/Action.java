package ru.mipt.bit.platformer.actionGenerators;

public interface Action<T> {
    void apply(T t);

    Class<?> getRequiredInterface();
}
