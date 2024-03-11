package fr.math.minecraft.client.network;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.client.network.packet.ClientPacket;
import fr.math.minecraft.shared.GameConfiguration;

import java.util.LinkedList;
import java.util.Queue;
import static org.lwjgl.glfw.GLFW.*;

public class FixedPacketSender extends Thread {

    private static FixedPacketSender instance = null;
    private final Queue<ClientPacket> packetsQueue;
    private int ping;

    private FixedPacketSender() {
        this.setName("PacketHandler");
        this.packetsQueue = new LinkedList<>();
        this.ping = 0;
    }

    @Override
    public void run() {
        Game game = Game.getInstance();
        Player player = game.getPlayer();
        double lastPingTime = System.currentTimeMillis();
        long lastTickTime = System.currentTimeMillis();
        double tickTimer = 0;
        while (!glfwWindowShouldClose(game.getWindow())) {

            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - lastTickTime;

            if (currentTime - lastPingTime >= 1000) {
                lastPingTime = currentTime;
            }

            tickTimer += deltaTime;

            lastTickTime = currentTime;
            tick();

            while (tickTimer >= 1000 / GameConfiguration.TICK_PER_SECONDS) {
                tickTimer -= 1000 / GameConfiguration.TICK_PER_SECONDS;
            }
        }
    }

    private void tick() {

        while (!packetsQueue.isEmpty()) {
            ClientPacket packet = packetsQueue.poll();
            if (packet == null) return;
            packet.send();
        }
    }

    public static FixedPacketSender getInstance() {
        if (instance == null) {
            instance = new FixedPacketSender();
        }
        return instance;
    }

    public synchronized void enqueue(ClientPacket packet) {
        //packetsQueue.add(packet);
        packet.send();
    }

    public Queue<ClientPacket> getPacketsQueue() {
        return packetsQueue;
    }
}
