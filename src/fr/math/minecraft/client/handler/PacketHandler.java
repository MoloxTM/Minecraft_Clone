package fr.math.minecraft.client.handler;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.GameConfiguration;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.manager.WorldManager;
import fr.math.minecraft.client.network.packet.ClientPacket;
import fr.math.minecraft.client.network.packet.PlayerMovePacket;
import fr.math.minecraft.client.network.packet.PlayersListPacket;

import java.util.LinkedList;
import java.util.Queue;
import static org.lwjgl.glfw.GLFW.*;

public class PacketHandler extends Thread {

    private static PacketHandler instance = null;
    private final Queue<ClientPacket> packetsQueue;
    private int ping;

    private PacketHandler() {
        this.setName("PacketHandler");
        this.packetsQueue = new LinkedList<>();
        this.ping = 0;
    }

    @Override
    public void run() {
        Game game = Game.getInstance();
        Player player = game.getPlayer();
        double lastPingTime = glfwGetTime();
        long lastTickTime = System.currentTimeMillis();
        double tickTimer = 0;
        while (!glfwWindowShouldClose(game.getWindow())) {

            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - lastTickTime;

            tickTimer += deltaTime;

            lastTickTime = currentTime;

            while (tickTimer >= 50) {
                tick();
                tickTimer -= 50;
            }
        }
    }

    private void tick() {
        Game game = Game.getInstance();
        game.getPlayerMovementHandler().handle(game.getPlayer());

        while (!packetsQueue.isEmpty()) {
            ClientPacket packet = packetsQueue.poll();
            packet.send();
        }
    }

    public static PacketHandler getInstance() {
        if (instance == null) {
            instance = new PacketHandler();
        }
        return instance;
    }

    public synchronized void enqueue(ClientPacket packet) {
        packetsQueue.add(packet);
    }

    public Queue<ClientPacket> getPacketsQueue() {
        return packetsQueue;
    }
}
