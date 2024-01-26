package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.world.Coordinates;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

import java.util.ArrayList;

public class PlainBiome extends AbstractBiome{

    public PlainBiome() {
        this.noise = new NoiseGenerator(9, 30, 1000.0f, .6f, 25);
        this.biomeName = "Plains";
    }

    @Override
    public Material getUpperBlock() {
        return Material.GRASS;
    }

    @Override
    public Material getSecondBlock() {
        return Material.DIRT;
    }

    @Override
    public void buildTree(ServerChunk chunk, int x, int y, int z, ArrayList<Coordinates> trees) {

    }

    @Override
    public void buildWeeds(ServerChunk chunk, int x, int y, int z) {

    }
};
