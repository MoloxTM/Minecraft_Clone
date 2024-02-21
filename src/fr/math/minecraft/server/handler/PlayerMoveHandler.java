package fr.math.minecraft.server.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.TickHandler;
import fr.math.minecraft.server.payload.InputPayload;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class PlayerMoveHandler extends PacketHandler implements Runnable {

    private final Client client;

    public PlayerMoveHandler(Client client, JsonNode packetData, InetAddress address, int clientPort) {
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
