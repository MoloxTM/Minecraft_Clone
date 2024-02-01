package fr.math.minecraft.client.handler;

import fr.math.minecraft.client.Game;
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

        while (!glfwWindowShouldClose(game.getWindow())) {

            ClientPacket packet = null;
            synchronized (this.getPacketsQueue()) {
                if (packetsQueue.isEmpty()) {
                    continue;
                }

                packet = packetsQueue.poll();
            }

            if (packet == null) continue;

            packet.send();

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
