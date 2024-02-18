package fr.math.minecraft.shared.network;

import org.joml.Vector3f;

public class Hitbox {

    private final Vector3f start;
    private final Vector3f end;

    public Hitbox(Vector3f start, Vector3f end) {
        this.start = start;
        this.end = end;
    }

    public float getWidth() {
        return Math.abs(end.x - start.x);
    }

    public float getHeight() {
        return Math.abs(end.y - start.y);
    }

    public float getDepth() {
        return Math.abs(end.z - start.z);
    }



}
