package fr.math.minecraft.client.meshs.model;

import fr.math.minecraft.client.vertex.VillagerVertex;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class VillagerModel {

    public final static float SKIN_SIZE = 64.0f;
    public final static float HEAD_SIZE = .25f;
    public final static float HEAD_OFFSET = .1f;
    public final static float NOISE_WIDTH = HEAD_SIZE / 4.5f;
    public final static float NOISE_HEIGHT = HEAD_SIZE / 1.1f;
    public final static float NOISE_DEPTH = NOISE_WIDTH * 1.5f;
    public final static float NOISE_OFFSET = .25f;
    public final static float CHEST_HEIGHT = .75f;
    public final static float CHEST_DEPTH = .28f;
    public final static float CHEST_WIDTH = .75f;
    public final static float HAND_WIDTH = .25f;
    public final static float HAND_HEIGHT = .6f;
    public final static float HAND_DEPTH = HAND_WIDTH;
    public final static float LEG_WIDTH = .5f;
    public final static float LEG_HEIGHT = .75f;
    private static final float LEG_DEPTH = HEAD_SIZE / 2.0f;


    public final static VillagerVertex[] VILLAGER_HEAD_POS = {
        // PZ_POS (Backward)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PZ_POS (Backward)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PX_POS (Right)
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // NX_POS (Left)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(0.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(0.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(0.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PY_POS (UP)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

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
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(32.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(32.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(32.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // NZ_POS (Backward)
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PX_POS (Right)
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PX_POS (Right)
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(26.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PY_POS (UP)
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(26.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(28.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(28.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // NY_POS (DOWN)
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(28.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(30.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

    };

    public final static VillagerVertex[] VILLAGER_CHEST_POS = {

        // PZ_POS (Backward)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PZ_POS (Backward)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PX_POS (Right)
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // NX_POS (Left)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // PY_POS (UP)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

        // NY_POS (DOWN)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(37.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(37.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(37.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID)

    };

    public final static VillagerVertex[] VILLAGER_LEFT_HAND = {
        // PZ
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // NZ
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // PX
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // NX
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // PY
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // NY
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
    };

    public final static VillagerVertex[] VILLAGER_RIGHT_HAND = {
        // PZ
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // NZ
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE +  HAND_WIDTH             , -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH            , -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH            , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // PX
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // NX
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // PY
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // NY
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
    };

    public final static VillagerVertex[] VILLAGER_BOTTOM_HAND_POS = {
        // PZ_POS (Backward)

        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // PZ_POS (Backward)
        /*
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE - HAND_HEIGHT / 2.0f, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE - HAND_HEIGHT / 2.0f, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE - HAND_HEIGHT / 2.0f, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
         */

        // PX_POS (Right)
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // NX_POS (Left)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(40.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(40.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(40.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // PY_POS (UP)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

        // NY_POS (DOWN)
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(60.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID)
    };

    public final static VillagerVertex[] VILLAGER_LEFT_LEG = {
        // PZ
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

        // NZ
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),


        // NX
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

        // PX
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

        // PY
        new VillagerVertex(new Vector3f(-HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

        // NY
        new VillagerVertex(new Vector3f(-HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
    };

    public final static VillagerVertex[] VILLAGER_RIGHT_LEG = {
        // PZ
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

        // NZ
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

        // NX
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

        // PX
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

        // PY
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

        // NY
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
        new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
    };

    public final static VillagerVertex[] LAYER_VILLAGER_HEAD_POS = {
            // PZ_POS (Backward)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(40.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(40.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(48.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(48.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(48.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(40.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // PZ_POS (Backward)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(56.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(56.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(64.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(64.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(64.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(56.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // PX_POS (Right)
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(48.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(48.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(56.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(56.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(56.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(48.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // NX_POS (Left)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(40.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(40.0f / SKIN_SIZE, 8.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(40.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 0.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // PY_POS (UP)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, -HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE + HEAD_OFFSET, HEAD_SIZE), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // NY_POS (DOWN)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID)

    };

    public final static VillagerVertex[] LAYER_VILLAGER_NOSE_POS = {
            // PZ_POS (Backward)
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(32.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(32.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(32.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // NZ_POS (Backward)
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // PX_POS (Right)
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // PX_POS (Right)
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(26.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 58.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // PY_POS (UP)
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(26.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(28.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(28.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE + NOISE_OFFSET, HEAD_SIZE), new Vector2f(26.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // NY_POS (DOWN)
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(28.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE + NOISE_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(30.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-NOISE_WIDTH, -HEAD_SIZE - NOISE_HEIGHT + NOISE_OFFSET, HEAD_SIZE), new Vector2f(28.0f / SKIN_SIZE, 62.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

    };

    public final static VillagerVertex[] LAYER_VILLAGER_CHEST_POS = {

            // PZ_POS (Backward)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // PZ_POS (Backward)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // PX_POS (Right)
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(36.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // NX_POS (Left)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // PY_POS (UP)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, -CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, CHEST_DEPTH), new Vector2f(22.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),

            // NY_POS (DOWN)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(37.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -CHEST_DEPTH), new Vector2f(37.0f / SKIN_SIZE, 45.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(37.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, CHEST_DEPTH), new Vector2f(30.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.HEAD_PART_ID)

    };

    public final static VillagerVertex[] LAYER_VILLAGER_LEFT_HAND = {
            // PZ
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // NZ
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // PX
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE             , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // NX
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // PY
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // NY
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE - HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
    };

    public final static VillagerVertex[] LAYER_VILLAGER_RIGHT_HAND = {
            // PZ
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // NZ
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE +  HAND_WIDTH             , -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH            , -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH            , -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // PX
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f             ), new Vector2f(56.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // NX
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE               , HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE               , HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f             ), new Vector2f(48.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 30.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // PY
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE, HEAD_SIZE / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // NY
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE + HAND_WIDTH, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(56.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
    };

    public final static VillagerVertex[] LAYER_VILLAGER_BOTTOM_HAND_POS = {
            // PZ_POS (Backward)

            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // PZ_POS (Backward)
            /*
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, HEAD_SIZE - HAND_HEIGHT / 2.0f, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE - HAND_HEIGHT / 2.0f, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, HEAD_SIZE - HAND_HEIGHT / 2.0f, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, -HEAD_SIZE), new Vector2f(32.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, -HEAD_SIZE), new Vector2f(24.0f / SKIN_SIZE, 46.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
             */

            // PX_POS (Right)
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // NX_POS (Left)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(40.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(40.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(40.0f / SKIN_SIZE, 18.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // PY_POS (UP)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT / 2.0f, HEAD_SIZE / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),

            // NY_POS (DOWN)
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f - HAND_DEPTH), new Vector2f(60.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(60.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - HAND_HEIGHT, HEAD_SIZE / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 22.0f / SKIN_SIZE), VillagerVertex.LEFT_HAND_PART_ID)
    };

    public final static VillagerVertex[] LAYER_VILLAGER_LEFT_LEG = {
            // PZ
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

            // NZ
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),


            // NX
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

            // PX
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

            // PY
            new VillagerVertex(new Vector3f(-HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

            // NY
            new VillagerVertex(new Vector3f(-HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
    };

    public final static VillagerVertex[] LAYER_VILLAGER_RIGHT_LEG = {
            // PZ
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

            // NZ
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

            // NX
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

            // PX
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 26.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

            // PY
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT, LEG_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),

            // NY
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE , -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, -LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 42.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
            new VillagerVertex(new Vector3f(-HEAD_SIZE + HEAD_SIZE, -HEAD_SIZE - CHEST_HEIGHT - LEG_HEIGHT, LEG_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 38.0f / SKIN_SIZE), VillagerVertex.LEFT_LEG_PART_ID),
    };

}