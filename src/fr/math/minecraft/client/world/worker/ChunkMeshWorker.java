package fr.math.minecraft.client.world.worker;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.meshs.WaterMesh;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.World;

public class ChunkMeshWorker implements Runnable {

    private final Chunk chunk;

    public ChunkMeshWorker(Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public void run() {
        Game game = Game.getInstance();
        World world = game.getWorld();
        // Coordinates chunkPosition = new Coordinates(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z);
        ChunkMesh chunkMesh = new ChunkMesh(chunk);
        WaterMesh waterMesh = new WaterMesh(chunk);
        synchronized (world.getChunks()) {
            chunk.setMesh(chunkMesh);
            chunk.setWaterMesh(waterMesh);
        }
        chunk.setLoaded(true);
        // world.getLoadingChunks().remove(chunkPosition);
    }

    public Chunk getChunk() {
        return chunk;
    }
}
