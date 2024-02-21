package fr.math.minecraft.client.world.worker;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.World;

public class ChunkGenerationWorker implements Runnable {

    private final int x, y, z;
    private final World world;

    public ChunkGenerationWorker(World world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void run() {
        Game game = Game.getInstance();
        Chunk chunk = new Chunk(x, y, z);
        chunk.generate(world, world.getTerrainGenerator());
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
