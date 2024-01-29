package fr.math.minecraft.client.world.worker;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.manager.ChunkManager;
import fr.math.minecraft.client.manager.WorldManager;
import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.packet.ChunkRequestPacket;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.Coordinates;
import fr.math.minecraft.client.world.World;
import org.joml.Vector3i;

import java.util.concurrent.ThreadPoolExecutor;

public class ChunkGenerationWorker {

    private final Game game;
    private final ChunkManager chunkManager;
    private final Coordinates chunkPosition;

    public ChunkGenerationWorker(Game game, Coordinates chunkPosition) {
        this.game = game;
        this.chunkPosition = chunkPosition;
        this.chunkManager = new ChunkManager();
    }

    public void work() {

        int x = chunkPosition.getX();
        int y = chunkPosition.getY();
        int z = chunkPosition.getZ();

        World world = game.getWorld();

        Coordinates[] positions = new Coordinates[] {
                new Coordinates(x, y, z),
                new Coordinates(x - 1, y, z),
                new Coordinates(x + 1, y, z),
                new Coordinates(x, y - 1, z),
                new Coordinates(x, y + 1, z),
                new Coordinates(x, y, z - 1),
                new Coordinates(x, y, z + 1),
        };
        for (int i = 0; i < positions.length; i++) {

            Coordinates coordinates = positions[i];
            Chunk chunk = world.getChunk(coordinates.getX(), coordinates.getY(), coordinates.getZ());

            if (chunk != null) continue;

            ChunkRequestPacket packet = new ChunkRequestPacket(new Vector3i(coordinates.getX(), coordinates.getY(), coordinates.getZ()));
            packet.send();

            if (packet.getChunkData() == null) continue;

            JsonNode chunkData = packet.getChunkData();
            chunkManager.loadChunkData(chunkData);

            if (i == 0 && world.getChunk(coordinates.getX(), coordinates.getY(), coordinates.getZ()).isEmpty()) {
                world.getChunk(coordinates.getX(), coordinates.getY(), coordinates.getZ()).setLoaded(true);
                world.getLoadingChunks().remove(chunkPosition);
                return;
            }
        }

        Chunk chunk = world.getChunk(chunkPosition.getX(), chunkPosition.getY(), chunkPosition.getZ());

        if (!chunk.isEmpty()) {
            synchronized (game.getPendingChunks()) {
                game.getPendingChunks().add(chunk);
            }
        }
    }
}
