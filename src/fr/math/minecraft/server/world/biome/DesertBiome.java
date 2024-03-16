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

public class DesertBiome extends AbstractBiome{

    public DesertBiome() {
        this.noise = new NoiseGenerator(9, 30, 500.0f, .5f, 25);
        this.treeNoise = new NoiseGenerator(4, 25, 0.001f, 0.31f, 0);
        this.weedNoise = new NoiseGenerator(4, 25, 0.05f, 0.2f, 0);
        this.biomeName = "Deserts";
        this.biomeID = 0;
    }

    public Material getUpperBlock() {
        return Material.SAND;
    }

    @Override
    public Material getSecondBlock() {
        return Material.SAND;
    }

    @Override
    public void buildTree(int worldX, int worldY, int worldZ, Structure structure, World world) {

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
        //Calul distance
        for (Coordinates coordinates1 : structure.getStructureMap().keySet()) {
            double dist = Utils.distance(coordinates, coordinates1);
            if (dist <= 20) return;
        }

        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate < 0.3f) {
            StructureBuilder.buildSimpleCactus(structure, worldX, worldY, worldZ);
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
            StructureBuilder.buildDeadBush(structure, worldX, worldY, worldZ);
        }
    }

}
