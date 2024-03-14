package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.shared.world.Coordinates;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.shared.world.generator.NoiseGenerator;
import fr.math.minecraft.shared.world.generator.OverworldGenerator;

public class PlainBiome extends AbstractBiome{

    public final static float TREE_DROP_RATE = 0.2f;
    public final static float WEED_DROP_RATE = 97.0f;

    public PlainBiome() {
        this.noise = new NoiseGenerator(9, 30, 1000.0f, .6f, 25);
        this.treeNoise = new NoiseGenerator(4, 25, 0.01f, 0.21f, 0);
        this.weedNoise = new NoiseGenerator(4, 25, 0.05f, 0.42f, 0);
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
    public void buildTree(int worldX, int worldY, int worldZ, Structure structure, World world) {

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);

        //Calul distance
        for (Coordinates coordinates1 : structure.getStructureMap().keySet()) {
            double dist = Utils.distance(coordinates, coordinates1);
            if (dist <= 10) return;
        }

        float treeNoiseValue = treeNoise.getNoise(worldX, worldZ);

        if (treeNoiseValue < 0.25f) {
            StructureBuilder.buildSimpleTree(structure, worldX, worldY, worldZ, Material.BIRCH_LOG, Material.BIRCH_LEAVES, 2, ForestBiome.TREE_DROP_RATE);
            structure.getStructures().add(coordinates);
        } else if (treeNoiseValue < 0.32f) {
            StructureBuilder.buildFancyTree(structure, worldX, worldY, worldZ, Material.OAK_LOG, Material.OAK_LEAVES);
            structure.getStructures().add(coordinates);
        } else if (treeNoiseValue < 0.35f) {
            StructureBuilder.buildBallonTree(structure, worldX, worldY, worldZ, Material.OAK_LOG, Material.OAK_LEAVES);
            structure.getStructures().add(coordinates);
        }
    }

    @Override
    public void buildWeeds(int worldX, int worldY, int worldZ, Structure structure, World world) {
        if (worldY <= OverworldGenerator.WATER_LEVEL) {
            return;
        }

        float weedNoiseValue = weedNoise.getNoise(worldX, worldZ);

        if (weedNoiseValue < .23f) {
            StructureBuilder.buildWeed(structure, worldX, worldY, worldZ, weedNoiseValue);
        }
    }
}
