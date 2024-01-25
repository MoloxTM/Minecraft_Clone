package fr.math.minecraft.client.tick;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.GameConfiguration;
import fr.math.minecraft.client.Utils;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.packet.ChunkRequestPacket;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.manager.ChunkManager;
import fr.math.minecraft.client.world.Coordinates;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

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

    }

}
