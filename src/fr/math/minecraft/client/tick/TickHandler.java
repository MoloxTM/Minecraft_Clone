package fr.math.minecraft.client.tick;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.manager.ChunkManager;
import fr.math.minecraft.client.manager.WorldManager;

import static org.lwjgl.glfw.GLFW.*;

public class TickHandler extends Thread {

    private final static float TICK_PER_SECONDS = 20.0f;
    private final static float TICK_RATE = 1.0f / TICK_PER_SECONDS;
    private final Game game;
    private final ChunkManager chunkManager;

    public TickHandler() {
        this.setName("TickThread");
        this.game = Game.getInstance();
        this.chunkManager = new ChunkManager();
    }

    @Override
    public void run() {
        float tickTimer = 0.0f;
        double previousTime = glfwGetTime();
        Player player = game.getPlayer();
        WorldManager worldManager = new WorldManager();

        while (!glfwWindowShouldClose(game.getWindow())) {
            double currentTime = glfwGetTime();
            double deltaTime = currentTime - previousTime;

            tickTimer += deltaTime;

            previousTime = currentTime;



            while (tickTimer > TICK_RATE) {
                worldManager.loadChunks(game.getWorld());
                tick();
                tickTimer -= TICK_RATE;
            }
        }
    }

    private void tick() {

    }

}
