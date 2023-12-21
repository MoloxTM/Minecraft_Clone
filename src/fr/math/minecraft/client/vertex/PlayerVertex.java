package fr.math.minecraft.client.vertex;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class PlayerVertex extends Vertex {

    public final static float HEAD_PART_ID = 1.0f;
    public final static float CHEST_PART_ID = 2.0f;
    public final static float HAND_PART_ID = 3.0f;
    public final static float LEG_PART_ID = 4.0f;

    private final float partId;

    public PlayerVertex(Vector3f position, Vector2f texCoords, float partId) {
        super(position, texCoords);
        this.partId = partId;
    }

    public float getPartId() {
        return partId;
    }
}
