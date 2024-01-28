package fr.math.minecraft.server.manager;

import fr.math.minecraft.server.world.biome.AbstractBiome;
import fr.math.minecraft.server.world.biome.DesertBiome;
import fr.math.minecraft.server.world.biome.ForestBiome;
import fr.math.minecraft.server.world.biome.PlainBiome;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

public class BiomeManager {
    private final NoiseGenerator noise;
    private final AbstractBiome desertBiome;
    private final AbstractBiome forestBiome;
    private final AbstractBiome plainBiome;

    public BiomeManager() {
        this.noise = new NoiseGenerator(9, 30, 1500.0f, .25f, 25);
        this.desertBiome= new DesertBiome();
        this.forestBiome = new ForestBiome();
        this.plainBiome = new PlainBiome();
    }

    public AbstractBiome getBiome(int x, int z){
        float res = this.noise.getNoise(x,z);
        if(res<0.3){
            return this.desertBiome;
        } else if (res<0.6) {
            return this.forestBiome;
        } else{
            return this.plainBiome;
        }
    }

}