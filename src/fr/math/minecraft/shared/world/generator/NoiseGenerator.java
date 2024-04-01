package fr.math.minecraft.shared.world.generator;

import fr.math.minecraft.shared.world.World;
import org.joml.SimplexNoise;

public class NoiseGenerator {

    private final float smoothness;
    private final float offset;
    private final float roughness;
    private final float octaves;
    private final float amplitude;

    public NoiseGenerator(float octaves, float amplitude, float smoothness, float roughness, float offset) {
        this.octaves = octaves;
        this.smoothness = smoothness;
        this.roughness = roughness;
        this.offset = offset;
        this.amplitude = amplitude;
    }

    public float getNoise(int x, int y) {
        float totalAmplitude = 0.0f;
        float noiseValue = 0.0f;
        for (int i = 0; i < octaves; i++) {
            float frequency = (float) Math.pow(2.0, i);
            float amplitude = (float) Math.pow(roughness, i);

            float noiseX = x * frequency / smoothness;
            float noiseY = y * frequency / smoothness;

            float noise = SimplexNoise.noise(noiseX, noiseY);
            noise = (noise + 1.0f) / 2.0f;
            noiseValue += noise * amplitude;
            totalAmplitude += amplitude;
        }

        return noiseValue / totalAmplitude;
    }

    public float getHeight(int x, int y, float seed) {
        float noiseValue = 0.0f;
        for (int i = 0; i < octaves; i++) {
            float frequency = (float) Math.pow(2.0, i);
            float amplitude = (float) Math.pow(roughness, i);

            float noiseX = x * frequency / smoothness;
            float noiseY = y * frequency / smoothness;

            noiseValue += SimplexNoise.noise(noiseX+seed, noiseY+seed) * amplitude;
        }

        float height = (((noiseValue / 2.1f) + 1.2f) * amplitude) + offset;

        return height > 0 ? height : 1;
    }

    public float getAmplitude() {
        return this.amplitude;
    }

    public float getOffset() {
        return this.offset;
    }
}
