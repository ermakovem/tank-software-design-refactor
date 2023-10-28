package ru.mipt.bit.platformer.controllers;


/**
 * INIT = no GameObject yet
 * ACTIVE = controllers GameObject
 * DEAD = doesn't control GameObject
 */
public enum ControllerState {
    INIT,
    ACTIVE,
    DEAD;
}
