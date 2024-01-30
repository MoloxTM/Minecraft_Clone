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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ChunkGenerationWorker implements Runnable {

    private final Game game;
    private final ChunkManager chunkManager;
    private final Coordinates chunkPosition;

    public ChunkGenerationWorker(Game game, Coordinates chunkPosition) {
        this.game = game;
        this.chunkPosition = chunkPosition;
        this.chunkManager = new ChunkManager();
    }

    @Override
    public void run() {

        int x = chunkPosition.getX();
        int y = chunkPosition.getY();
        int z = chunkPosition.getZ();
        ThreadPoolExecutor packetQueue = game.getPacketQueue();

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

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {

            Coordinates coordinates = positions[i];
            Chunk chunk = world.getChunk(coordinates.getX(), coordinates.getY(), coordinates.getZ());

            if (chunk != null) continue;

            Future<?> future = packetQueue.submit(() -> {
                ChunkRequestPacket packet = new ChunkRequestPacket(new Vector3i(coordinates.getX(), coordinates.getY(), coordinates.getZ()));
                packet.send();

                JsonNode chunkData = packet.getChunkData();
                chunkManager.loadChunkData(chunkData);
            });

            futures.add(future);
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        Chunk chunk = world.getChunk(chunkPosition.getX(), chunkPosition.getY(), chunkPosition.getZ());

        if (!chunk.isEmpty()) {
            synchronized (game.getPendingChunks()) {
                game.getPendingChunks().add(chunk);
            }
//            Coordinates chunkPosition = new Coordinates(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z);
            ChunkMesh chunkMesh = new ChunkMesh(chunk);
            synchronized (world.getChunks()) {
                chunk.setMesh(chunkMesh);
            }
            chunk.setLoaded(true);
        }
    }
}
