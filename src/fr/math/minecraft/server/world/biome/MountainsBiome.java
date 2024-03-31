package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.shared.world.Coordinates;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.Region;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.shared.world.generator.NoiseGenerator;
import fr.math.minecraft.shared.world.generator.OverworldGenerator;

public class MountainsBiome extends AbstractBiome {

    public MountainsBiome() {
        this.noise = new NoiseGenerator(3, 55, 100.0f, .01f, 20);
        this.treeNoise = new NoiseGenerator(4, 25, 0.01f, 0.5f, 0);
        this.biomeName = "Mountains";
        this.biomeID = 2;
    }

    @Override
    public Material getUpperBlock() {
        return Material.SNOW;
    }

    @Override
    public Material getSecondBlock() {
        return Material.SNOW;
    }

    @Override
    public void buildTree(int worldX, int worldY, int worldZ, Structure structure, World world) {

        if (worldY <= OverworldGenerator.WATER_LEVEL) {
            return;
        }

        float treeNoiseValue = treeNoise.getNoise(worldX, worldZ);
        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);

        for (Coordinates coordinatesAlreadyPlace : structure.getStructures()) {
            double dist = Utils.distance(coordinates, coordinatesAlreadyPlace);
            if (dist <= 6) return;
        }

        if (treeNoiseValue < 0.51f) {
            StructureBuilder.buildSnowTree(structure, worldX, worldY, worldZ, Material.SPRUCE_WOOD, Material.SPRUCE_LEAVES);
            structure.getStructures().add(coordinates);
        }
    }

    @Override
    public void buildWeeds(int worldX, int worldY, int worldZ, Structure structure, World world) {

    }

    @Override
    public void buildVillage(int worldX, int worldY, int worldZ, Structure structure, World world, Region region) {

    }
}
