package fr.math.minecraft.shared.world.generator;

import fr.math.minecraft.ServerMain;
import org.joml.SimplexNoise;

public class PerlinNoiseGenerator {

    private final float frequency;
    private final float persistence;
    private final int octaves;
    private final float lacunarity;
    private final int seed;

    public PerlinNoiseGenerator(float frequency, float persistence, int octaves, float lacunarity, int seed) {
        this.frequency = frequency;
        this.persistence = persistence;
        this.octaves = octaves;
        this.lacunarity = lacunarity;
        this.seed = seed;
    }

    public float getNoise(float seed, float x, float y, float z) {

        float noiseValue = 0.0f;
        float currentPersistence = 1.0f;
        x *= frequency;
        y *= frequency;
        z *= frequency;

        for (int i = 0; i < octaves; i++) {

            float noise = SimplexNoise.noise(x + seed, y + seed, z + seed);
            noiseValue += noise * currentPersistence;

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;

            currentPersistence *= persistence;

        }

        return noiseValue;
    }
}
