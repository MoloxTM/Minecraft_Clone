package fr.math.minecraft.shared.world;

import fr.math.minecraft.server.manager.BiomeManager;
import fr.math.minecraft.server.world.Structure;
import fr.math.minecraft.server.world.biome.AbstractBiome;
import fr.math.minecraft.server.world.biome.DesertBiome;
import fr.math.minecraft.server.world.biome.ForestBiome;
import fr.math.minecraft.server.world.biome.PlainBiome;
import fr.math.minecraft.shared.world.generator.OverworldGenerator;
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

    public void generateStructure(World world) {
        OverworldGenerator generator = (OverworldGenerator) world.getTerrainGenerator();
        for (int x = 0; x < SIZE * Chunk.SIZE; x++) {
            for (int z = 0; z < SIZE * Chunk.SIZE; z++) {

                int worldX = position.x * SIZE * Chunk.SIZE + x;
                int worldZ = position.z * SIZE * Chunk.SIZE + z;

                BiomeManager biomeManager = new BiomeManager();
                AbstractBiome currentBiome = biomeManager.getBiome(worldX, worldZ);

                int worldHeight = generator.getHeight(worldX, worldZ);
                if (SIZE / 2 < x && x < Chunk.SIZE * SIZE - SIZE / 2 && SIZE / 2 < z && z < Chunk.SIZE * SIZE - SIZE / 2) {
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
