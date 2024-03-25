package fr.math.minecraft.shared.world;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.manager.ChunkManager;
import org.joml.Vector3i;

import java.util.Objects;

public class BreakedBlock {

    private final Vector3i position;
    private final byte block;

    public BreakedBlock(Vector3i position, byte block) {
        this.position = position;
        this.block = block;
    }

    public BreakedBlock(JsonNode data) {
        int worldX = data.get("wx").asInt();
        int worldY = data.get("wy").asInt();
        int worldZ = data.get("wz").asInt();
        byte block = (byte) data.get("block").asInt();
        this.position = new Vector3i(worldX, worldY, worldZ);
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

    public ObjectNode toJSONObject() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode blockNode = mapper.createObjectNode();

        blockNode.put("wx", position.x);
        blockNode.put("wy", position.y);
        blockNode.put("wz", position.z);
        blockNode.put("block", block);

        return blockNode;
    }

}
