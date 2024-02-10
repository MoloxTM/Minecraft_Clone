package fr.math.minecraft.client.network.payload;

import fr.math.minecraft.client.entity.Player;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class InputPayload {

    private final int tick;
    private Vector3i inputVector;
    private float yaw;
    private float pitch;

    public InputPayload(int tick, Vector3i inputVector, float yaw, float pitch) {
        this.tick = tick;
        this.yaw = yaw;
        this.pitch = pitch;
        this.inputVector = inputVector;
    }

    public InputPayload(int tick) {
        this.tick = tick;
    }

    public int getTick() {
        return tick;
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
