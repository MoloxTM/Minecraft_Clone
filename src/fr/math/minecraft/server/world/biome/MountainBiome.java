package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.world.*;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

public class MountainBiome extends AbstractBiome{

    public MountainBiome() {
        this.noise = new NoiseGenerator(9, 30, 800.0f, .7f, 25);
        this.biomeName = "Mountains";
        this.biomeID = 2;
    }
    @Override
    public Material getUpperBlock() {
        return Material.GRASS;
    }

    @Override
    public Material getSecondBlock() {
        return Material.STONE;
    }

    @Override
    public void buildTree(int worldX, int worldY, int worldZ, Structure structure, ServerWorld world) {

    }

    @Override
    public void buildWeeds(ServerChunk chunk, int x, int y, int z, Structure structure) {

    }
}
