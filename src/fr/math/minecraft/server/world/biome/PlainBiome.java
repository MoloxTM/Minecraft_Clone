package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

public class PlainBiome extends AbstractBiome{

    public PlainBiome() {
        this.noise = new NoiseGenerator(9, 30, 1000.0f, .6f, 25);
        this.biomeName = "Plains";
        this.biomeID = 3;
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
    public void buildTree(int worldX, int worldY, int worldZ, Structure structure, ServerWorld world) {

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
        //Calul distance
        for (Coordinates coordinates1 : structure.getStructureMap().keySet()) {
            double dist = Utils.distance(coordinates, coordinates1);
            if(dist <= 2)return;
        }

        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate < 0.2f) {
            StructureBuilder.buildSimpleTree(structure, worldX, worldY, worldZ, Material.OAK_LOG, Material.OAK_LEAVES, dropRate);
        }
    }

    @Override
    public void buildWeeds(int worldX, int worldY, int worldZ, Structure structure, ServerWorld world) {
        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate > 97.0f) {
            StructureBuilder.buildWeed(structure, worldX, worldY, worldZ);
        }
    }
}
