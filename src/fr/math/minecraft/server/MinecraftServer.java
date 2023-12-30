package fr.math.minecraft.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MinecraftServer {

    private DatagramSocket socket;
    private boolean running;
    private final byte[] buffer;
    private final int port;
    private final Logger logger;
    private final Map<String, Client> clients;
    private final Map<String, String> sockets;
    private final Map<String, Long> lastActivities;

    public MinecraftServer(int port) {
        this.running = false;
        this.buffer = new byte[256];
        this.port = port;
        this.logger = LoggerUtility.getServerLogger(MinecraftServer.class, LogType.TXT);
        this.clients = new HashMap<>();
        this.sockets = new HashMap<>();
        this.lastActivities = new HashMap<>();
    }

    public void start() throws IOException {
        this.running = true;
        socket = new DatagramSocket(this.port);
        System.out.println("Serveur en écoute sur le port " + this.port + "...");
        while (this.running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            InetAddress address = packet.getAddress();
            int clientPort = packet.getPort();

            ClientHandler handler = new ClientHandler();
            JsonNode packetData = handler.parsePacket(packet);

            if (packetData == null)
                continue;

            String packetType = packetData.get("type").asText();
            byte[] buffer;
            switch (packetType) {
                case "CONNECTION_INIT":
                    packet = this.handleConnectionInit(packet, packetData, address, clientPort);

                    socket.send(packet);
                    break;
                case "PLAYER_MOVE":
                    String playerId = packetData.get("uuid").asText();

                    Client client = clients.get(playerId);
                    if (client == null) break;

                    packet = this.handleMovement(client, packetData, address, clientPort);

                    socket.send(packet);

                    break;
                case "PLAYERS_LIST_REQUEST":
                    packet = this.handlePlayerList(packetData, address, clientPort);

                    socket.send(packet);
                    break;
                default:
                    logger.error("Type de packet : " + packetType + " non reconnu.");
            }
        }
        socket.close();
    }

    private DatagramPacket handleConnectionInit(DatagramPacket receivedPacket, JsonNode packetData, InetAddress address, int clientPort) {
        String playerName = packetData.get("playerName").asText();

        for (Client client : clients.values()) {
            if (client.getName().equalsIgnoreCase(playerName)) {
                byte[] buffer = "USERNAME_NOT_AVAILABLE".getBytes(StandardCharsets.UTF_8);
                return new DatagramPacket(buffer, buffer.length, address, clientPort);
            }
        }

        String uuid = UUID.randomUUID().toString();
        String skin = packetData.get("skin").asText();
        byte[] buffer = uuid.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, clientPort);
        clients.put(uuid, new Client(uuid, playerName, skin));
        sockets.put(String.valueOf(receivedPacket.getAddress()), uuid);
        logger.info(playerName + " a rejoint le serveur ! (" + clients.size() + "/???)");

        if (!lastActivities.containsKey(uuid)) {
            lastActivities.put(uuid, System.currentTimeMillis());
            TimeoutHandler handler = new TimeoutHandler(this, uuid);
            handler.start();
        }


        return packet;
    }

    private DatagramPacket handlePlayerList(JsonNode packetData, InetAddress address, int clientPort) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode playersNode = mapper.createArrayNode();

        for (Map.Entry<String, Client> entrySet : clients.entrySet()) {
            Client client = entrySet.getValue();
            playersNode.add(client.toJSON());
        }

        String uuid = packetData.get("uuid").asText();
        lastActivities.put(uuid, System.currentTimeMillis());

        try {
            String message = mapper.writeValueAsString(playersNode);
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, clientPort);

            return packet;
        } catch (JsonProcessingException e) {
            return null;
        }

    }

    public DatagramPacket handleMovement(Client client, JsonNode packetData, InetAddress address, int clientPort) throws JsonProcessingException {

        client.updatePosition(packetData);

        ObjectNode positionNode = new ObjectMapper().createObjectNode();
        positionNode.put("x", client.getPosition().x);
        positionNode.put("y", client.getPosition().y);
        positionNode.put("z", client.getPosition().z);

        String response = new ObjectMapper().writeValueAsString(positionNode);
        byte[] buffer = response.getBytes(StandardCharsets.UTF_8);

        return new DatagramPacket(buffer, buffer.length, address, clientPort);
    }

    public Map<String, Long> getLastActivities() {
        return lastActivities;
    }

    public Map<String, Client> getClients() {
        return clients;
    }
}
