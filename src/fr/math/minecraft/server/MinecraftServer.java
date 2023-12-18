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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
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

    public MinecraftServer(int port) {
        this.running = false;
        this.buffer = new byte[256];
        this.port = port;
        this.logger = LoggerUtility.getServerLogger(MinecraftServer.class, LogType.TXT);
        this.clients = new HashMap<>();
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
                    packet = this.handleConnectionInit(packetData, address, clientPort);

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
                    packet = this.handlePlayerList(address, clientPort);

                    socket.send(packet);
                    break;
                default:
                    logger.error("Type de packet : " + packetType + " non reconnu.");
            }

        }
        socket.close();
    }

    private DatagramPacket handleConnectionInit(JsonNode packetData, InetAddress address, int clientPort) {
        String playerName = packetData.get("playerName").asText();
        UUID uuid = UUID.randomUUID();
        byte[] buffer = uuid.toString().getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, clientPort);
        clients.put(uuid.toString(), new Client(uuid.toString(), playerName));

        return packet;
    }

    private DatagramPacket handlePlayerList(InetAddress address, int clientPort) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode playersNode = mapper.createArrayNode();

        for (Map.Entry<String, Client> entrySet : clients.entrySet()) {
            Client client = entrySet.getValue();
            playersNode.add(client.toJSON());
        }

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

        String direction = packetData.get("direction").asText();
        float yaw = packetData.get("yaw").floatValue();
        float pitch = packetData.get("pitch").floatValue();

        client.updatePosition(direction, yaw, pitch);

        ObjectNode positionNode = new ObjectMapper().createObjectNode();
        positionNode.put("x", client.getPosition().x);
        positionNode.put("y", client.getPosition().y);
        positionNode.put("z", client.getPosition().z);

        String response = new ObjectMapper().writeValueAsString(positionNode);
        byte[] buffer = response.getBytes(StandardCharsets.UTF_8);

        return new DatagramPacket(buffer, buffer.length, address, clientPort);
    }

}
