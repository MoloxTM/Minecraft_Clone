package fr.math.minecraft.server.manager;

import fr.math.minecraft.client.GameConfiguration;
import fr.math.minecraft.client.world.Coordinates;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.ServerWorld;

public class ClientManager {

    public void fillNearChunksQueue(Client client) {
        int startX = (int) (client.getPosition().x / ServerChunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE);
        int startZ = (int) (client.getPosition().z / ServerChunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE);

        int endX = (int) (client.getPosition().x / ServerChunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE);
        int endZ = (int) (client.getPosition().z / ServerChunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE);

        MinecraftServer server = MinecraftServer.getInstance();
        ServerWorld world = server.getWorld();

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                for (int y = 0; y <= 10; y++) {

                    Coordinates coordinates = new Coordinates(x, y, z);
                    ServerChunk chunk = world.getChunks().get(coordinates);

                    if (client.getReceivedChunks().contains(coordinates)) {
                        continue;
                    }

                    if (chunk == null) {
                        chunk = new ServerChunk(x, y, z);
                        world.addChunk(chunk);
                    }

                    if (client.getNearChunks().contains(chunk)) {
                        continue;
                    }

                    synchronized (client.getNearChunks()) {
                        client.getNearChunks().add(chunk);
                    }
                }
            }
        }
    }

}
