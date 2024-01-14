package fr.math.minecraft.client.world;

import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class World {

    private final HashMap<Coordinates, Chunk> chunks;
    public final static int WIDTH = 10;
    public final static int HEIGHT = 10;
    public final static int DEPTH = 10;

    public World() {
        this.chunks = new HashMap<>();
        this.buildChunks();
    }

    public void buildChunks() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int z = 0; z < DEPTH; z++) {
                    Coordinates coordinates = new Coordinates(x, y, z);
                    chunks.put(coordinates, new Chunk(x, y, z));
                }
            }
        }
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int z = 0; z < DEPTH; z++) {
                    Chunk chunk = this.getChunk(x, y, z);
                    chunk.setChunkMesh(new ChunkMesh(chunk));
                }
            }
        }
        System.out.println(getChunk(0, 0, 0));
    }

    public HashMap<Coordinates, Chunk> getChunks() {
        return chunks;
    }

    public Chunk getChunk(int x, int y, int z) {
        Coordinates coordinates = new Coordinates(x, y, z);
        if(!chunks.containsKey(coordinates)) return null;
        return chunks.get(coordinates);
    }
}
