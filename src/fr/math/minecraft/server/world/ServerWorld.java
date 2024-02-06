package fr.math.minecraft.server.world;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.GameConfiguration;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.server.world.generator.OverworldGenerator;
import fr.math.minecraft.server.world.generator.TerrainGenerator;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerWorld {

    private final HashMap<Coordinates, ServerChunk> chunks;
    public final static int WIDTH = 10;
    public final static int HEIGHT = 10;
    public final static int DEPTH = 10;
    private final int seed;
    private TerrainGenerator overworldGenerator;

    public ServerWorld() {
        this.chunks = new HashMap<>();
        this.seed = 0;
        this.overworldGenerator = new OverworldGenerator();
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
        int chunkX = (int) Math.floor(worldX / (double) Chunk.SIZE);
        int chunkY = (int) Math.floor(worldY / (double) Chunk.SIZE);
        int chunkZ = (int) Math.floor(worldZ / (double) Chunk.SIZE);

        return this.getChunk(chunkX, chunkY, chunkZ);
    }
    public byte getBlockAt(int worldX, int worldY, int  worldZ) {
        //Déterminer le chunck
        ServerChunk chunk = getChunkAt(worldX, worldY, worldZ);
        if(chunk == null) {
            chunk = new ServerChunk(worldX / ServerChunk.SIZE, worldY / ServerChunk.SIZE, worldZ / ServerChunk.SIZE);
            this.addChunk(chunk);
        }
        //Chopper les coos du block
        int blockX = worldX % Chunk.SIZE;
        int blockY = worldY % Chunk.SIZE;
        int blockZ = worldZ % Chunk.SIZE;

        blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
        blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
        blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

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

    public int getSeed() {
        return seed;
    }

    public TerrainGenerator getOverworldGenerator() {
        return overworldGenerator;
    }
}
