package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;

public class DesertBiome extends AbstractBiome{

    public Material getUpperBlock() {
        return Material.SAND;
    }

    @Override
    public Material getSecondBlock() {
        return Material.SAND;
    }

    @Override
    public void buildTree(ServerChunk chunk, int x, int y, int z) {
        StructureBuilder.buildSimpleCactus(chunk, x, y, z);
    }

    @Override
    public void buildWeeds(ServerChunk chunk, int x, int y, int z) {

    }
}
