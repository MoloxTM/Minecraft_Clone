package fr.math.minecraft.client.world;

import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class World {

    private final HashMap<Coordinates, Chunk> chunks;

    public World() {
        this.chunks = new HashMap<>();
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

    public Chunk getChunk(int x, int y, int z) {
        Coordinates coordinates = new Coordinates(x, y, z);
        if (!chunks.containsKey(coordinates)) {
            return null;
        }
        return chunks.get(coordinates);
    }

}
