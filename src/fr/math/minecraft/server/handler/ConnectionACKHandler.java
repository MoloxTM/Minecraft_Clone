package fr.math.minecraft.server.handler;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.manager.ClientManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ConnectionACKHandler extends PacketHandler implements Runnable {
    public ConnectionACKHandler(JsonNode packetData, InetAddress address, int clientPort) {
        super(packetData, address, clientPort);
    }

    @Override
    public void run() {
        MinecraftServer server = MinecraftServer.getInstance();
        String uuid = packetData.get("uuid").asText();
        Client connectedClient = server.getClients().get(uuid);

        if (connectedClient == null) {
            return;
        }

        connectedClient.setActive(true);

        synchronized (server.getClients()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.createObjectNode();
            ObjectNode clientJsonNode = connectedClient.toJSONWithSkin();
            node.put("type", "PLAYER_JOIN");
            node.set("playerData", clientJsonNode);
            try {
                String clientData = mapper.writeValueAsString(node);
                byte[] buffer = clientData.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                for (Client client : server.getClients().values()) {

                    if (uuid.equals(client.getUuid())) {
                        continue;
                    }

                    packet.setAddress(client.getAddress());
                    packet.setPort(client.getPort());
                    server.sendPacket(packet);
                    System.out.println("j'envoie un packet PLAYER_JOIN à " + client.getAddress() + ":" + client.getPort());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
