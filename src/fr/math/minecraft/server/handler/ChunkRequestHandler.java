package fr.math.minecraft.server.handler;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.world.Coordinates;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.ServerWorld;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ChunkRequestHandler extends PacketHandler implements Runnable {

    public ChunkRequestHandler(JsonNode packetData, InetAddress address, int clientPort) {
        super(packetData, address, clientPort);
    }

    public boolean isEmpty(ServerWorld world, int worldX, int worldY, int worldZ) {

        int chunkX = (int) Math.floor(worldX / (double) ServerChunk.SIZE);
        int chunkY = (int) Math.floor(worldY / (double) ServerChunk.SIZE);
        int chunkZ = (int) Math.floor(worldZ / (double) ServerChunk.SIZE);

        ServerChunk chunk = world.getChunk(chunkX, chunkY, chunkZ);

        if (chunk == null) {
            chunk = new ServerChunk(chunkX, chunkY, chunkZ);
            world.addChunk(chunk);
        }

        int blockX = worldX % ServerChunk.SIZE;
        int blockY = worldY % ServerChunk.SIZE;
        int blockZ = worldZ % ServerChunk.SIZE;

        blockX = blockX < 0 ? blockX + ServerChunk.SIZE : blockX;
        blockY = blockY < 0 ? blockY + ServerChunk.SIZE : blockY;
        blockZ = blockZ < 0 ? blockZ + ServerChunk.SIZE : blockZ;

        return chunk.getBlock(blockX, blockY, blockZ) == Material.AIR.getId();
    }

    @Override
    public void run() {
        MinecraftServer server = MinecraftServer.getInstance();
        ServerWorld world = server.getWorld();
        int chunkX = packetData.get("x").asInt();
        int chunkY = packetData.get("y").asInt();
        int chunkZ = packetData.get("z").asInt();

        ServerChunk chunk = world.getChunk(chunkX, chunkY, chunkZ);

        if (chunk == null) {
            chunk = new ServerChunk(chunkX, chunkY, chunkZ);
            world.addChunk(chunk);
        }

        Map<String, Boolean> emptyMap = chunk.getEmptyMap();

        for (int x = 0; x < ServerChunk.SIZE; x++) {
            for (int y = 0; y < ServerChunk.SIZE; y++) {
                for (int z = 0; z < ServerChunk.SIZE; z++) {

                    int worldX = chunk.getPosition().x * ServerChunk.SIZE + x;
                    int worldY = chunk.getPosition().y * ServerChunk.SIZE + y;
                    int worldZ = chunk.getPosition().z * ServerChunk.SIZE + z;

                    if (x + 1 >= ServerChunk.SIZE) {
                        String coords = (worldX + 1) + "," + worldY + "," + worldZ;
                        emptyMap.put(coords, isEmpty(world, worldX + 1, worldY, worldZ));
                    }
                    if (x - 1 < 0) {
                        String coords = (worldX - 1) + "," + worldY + "," + worldZ;
                        emptyMap.put(coords, isEmpty(world, worldX - 1, worldY, worldZ));
                    }
                    if (y + 1 >= ServerChunk.SIZE) {
                        String coords = worldX + "," + (worldY + 1) + "," + worldZ;
                        emptyMap.put(coords, isEmpty(world, worldX, worldY + 1, worldZ));
                    }
                    if (y - 1 < 0) {
                        String coords = worldX + "," + (worldY - 1) + "," + worldZ;
                        emptyMap.put(coords, isEmpty(world, worldX, worldY - 1, worldZ));
                    }
                    if (z + 1 >= ServerChunk.SIZE) {
                        String coords = worldX + "," + worldY + "," + (worldZ + 1);
                        emptyMap.put(coords, isEmpty(world, worldX, worldY, worldZ + 1));
                    }
                    if (z - 1 < 0) {
                        String coords = worldX + "," + worldY + "," + (worldZ - 1);
                        emptyMap.put(coords, isEmpty(world, worldX, worldY, worldZ - 1));
                    }
                }
            }
        }

        String chunkData = chunk.toJSON();
        byte[] buffer = chunkData.getBytes(StandardCharsets.UTF_8);

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, clientPort);
        server.sendPacket(packet);
    }
}
