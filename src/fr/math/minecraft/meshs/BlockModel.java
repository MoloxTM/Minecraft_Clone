package fr.math.minecraft.meshs;

import org.joml.Vector3f;

public class BlockModel {

    public final static Vector3f[] PZ_POS = {
        new Vector3f(-0.5f, -0.5f, 0.5f),
        new Vector3f(-0.5f, 0.5f, 0.5f),
        new Vector3f(0.5f, 0.5f, 0.5f),
        new Vector3f(0.5f, 0.5f, 0.5f),
        new Vector3f(0.5f, -0.5f, 0.5f),
        new Vector3f(-0.5f, -0.5f, 0.5f)
    };

    public final static Vector3f[] NZ_POS = {
        new Vector3f(-0.5f, -0.5f, -0.5f),
        new Vector3f(-0.5f, 0.5f, -0.5f),
        new Vector3f(0.5f, 0.5f, -0.5f),
        new Vector3f(0.5f, 0.5f, -0.5f),
        new Vector3f(0.5f, -0.5f, -0.5f),
        new Vector3f(-0.5f, -0.5f, -0.5f)
    };


    public final static Vector3f[] PX_POS = {
        new Vector3f(0.5f, -0.5f, -0.5f),
        new Vector3f(0.5f, 0.5f, -0.5f),
        new Vector3f(0.5f, 0.5f, 0.5f),
        new Vector3f(0.5f, 0.5f, 0.5f),
        new Vector3f(0.5f, -0.5f, 0.5f),
        new Vector3f(0.5f, -0.5f, -0.5f)
    };

    public final static Vector3f[] NX_POS = {
        new Vector3f(-0.5f, -0.5f, -0.5f),
        new Vector3f(-0.5f, 0.5f, -0.5f),
        new Vector3f(-0.5f, 0.5f, 0.5f),
        new Vector3f(-0.5f, 0.5f, 0.5f),
        new Vector3f(-0.5f, -0.5f, 0.5f),
        new Vector3f(-0.5f, -0.5f, -0.5f)
    };

    public final static Vector3f[] PY_POS = {
        new Vector3f(-0.5f, 0.5f, 0.5f),
        new Vector3f(-0.5f, 0.5f, -0.5f),
        new Vector3f(0.5f, 0.5f, -0.5f),
        new Vector3f(0.5f, 0.5f, -0.5f),
        new Vector3f(0.5f, 0.5f, 0.5f),
        new Vector3f(-0.5f, 0.5f, 0.5f)
    };

    public final static Vector3f[] NY_POS = {
        new Vector3f(-0.5f, -0.5f, 0.5f),
        new Vector3f(-0.5f, -0.5f, -0.5f),
        new Vector3f(0.5f, -0.5f, -0.5f),
        new Vector3f(0.5f, -0.5f, -0.5f),
        new Vector3f(0.5f, -0.5f, 0.5f),
        new Vector3f(-0.5f, -0.5f, 0.5f)
    };
}