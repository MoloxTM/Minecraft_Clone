package fr.math.minecraft.server.handler;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.world.Coordinates;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;

import java.net.InetAddress;

public class ChunkACKHandler extends PacketHandler implements Runnable {

    public ChunkACKHandler(JsonNode packetData) {
        super(packetData, null, -1);
    }

    @Override
    public void run() {
        String uuid = packetData.get("uuid").asText();
        MinecraftServer server = MinecraftServer.getInstance();
        Client client = server.getClients().get(uuid);

        if (client == null) {
            return;
        }

        int chunkX = packetData.get("x").asInt();
        int chunkY = packetData.get("y").asInt();
        int chunkZ = packetData.get("z").asInt();

        Coordinates coordinates = new Coordinates(chunkX, chunkY, chunkZ);

        synchronized (client.getReceivedChunks()) {
            client.getReceivedChunks().add(coordinates);
        }
    }
}
