package fr.math.minecraft.shared.world.generator;

import fr.math.minecraft.server.world.biome.DesertBiome;
import fr.math.minecraft.shared.world.*;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.server.manager.BiomeManager;
import fr.math.minecraft.server.math.InterpolateMath;
import fr.math.minecraft.server.world.biome.AbstractBiome;
import org.joml.Vector2i;

import java.util.HashMap;

public class OverworldGenerator implements TerrainGenerator {
    private final HashMap<Vector2i, Integer> heightMap;

    public OverworldGenerator() {
        this.heightMap = new HashMap<>();
    }

    public float calculBiomeHeight(int worldX, int worldZ) {
        BiomeManager biomeManager = new BiomeManager();
        AbstractBiome currentBiome = biomeManager.getBiome(worldX, worldZ);
        float height = currentBiome.getHeight(worldX, worldZ);
        return height;
    }

    public void fillHeightMap(int chunkX, int chunkZ, int xMin, int xMax, int zMin, int zMax) {

        int worldX = chunkX * Chunk.SIZE;
        int worldZ = chunkZ * Chunk.SIZE;

        float bottomLeft = this.calculBiomeHeight(worldX + xMin, worldZ + zMin);
        float bottomRight =  this.calculBiomeHeight(worldX + xMax, worldZ + zMin);
        float topLeft =  this.calculBiomeHeight(worldX + xMin, worldZ + zMax);
        float topRight =  this.calculBiomeHeight(worldX + xMax, worldZ + zMax);

        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {
                int worldHeight = (int) (InterpolateMath.smoothInterpolation(bottomLeft, bottomRight, topLeft, topRight, xMin, xMax, zMin, zMax, x, z));
                heightMap.put(new Vector2i(x, z), worldHeight);
            }
        }
    }

    public int getHeight(int worldX, int worldZ) {

        int chunkX = ((int) Math.floor(worldX / (double) Chunk.SIZE)) * Chunk.SIZE;
        int chunkZ = ((int) Math.floor(worldZ / (double) Chunk.SIZE)) * Chunk.SIZE;

        int xMin = 0, zMin = 0;
        int xMax = Chunk.SIZE - 1, zMax = Chunk.SIZE - 1;

        float bottomLeft = this.calculBiomeHeight(chunkX + xMin, chunkZ + zMin);
        float bottomRight =  this.calculBiomeHeight(chunkX + xMax, chunkZ + zMin);
        float topLeft =  this.calculBiomeHeight(chunkX + xMin, chunkZ + zMax);
        float topRight =  this.calculBiomeHeight(chunkX + xMax, chunkZ + zMax);

        int worldHeight = (int) (InterpolateMath.smoothInterpolation(bottomLeft, bottomRight, topLeft, topRight, xMin, xMax, zMin, zMax, worldX % Chunk.SIZE, worldZ % Chunk.SIZE));

        return worldHeight;
    }

    @Override
    public void generateChunk(World world, Chunk chunk) {

        this.fillHeightMap(chunk.getPosition().x, chunk.getPosition().z, 0, Chunk.SIZE - 1, 0, Chunk.SIZE - 1);
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int z = 0; z < Chunk.SIZE; z++) {

                BiomeManager biomeManager = new BiomeManager();

                int worldX = x + chunk.getPosition().x * Chunk.SIZE;
                int worldZ = z + chunk.getPosition().z * Chunk.SIZE;

                AbstractBiome currentBiome = biomeManager.getBiome(worldX, worldZ);
                chunk.setBiome(currentBiome);

                int worldHeight = heightMap.get(new Vector2i(x, z));

                for (int y = 0; y < Chunk.SIZE; y++) {
                    if (chunk.getBlock(x, y, z) == Material.OAK_LEAVES.getId() || chunk.getBlock(x, y, z) == Material.OAK_LOG.getId()) {
                        continue;
                    }

                    int worldY = y + chunk.getPosition().y * Chunk.SIZE;

                    int regionX = (int) Math.floor(worldX / (double) (Chunk.SIZE * Region.SIZE));
                    int regionY = (int) Math.floor(worldY / (double) (Chunk.SIZE * Region.SIZE));
                    int regionZ = (int) Math.floor(worldZ / (double) (Chunk.SIZE * Region.SIZE));

                    Region chunkRegion = world.getRegion(regionX, regionY, regionZ);

                    Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
                    if (chunkRegion != null && chunkRegion.getStructure().getStructureMap().containsKey(coordinates)) {
                        if (chunk.isEmpty()) {
                            chunk.setEmpty(false);
                        }
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
                        if (worldY <= 43) {
                            material = Material.WATER;
                        }
                    }

                    if (material == Material.AIR) continue;

                    if (chunk.isEmpty()) {
                        chunk.setEmpty(false);
                    }

                    chunk.setBlock(x, y, z, material.getId());
                }
            }
        }
    }
}
