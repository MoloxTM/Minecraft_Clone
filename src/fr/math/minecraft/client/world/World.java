package fr.math.minecraft.client.world;

import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private final HashMap<Coordinates, Chunk> chunks;
    private final ConcurrentHashMap<Coordinates, Chunk> pendingChunks;
    private final Set<Coordinates> loadingChunks;
    private final ArrayList<Byte> transparents;

    public World() {
        this.chunks = new HashMap<>();
        this.pendingChunks = new ConcurrentHashMap<>();
        this.loadingChunks = new HashSet<>();
        this.transparents = initTransparents();
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

    public Set<Coordinates> getLoadingChunks() {
        return loadingChunks;
    }
    
    public ArrayList<Byte> initTransparents() {
        ArrayList<Byte> transparent = new ArrayList<>();
        transparent.add(Material.AIR.getId());
        transparent.add(Material.OAK_LEAVES.getId());
        transparent.add(Material.WEED.getId());
        transparent.add(Material.ROSE.getId());
        return transparent;
    }

    public ArrayList<Byte> getTransparents() {
        return transparents;
    }

    public ConcurrentHashMap<Coordinates, Chunk> getPendingChunks() {
        return pendingChunks;
    }
}
