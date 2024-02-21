package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.shared.world.Coordinates;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.shared.world.generator.NoiseGenerator;

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
    public void buildTree(int worldX, int worldY, int worldZ, Structure structure, World world) {

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
        //Calul distance
        for (Coordinates coordinatesAlreadyPlace : structure.getStructures()) {
            double dist = Utils.distance(coordinates, coordinatesAlreadyPlace);
            if (dist <= 3) return;
        }


        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate < 10.0f) {
            StructureBuilder.buildSimpleTree(structure, worldX, worldY, worldZ);
            structure.getStructures().add(coordinates);
        }
    }

    @Override
    public void buildWeeds(int worldX, int worldY, int worldZ, Structure structure, World world) {
        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate > 99.0f) {
            StructureBuilder.buildWeed(structure, worldX, worldY, worldZ);
        }
    }
}
