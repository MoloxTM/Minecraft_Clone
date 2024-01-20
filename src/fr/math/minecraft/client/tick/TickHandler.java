package fr.math.minecraft.client.tick;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.GameConfiguration;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.packet.ChunkRequestPacket;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.server.manager.ChunkManager;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class TickHandler extends Thread {

    private final static float TICK_PER_SECONDS = 1.0f;
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

        while (!glfwWindowShouldClose(game.getWindow())) {
            double currentTime = glfwGetTime();
            double deltaTime = currentTime - previousTime;

            tickTimer += deltaTime;

            previousTime = currentTime;

            while (tickTimer > TICK_RATE) {
                tick();
                tickTimer -= TICK_RATE;
            }

        }
    }

    private void tick() {
        Player player = game.getPlayer();

        int startX = Math.max((int) (player.getPosition().x / Chunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE), 0);
        int startY = Math.max((int) (player.getPosition().y / Chunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE), 0);
        int startZ = Math.max((int) (player.getPosition().z / Chunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE), 0);

        int endX = (int) player.getPosition().x / Chunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE;
        int endY = (int) player.getPosition().y / Chunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE;
        int endZ = (int) player.getPosition().z / Chunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {

                    ChunkRequestPacket packet = new ChunkRequestPacket(new Vector3f(x, y, z));
                    packet.send();

                    if (packet.getChunkData() == null) continue;

                    JsonNode chunkData = packet.getChunkData();
                    chunkManager.loadChunkData(chunkData);

                }
            }
        }
    }

}
