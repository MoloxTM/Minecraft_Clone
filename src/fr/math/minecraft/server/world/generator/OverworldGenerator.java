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

    private Structure structure;
    public OverworldGenerator() {
        this.heightMap = new HashMap<>();
        this.structure = new Structure();
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
                chunk.setBiome(currentBiome);

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

                        if(currentBiome instanceof ForestBiome  && ((x + 2) <= 15) && ((z - 2) >= 0) && ((z + 2) <= 15) && ((y + 8) < ServerChunk.SIZE)){
                            currentBiome.buildTree(chunk, x, y, z, structure);
                        } else if(currentBiome instanceof PlainBiome) {
                            if((y + 1) < ServerChunk.SIZE) currentBiome.buildWeeds(chunk, x, y, z, structure);
                            if(((x - 2) >= 0) && ((x + 2) <= 15) && ((z - 2) >= 0) && ((z + 2) <= 15) && ((y + 8) < ServerChunk.SIZE)){
                                currentBiome.buildTree(chunk, x, y, z, structure);
                            }
                        }else if(currentBiome instanceof DesertBiome && ((x - 2) >= 0) && ((x + 2) <= 15) && ((z - 2) >= 0) && ((z + 2) <= 15) && ((y + 4) < ServerChunk.SIZE)){
                            if((y + 1) < ServerChunk.SIZE){
                                currentBiome.buildWeeds(chunk, x, y, z, structure);
                                currentBiome.buildTree(chunk, x, y, z, structure);
                            }
                        }
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
        HashMap<Coordinates, Byte> structMap = this.getStructure().getStructureMap();
        //System.out.println("\nCoos Chunk:" + chunk.getPosition().toString());
        for (Coordinates coordinates : structMap.keySet()) {

            if (Utils.isInChunk(coordinates, chunk)) {
                //System.out.println("Coos bloc:" + coordinates + " | " + "coos chunk:" + chunk.getPosition());
                int worldX = coordinates.getX();
                int worldY = coordinates.getY();
                int worldZ = coordinates.getZ();

                byte block = structMap.get(coordinates);

                int blockX = worldX % Chunk.SIZE;
                int blockY = worldY % Chunk.SIZE;
                int blockZ = worldZ % Chunk.SIZE;

                blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
                blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
                blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

                //System.out.println("Je place un block de " + fr.math.minecraft.client.world.Material.getMaterialById(block));

                chunk.setBlock(blockX, blockY, blockZ, block);
            }
        }
    }

    @Override
    public void placeStruture(ServerChunk chunk) {
    }

    public Structure getStructure() {
        return structure;
    }
}
