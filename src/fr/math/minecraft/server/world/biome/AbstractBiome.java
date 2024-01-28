package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.world.Coordinates;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

import java.util.ArrayList;

public abstract class AbstractBiome {
    protected NoiseGenerator noise;
    protected String biomeName;
    public abstract Material getUpperBlock();
    public abstract Material getSecondBlock();
    public abstract void buildTree(ServerChunk chunk, int x, int y, int z, ArrayList<Coordinates> trees);
    public abstract void buildWeeds(ServerChunk chunk, int x, int y, int z);
    public float getHeight(int x, int z) {
        return noise.getHeight(x, z);
    }
    public String getBiomeName() {
        return biomeName;
    }
}
