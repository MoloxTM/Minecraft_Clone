package fr.math.minecraft.client.vertex;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class VillagerVertex extends Vertex {

    public final static float HEAD_PART_ID = 1.0f;
    public final static float CHEST_PART_ID = 2.0f;
    public final static float LEFT_HAND_PART_ID = 3.0f;
    public final static float RIGHT_HAND_PART_ID = 3.5f;
    public final static float LEFT_LEG_PART_ID = 4.0f;
    public final static float RIGHT_LEG_PART_ID = 4.5f;

    private final float partId;

    public VillagerVertex(Vector3f position, Vector2f texCoords, float partId) {
        super(position, texCoords);
        this.partId = partId;
    }

    public VillagerVertex(VillagerVertex vertex) {
        super(new Vector3f(vertex.getPosition()), new Vector2f(vertex.getTextureCoords()));
        this.partId = vertex.getPartId();
    }

    public float getPartId() {
        return partId;
    }
}
