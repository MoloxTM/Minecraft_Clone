package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

public class ForestBiome extends AbstractBiome{

    public ForestBiome() {
        this.noise = new NoiseGenerator(9, 30, 800.0f, .7f, 25);
        this.biomeName = "Forests";
        this.biomeID = 1;
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
        for (Coordinates coordinatesAlreadyPlace : structure.getStructures()) {
            double dist = Utils.distance(coordinates, coordinatesAlreadyPlace);
            if(dist <= 4)return;
        }

        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate <= 9.0f) {
            if(dropRate <= 2.25f) {
                StructureBuilder.buildBallonTree(structure, worldX, worldY, worldZ, Material.OAK_LOG, Material.OAK_LEAVES, dropRate);
            } else {
                StructureBuilder.buildSimpleTree(structure, worldX, worldY, worldZ, Material.OAK_LOG, Material.OAK_LEAVES, dropRate);
            }
            structure.getStructures().add(coordinates);
        }
    }

    @Override
    public void buildWeeds(int worldX, int worldY, int worldZ, Structure structure, ServerWorld world) {
        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate > 99.0f) {
            StructureBuilder.buildWeed(structure, worldX, worldY, worldZ);
        }
    }
}
