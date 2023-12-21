package fr.math.minecraft.client.vertex;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class Vertex {

    private final Vector3f position;
    private final Vector2f textureCoords;

    public Vertex(float x, float y, float z, float textureCoordX, float textureCoordY) {
        this(new Vector3f(x, y, z), new Vector2f(textureCoordX, textureCoordY));
    }
    public Vertex(Vector3f position, Vector2f textureCoords) {
        this.position = position;
        this.textureCoords = textureCoords;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector2f getTextureCoords() {
        return textureCoords;
    }

}
