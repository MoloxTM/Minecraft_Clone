package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.shared.world.Coordinates;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.Region;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.shared.world.generator.NoiseGenerator;
import fr.math.minecraft.shared.world.generator.OverworldGenerator;

public class ForestBiome extends AbstractBiome {

    public final static float TREE_DROP_RATE = 8.0f;
    public final static float TREE_DISTANCE = 6;

    public ForestBiome() {
        this.noise = new NoiseGenerator(9, 30, 800.0f, .7f, 25);
        this.treeNoise = new NoiseGenerator(4, 25, 0.01f, 0.5f, 0);
        this.weedNoise = new NoiseGenerator(4, 25, 0.05f, 0.2f, 0);
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
    public void buildTree(int worldX, int worldY, int worldZ, Structure structure, World world) {

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);

        if (worldY <= OverworldGenerator.WATER_LEVEL) {
            return;
        }

        //Calul distance
        for (Coordinates coordinatesAlreadyPlace : structure.getStructures()) {
            double dist = Utils.distance(coordinates, coordinatesAlreadyPlace);
            if (dist <= ForestBiome.TREE_DISTANCE) return;
        }


        float treeNoiseValue = treeNoise.getNoise(worldX, worldZ);

        if (treeNoiseValue < 0.25f) {
            StructureBuilder.buildSimpleTree(structure, worldX, worldY, worldZ, Material.BIRCH_LOG, Material.BIRCH_LEAVES, treeNoiseValue, ForestBiome.TREE_DROP_RATE);
            structure.getStructures().add(coordinates);
        } else if (treeNoiseValue < 0.32f) {
            StructureBuilder.buildFancyTree(structure, worldX, worldY, worldZ, Material.OAK_LOG, Material.OAK_LEAVES);
            structure.getStructures().add(coordinates);
        } else if (treeNoiseValue < 0.41f) {
            StructureBuilder.buildBallonTree(structure, worldX, worldY, worldZ, Material.OAK_LOG, Material.OAK_LEAVES);
            structure.getStructures().add(coordinates);
        }
    }

    @Override
    public void buildWeeds(int worldX, int worldY, int worldZ, Structure structure, World world) {

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);

        if (worldY <= OverworldGenerator.WATER_LEVEL) {
            return;
        }

        if(structure.getStructureMap().containsKey(coordinates)) {
            return;
        }

        float weedNoiseValue = weedNoise.getNoise(worldX, worldZ);

        if (weedNoiseValue < 0.23f) {
            StructureBuilder.buildWeed(world,structure, worldX, worldY, worldZ, weedNoiseValue);
        }
    }

    @Override
    public void buildVillage(int worldX, int worldY, int worldZ, Structure structure, World world, Region region) {

    }
}
