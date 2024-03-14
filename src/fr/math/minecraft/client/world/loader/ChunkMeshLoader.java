package fr.math.minecraft.client.world.loader;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.shared.world.Chunk;
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
            synchronized (game.getPendingMeshs()) {
                if (game.getPendingMeshs().isEmpty()) {
                    continue;
                }
                chunk = game.getPendingMeshs().poll();
            }
            ChunkMeshWorker worker = new ChunkMeshWorker(chunk);
            //worker.run();
            game.getChunkMeshLoadingQueue().submit(worker);

        }
    }

}
