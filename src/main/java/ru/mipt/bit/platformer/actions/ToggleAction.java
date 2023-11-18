package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.graphics.objects.Toggleable;

public class ToggleAction implements Action<Toggleable> {
    @Override
    public void apply(Toggleable toggleable) {
        toggleable.toggle();
    }

    @Override
    public Class<?> getRequiredInterface() {
        return Toggleable.class;
    }
}
