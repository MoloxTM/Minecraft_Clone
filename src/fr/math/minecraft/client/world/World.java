package fr.math.minecraft.client.world;

import java.util.ArrayList;

public class World {

    private final ArrayList<Chunk> chunks;
    public final static int WIDTH = 10;
    public final static int HEIGHT = 10;
    public final static int DEPTH = 10;

    public World() {
        this.chunks = new ArrayList<>();
        this.buildChunks();
    }

    private void buildChunks() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int z = 0; z < DEPTH; z++) {
                    chunks.add(new Chunk(x, y, z));
                }
            }
        }
    }

    public ArrayList<Chunk> getChunks() {
        return chunks;
    }
}
