package fr.math.minecraft.client.vertex;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class Vertex {

    private final Vector3f position;
    private final Vector2f textureCoords;
    private final float blockID;
    private final float blockFace;
    private final int occlusion;

    public Vertex(Vector3f position, Vector2f textureCoords, float blockID, float blockFace) {
        this(position, textureCoords, blockID, blockFace, 3);
    }

    public Vertex(Vector3f position, Vector2f textureCoords, float blockID, float blockFace, int occlusion) {
        this.position = position;
        this.textureCoords = textureCoords;
        this.blockID = blockID;
        this.blockFace = blockFace;
        this.occlusion = occlusion;
    }

    public Vertex(Vector3f position, Vector2f textureCoords) {
        this.position = position;
        this.textureCoords = textureCoords;
        this.blockID = -1;
        this.blockFace = -1;
        this.occlusion = -1;
    }


    public Vector3f getPosition() {
        return position;
    }

    public Vector2f getTextureCoords() {
        return textureCoords;
    }

    public float getBlockID() {
        return blockID;
    }

    public float getBlockFace() {
        return blockFace;
    }

    public int getOcclusion() {
        return occlusion;
    }

}
