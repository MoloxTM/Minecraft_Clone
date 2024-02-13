package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

import java.util.HashMap;
import java.util.Map;

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
    public void buildTree(ServerChunk chunk, int x, int y, int z, Structure structure) {

        ServerWorld world = MinecraftServer.getInstance().getWorld();

        int worldX = chunk.getPosition().x * ServerChunk.SIZE + x;
        int worldY = chunk.getPosition().y * ServerChunk.SIZE + y;
        int worldZ = chunk.getPosition().z * ServerChunk.SIZE - z;

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
        //Calul distance
        for (Coordinates coordinatesAlreadyPlace : structure.getStructures()) {
            double dist = Utils.distance(coordinates, coordinatesAlreadyPlace);
            if(dist <= 2)return;
        }

        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate < 20.0f) {
            StructureBuilder.buildSimpleTree(structure, worldX, worldY, worldZ);
            structure.getStructures().add(coordinates);
        }
    }

    @Override
    public void buildWeeds(ServerChunk chunk, int x, int y, int z, Structure structure) {

    }
}
