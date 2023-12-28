package fr.math.minecraft.client.vertex;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class BatchVertex {

    private final Vector2f position;
    private final Vector3f rgb;
    private final Vector2f texCoords;

    public BatchVertex(Vector2f position, Vector3f rgb, Vector2f texCoords) {
        this.position = position;
        this.rgb = rgb;
        this.texCoords = texCoords;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector3f getRgb() {
        return rgb;
    }

    public Vector2f getTexCoords() {
        return texCoords;
    }
}
