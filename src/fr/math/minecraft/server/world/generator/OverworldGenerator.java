package fr.math.minecraft.server.world.generator;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.world.ServerWorld;
import fr.math.minecraft.server.world.Structure;
import fr.math.minecraft.server.manager.BiomeManager;
import fr.math.minecraft.server.math.InterpolateMath;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.biome.AbstractBiome;
import fr.math.minecraft.server.world.biome.DesertBiome;
import fr.math.minecraft.server.world.biome.ForestBiome;
import fr.math.minecraft.server.world.biome.PlainBiome;
import org.joml.Vector2i;
import org.joml.Vector3i;

import java.util.HashMap;

public class OverworldGenerator implements TerrainGenerator {
    private final HashMap<Vector2i, Integer> heightMap;
    private final HashMap<Vector3i, Structure> structureMap;

    public OverworldGenerator() {
        this.heightMap = new HashMap<>();
        this.structureMap = new HashMap<>();
    }

    public float calculBiomeHeight(int worldX, int worldZ) {
        BiomeManager biomeManager = new BiomeManager();
        AbstractBiome currentBiome = biomeManager.getBiome(worldX, worldZ);
        float height = currentBiome.getHeight(worldX, worldZ);
        return height;
    }

    public void fillHeightMap(ServerChunk chunk, int xMin, int xMax, int zMin, int zMax) {

        int worldX = chunk.getPosition().x * ServerChunk.SIZE;
        int worldZ = chunk.getPosition().z * ServerChunk.SIZE;


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

    @Override
    public void generateChunk(ServerChunk chunk) {

        MinecraftServer minecraftServer = MinecraftServer.getInstance();

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

                        if(currentBiome instanceof ForestBiome && ((x - 2) >= 0) && ((x + 2) <= 15) && ((z - 2) >= 0) && ((z + 2) <= 15) && ((y + 8) < ServerChunk.SIZE)){
                            currentBiome.buildTree(chunk, x, y, z, minecraftServer.getWorld().getTrees());
                        } else if(currentBiome instanceof PlainBiome) {
                            currentBiome.buildWeeds(chunk, x, y, z);
                            if(((x - 2) >= 0) && ((x + 2) <= 15) && ((z - 2) >= 0) && ((z + 2) <= 15) && ((y + 8) < ServerChunk.SIZE)){
                                currentBiome.buildTree(chunk, x, y, z, minecraftServer.getWorld().getTrees());
                            }
                        }else if(currentBiome instanceof DesertBiome && ((x - 2) >= 0) && ((x + 2) <= 15) && ((z - 2) >= 0) && ((z + 2) <= 15) && ((y + 4) < ServerChunk.SIZE)){
                            currentBiome.buildTree(chunk, x, y, z, minecraftServer.getWorld().getTrees());
                        }

                    } else {
                        if (worldY <= 24) {
                            material = Material.WATER;
                        }
                    }
                    if(material == Material.AIR) continue;
                    chunk.setBlock(x, y, z, material.getId());
                }
            }
        }
    }



}
