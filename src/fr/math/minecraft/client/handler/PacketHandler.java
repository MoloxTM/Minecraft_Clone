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
        double lastTickTime = glfwGetTime();
        double tickTimer = 0;
        while (!glfwWindowShouldClose(game.getWindow())) {

            double currentTime = glfwGetTime();
            double deltaTime = currentTime - lastTickTime;

            tickTimer += deltaTime;

            if (currentTime - lastPingTime >= 1.0) {
                player.setPing(ping);
                lastPingTime = currentTime;
            }

            lastTickTime = currentTime;

            while (tickTimer >= GameConfiguration.TICK_RATE) {
                tick();
                tickTimer -= GameConfiguration.TICK_RATE;
            }
        }
    }

    private void tick() {
        Game game = Game.getInstance();
        game.getPlayerMovementHandler().handle(game.getPlayer());

        if (packetsQueue.isEmpty()) {
            return;
        }


        ClientPacket packet = packetsQueue.poll();
        packet.send();


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
