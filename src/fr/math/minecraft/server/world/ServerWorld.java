package fr.math.minecraft.server.world;

import fr.math.minecraft.client.meshs.ChunkMesh;

import java.util.HashMap;

public class ServerWorld {

    private final HashMap<Coordinates, ServerChunk> chunks;
    public final static int WIDTH = 10;
    public final static int HEIGHT = 10;
    public final static int DEPTH = 10;

    public ServerWorld() {
        this.chunks = new HashMap<>();
        this.buildChunks();
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
        System.out.println(getChunk(0, 0, 0));
    }

    public HashMap<Coordinates, ServerChunk> getChunks() {
        return chunks;
    }

    public ServerChunk getChunk(int x, int y, int z) {
        Coordinates coordinates = new Coordinates(x, y, z);
        if(!chunks.containsKey(coordinates)) return null;
        return chunks.get(coordinates);
    }
}
