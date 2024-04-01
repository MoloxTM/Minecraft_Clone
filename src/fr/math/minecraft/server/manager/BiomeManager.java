package fr.math.minecraft.server.manager;

import fr.math.minecraft.server.world.biome.*;
import fr.math.minecraft.shared.world.generator.NoiseGenerator;

public class BiomeManager {
    private final NoiseGenerator noise;
    private final AbstractBiome desertBiome;
    private final AbstractBiome forestBiome;
    private final AbstractBiome plainBiome;
    private final AbstractBiome montainsBiome;

    public BiomeManager() {
        this.noise = new NoiseGenerator(9, 30, 1500.0f, .25f, 25);
        this.desertBiome= new DesertBiome();
        this.forestBiome = new ForestBiome();
        this.plainBiome = new PlainBiome();
        this.montainsBiome = new MountainsBiome();
    }

    public AbstractBiome getBiome(int x, int z,float seed){
        float res = noise.getNoise(x+(int)seed, z+(int)seed);
        if (res < 0.23f){
            return this.desertBiome;
        } else if (res < 0.42f) {
            return this.forestBiome;
        } else if (res < 0.62f) {
            return this.montainsBiome;
        } else {
            return this.plainBiome;
        }
    }

}