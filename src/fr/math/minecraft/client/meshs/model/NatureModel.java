package fr.math.minecraft.client.meshs.model;

import org.joml.Vector3f;

public class NatureModel {

    public final static Vector3f[] FIRST_FACE = {
            new Vector3f(0.5f, -0.5f, -0.5f),
            new Vector3f(0.5f, 0.5f, -0.5f),
            new Vector3f(-0.5f, 0.5f, 0.5f),
            new Vector3f(-0.5f, -0.5f, 0.5f),

    };

    public final static Vector3f[] SECOND_FACE = {
            new Vector3f(0.5f, -0.5f, 0.5f),
            new Vector3f(0.5f, 0.5f, 0.5f),
            new Vector3f(-0.5f, 0.5f, -0.5f),
            new Vector3f(-0.5f, -0.5f, -0.5f),
    };
}
