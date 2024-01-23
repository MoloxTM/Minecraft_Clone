package fr.math.minecraft.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.ServerWorld;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MinecraftServer {

    private static MinecraftServer instance = null;
    private DatagramSocket socket;
    private boolean running;
    private final byte[] buffer;
    private final int port;
    private final static Logger logger = LoggerUtility.getServerLogger(MinecraftServer.class, LogType.TXT);;
    private final Map<String, Client> clients;
    private final Map<String, String> sockets;
    private final Map<String, Long> lastActivities;
    private final ServerWorld world;
    private final static int MAX_REQUEST_SIZE = 4096;

    private MinecraftServer(int port) {
        this.running = false;
        this.buffer = new byte[MAX_REQUEST_SIZE];
        this.port = port;
        this.clients = new HashMap<>();
        this.sockets = new HashMap<>();
        this.lastActivities = new HashMap<>();
        this.world = new ServerWorld();
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
                case "SKIN_REQUEST":
                    packet = this.handleSkinRequest(packetData, address, clientPort);

                    socket.send(packet);
                    break;
                case "CHUNK_REQUEST":
                    packet = this.handleChunkRequest(packetData, address, clientPort);

                    socket.send(packet);
                    break;
                default:
                    String message = "UNAUTHORIZED_PACKET";
                    buffer = message.getBytes(StandardCharsets.UTF_8);
                    socket.send(new DatagramPacket(buffer, buffer.length, address, clientPort));
                    logger.error("Type de packet : " + packetType + " non reconnu.");
            }
        }
        socket.close();
    }

    private DatagramPacket handleChunkRequest(JsonNode packetData, InetAddress address, int clientPort) {

        int x = packetData.get("x").asInt();
        int y = packetData.get("y").asInt();
        int z = packetData.get("z").asInt();

        ServerChunk chunk = world.getChunk(x, y, z);

        if (chunk == null) {
            byte[] buffer = "CHUNK_UNKNOWN".getBytes(StandardCharsets.UTF_8);
            return new DatagramPacket(buffer, buffer.length, address, clientPort);
        }

        String chunkData = chunk.toJSON();
        byte[] buffer = chunkData.getBytes(StandardCharsets.UTF_8);

        return new DatagramPacket(buffer, buffer.length, address, clientPort);

    }

    private DatagramPacket handleSkinRequest(JsonNode packetData, InetAddress address, int clientPort) {
        String uuid = packetData.get("uuid").asText();

        File file = new File("skins/" + uuid + ".png");
        if (!file.exists()) {
            byte[] buffer = "PLAYER_DOESNT_EXISTS".getBytes(StandardCharsets.UTF_8);
            return new DatagramPacket(buffer, buffer.length, address, clientPort);
        }

        try {
            BufferedImage skin = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(skin, "png", baos);
            String base64Skin = Base64.getEncoder().encodeToString(baos.toByteArray());
            byte[] buffer = base64Skin.getBytes();

            return new DatagramPacket(buffer, buffer.length, address, clientPort);
        } catch (IOException e) {
            byte[] buffer = "ERROR".getBytes(StandardCharsets.UTF_8);
            logger.error(e.getMessage());
            return new DatagramPacket(buffer, buffer.length, address, clientPort);
        }
    }

    private DatagramPacket handleConnectionInit(DatagramPacket receivedPacket, JsonNode packetData, InetAddress address, int clientPort) {
        String playerName = packetData.get("playerName").asText();

        /*
        for (Client client : clients.values()) {
            if (client.getName().equalsIgnoreCase(playerName)) {
                byte[] buffer = "USERNAME_NOT_AVAILABLE".getBytes(StandardCharsets.UTF_8);
                return new DatagramPacket(buffer, buffer.length, address, clientPort);
            }
        }
         */


        String uuid = UUID.randomUUID().toString();
        byte[] buffer = uuid.getBytes(StandardCharsets.UTF_8);
        clients.put(uuid, new Client(uuid, playerName));

        byte[] skinBytes = Base64.getDecoder().decode(packetData.get("skin").asText());
        try {
            BufferedImage skin = ImageIO.read(new ByteArrayInputStream(skinBytes));
            ImageIO.write(skin, "png", new File("skins/" + uuid + ".png"));
            logger.info("Le skin du joueur" + playerName + " (" + uuid + ") a été sauvegardé avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, clientPort);
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

    public static MinecraftServer getInstance() {
        if(instance == null) {
            instance = new MinecraftServer(50000);
        }
        return instance;
    }

    public Map<String, Long> getLastActivities() {
        return lastActivities;
    }

    public Map<String, Client> getClients() {
        return clients;
    }
}
