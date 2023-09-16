package ru.mipt.bit.platformer.util;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedX;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedY;

public class Direction {
    private Player player;
    private Obstacle obstacle;
    public Direction(Player player, Obstacle obstacle) {
        this.obstacle = obstacle;
        this.player = player;
    }
    public void goUp() {
        if (isEqual(player.getMovementProgress(), 1f)) {
            if (!obstacle.getCoordinates().equals(incrementedY(player.getCoordinates()))) {
                player.getDestinationCoordinates().y++;
                player.setMovementProgress(0f);
            }
            player.setRotation(90f);
        }
    }
    public void goLeft() {
        if (isEqual(player.getMovementProgress(), 1f)) {
            if (!obstacle.getCoordinates().equals(decrementedX(player.getCoordinates()))) {
                player.getDestinationCoordinates().x--;
                player.setMovementProgress(0f);
            }
            player.setRotation(-180f);
        }
    }
    public void goDown() {
        if (isEqual(player.getMovementProgress(), 1f)) {
            if (!obstacle.getCoordinates().equals(decrementedX(player.getCoordinates()))) {
                player.getDestinationCoordinates().y--;
                player.setMovementProgress(0f);
            }
            player.setRotation(-90f);
        }
    }
    public void goRight() {
        if (isEqual(player.getMovementProgress(), 1f)) {
            if (!obstacle.getCoordinates().equals(decrementedX(player.getCoordinates()))) {
                player.getDestinationCoordinates().x++;
                player.setMovementProgress(0f);
            }
            player.setRotation(0f);
        }
    }

}
