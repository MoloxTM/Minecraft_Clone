package fr.math.minecraft.client.handler;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.network.packet.ClientPacket;
import fr.math.minecraft.client.network.packet.PlayerMovePacket;

import java.util.LinkedList;
import java.util.Queue;
import static org.lwjgl.glfw.GLFW.*;

public class PacketHandler extends Thread {

    private static PacketHandler instance = null;
    private final Queue<ClientPacket> packetsQueue;

    private PacketHandler() {
        this.setName("PacketHandler");
        this.packetsQueue = new LinkedList<>();
    }

    @Override
    public void run() {
        Game game = Game.getInstance();
        Player player = game.getPlayer();
        double lastPingTime = glfwGetTime();
        int ping = 0;
        while (!glfwWindowShouldClose(game.getWindow())) {

            double currentTime = glfwGetTime();

            if (currentTime - lastPingTime >= 1.0) {
                player.setPing(ping);
                lastPingTime = currentTime;
            }

            ClientPacket packet = null;
            synchronized (this.getPacketsQueue()) {
                if (packetsQueue.isEmpty()) {
                    continue;
                }

                packet = packetsQueue.poll();
            }

            if (packet == null) continue;

            long start = System.currentTimeMillis();
            packet.send();
            long end = System.currentTimeMillis();
            ping = (int) (end - start);
        }
    }

    private void tick() {

    }


    public void enqueue(ClientPacket packet) {
        packetsQueue.add(packet);
    }

    public static PacketHandler getInstance() {
        if (instance == null) {
            instance = new PacketHandler();
        }
        return instance;
    }

    public Queue<ClientPacket> getPacketsQueue() {
        return packetsQueue;
    }
}
