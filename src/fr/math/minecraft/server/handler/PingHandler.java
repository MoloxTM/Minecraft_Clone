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

public class PingHandler extends PacketHandler {
    public PingHandler(JsonNode packetData, InetAddress address, int clientPort) {
        super(packetData, address, clientPort);
    }

    public void run() {


        long currentTime = System.currentTimeMillis();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        MinecraftServer server = MinecraftServer.getInstance();

        String uuid = packetData.get("uuid").asText();
        Client client = server.getClients().get(uuid);

        if (client == null) {
            return;
        }

        node.put("type", "PING_REPLY");
        node.put("receivedTime", currentTime);
        node.put("sentTime", packetData.get("time").longValue());

        try {
            String response = mapper.writeValueAsString(node);
            byte[] buffer = response.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, clientPort);

            server.sendPacket(packet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
