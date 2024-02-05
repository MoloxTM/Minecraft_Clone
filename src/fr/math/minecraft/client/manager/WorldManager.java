package fr.math.minecraft.client.manager;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.Coordinates;
import fr.math.minecraft.client.world.World;

import java.util.*;

public class WorldManager {

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
