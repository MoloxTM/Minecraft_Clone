package fr.math.minecraft.server.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;

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
        client.handleInputs(packetData);

        ObjectNode positionNode = new ObjectMapper().createObjectNode();
        positionNode.put("type", "PLAYER_MOVE_PACKET");
        positionNode.put("tick", packetData.get("tick").asInt());
        positionNode.put("x", client.getPosition().x);
        positionNode.put("y", client.getPosition().y);
        positionNode.put("z", client.getPosition().z);

        String response = null;
        try {
            response = new ObjectMapper().writeValueAsString(positionNode);
            byte[] buffer = response.getBytes(StandardCharsets.UTF_8);

            String uuid = packetData.get("uuid").asText();
            server.getLastActivities().put(uuid, System.currentTimeMillis());

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, clientPort);
            server.sendPacket(packet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
