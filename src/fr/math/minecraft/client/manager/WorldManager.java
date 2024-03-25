package fr.math.minecraft.client.manager;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.shared.math.MathUtils;
import fr.math.minecraft.client.world.worker.ChunkGenerationWorker;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.Coordinates;
import fr.math.minecraft.shared.world.World;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.*;

public class WorldManager {

    private final Set<Coordinates> loadedChunks;
    private final List<ChunkMesh> meshsDeleteQueue;

    public WorldManager() {
        this.loadedChunks = new HashSet<>();
        this.meshsDeleteQueue = new ArrayList<>();
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

    public void loadChunks(World world) {
        Game game = Game.getInstance();
        Player player = game.getPlayer();

        int startX = (int) (Math.floor(player.getPosition().x / (double) Chunk.SIZE) - GameConfiguration.CHUNK_RENDER_DISTANCE);
        int startZ = (int) (Math.floor(player.getPosition().z / (double) Chunk.SIZE) - GameConfiguration.CHUNK_RENDER_DISTANCE);

        int endX = (int) (Math.floor(player.getPosition().x / (double) Chunk.SIZE) + GameConfiguration.CHUNK_RENDER_DISTANCE);
        int endZ = (int) (Math.floor(player.getPosition().z / (double) Chunk.SIZE) + GameConfiguration.CHUNK_RENDER_DISTANCE);

        for (int x = startX; x <= endX; x++) {
            for (int y = 0; y <= 10; y++) {
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

                    if (loadedChunks.contains(coordinates)) {
                        continue;
                    }

                    ChunkGenerationWorker worker = new ChunkGenerationWorker(world, x, y, z);
                    worker.run();

                    loadedChunks.add(coordinates);
                }
            }
        }
    }

    public Vector3i[] getNeighboors(Chunk chunk) {
        Vector3i[] neighboors = new Vector3i[] {
            new Vector3i(chunk.getPosition().x + 1, chunk.getPosition().y, chunk.getPosition().z),
            new Vector3i(chunk.getPosition().x - 1, chunk.getPosition().y, chunk.getPosition().z),
            new Vector3i(chunk.getPosition().x, chunk.getPosition().y + 1, chunk.getPosition().z),
            new Vector3i(chunk.getPosition().x, chunk.getPosition().y - 1, chunk.getPosition().z),
            new Vector3i(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z + 1),
            new Vector3i(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z - 1)
        };
        return neighboors;
    }

    public void updateNeighboors(Chunk chunk, World world) {
        Vector3i[] neighboors = this.getNeighboors(chunk);
        Game game = Game.getInstance();

        for (int i = 0; i < neighboors.length; i++) {
            Chunk neighboorChunk = world.getChunk(neighboors[i].x, neighboors[i].y, neighboors[i].z);
            if (neighboorChunk != null && world.getChunk(neighboors[i].x, neighboors[i].y, neighboors[i].z).isLoaded()) {
                synchronized (game.getChunkUpdateQueue()) {
                    game.getChunkUpdateQueue().add(neighboorChunk);
                }
            }
        }
    }

    public List<ChunkMesh> getMeshsDeleteQueue() {
        return meshsDeleteQueue;
    }
}
