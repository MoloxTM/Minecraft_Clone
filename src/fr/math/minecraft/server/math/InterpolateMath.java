package fr.math.minecraft.server.math;

public class InterpolateMath {

    public static float smoothStep(float edge0, float edge1, float x) {
        x = x * x * (3.0f - 2.0f * x);
        return edge0 * x + edge1 * (1 - x);
    }

    public static float smoothInterpolation(float bottomLeft, float bottomRight, float topLeft, float topRight, float xMin, float xMax, float zMin, float zMax, float x, float z) {

        float width = xMax - xMin;
        float height = zMax - zMin;
        float xValue = 1 - (x - xMin) / width;
        float zValue = 1 - (z - zMin) / height;

        float a = smoothStep(bottomLeft, bottomRight, xValue);
        float b = smoothStep(topLeft, topRight, xValue);

        return smoothStep(a, b, zValue);
    }

}
