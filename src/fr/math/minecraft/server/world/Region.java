package fr.math.minecraft.server.world;

import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.manager.BiomeManager;
import fr.math.minecraft.server.world.biome.AbstractBiome;
import fr.math.minecraft.server.world.biome.ForestBiome;
import fr.math.minecraft.server.world.generator.OverworldGenerator;
import fr.math.minecraft.server.world.generator.TerrainGenerator;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.HashMap;
import java.util.Map;

public class Region {

    private final Vector3i position;
    private final Map<Coordinates, Byte> structureMap;

    public final static int SIZE = 16;

    public Region(Vector3i position) {
        this.position = position;
        this.structureMap = new HashMap<>();
    }

    public void generateStructure() {
        MinecraftServer server = MinecraftServer.getInstance();
        ServerWorld world = server.getWorld();
        OverworldGenerator generator = (OverworldGenerator) world.getOverworldGenerator();
        for (int x = 0; x < SIZE * ServerChunk.SIZE; x++) {
            for (int y = 0; y < SIZE * ServerChunk.SIZE; y++) {
                for (int z = 0; z < SIZE * ServerChunk.SIZE; z++) {

                    int worldX = position.x * SIZE * ServerChunk.SIZE + x;
                    int worldY = position.y * SIZE * ServerChunk.SIZE + y;
                    int worldZ = position.z * SIZE * ServerChunk.SIZE + z;

                    BiomeManager biomeManager = new BiomeManager();
                    AbstractBiome currentBiome = biomeManager.getBiome(worldX, worldZ);

                    int worldHeight = generator.getHeight(worldX, worldZ);

                    if (currentBiome instanceof ForestBiome) {
                        if (8 < x && x < 256 - 8 && 8 < z && z < 256 - 8) {
                            currentBiome.buildTree(worldX, worldHeight, worldZ, generator.getStructure());
                        }
                    }
                }
            }
        }
    }

    public Map<Coordinates, Byte> getStructureMap() {
        return structureMap;
    }

    public Vector3i getPosition() {
        return position;
    }
}
