package fr.math.minecraft.client.manager;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.GameConfiguration;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.worker.ChunkGenerationWorker;
import fr.math.minecraft.client.world.Coordinates;
import fr.math.minecraft.client.world.World;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

public class WorldManager {


    public void loadChunks(World world) {
        Game game = Game.getInstance();
        Player player = game.getPlayer();
        Set<Coordinates> loadingChunks = world.getLoadingChunks();
        ThreadPoolExecutor chunkLoadingQueue = game.getChunkLoadingQueue();

        int startX = (int) (player.getPosition().x / Chunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE);
        int startZ = (int) (player.getPosition().z / Chunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE);

        int endX = (int) (player.getPosition().x / Chunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE);
        int endZ = (int) (player.getPosition().z / Chunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE);

        for (int x = startX; x <= endX; x++) {
            for (int y = -3; y <= 10; y++) {
                for (int z = startZ; z <= endZ; z++) {

                    Coordinates coordinates = new Coordinates(x, y, z);
                    Chunk chunk = world.getChunks().get(coordinates);

                    if (chunk != null && chunk.isLoaded()) {
                        continue;
                    }

                    if (loadingChunks.contains(coordinates)) {
                        continue;
                    }

                    loadingChunks.add(coordinates);
                    ChunkGenerationWorker worker = new ChunkGenerationWorker(game, coordinates);
                    worker.work();
                }
            }
        }
    }

    public void cleanChunks(World world) {
        synchronized (world.getChunks()) {
            Game game = Game.getInstance();
            Player player = game.getPlayer();
            ChunkManager chunkManager = new ChunkManager();
            Map<Coordinates, Chunk> chunks = new HashMap<>(world.getChunks());
            for (Map.Entry<Coordinates, Chunk> chunksSet : chunks.entrySet()) {
                Chunk chunk = chunksSet.getValue();
                Coordinates coordinates = chunksSet.getKey();
                if (chunk.isOutOfView(player) && chunk.isLoaded() && !world.getLoadingChunks().contains(coordinates)) {
                    chunkManager.deleteChunk(world, chunk);
                    world.getLoadingChunks().remove(coordinates);
                }
            }
        }
    }

}
