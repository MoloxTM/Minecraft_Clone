package fr.math.minecraft.client.world;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private final HashMap<Coordinates, Chunk> chunks;
    private final ConcurrentHashMap<Coordinates, Chunk> pendingChunks;
    private final Set<Coordinates> loadingChunks;
    private final ArrayList<Byte> transparents;
    private final Set<Byte> solidBlocks;

    public World() {
        this.chunks = new HashMap<>();
        this.pendingChunks = new ConcurrentHashMap<>();
        this.loadingChunks = new HashSet<>();
        this.solidBlocks = new HashSet<>();
        this.transparents = initTransparents();

        for (Material material : Material.values()) {
            if (material.isSolid()) {
                System.out.println("j'ajoute " + material);
                solidBlocks.add(material.getId());
            }
        }
        System.out.println(solidBlocks);
    }

    public HashMap<Coordinates, Chunk> getChunks() {
        return chunks;
    }

    public void removeChunk(Chunk chunk) {
        Coordinates coordinates = new Coordinates(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z);
        synchronized (this.getChunks()) {
            chunks.remove(coordinates);
        }
    }

    public void addChunk(Chunk chunk) {
        chunks.put(new Coordinates(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z), chunk);
    }

    public void addPendingChunk(Chunk chunk) {
        synchronized (this.getPendingChunks()) {
            pendingChunks.put(new Coordinates(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z), chunk);
        }
    }

    public Chunk getChunk(int x, int y, int z) {
        Coordinates coordinates = new Coordinates(x, y, z);
        return chunks.get(coordinates);
    }

    public Chunk getChunkAt(int worldX, int worldY, int  worldZ) {
        int chunkX = (int) Math.floor(worldX / (double) Chunk.SIZE);
        int chunkY = (int) Math.floor(worldY / (double) Chunk.SIZE);
        int chunkZ = (int) Math.floor(worldZ / (double) Chunk.SIZE);

        return this.getChunk(chunkX, chunkY, chunkZ);
    }

    public byte getBlockAt(int worldX, int worldY, int worldZ) {
        //Déterminer le chunck
        Chunk chunk = getChunkAt(worldX, worldY, worldZ);
        if(chunk == null) {
            return Material.AIR.getId();
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

    public Set<Coordinates> getLoadingChunks() {
        return loadingChunks;
    }
    
    public ArrayList<Byte> initTransparents() {
        ArrayList<Byte> transparent = new ArrayList<>();
        transparent.add(Material.AIR.getId());
        transparent.add(Material.OAK_LEAVES.getId());
        transparent.add(Material.WEED.getId());
        transparent.add(Material.ROSE.getId());
        transparent.add(Material.CACTUS.getId());
        transparent.add(Material.DEAD_BUSH.getId());
        return transparent;
    }

    public ArrayList<Byte> getTransparents() {
        return transparents;
    }

    public ConcurrentHashMap<Coordinates, Chunk> getPendingChunks() {
        return pendingChunks;
    }

    public Set<Byte> getSolidBlocks() {
        return solidBlocks;
    }
}
