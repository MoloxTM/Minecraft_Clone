package fr.math.minecraft.client.manager;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.builder.MeshBuilder;
import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.Coordinates;
import fr.math.minecraft.client.world.World;

public class ChunkManager {

    public void loadChunkData(JsonNode chunkData) {

        JsonNode blocks = chunkData.get("blocks");

        if (blocks == null) return;
        if (!blocks.isArray()) return;

        int x = chunkData.get("x").asInt();
        int y = chunkData.get("y").asInt();
        int z = chunkData.get("z").asInt();
        int biome = chunkData.get("biome").asInt();

        World world = Game.getInstance().getWorld();
        Chunk chunk = world.getChunk(x, y, z);

        if (chunk == null) {
            chunk = new Chunk(x, y, z);
        }

        for (int i = 0; i < blocks.size(); i++) {
            JsonNode blockNode = blocks.get(i);
            byte block =(byte) blockNode.asInt();
            chunk.getBlocks()[i] = block;
        }

        if (chunk.getBlocksSize() > 0) {
            chunk.setEmpty(false);
        }

        chunk.setBiome(biome);

        synchronized (world.getChunks()) {
            world.addChunk(chunk);
        }
    }

}
