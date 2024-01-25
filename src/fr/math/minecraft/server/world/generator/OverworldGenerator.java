package fr.math.minecraft.server.world.generator;

import fr.math.minecraft.server.manager.BiomeManager;
import fr.math.minecraft.server.math.InterpolateMath;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.biome.AbstractBiome;
import fr.math.minecraft.server.world.biome.DesertBiome;
import fr.math.minecraft.server.world.biome.PlainBiome;
import org.joml.Vector2i;

import java.util.HashMap;

public class OverworldGenerator implements TerrainGenerator {

    private final NoiseGenerator noise;
    private final HashMap<Vector2i, Integer> heightMap;


    public OverworldGenerator() {
        this.noise = new NoiseGenerator(9, 30, 800.0f, .7f, 25);
        this.heightMap = new HashMap<>();

    }

    public void fillHeightMap(ServerChunk chunk, int xMin, int xMax, int zMin, int zMax) {

        int worldX = chunk.getPosition().x * ServerChunk.SIZE;
        int worldZ = chunk.getPosition().z * ServerChunk.SIZE;

        float bottomLeft = noise.getHeight(worldX + xMin, worldZ + zMin);
        float bottomRight = noise.getHeight(worldX + xMax, worldZ + zMin);
        float topLeft = noise.getHeight(worldX + xMin, worldZ + zMax);
        float topRight = noise.getHeight(worldX + xMax, worldZ + zMax);

        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {

                int worldHeight = (int) (InterpolateMath.smoothInterpolation(bottomLeft, bottomRight, topLeft, topRight, xMin, xMax, zMin, zMax, x, z));

                heightMap.put(new Vector2i(x, z), worldHeight);
            }
        }
    }

    @Override
    public void generateChunk(ServerChunk chunk) {
        this.fillHeightMap(chunk, 0, ServerChunk.SIZE - 1, 0, ServerChunk.SIZE - 1);
        for (int x = 0; x < ServerChunk.SIZE; x++) {
            for (int z = 0; z < ServerChunk.SIZE; z++) {
                BiomeManager biomeManager = new BiomeManager();
                AbstractBiome currentBiome = biomeManager.getBiome(x+chunk.getPosition().x*ServerChunk.SIZE,z+chunk.getPosition().z*ServerChunk.SIZE);

                int worldHeight = heightMap.get(new Vector2i(x, z));

                for (int y = 0; y < ServerChunk.SIZE; y++) {
                    int worldY = y + chunk.getPosition().y * ServerChunk.SIZE;
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
                        if (worldY <= 24) {
                            material = Material.WATER;
                        }
                    }
                    chunk.setBlock(x, y, z, material.getId());
                }
            }
        }
    }

    @Override
    public void generateStructure(ServerChunk chunk) {
        int yTree = -1;

        for (int x = 0; x < ServerChunk.SIZE; x++) {
            for (int z = 0; z < ServerChunk.SIZE; z++) {
                BiomeManager biomeManager = new BiomeManager();
                AbstractBiome currentBiome = biomeManager.getBiome(x+chunk.getPosition().x*ServerChunk.SIZE,z+chunk.getPosition().z*ServerChunk.SIZE);
                int worldHeight = heightMap.get(new Vector2i(x, z));
                for (int y = 0; y < ServerChunk.SIZE; y++) {
                    int worldY = y + chunk.getPosition().y * ServerChunk.SIZE;
                    Material material = Material.AIR;
                    if(x == 7 && z == 7 && worldY == worldHeight && (y + 8) < ServerChunk.SIZE) {
                        yTree = y;
                    }
                }

                if(currentBiome instanceof PlainBiome && yTree != -1) {
                    currentBiome.buildTree(chunk, 7, yTree, 7);
                } else if(currentBiome instanceof DesertBiome && yTree != -1) {
                    currentBiome.buildTree(chunk, 7, yTree, 7);
                }
            }
        }
    }

}
