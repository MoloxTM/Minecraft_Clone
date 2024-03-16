package fr.math.minecraft.server;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.server.handler.*;
import fr.math.minecraft.server.manager.ChunkManager;
import fr.math.minecraft.shared.world.Region;
import fr.math.minecraft.shared.world.World;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
    private final World world;
    private final static int MAX_REQUEST_SIZE = 16384;
    private final ThreadPoolExecutor packetQueue;
    private final TickHandler tickHandler;
    private final ChunkManager chunkManager;

    private MinecraftServer(int port) {
        this.running = false;
        this.buffer = new byte[MAX_REQUEST_SIZE];
        this.port = port;
        this.clients = new HashMap<>();
        this.sockets = new HashMap<>();
        this.lastActivities = new HashMap<>();
        this.world = new World();
        this.world.buildSpawn();
        this.world.calculateSpawnPosition();
        this.packetQueue = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        this.tickHandler = new TickHandler();
        this.chunkManager = new ChunkManager();

        logger.info("Point de spawn calculé en " + world.getSpawnPosition());
    }

    public void start() throws IOException {
        this.running = true;
        socket = new DatagramSocket(this.port);
        System.out.println("Serveur en écoute sur le port " + this.port + "...");

        tickHandler.start();

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
                case "CONNECTION_INIT_ACK":
                    ConnectionACKHandler ackHandler = new ConnectionACKHandler(packetData, address, clientPort);
                    ackHandler.run();
                    break;
                case "CONNECTION_INIT":
                    ConnectionInitHandler connectionInitHandler = new ConnectionInitHandler(packetData, address, clientPort);
                    connectionInitHandler.run();
                    break;
                case "PLAYER_MOVE":
                    String playerId = packetData.get("uuid").asText();

                    Client client = clients.get(playerId);
                    if (client == null) break;

                    PlayerMoveHandler moveHandler = new PlayerMoveHandler(client, packetData, address, clientPort);
                    packetQueue.submit(moveHandler);
                    break;
                case "SKIN_REQUEST":
                    SkinRequestHandler skinHandler = new SkinRequestHandler(packetData, address, clientPort);
                    skinHandler.run();
                    break;
                case "PING_PACKET":
                    PingHandler pingHandler = new PingHandler(packetData, address, clientPort);
                    pingHandler.run();
                    break;
                case "CHUNK_REQUEST":
                    ChunkRequestHandler chunkRequestHandler = new ChunkRequestHandler(packetData, address, clientPort);
                    packetQueue.submit(chunkRequestHandler);
                    break;
                case "PLAYERS_LIST_REQUEST":
                    PlayersListHandler playersListHandler = new PlayersListHandler(packetData, address, clientPort);
                    playersListHandler.run();
                    break;
                default:
                    String message = "UNAUTHORIZED_PACKET";
                    buffer = message.getBytes(StandardCharsets.UTF_8);
                    this.sendPacket(new DatagramPacket(buffer, buffer.length, address, clientPort));
                    logger.error("Type de packet : " + packetType + " non reconnu.");
            }
        }
        socket.close();
    }

    public synchronized void sendPacket(DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MinecraftServer getInstance() {
        if(instance == null) {
            instance = new MinecraftServer(50000);
        }
        return instance;
    }

    public World getWorld() {
        return world;
    }

    public Map<String, Long> getLastActivities() {
        return lastActivities;
    }

    public Map<String, Client> getClients() {
        return clients;
    }

    public void broadcast(String message) {
        synchronized (this.getClients()) {
            byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            for (Client client : this.getClients().values()) {
                if (!client.isActive()) continue;
                packet.setAddress(client.getAddress());
                packet.setPort(client.getPort());

                this.sendPacket(packet);
            }
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public TickHandler getTickHandler() {
        return tickHandler;
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }
}
