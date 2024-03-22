package fr.math.minecraft.server.handler;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.TickHandler;
import fr.math.minecraft.server.payload.InputPayload;

import java.net.InetAddress;

public class PlayerActionsHandler extends PacketHandler implements Runnable {

    private final Client client;

    public PlayerActionsHandler(Client client, JsonNode packetData, InetAddress address, int clientPort) {
        super(packetData, address, clientPort);
        this.client = client;
    }

    @Override
    public void run() {
        MinecraftServer server = MinecraftServer.getInstance();
        // client.handleInputs(packetData);

        InputPayload payload = new InputPayload(packetData);
        TickHandler tickHandler = server.getTickHandler();
        String uuid = packetData.get("uuid").asText();

        tickHandler.enqueue(payload);

    }
}
