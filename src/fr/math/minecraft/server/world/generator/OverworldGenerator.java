package fr.math.minecraft.server.world.generator;

import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.server.manager.BiomeManager;
import fr.math.minecraft.server.math.InterpolateMath;
import fr.math.minecraft.server.world.biome.AbstractBiome;
import fr.math.minecraft.server.world.biome.DesertBiome;
import fr.math.minecraft.server.world.biome.ForestBiome;
import fr.math.minecraft.server.world.biome.PlainBiome;
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

        int worldX = chunkX * ServerChunk.SIZE;
        int worldZ = chunkZ * ServerChunk.SIZE;


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

        int chunkX = ((int) Math.floor(worldX / (double) Chunk.SIZE)) * ServerChunk.SIZE;
        int chunkZ = ((int) Math.floor(worldZ / (double) Chunk.SIZE)) * ServerChunk.SIZE;

        int xMin = 0, zMin = 0;
        int xMax = ServerChunk.SIZE - 1, zMax = ServerChunk.SIZE - 1;

        float bottomLeft = this.calculBiomeHeight(chunkX + xMin, chunkZ + zMin);
        float bottomRight =  this.calculBiomeHeight(chunkX + xMax, chunkZ + zMin);
        float topLeft =  this.calculBiomeHeight(chunkX + xMin, chunkZ + zMax);
        float topRight =  this.calculBiomeHeight(chunkX + xMax, chunkZ + zMax);

        int worldHeight = (int) (InterpolateMath.smoothInterpolation(bottomLeft, bottomRight, topLeft, topRight, xMin, xMax, zMin, zMax, worldX %( ServerChunk.SIZE), worldZ % ( ServerChunk.SIZE)));

        return worldHeight;
    }

    @Override
    public void generateChunk(ServerChunk chunk) {

        MinecraftServer minecraftServer = MinecraftServer.getInstance();

        this.fillHeightMap(chunk.getPosition().x, chunk.getPosition().z, 0, ServerChunk.SIZE - 1, 0, ServerChunk.SIZE - 1);
        for (int x = 0; x < ServerChunk.SIZE; x++) {
            for (int z = 0; z < ServerChunk.SIZE; z++) {

                BiomeManager biomeManager = new BiomeManager();
                AbstractBiome currentBiome = biomeManager.getBiome(x+chunk.getPosition().x*ServerChunk.SIZE,z+chunk.getPosition().z*ServerChunk.SIZE);
                chunk.setBiome(currentBiome);

                int worldHeight = heightMap.get(new Vector2i(x, z));

                for (int y = 0; y < ServerChunk.SIZE; y++) {
                    if (chunk.getBlock(x, y, z) == Material.OAK_LEAVES.getId() || chunk.getBlock(x, y, z) == Material.OAK_LOG.getId()) {
                        continue;
                    }

                    int worldX = x + chunk.getPosition().x * ServerChunk.SIZE;
                    int worldY = y + chunk.getPosition().y * ServerChunk.SIZE;
                    int worldZ = z + chunk.getPosition().z * ServerChunk.SIZE;

                    int regionX = (int) Math.floor(worldX / (double) (ServerChunk.SIZE * Region.SIZE));
                    int regionY = (int) Math.floor(worldY / (double) (ServerChunk.SIZE * Region.SIZE));
                    int regionZ = (int) Math.floor(worldZ / (double) (ServerChunk.SIZE * Region.SIZE));

                    Region chunkRegion = minecraftServer.getWorld().getRegion(regionX, regionY, regionZ);

                    Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
                    if(chunkRegion != null && chunkRegion.getStructure().getStructureMap().containsKey(coordinates)) {
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

                        /*
                        if(currentBiome instanceof ForestBiome){
                            //currentBiome.buildTree(chunk, x, y, z, structure);
                        } else if(currentBiome instanceof PlainBiome) {
                            if((y + 1) < ServerChunk.SIZE) //currentBiome.buildWeeds(chunk, x, y, z, structure);
                            if(((x - 2) >= 0) && ((x + 2) <= 15) && ((z - 2) >= 0) && ((z + 2) <= 15) && ((y + 8) < ServerChunk.SIZE)){
                                //currentBiome.buildTree(chunk, x, y, z, structure);
                            }
                        }else if(currentBiome instanceof DesertBiome && ((x - 2) >= 0) && ((x + 2) <= 15) && ((z - 2) >= 0) && ((z + 2) <= 15) && ((y + 4) < ServerChunk.SIZE)){
                            if((y + 1) < ServerChunk.SIZE){
                                //currentBiome.buildWeeds(chunk, x, y, z, structure);
                                //currentBiome.buildTree(chunk, x, y, z, structure);
                            }
                        }
                        */
                    } else {
                        if (worldY <= 40) {
                            material = Material.WATER;
                        }
                    }
                    if(material == Material.AIR) continue;

                    chunk.setBlock(x, y, z, material.getId());
                }
            }
        }
    }

    @Override
    public void generateStructure(ServerChunk chunk) {
        for (int x = 0; x < ServerChunk.SIZE; x++) {
            for (int y = 0; y < ServerChunk.SIZE; y++) {
                for (int z = 0; z < ServerChunk.SIZE; z++) {
                    int worldX = x + chunk.getPosition().x * ServerChunk.SIZE;
                    int worldY = y + chunk.getPosition().y * ServerChunk.SIZE;
                    int worldZ = z + chunk.getPosition().z * ServerChunk.SIZE;

                    Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
                }
            }
        }

    }

    @Override
    public void placeStruture(ServerChunk chunk) {
    }
}
