package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.world.Coordinates;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.Structure;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

import java.util.HashMap;

public abstract class AbstractBiome {
    protected NoiseGenerator noise;
    protected String biomeName;
    protected int biomeID;
    public abstract Material getUpperBlock();
    public abstract Material getSecondBlock();
    public abstract void buildTree(int worldX, int worldY, int worldZ, Structure structure);
    public abstract void buildWeeds(ServerChunk chunk, int x, int y, int z, Structure structure);
    public float getHeight(int x, int z) {
        return noise.getHeight(x, z);
    }
    public String getBiomeName() {
        return biomeName;
    }
    public int getBiomeID() {
        return biomeID;
    }
}
