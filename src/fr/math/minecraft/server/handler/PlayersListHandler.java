package fr.math.minecraft.server.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Map;

public class PlayersListHandler extends PacketHandler {

    public PlayersListHandler(JsonNode packetData, InetAddress address, int clientPort) {
        super(packetData, address, clientPort);
    }

    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode playersNode = mapper.createArrayNode();
        ObjectNode node = mapper.createObjectNode();
        MinecraftServer server = MinecraftServer.getInstance();
        String uuid = packetData.get("uuid").asText();

        if (server.getClients().get(uuid) == null) {
            return;
        }

        synchronized (server.getClients()) {
            for (Map.Entry<String, Client> entrySet : server.getClients().entrySet()) {
                Client client = entrySet.getValue();
                playersNode.add(client.toJSON());
            }
        }

        try {
            node.put("type", "PLAYERS_LIST");
            node.set("players", playersNode);
            String message = mapper.writeValueAsString(node);
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, clientPort);

            server.sendPacket(packet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
