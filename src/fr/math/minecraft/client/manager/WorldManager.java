package fr.math.minecraft.client.manager;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.network.FixedPacketSender;
import fr.math.minecraft.client.math.MathUtils;
import fr.math.minecraft.client.network.packet.ChunkRequestPacket;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.Coordinates;
import fr.math.minecraft.client.world.World;
import org.joml.Vector3f;
import org.joml.Vector3i;

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

    public void loadChunks(World world) {
        Game game = Game.getInstance();
        Player player = game.getPlayer();
        Set<Coordinates> loadingChunks = world.getLoadingChunks();

        int startX = (int) (player.getPosition().x / Chunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE);
        int startZ = (int) (player.getPosition().z / Chunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE);

        int endX = (int) (player.getPosition().x / Chunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE);
        int endZ = (int) (player.getPosition().z / Chunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE);

        for (int x = startX; x <= endX; x++) {
            for (int y = -3; y <= 10; y++) {
                for (int z = startZ; z <= endZ; z++) {

                    Coordinates coordinates = new Coordinates(x, y, z);
                    Chunk chunk = world.getChunks().get(coordinates);

                    int worldX = x * Chunk.SIZE;
                    int worldY = y * Chunk.SIZE;
                    int worldZ = z * Chunk.SIZE;

                    if (MathUtils.distance(player, new Vector3f(worldX, worldY, worldZ)) >= GameConfiguration.CHUNK_RENDER_DISTANCE * Chunk.SIZE) {
                        continue;
                    }

                    if (chunk != null && chunk.isLoaded()) {
                        continue;
                    }

                    if (loadingChunks.contains(coordinates)) {
                        continue;
                    }

                    loadingChunks.add(coordinates);

                    ChunkRequestPacket packet = new ChunkRequestPacket(new Vector3i(coordinates.getX(), coordinates.getY(), coordinates.getZ()));
                    FixedPacketSender.getInstance().enqueue(packet);
                    // chunkLoadingQueue.submit(worker);
                }
            }
        }
    }

}
