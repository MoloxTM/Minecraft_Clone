package fr.math.minecraft.server.handler;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.builder.EmptyMapBuilder;
import fr.math.minecraft.server.world.Coordinates;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.ServerWorld;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ChunkRequestHandler extends PacketHandler implements Runnable {

    public ChunkRequestHandler(JsonNode packetData, InetAddress address, int clientPort) {
        super(packetData, address, clientPort);
    }

    @Override
    public void run() {
        int x = packetData.get("x").asInt();
        int y = packetData.get("y").asInt();
        int z = packetData.get("z").asInt();
        MinecraftServer server = MinecraftServer.getInstance();
        ServerWorld world = server.getWorld();

        ServerChunk chunk = world.getChunk(x, y, z);

        if (chunk == null) {
            chunk = new ServerChunk(x, y, z);
            chunk.generate();
            world.addChunk(chunk);
        }

        if (chunk.getEmptyMap().isEmpty()) {
            chunk.setEmptyMap(EmptyMapBuilder.buildEmptyMap(world, chunk));
        }

        String chunkData = chunk.toJSON();
        byte[] buffer = chunkData.getBytes(StandardCharsets.UTF_8);

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, clientPort);

        server.sendPacket(packet);
    }
}
