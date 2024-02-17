package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

public class ForestBiome extends AbstractBiome{

    public final static float TREE_DROP_RATE = 8.0f;
    public final static float TREE_DISTANCE = 4;

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
            if(dist <= ForestBiome.TREE_DISTANCE)return;
        }

        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate <= ForestBiome.TREE_DROP_RATE) {
            if (dropRate <= ForestBiome.TREE_DROP_RATE * 0.07f) {
                StructureBuilder.buildFancyTree(structure, worldX, worldY, worldZ, Material.OAK_LOG, Material.OAK_LEAVES);
            } else if(dropRate <= ForestBiome.TREE_DROP_RATE * 0.25f) {
                StructureBuilder.buildBallonTree(structure, worldX, worldY, worldZ, Material.OAK_LOG, Material.OAK_LEAVES);
            } else {
                if(dropRate >= ForestBiome.TREE_DROP_RATE * 0.9f) {
                    StructureBuilder.buildSimpleTree(structure, worldX, worldY, worldZ, Material.BIRCH_LOG, Material.BIRCH_LEAVES, dropRate, ForestBiome.TREE_DROP_RATE);
                } else {
                    StructureBuilder.buildSimpleTree(structure, worldX, worldY, worldZ, Material.OAK_LOG, Material.OAK_LEAVES, dropRate, ForestBiome.TREE_DROP_RATE);
                }
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
