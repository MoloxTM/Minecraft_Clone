package fr.math.minecraft.client.manager;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.Coordinates;
import fr.math.minecraft.client.world.World;

import java.util.Iterator;

public class ChunkManager {

    public void deleteChunk(World world, Chunk chunk) {
        synchronized (world.getChunks()) {
            ChunkMesh mesh = chunk.getMesh();
            if (!chunk.isEmpty() && mesh != null && mesh.isInitiated()) {
                mesh.delete();
            }
            world.removeChunk(chunk);
        }
    }

    public void loadChunkData(JsonNode chunkData) {

        JsonNode blocks = chunkData.get("blocks");
        JsonNode emptyMap = chunkData.get("emptyMap");
        Game game = Game.getInstance();

        if (blocks == null) {
            System.out.println("blocks null!" + chunkData);
            return;
        }
        if (emptyMap == null) {
            System.out.println("emptyMap null!" + chunkData);
            return;
        }
        if (!emptyMap.isObject()) {
            System.out.println("emptyMap not object!" + chunkData);
            return;
        }

        if (!blocks.isArray()) {
            System.out.println("blocks not an array!" + chunkData);
            return;
        }

        int x = chunkData.get("x").asInt();
        int y = chunkData.get("y").asInt();
        int z = chunkData.get("z").asInt();

        World world = Game.getInstance().getWorld();
        Chunk chunk = world.getChunk(x, y, z);

        if (chunk == null) {
            chunk = new Chunk(x, y, z);
        }

        for (int i = 0; i < blocks.size(); i++) {
            JsonNode blockNode = blocks.get(i);
            byte block = (byte) blockNode.asInt();
            chunk.getBlocks()[i] = block;
        }

        for (Iterator<String> it = emptyMap.fieldNames(); it.hasNext(); ) {
            String encodedPosition = it.next();
            boolean emptyValue = emptyMap.get(encodedPosition).asBoolean();

            String[] positions = encodedPosition.split(",");

            int worldX = Integer.parseInt(positions[0]);
            int worldY = Integer.parseInt(positions[1]);
            int worldZ = Integer.parseInt(positions[2]);

            Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);

            chunk.getEmptyMap().put(coordinates, emptyValue);

        }

        if (chunk.getBlocksSize() > 0) {
            chunk.setEmpty(false);
        }

        synchronized (world.getChunks()) {
            world.addChunk(chunk);
            if (!chunk.isEmpty()) {
                synchronized (game.getPendingMeshs()) {
                    game.getPendingMeshs().add(chunk);
                }
            }
        }
    }

}
