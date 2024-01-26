package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;

import java.util.Random;

public class PlainBiome extends AbstractBiome{
    @Override
    public Material getUpperBlock() {
        return Material.GRASS;
    }

    @Override
    public Material getSecondBlock() {
        return Material.DIRT;
    }

    @Override
    public void buildTree(ServerChunk chunk, int x, int y, int z) {
        Random random = new Random();
        float dropRate = random.nextFloat() * 100.0f;
        if(dropRate < 1.0f) {
            StructureBuilder.buildSimpleTree(chunk, x, y, z);
        }
    }

    @Override
    public void buildWeeds(ServerChunk chunk, int x, int y, int z) {

    }
};
