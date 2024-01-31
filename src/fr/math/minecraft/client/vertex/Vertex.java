package fr.math.minecraft.client.vertex;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class Vertex {

    private final Vector3f position;
    private final Vector2f textureCoords;
    private final int blockID;
    private final int blockFace;
    private float packedData;

    public Vertex(float packedData, Vector2f textureCoords) {
        this.packedData = packedData;
        this.position = null;
        this.textureCoords = textureCoords;
        this.blockFace = -1;
        this.blockID = -1;
    }

    public Vertex(Vector3f position, Vector2f textureCoords, int blockID, int blockFace) {
        this.position = position;
        this.textureCoords = textureCoords;
        this.blockID=blockID;
        this.blockFace=blockFace;
    }
    public Vertex(Vector3f position, Vector2f textureCoords) {
        this.position = position;
        this.textureCoords = textureCoords;
        this.blockID=-1;
        this.blockFace=-1;
    }


    public Vector3f getPosition() {
        return position;
    }

    public Vector2f getTextureCoords() {
        return textureCoords;
    }

    public int getBlockID() { return blockID; }

    public int getBlockFace() { return blockFace; }

    public float getPackedData() {
        return packedData;
    }
}
