package fr.math.minecraft.client.math;

public class AnimationMath {

    public static float smoothStep(float t) {
        float v0 = t * t;
        float v1 = 1.0f - (1.0f - t) * (1.0f - t);
        return lerp(v0, v1, t);
    }

    public static float lerp(float v0, float v1, float t) {
        return (1 - t) * v0 + t * v1;
    }

}
