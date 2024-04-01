package fr.math.minecraft.client.events.listeners;

import org.joml.Vector3f;

public class EntityUpdate {

    private final Vector3f position;
    private final float yaw;
    private final float pitch;
    private final float bodyYaw;

    public EntityUpdate(Vector3f position, float yaw, float pitch, float bodyYaw) {
        this.position = position;
        this.yaw = yaw;
        this.pitch = pitch;
        this.bodyYaw = bodyYaw;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getBodyYaw() {
        return bodyYaw;
    }

}
