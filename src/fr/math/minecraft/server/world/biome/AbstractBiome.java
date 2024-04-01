package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.world.*;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.Region;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.shared.world.generator.NoiseGenerator;

public abstract class AbstractBiome {

    protected NoiseGenerator noise, treeNoise, weedNoise;
    protected String biomeName;
    protected int biomeID;
    
    public abstract Material getUpperBlock();
    public abstract Material getSecondBlock();
    public abstract void buildTree(int worldX, int worldY, int worldZ, Structure structure, World world);
    public abstract void buildWeeds(int worldX, int worldY, int worldZ, Structure structure, World world);
    public abstract void buildVillage(int worldX, int worldY, int worldZ, Structure structure, World world, Region region);

    public float getHeight(int x, int z,float seed) {

        return noise.getHeight(x, z,seed);
    }
    
    public String getBiomeName() {
        return biomeName;
    }
    
    public int getBiomeID() {
        return biomeID;
    }
}
