package fr.math.minecraft.shared.world;

import fr.math.minecraft.shared.world.generator.OverworldGenerator;
import fr.math.minecraft.shared.world.generator.TerrainGenerator;
import org.joml.Vector3i;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private final HashMap<Coordinates, Chunk> chunks;
    private final ConcurrentHashMap<Coordinates, Chunk> pendingChunks;
    private final Set<Coordinates> loadingChunks;
    private final ArrayList<Byte> transparents;
    private final Set<Byte> solidBlocks;

    private final Map<Coordinates, Region> regions;

    private TerrainGenerator terrainGenerator;

    public World() {
        this.chunks = new HashMap<>();
        this.regions = new HashMap<>();
        this.pendingChunks = new ConcurrentHashMap<>();
        this.loadingChunks = new HashSet<>();
        this.solidBlocks = new HashSet<>();
        this.transparents = initTransparents();
        this.terrainGenerator = new OverworldGenerator();


        for (Material material : Material.values()) {
            if (material.isSolid()) {
                solidBlocks.add(material.getId());
            }
        }
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

    public synchronized void addChunk(Chunk chunk) {
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

        if (chunk == null) {
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

    public TerrainGenerator getTerrainGenerator() {
        return terrainGenerator;
    }

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
}
