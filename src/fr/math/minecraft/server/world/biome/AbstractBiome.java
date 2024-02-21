package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.world.*;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.shared.world.generator.NoiseGenerator;

public abstract class AbstractBiome {

    protected NoiseGenerator noise;
    protected String biomeName;
    protected int biomeID;
    
    public abstract Material getUpperBlock();
    public abstract Material getSecondBlock();
    public abstract void buildTree(int worldX, int worldY, int worldZ, Structure structure, World world);
    public abstract void buildWeeds(int worldX, int worldY, int worldZ, Structure structure, World world);

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
