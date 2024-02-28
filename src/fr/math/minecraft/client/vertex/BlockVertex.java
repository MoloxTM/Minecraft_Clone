package fr.math.minecraft.client.vertex;


import org.joml.Vector2f;
import org.joml.Vector3f;

public class BlockVertex extends Vertex {

    private final float index;

    public BlockVertex(Vector3f position, Vector2f textureCoords, int index) {
        super(position, textureCoords);
        this.index = index;
    }

    public BlockVertex(Vector3f position, Vector2f textureCoords) {
        this(position, textureCoords, -1);
    }

    public float getIndex() {
        return index;
    }
}