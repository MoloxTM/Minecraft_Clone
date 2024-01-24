package fr.math.minecraft.client.world;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.meshs.ChunkMesh;
import org.joml.Vector3i;

public class Chunk {

    private final Vector3i position;
    private final byte[] blocks;
    private boolean empty;

    public final static int SIZE = 16;
    public final static int AREA = SIZE * SIZE;

    public final static int VOLUME = SIZE * AREA;
    private ChunkMesh chunkMesh;

    public Chunk(int x, int y, int z) {
        this.position = new Vector3i(x, y, z);
        this.blocks = new byte[VOLUME];
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

    public Chunk(JsonNode chunkData) {
        this.position = new Vector3i(chunkData.get("x").asInt(), chunkData.get("y").asInt(), chunkData.get("z").asInt());
        this.blocks = new byte[VOLUME];

        JsonNode dataBlocks = chunkData.get("blocks");

        for (int i = 0; i < dataBlocks.size(); i++) {
            JsonNode blockNode = dataBlocks.get(i);
            byte block =(byte) blockNode.asInt();
            blocks[i] = block;
        }
    }

    public byte[] getBlocks() {
        return blocks;
    }

    public void setBlock(int x, int y, int z, byte block) {
        blocks[x + y * AREA + z * SIZE] = block;
    }

    public byte getBlock(int x, int y, int z) {
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
        for (byte block : blocks) {
            if (block != Material.AIR.getId()) {
                count++;
            }
        }
        return count;
    }
}
