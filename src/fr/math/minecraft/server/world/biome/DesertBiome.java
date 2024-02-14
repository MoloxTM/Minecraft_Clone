package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.server.world.generator.NoiseGenerator;

public class DesertBiome extends AbstractBiome{

    public DesertBiome() {
        this.noise = new NoiseGenerator(9, 30, 500.0f, .5f, 25);
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
    public void buildTree(int worldX, int worldY, int worldZ, Structure structure, ServerWorld world) {


        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
        //Calul distance
        for (Coordinates coordinates1 : structure.getStructureMap().keySet()) {
            double dist = Utils.distance(coordinates, coordinates1);
            if(dist <= 20)return;
        }

        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate < 0.3f) {
            StructureBuilder.buildSimpleCactus(structure, worldX, worldY, worldZ);
        }
    }

    @Override
    public void buildWeeds(ServerChunk chunk, int x, int y, int z, Structure structure) {
        ServerWorld world = MinecraftServer.getInstance().getWorld();

        int worldX = chunk.getPosition().x * ServerChunk.SIZE + x;
        int worldZ = chunk.getPosition().x * ServerChunk.SIZE + z;
        int worldY = chunk.getPosition().x * ServerChunk.SIZE + y;

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);

        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate < 0.4f) {
            StructureBuilder.buildDeadBush(structure, x, y, z);
        }
    }

}
