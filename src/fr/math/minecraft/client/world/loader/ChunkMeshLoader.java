package fr.math.minecraft.client.world.loader;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.Coordinates;
import fr.math.minecraft.client.world.World;
import fr.math.minecraft.client.world.worker.ChunkMeshWorker;

import static org.lwjgl.glfw.GLFW.*;

public class ChunkMeshLoader extends Thread {

    private final Game game;

    public ChunkMeshLoader(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (!glfwWindowShouldClose(game.getWindow())) {
            Chunk chunk;
            synchronized (game.getPendingChunks()) {
                if (game.getPendingChunks().isEmpty()) {
                    continue;
                }
                chunk = game.getPendingChunks().poll();
            }
            ChunkMeshWorker worker = new ChunkMeshWorker(chunk);
            // worker.run();
            game.getChunkLoadingQueue().submit(worker);

        }
    }

}
