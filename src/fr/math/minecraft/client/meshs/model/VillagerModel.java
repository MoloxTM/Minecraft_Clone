package fr.math.minecraft.client.meshs.model;

import fr.math.minecraft.client.vertex.VillagerVertex;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class VillagerModel {

    public final static float SKIN_SIZE = 64.0f;
    public final static float HEAD_SIZE = .35f;
    public final static float NOISE_WIDTH = HEAD_SIZE / 4.5f;
    public final static float NOISE_HEIGHT = HEAD_SIZE / 1.5f;
    public final static float NOISE_DEPTH = NOISE_WIDTH * 1.5f;
    public final static float NOISE_OFFSET = .1f;
    public final static float CHEST_HEIGHT = .75f;
    public final static float CHEST_DEPTH = .375f;
    public final static float CHEST_WIDTH = .75f;
    public final static float LEG_WIDTH = .5f;

    public final static VillagerVertex[] VILLAGER_HEAD_POS = {
        // PZ_POS (Backward)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PZ_POS (Backward)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PX_POS (Right)
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // NX_POS (Left)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(0.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE, -HEAD_SIZE), new Vector2f(0.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(0.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PY_POS (UP)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE, -HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE, -HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE, -HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // NY_POS (DOWN)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID)

    };

    public final static VillagerVertex[] VILLAGER_NOSE_POS = {
        // PZ_POS (Backward)
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(24.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(24.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(24.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(26.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(24.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // NZ_POS (Backward)
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PX_POS (Right)
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(24.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PX_POS (Right)
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(24.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PY_POS (UP)
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // NY_POS (DOWN)
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

    };

}