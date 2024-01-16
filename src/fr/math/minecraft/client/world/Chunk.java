package fr.math.minecraft.client.world;

import fr.math.minecraft.client.meshs.ChunkMesh;
import org.joml.Vector3i;

public class Chunk {

    private final Vector3i position;
    private final int[] blocks;
    private boolean empty;

    public final static int SIZE = 16;
    public final static int AREA = SIZE * SIZE;

    public final static int VOLUME = SIZE * AREA;
    private ChunkMesh chunkMesh;

    public Chunk(int x, int y, int z) {
        this.position = new Vector3i(x, y, z);
        this.blocks = new int[VOLUME];
        for (int blockX = 0; blockX < Chunk.SIZE; blockX++) {
            for (int blockY = 0; blockY < Chunk.SIZE; blockY++) {
                for (int blockZ = 0; blockZ < Chunk.SIZE; blockZ++) {
                    this.setBlock(blockX, blockY, blockZ, Material.AIR.getId());
                }
            }
        }
        this.empty = true;
        this.chunkMesh = null;
    }

    public int[] getBlocks() {
        return blocks;
    }

    public void setBlock(int x, int y, int z, int block) {
        blocks[x + y * AREA + z * SIZE] = block;
    }

    public int getBlock(int x, int y, int z) {
        return blocks[x + y * AREA + z * SIZE];
    }

    public Vector3i getPosition() {
        return position;
    }

    public ChunkMesh getChunkMesh() {
        return chunkMesh;
    }

    public void setChunkMesh(ChunkMesh chunkMesh) {
        this.chunkMesh = chunkMesh;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public int getBlocksSize() {
        int count = 0;
        for (int block : blocks) {
            if (block != Material.AIR.getId()) {
                count++;
            }
        }
        return count;
    }
}
