package ru.mipt.bit.platformer.game.actions;

import ru.mipt.bit.platformer.game.graphics.Toggleable;

public class ToggleAction implements Action<Toggleable> {
    @Override
    public void apply(Toggleable toggleable) {
        toggleable.toggle();
    }
}
