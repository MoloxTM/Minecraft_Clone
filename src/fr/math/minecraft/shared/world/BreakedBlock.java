package fr.math.minecraft.shared.world;

import fr.math.minecraft.client.manager.ChunkManager;
import fr.math.minecraft.shared.MathUtils;
import org.joml.Vector3i;

import java.util.Objects;

public class BreakedBlock {

    private final Vector3i position;
    private final byte block;

    public BreakedBlock(Vector3i position, byte block) {
        this.position = position;
        this.block = block;
    }

    public Vector3i getPosition() {
        return position;
    }

    public byte getBlock() {
        return block;
    }

    public void restore(World world) {
        ChunkManager chunkManager = new ChunkManager();
        Chunk chunk = world.getChunkAt(position);

        int blockX = position.x % Chunk.SIZE;
        int blockY = position.y % Chunk.SIZE;
        int blockZ = position.z % Chunk.SIZE;

        blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
        blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
        blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

        Vector3i localPosition = new Vector3i(blockX, blockY, blockZ);
        Material material = Material.getMaterialById(block);

        chunkManager.placeBlock(chunk, localPosition, world, material);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BreakedBlock that = (BreakedBlock) o;
        return block == that.block && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, block);
    }

    @Override
    public String toString() {
        return "BreakedBlock{" +
                "position=" + position +
                ", block=" + block +
                '}';
    }
}
