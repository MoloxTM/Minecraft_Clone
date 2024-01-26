package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.Coordinates;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

import java.util.ArrayList;

public class DesertBiome extends AbstractBiome{

    public DesertBiome() {
        this.noise = new NoiseGenerator(9, 30, 500.0f, .5f, 25);
        this.biomeName = "Deserts";
    }

    public Material getUpperBlock() {
        return Material.SAND;
    }

    @Override
    public Material getSecondBlock() {
        return Material.SAND;
    }

    @Override
    public void buildTree(ServerChunk chunk, int x, int y, int z, ArrayList<Coordinates> trees) {
        StructureBuilder.buildSimpleCactus(chunk, x, y, z);
    }

    @Override
    public void buildWeeds(ServerChunk chunk, int x, int y, int z) {

    }

}
