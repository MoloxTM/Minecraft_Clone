package fr.math.minecraft.shared.world.generator;

import fr.math.minecraft.server.world.biome.ForestBiome;
import fr.math.minecraft.shared.world.*;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.manager.BiomeManager;
import fr.math.minecraft.server.math.InterpolateMath;
import fr.math.minecraft.server.world.biome.AbstractBiome;
import org.joml.Math;
import org.joml.SimplexNoise;
import org.joml.Vector2i;
import org.joml.Vector3i;

import java.util.HashMap;
import java.util.Map;

public class OverworldGenerator implements TerrainGenerator {

    private final CavesGenerator cavesGenerator;
    public final static int WATER_LEVEL = 43;

    public OverworldGenerator() {
        this.cavesGenerator = new OverworldCavesGenerator();
    }

    public float calculBiomeHeight(int worldX, int worldZ, float seed) {
        BiomeManager biomeManager = new BiomeManager();
        AbstractBiome currentBiome = biomeManager.getBiome(worldX, worldZ,seed);
        float height = currentBiome.getHeight(worldX, worldZ,seed);
        return height;
    }

    public Map<Vector2i, Integer> fillHeightMap(int chunkX, int chunkZ, int xMin, int xMax, int zMin, int zMax,World world) {

        Map<Vector2i, Integer> heightMap = new HashMap<>();

        int worldX = chunkX * Chunk.SIZE;
        int worldZ = chunkZ * Chunk.SIZE;

        float bottomLeft = this.calculBiomeHeight(worldX + xMin, worldZ + zMin,world.getSeed());
        float bottomRight =  this.calculBiomeHeight(worldX + xMax, worldZ + zMin,world.getSeed());
        float topLeft =  this.calculBiomeHeight(worldX + xMin, worldZ + zMax,world.getSeed());
        float topRight =  this.calculBiomeHeight(worldX + xMax, worldZ + zMax,world.getSeed());

        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {
                int worldHeight = (int) (InterpolateMath.smoothInterpolation(bottomLeft, bottomRight, topLeft, topRight, xMin, xMax, zMin, zMax, x, z));
                heightMap.put(new Vector2i(x, z), worldHeight);
            }
        }
        return heightMap;
    }

    public int getHeight(int worldX, int worldZ, World world) {

        int chunkX = ((int) Math.floor(worldX / (double) Chunk.SIZE)) * Chunk.SIZE;
        int chunkZ = ((int) Math.floor(worldZ / (double) Chunk.SIZE)) * Chunk.SIZE;

        int xMin = 0, zMin = 0;
        int xMax = Chunk.SIZE - 1, zMax = Chunk.SIZE - 1;

        float bottomLeft = this.calculBiomeHeight(chunkX + xMin, chunkZ + zMin,world.getSeed());
        float bottomRight =  this.calculBiomeHeight(chunkX + xMax, chunkZ + zMin,world.getSeed());
        float topLeft =  this.calculBiomeHeight(chunkX + xMin, chunkZ + zMax,world.getSeed());
        float topRight =  this.calculBiomeHeight(chunkX + xMax, chunkZ + zMax,world.getSeed());

        int x = (worldX % Chunk.SIZE);
        int z = (worldZ % Chunk.SIZE);

        x = x < 0 ? x + Chunk.SIZE : x;
        z = z < 0 ? z + Chunk.SIZE : z;

        int worldHeight = (int) (InterpolateMath.smoothInterpolation(bottomLeft, bottomRight, topLeft, topRight, xMin, xMax, zMin, zMax, x, z));


        return worldHeight;
    }

    @Override
    public void generateChunk(World world, Chunk chunk) {

        Map<Vector2i, Integer> heightMap = fillHeightMap(chunk.getPosition().x, chunk.getPosition().z, 0, Chunk.SIZE - 1, 0, Chunk.SIZE - 1,world);

        int regionX = (int) Math.floor((chunk.getPosition().x * Chunk.SIZE)/ (double) (Chunk.SIZE * Region.SIZE));
        int regionY = (int) Math.floor((chunk.getPosition().y * Chunk.SIZE) / (double) (Chunk.SIZE * Region.SIZE));
        int regionZ = (int) Math.floor((chunk.getPosition().z * Chunk.SIZE) / (double) (Chunk.SIZE * Region.SIZE));

        world.generateRegion(new Vector3i(regionX, regionY, regionZ));

        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int z = 0; z < Chunk.SIZE; z++) {

                BiomeManager biomeManager = new BiomeManager();

                int worldX = x + chunk.getPosition().x * Chunk.SIZE;
                int worldZ = z + chunk.getPosition().z * Chunk.SIZE;

                AbstractBiome currentBiome = biomeManager.getBiome(worldX, worldZ,world.getSeed());
                chunk.setBiome(currentBiome);

                int worldHeight = heightMap.get(new Vector2i(x, z));

                for (int y = 0; y < Chunk.SIZE; y++) {

                    byte block = chunk.getBlock(x, y, z);
                    int worldY = y + chunk.getPosition().y * Chunk.SIZE;

                    Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
                    Vector3i blockWorldPosition = new Vector3i(worldX, worldY, worldZ);
                    BreakedBlock breakedBlock = world.getBrokenBlocks().get(blockWorldPosition);
                    PlacedBlock placedBlock = world.getPlacedBlocks().get(blockWorldPosition);

                    if (breakedBlock != null) {
                        chunk.setBlock(x, y, z, Material.AIR.getId());
                        continue;
                    }

                    if (placedBlock != null) {
                        chunk.setBlock(x, y, z, placedBlock.getBlock());
                        continue;
                    }

                    if (block == Material.OAK_LEAVES.getId() || block == Material.OAK_LOG.getId()) {
                        continue;
                    }

                    worldY = y + chunk.getPosition().y * Chunk.SIZE;

                    regionX = (int) Math.floor(worldX / (double) (Chunk.SIZE * Region.SIZE));
                    regionY = (int) Math.floor(worldY / (double) (Chunk.SIZE * Region.SIZE));
                    regionZ = (int) Math.floor(worldZ / (double) (Chunk.SIZE * Region.SIZE));
                    Region chunkRegion = world.getRegion(regionX, regionY, regionZ);

                    if (chunkRegion != null && chunkRegion.getStructure().getStructureMap().containsKey(coordinates)) {
                        chunk.setBlock(x, y, z, chunkRegion.getStructure().getStructureMap().get(coordinates));
                        continue;
                    }

                    Material material = Material.AIR;
                    if (worldY < worldHeight) {
                        if (worldY < worldHeight - 3) {
                            material = Material.STONE;
                        } else {
                            material = currentBiome.getSecondBlock();
                        }
                    } else if (worldY == worldHeight) {
                        material = currentBiome.getUpperBlock();
                    } else {
                        if (worldY <= WATER_LEVEL) {
                            material = Material.WATER;
                        }
                    }

                    if (material == Material.AIR) continue;

                    chunk.setBlock(x, y, z, material.getId());
                }
            }
        }

        cavesGenerator.generateCaves(world, chunk);
    }
}
