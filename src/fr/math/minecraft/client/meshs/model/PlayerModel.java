package fr.math.minecraft.client.meshs.model;

import fr.math.minecraft.client.Vertex;
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

    public static final Vertex[] PLAYER_HEAD_POS = {
        // PZ_POS (Backward)
        new Vertex(new Vector3f(-0.25f, -0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, 0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, 0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, 0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),

        // NZ_POS (Forward)
        new Vertex(new Vector3f(-0.25f, -0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, 0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(32.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(32.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f, -0.25f), new Vector2f(32.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),

        // PX_POS (Right)
        new Vertex(new Vector3f(0.25f, -0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, 0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),

        // NX_POS (Left)
        new Vertex(new Vector3f(-0.25f, -0.25f, -0.25f), new Vector2f(0.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, 0.25f, -0.25f), new Vector2f(0.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, 0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, 0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f, -0.25f), new Vector2f(0.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),

        // PY_POS (UP)
        new Vertex(new Vector3f(-0.25f, 0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, 0.25f, -0.25f), new Vector2f(8.0f / SKIN_SIZE, 64.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, 0.25f, -0.25f), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, 0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, 0.25f, 0.25f), new Vector2f(8.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),

        // NY_POS (DOWN)
        new Vertex(new Vector3f(-0.25f, -0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f, -0.25f), new Vector2f(16.0f / SKIN_SIZE, 64.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 64.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f, -0.25f), new Vector2f(24.0f / SKIN_SIZE, 64.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f, 0.25f), new Vector2f(24.0f / SKIN_SIZE, 56.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f, 0.25f), new Vector2f(16.0f / SKIN_SIZE, 56.0f / SKIN_SIZE))
    };

    public static final Vertex[] PLAYER_CHEST_POS = {
        // PZ_POS
        new Vertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f),    new Vector2f(20.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f               , 0.25f / 2.0f),    new Vector2f(20.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f),    new Vector2f(28.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f),    new Vector2f(28.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT , 0.25f / 2.0f),    new Vector2f(28.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f),    new Vector2f(20.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // NZ_POS
        new Vertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f               , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(40.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(40.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(40.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // PX_POS
        new Vertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f               ),    new Vector2f(28.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f               , 0.25f / 2.0f               ),    new Vector2f(28.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(32.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f               ),    new Vector2f(28.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // NX_POS
        new Vertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f               ),    new Vector2f(16.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f               , 0.25f / 2.0f               ),    new Vector2f(16.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(20.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f                , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(20.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT , 0.25f / 2.0f - CHEST_DEPTH),    new Vector2f(20.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f - CHEST_HEIGHT, 0.25f / 2.0f               ),    new Vector2f(16.0f / SKIN_SIZE, 32.0f / SKIN_SIZE))

        // PY

        // NY
    };

    public final static Vertex[] PLAYER_LEFT_HAND = {
        // PZ
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f               , .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f             , -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // NZ
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f             , -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // PX
        new Vertex(new Vector3f(-0.25f             , -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f             , -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f             , -0.25f - CHEST_HEIGHT, .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f             , -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // NX
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // PY
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),

        // NY
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
    };

    public final static Vertex[] PLAYER_RIGHT_HAND = {
        // PZ
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f               , .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // NZ
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(56.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // PX
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // NX
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f               , .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f               , .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f             ), new Vector2f(52.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - CHEST_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // PY
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(44.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f, .25f / 2.0f), new Vector2f(44.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),

        // NY
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(48.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(52.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(52.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + CHEST_WIDTH, -0.25f - HAND_HEIGHT, .25f / 2.0f), new Vector2f(48.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
    };

    public final static Vertex[] PLAYER_RIGHT_LEG = {

        // PZ
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // NZ
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // PX
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f              + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // NX
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

        // PY
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),

        // NY
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
        new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH, -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
    };

    public final static Vertex[] PLAYER_LEFT_LEG = {

            // PZ
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f              , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

            // NZ
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f              , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(16.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

            // PX
            new Vertex(new Vector3f(-0.25f              , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f              , -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f              , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(12.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f              , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

            // NX
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f                - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f                - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f             ), new Vector2f(4.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - CHEST_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(0.0f / SKIN_SIZE, 32.0f / SKIN_SIZE)),

            // PY
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(4.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f , -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f , -0.25f - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f , -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - LEG_HEIGHT, .25f / 2.0f), new Vector2f(4.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),

            // NY
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(8.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f - HAND_DEPTH), new Vector2f(12.0f / SKIN_SIZE, 48.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(12.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
            new Vertex(new Vector3f(-0.25f - HAND_WIDTH + LEG_WIDTH , -0.25f - HAND_HEIGHT - LEG_HEIGHT, .25f / 2.0f), new Vector2f(8.0f / SKIN_SIZE, 44.0f / SKIN_SIZE)),
    };

}
