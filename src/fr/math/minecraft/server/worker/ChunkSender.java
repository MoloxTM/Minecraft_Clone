package fr.math.minecraft.server.worker;

import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.builder.EmptyMapBuilder;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.ServerWorld;

import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ChunkSender implements Runnable {

    private final Client client;
    private final ServerChunk chunk;
    private long sentTime;

    public ChunkSender(Client client, ServerChunk chunk) {
        this.client = client;
        this.chunk = chunk;
        this.sentTime = -1;
    }

    @Override
    public void run() {
        MinecraftServer server = MinecraftServer.getInstance();
        ServerWorld world = server.getWorld();

        if (chunk.getEmptyMap().isEmpty()) {
            chunk.setEmptyMap(EmptyMapBuilder.buildEmptyMap(world, chunk));
        }

        String chunkData = chunk.toJSON();
        byte[] buffer = chunkData.getBytes(StandardCharsets.UTF_8);

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, client.getAddress(), client.getPort());
        server.sendPacket(packet);

        this.sentTime = System.currentTimeMillis();
    }

    public ServerChunk getChunk() {
        return chunk;
    }

    public long getSentTime() {
        return sentTime;
    }
}
