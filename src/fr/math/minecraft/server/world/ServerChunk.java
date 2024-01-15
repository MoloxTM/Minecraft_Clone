package fr.math.minecraft.server.world;

import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.server.world.generator.OverworldGenerator;
import org.joml.Vector3i;

public class ServerChunk {

    private final Vector3i position;
    private final int[] blocks;

    public final static int SIZE = 16;
    public final static int AREA = SIZE * SIZE;

    public final static int VOLUME = SIZE * AREA;
    public ServerChunk(int x, int y, int z) {
        this.position = new Vector3i(x, y, z);
        this.blocks = new int[VOLUME];
        for (int blockX = 0; blockX < ServerChunk.SIZE; blockX++) {
            for (int blockY = 0; blockY < ServerChunk.SIZE; blockY++) {
                for (int blockZ = 0; blockZ < ServerChunk.SIZE; blockZ++) {
                    this.setBlock(blockX, blockY, blockZ, Material.AIR.getId());
                }
            }
        }
        this.load();
    }

    public void load() {
        new OverworldGenerator().generate(this);
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

}
