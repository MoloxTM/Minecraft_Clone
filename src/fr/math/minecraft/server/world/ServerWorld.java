package fr.math.minecraft.server.world;

import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.Material;
import fr.math.minecraft.server.world.generator.OverworldGenerator;
import fr.math.minecraft.server.world.generator.TerrainGenerator;
import org.joml.Vector3i;

import java.util.*;

public class ServerWorld {

    private final HashMap<Coordinates, ServerChunk> chunks;
    private final Map<Coordinates, Region> regions;
    public final static int WIDTH = 10;
    public final static int HEIGHT = 10;
    public final static int DEPTH = 10;
    private final int seed;
    private TerrainGenerator overworldGenerator;
    private final Set<Byte> solidBlocks;

    public ServerWorld() {
        this.chunks = new HashMap<>();
        this.regions = new HashMap<>();
        this.solidBlocks = new HashSet<>();
        this.seed = 0;
        this.overworldGenerator = new OverworldGenerator();

        for (Material material : Material.values()) {
            if (material.isSolid()) {
                solidBlocks.add(material.getId());
            }
        }
    }

    public void buildChunks() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int z = 0; z < DEPTH; z++) {
                    Coordinates coordinates = new Coordinates(x, y, z);
                    chunks.put(coordinates, new ServerChunk(x, y, z));
                }
            }
        }
        System.out.println("Monde généré avec succès");
    }

    public HashMap<Coordinates, ServerChunk> getChunks() {
        return chunks;
    }

    public void addChunk(ServerChunk chunk) {
        Coordinates coordinates = new Coordinates(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z);
        this.chunks.put(coordinates, chunk);
    }

    public ServerChunk getChunk(int x, int y, int z) {
        Coordinates coordinates = new Coordinates(x, y, z);
        return chunks.getOrDefault(coordinates, null);
    }

    public ServerChunk getChunkAt(int worldX, int worldY, int  worldZ) {
        int chunkX = (int) Math.floor(worldX / (double) ServerChunk.SIZE);
        int chunkY = (int) Math.floor(worldY / (double) ServerChunk.SIZE);
        int chunkZ = (int) Math.floor(worldZ / (double) ServerChunk.SIZE);

        return this.getChunk(chunkX, chunkY, chunkZ);
    }
    public byte getBlockAt(int worldX, int worldY, int  worldZ) {
        //Déterminer le chunck
        ServerChunk chunk = getChunkAt(worldX, worldY, worldZ);
        if (chunk == null) {
            int chunkX = (int) Math.floor(worldX / (double) ServerChunk.SIZE);
            int chunkY = (int) Math.floor(worldY / (double) ServerChunk.SIZE);
            int chunkZ = (int) Math.floor(worldZ / (double) ServerChunk.SIZE);

            chunk = new ServerChunk(chunkX, chunkY, chunkZ);
            this.addChunk(chunk);
        }
        //Chopper les coos du block
        int blockX = worldX % Chunk.SIZE;
        int blockY = worldY % Chunk.SIZE;
        int blockZ = worldZ % Chunk.SIZE;

        blockX = blockX < 0 ? blockX + ServerChunk.SIZE : blockX;
        blockY = blockY < 0 ? blockY + ServerChunk.SIZE : blockY;
        blockZ = blockZ < 0 ? blockZ + ServerChunk.SIZE : blockZ;

        return chunk.getBlock(blockX, blockY, blockZ);
    }

    public void setBlock(int worldX, int worldY, int worldZ, byte block) {
        ServerChunk serverChunk = getChunkAt(worldX, worldY, worldZ);
        if(serverChunk == null) return;
        int blockX = worldX % Chunk.SIZE;
        int blockY = worldY % Chunk.SIZE;
        int blockZ = worldZ % Chunk.SIZE;

        blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
        blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
        blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

        serverChunk.setBlock(blockX, blockY, blockZ, block);
    }

    /*
    public void updateStructure() {
        Set<Coordinates> coordsToRemove = new HashSet<>();
        for(Map.Entry<Coordinates, Byte> structureSet : overworldGenerator.getStructure().getStructureMap().entrySet()) {

            int worldX = structureSet.getKey().getX();
            int worldY = structureSet.getKey().getY();
            int worldZ = structureSet.getKey().getZ();

            int chunkX = (int) Math.floor(worldX / (double) Chunk.SIZE);
            int chunkY = (int) Math.floor(worldY / (double) Chunk.SIZE);
            int chunkZ = (int) Math.floor(worldZ / (double) Chunk.SIZE);

            ServerChunk chunk = this.getChunkAt(worldX, worldY, worldZ);

            if (chunk == null) {
                chunk = new ServerChunk(chunkX, chunkY, chunkZ);
                this.addChunk(chunk);
            }

            int blockX = worldX % Chunk.SIZE;
            int blockY = worldY % Chunk.SIZE;
            int blockZ = worldZ % Chunk.SIZE;

            blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
            blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
            blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;
            chunk.setBlock(blockX, blockY, blockZ, structureSet.getValue());

            coordsToRemove.add(structureSet.getKey());
        }

        for (Coordinates coordinates : coordsToRemove) {
            overworldGenerator.getStructure().getStructureMap().remove(coordinates);
        }
    }
    */

    public void addRegion(int regionX, int regionY, int regionZ) {
        Region region = new Region(new Vector3i(regionX, regionY, regionZ));
        this.regions.put(new Coordinates(regionX, regionY, regionZ), region);
    }

    public void addRegion(Region region) {
        this.regions.put(new Coordinates(region.getPosition().x, region.getPosition().y, region.getPosition().z), region);
    }

    public Region getRegion(int x, int y, int z) {
        Coordinates coordinates = new Coordinates(x, y, z);
        return regions.get(coordinates);
    }

    public Region getRegion(Coordinates coordinates) {
        return regions.get(coordinates);
    }
    public Map<Coordinates, Region> getRegions() {
        return regions;
    }

    public int getSeed() {
        return seed;
    }

    public Set<Byte> getSolidBlocks() {
        return solidBlocks;
    }

    public TerrainGenerator getOverworldGenerator() {
        return overworldGenerator;
    }
}
