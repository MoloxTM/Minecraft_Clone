package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.Coordinates;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.ServerWorld;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

import java.util.ArrayList;

public class ForestBiome extends AbstractBiome{

    public ForestBiome() {
        this.noise = new NoiseGenerator(9, 30, 800.0f, .7f, 25);
        this.biomeName = "Forests";
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
    public void buildTree(ServerChunk chunk, int x, int y, int z, ArrayList<Coordinates> trees) {

        ServerWorld world = MinecraftServer.getInstance().getWorld();

        int worldX = chunk.getPosition().x * ServerChunk.SIZE + x;
        int worldZ = chunk.getPosition().x * ServerChunk.SIZE + z;
        int worldY = chunk.getPosition().x * ServerChunk.SIZE + y;

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
        //Calul distance
        for (Coordinates coordinates1 : trees) {
            double dist = Utils.distance(coordinates, coordinates1);
            if(dist <= 2)return;
        }

        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate < 20.0f) {
            StructureBuilder.buildSimpleTree(chunk, x, y, z);
            world.getTrees().add(coordinates);
        }
    }

    @Override
    public void buildWeeds(ServerChunk chunk, int x, int y, int z, ArrayList<Coordinates> trees) {

    }
}
