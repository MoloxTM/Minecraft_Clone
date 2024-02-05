package fr.math.minecraft.client.network.payload;

import fr.math.minecraft.client.entity.Player;

public class InputPayload {

    private final int tick;
    private boolean movingLeft, movingRight, movingForward, movingBackward;
    private boolean sneaking, flying;

    public InputPayload(int tick, Player player) {
        this.tick = tick;
        this.movingLeft = player.isMovingLeft();
        this.movingRight = player.isMovingRight();
        this.movingForward = player.isMovingForward();
        this.movingBackward = player.isMovingBackward();
        this.flying = player.isFlying();
        this.sneaking = player.isSneaking();
    }

    public InputPayload(int tick) {
        this.tick = tick;
    }

    public int getTick() {
        return tick;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public boolean isMovingForward() {
        return movingForward;
    }

    public boolean isMovingBackward() {
        return movingBackward;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public boolean isFlying() {
        return flying;
    }
}
