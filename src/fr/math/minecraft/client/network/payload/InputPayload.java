package fr.math.minecraft.client.network.payload;

import fr.math.minecraft.client.entity.Player;
import org.joml.Vector3i;

public class InputPayload {

    private final int tick;
    private boolean sneaking, flying;
    private Vector3i inputVector;
    private float yaw;
    private float pitch;

    public InputPayload(int tick, Player player) {
        this.tick = tick;
        this.yaw = player.getYaw();
        this.pitch = player.getPitch();
        this.inputVector = new Vector3i(player.getInputVector());
        this.flying = player.isFlying();
        this.sneaking = player.isSneaking();
    }

    public InputPayload(int tick) {
        this.tick = tick;
    }

    public int getTick() {
        return tick;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public boolean isFlying() {
        return flying;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public Vector3i getInputVector() {
        return inputVector;
    }
}
