package fr.math.minecraft.client.meshs.model;

import fr.math.minecraft.client.vertex.PlayerVertex;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class PlayerModel {

    public final static float SKIN_SIZE = 64.0f;
    public final static float CHEST_HEIGHT = .75f; // 1.25 block
    public final static float CHEST_DEPTH = .25f; // 0.5 block
    public final static float CHEST_WIDTH = .75f;
    public final static float HAND_WIDTH = CHEST_DEPTH;
    public final static float HAND_HEIGHT = CHEST_HEIGHT;
    public final static float HAND_DEPTH = CHEST_DEPTH;
    public final static float LEG_WIDTH = .5f;

    public final static float LEG_HEIGHT = CHEST_HEIGHT;

    public final PlayerVertex[] PLAYER_HEAD_POS = {
        // PZ_POS (Backward)
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, 0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, 0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, 0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),

        // NZ_POS (Forward)
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, 0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(32.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(32.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f, -0.25f), new Vector2f(32.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),

        // PX_POS (Right)
        new PlayerVertex(new Vector3f(0.25f, -0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, 0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),

        // NX_POS (Left)
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, -0.25f), new Vector2f(0.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, 0.25f, -0.25f), new Vector2f(0.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, 0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, 0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, -0.25f), new Vector2f(0.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),

        // PY_POS (UP)
        new PlayerVertex(new Vector3f(-0.25f, 0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, 0.25f, -0.25f), new Vector2f(8.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, 0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, 0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),

        // NY_POS (DOWN)
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, -0.25f), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 64.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f, 0.25f), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE), PlayerVertex.HEAD_PART_ID)
    };

    public final PlayerVertex[] PLAYER_CHEST_POS = {
        // PZ_POS
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f),    new Vector2f(20.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f               , 0.25f / 2.0f),    new Vector2f(20.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f),    new Vector2f(28.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f),    new Vector2f(28.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT , 0.25f / 2.0f),    new Vector2f(28.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f),    new Vector2f(20.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),

        // NZ_POS
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f               , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(40.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(40.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(40.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),

        // PX_POS
        new PlayerVertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f               ),    new Vector2f(28.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f               , 0.25f / 2.0f               ),    new Vector2f(28.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f               ),    new Vector2f(28.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),

        // NX_POS
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f               ),    new Vector2f(16.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f               , 0.25f / 2.0f               ),    new Vector2f(16.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(20.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(20.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(20.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f               ),    new Vector2f(16.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),

        // PY
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, 0.25f / 2.0f), new Vector2f(20.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, -0.25f / 2.0f) , new Vector2f(20.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f, -0.25f / 2.0f), new Vector2f(28.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f, -0.25f / 2.0f), new Vector2f(28.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f, 0.25f / 2.0f), new Vector2f(28.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, 0.25f / 2.0f), new Vector2f(20.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),


        // NY
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f), new Vector2f(28.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, -0.25f / 2.0f), new Vector2f(28.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT, -0.25f / 2.0f), new Vector2f(36.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT, -0.25f / 2.0f), new Vector2f(36.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f), new Vector2f(36.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f), new Vector2f(28.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.CHEST_PART_ID),
    };

    public final PlayerVertex[] PLAYER_LEFT_HAND = {
        // PZ
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f               , .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),

        // NZ
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),

        // PX
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f - CHEST_HEIGHT, .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f             , -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),

        // NX
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),

        // PY
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),

        // NY
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_HAND_PART_ID),
    };

    public final PlayerVertex[] PLAYER_RIGHT_HAND = {
        // PZ
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f               , .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),

        // NZ
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),

        // PX
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),

        // NX
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),

        // PY
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),

        // NY
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_HAND_PART_ID),
    };

    public final PlayerVertex[] PLAYER_RIGHT_LEG = {

        // PZ
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),

        // NZ
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),

        // PX
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),

        // NX
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),

        // PY
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),

        // NY
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
        new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.RIGHT_LEG_PART_ID),
    };

    public final PlayerVertex[] PLAYER_LEFT_LEG = {

            // PZ
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),

            // NZ
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),

            // PX
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f              , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),

            // NX
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 32.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),

            // PY
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f , -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f , -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f , -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),

            // NY
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 48.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
            new PlayerVertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE), PlayerVertex.LEFT_LEG_PART_ID),
    };

}
