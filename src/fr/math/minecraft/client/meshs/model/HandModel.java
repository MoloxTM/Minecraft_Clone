package fr.math.minecraft.client.meshs.model;

import fr.math.minecraft.client.vertex.HandVertex;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class HandModel {

    public static HandVertex[] HAND_VERTICES = new HandVertex[] {
        new HandVertex(new Vector3f(0.0f, -1.0f, -1.0f), new Vector2f(10 * 4.0f / 64.0f, 12.0f / 64.0f)),
        new HandVertex(new Vector3f(0.2f, -1.0f, -1.0f), new Vector2f(9 * 4.0f / 64.0f, 12.0f / 64.0f)),
        new HandVertex(new Vector3f(0.2f, 0.0f, -1.0f), new Vector2f(9 * 4.0f / 64.0f, 0.0f)),
        new HandVertex(new Vector3f(0.0f, 0.0f, -1.0f), new Vector2f(10 * 4.0f / 64.0f, 0.0f)),

        new HandVertex(new Vector3f(0.2f, -1.0f, -1.0f), new Vector2f(11 * 4.0f / 64.0f, 12.0f / 64.0f)),
        new HandVertex(new Vector3f(0.2f, -1.0f, -1.2f), new Vector2f(10 * 4.0f / 64.0f, 12.0f / 64.0f)),
        new HandVertex(new Vector3f(0.2f, 0.0f, -1.2f), new Vector2f(10 * 4.0f / 64.0f, 0.0f)),
        new HandVertex(new Vector3f(0.2f, 0.0f, -1.0f), new Vector2f(11 * 4.0f / 64.0f, 0.0f))
    };

}
