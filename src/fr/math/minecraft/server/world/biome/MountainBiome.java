package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.world.Coordinates;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

import java.util.ArrayList;

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
    public void buildTree(ServerChunk chunk, int x, int y, int z, ArrayList<Coordinates> trees) {

    }

    @Override
    public void buildWeeds(ServerChunk chunk, int x, int y, int z, ArrayList<Coordinates> trees) {

    }
}
