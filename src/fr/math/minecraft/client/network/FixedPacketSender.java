package fr.math.minecraft.client.network;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.network.packet.ClientPacket;
import org.joml.Vector3f;
import org.joml.Vector3i;

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

        while (!packetsQueue.isEmpty()) {
            ClientPacket packet = packetsQueue.poll();
            if (packet == null) continue;
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
        packetsQueue.add(packet);
    }

    public Queue<ClientPacket> getPacketsQueue() {
        return packetsQueue;
    }
}
