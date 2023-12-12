package ru.mipt.bit.platformer.game.controllers;


/**
 * INIT = no GameObject yet
 * ACTIVE = controlls GameObject
 * DEAD = doesn't control GameObject
 */
public enum ControllerState {
    INIT,
    ACTIVE,
    DEAD
}
