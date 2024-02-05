package fr.math.minecraft.server.manager;

import fr.math.minecraft.client.GameConfiguration;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.Coordinates;
import fr.math.minecraft.client.world.World;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.ServerWorld;

import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;

public class ClientManager {

    public void sendNearChunks(Client client) {
        int startX = (int) (client.getPosition().x / ServerChunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE);
        int startZ = (int) (client.getPosition().z / ServerChunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE);

        int endX = (int) (client.getPosition().x / ServerChunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE);
        int endZ = (int) (client.getPosition().z / ServerChunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE);

        MinecraftServer server = MinecraftServer.getInstance();
        ServerWorld world = server.getWorld();

        for (int x = startX; x <= endX; x++) {
            for (int y = -3; y <= 10; y++) {
                for (int z = startZ; z <= endZ; z++) {

                    Coordinates coordinates = new Coordinates(x, y, z);
                    ServerChunk chunk = world.getChunks().get(coordinates);

                    if (chunk == null) {
                        chunk = new ServerChunk(x, y, z);
                        world.addChunk(chunk);
                    }

                    String chunkData = chunk.toJSON();
                    byte[] buffer = chunkData.getBytes(StandardCharsets.UTF_8);

                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, client.getAddress(), client.getPort());

                    try {
                        server.getSocket().send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
