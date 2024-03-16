package fr.math.minecraft.client.vertex;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class ItemVertex extends Vertex {

    public static final int VERTEX_SIZE = 5;

    public ItemVertex(Vector3f position, Vector2f textureCoords) {
        super(position, textureCoords);
    }

}
