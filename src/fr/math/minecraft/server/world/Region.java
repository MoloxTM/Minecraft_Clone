package fr.math.minecraft.server.world;

import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.manager.BiomeManager;
import fr.math.minecraft.server.world.biome.AbstractBiome;
import fr.math.minecraft.server.world.biome.DesertBiome;
import fr.math.minecraft.server.world.biome.ForestBiome;
import fr.math.minecraft.server.world.biome.PlainBiome;
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
    private Structure structure;

    public Region(Vector3i position) {
        this.position = position;
        this.structure = new Structure();
        this.structureMap = new HashMap<>();
    }

    public Region(int x, int y, int z) {
        this.position = new Vector3i(x, y, z);
        this.structure = new Structure();
        this.structureMap = new HashMap<>();
    }

    public void generateStructure(ServerWorld world) {
        OverworldGenerator generator = (OverworldGenerator) world.getOverworldGenerator();
        for (int x = 0; x < SIZE * ServerChunk.SIZE; x++) {
            for (int z = 0; z < SIZE * ServerChunk.SIZE; z++) {

                int worldX = position.x * SIZE * ServerChunk.SIZE + x;
                int worldZ = position.z * SIZE * ServerChunk.SIZE + z;

                BiomeManager biomeManager = new BiomeManager();
                AbstractBiome currentBiome = biomeManager.getBiome(worldX, worldZ);

                int worldHeight = generator.getHeight(worldX, worldZ);
                if ((SIZE/2) < x && x < (ServerChunk.SIZE * SIZE) - (SIZE/2) && (SIZE/2) < z && z < (ServerChunk.SIZE * SIZE) - (SIZE/2)) {
                    if (currentBiome instanceof ForestBiome) {
                        currentBiome.buildTree(worldX, worldHeight, worldZ, structure, world);
                    } else if(currentBiome instanceof PlainBiome) {
                        currentBiome.buildTree(worldX, worldHeight, worldZ, structure, world);
                        currentBiome.buildWeeds(worldX, worldHeight, worldZ, structure, world);
                    } else if(currentBiome instanceof DesertBiome) {
                        currentBiome.buildTree(worldX, worldHeight, worldZ, structure, world);
                        currentBiome.buildWeeds(worldX, worldHeight, worldZ, structure, world);
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

    public Structure getStructure() {
        return structure;
    }
}
