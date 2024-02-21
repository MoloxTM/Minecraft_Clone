package fr.math.minecraft.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.manager.ChunkManager;
import fr.math.minecraft.server.manager.ClientManager;
import fr.math.minecraft.server.payload.InputPayload;
import fr.math.minecraft.server.payload.StatePayload;
import fr.math.minecraft.server.worker.ChunkSender;
import fr.math.minecraft.server.world.Coordinates;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.shared.GameConfiguration;

import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TickHandler extends Thread {

    public final static long TICK_PER_SECONDS = 20;
    public final static long TICK_RATE_MS = 1000 / TICK_PER_SECONDS;

    public TickHandler() {
        this.setName("TickHandler");
    }

    @Override
    public void run() {
        long tickTimer = 0;
        long lastTickTime = 0;
        long lastTime = System.currentTimeMillis();
        int tickRate = 0;

        while (true) {
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - lastTime;

            tickTimer += deltaTime;

            if (currentTime - lastTickTime >= 1000) {
                System.out.println("TPS : " + tickRate);
                tickRate = 0;
                lastTickTime = currentTime;
            }

            lastTime = currentTime;

            while (tickTimer >= TICK_RATE_MS) {
                tick();
                tickRate++;
                tickTimer -= TICK_RATE_MS;
            }
        }
    }

    public void enqueue(InputPayload payload) {
        MinecraftServer server = MinecraftServer.getInstance();
        Client client = server.getClients().get(payload.getClientUuid());
        if (client == null) {
            return;
        }
        synchronized (client.getInputQueue()) {
            client.getInputQueue().add(payload);
        }
    }

    private void sendPlayers() {
        MinecraftServer server = MinecraftServer.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        ArrayNode playersNode = mapper.createArrayNode();
        synchronized (server.getClients()) {
            for (Client client : server.getClients().values()) {
                if (!client.isActive()) continue;
                playersNode.add(client.toJSON());
            }
        }
        try {
            node.put("type", "PLAYERS_LIST_PACKET");
            node.set("players", playersNode);

            String message = mapper.writeValueAsString(node);

            server.broadcast(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tick() {
        MinecraftServer server = MinecraftServer.getInstance();
        synchronized (server.getClients()) {
            for (Client client : server.getClients().values()) {

                synchronized (client.getInputQueue()) {
                    int bufferIndex = -1;
                    while (!client.getInputQueue().isEmpty()) {
                        InputPayload inputPayload = client.getInputQueue().poll();

                        if (inputPayload == null) continue;

                        if (!client.isActive()) {
                            continue;
                        }

                        bufferIndex = inputPayload.getTick() % GameConfiguration.BUFFER_SIZE;
                        StatePayload statePayload = new StatePayload(inputPayload);
                        statePayload.predictMovement(client);

                        //statePayload.send();

                        client.getStateBuffer()[bufferIndex] = statePayload;
                    }

                    if (bufferIndex != -1) {
                        StatePayload payload = client.getStateBuffer()[bufferIndex];
                        payload.send();

                        ObjectMapper mapper = new ObjectMapper();
                        ObjectNode node = payload.toJSON();
                        node.put("type", "PLAYER_STATE");
                        node.put("uuid", client.getUuid());
                        String payloadJSONData = null;

                        try {
                            payloadJSONData = mapper.writeValueAsString(node);

                            synchronized (server.getClients()) {
                                byte[] buffer = payloadJSONData.getBytes(StandardCharsets.UTF_8);
                                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                                for (Client onlineClient : server.getClients().values()) {
                                    if (onlineClient.getUuid().equalsIgnoreCase(client.getUuid())) {
                                        continue;
                                    }

                                    packet.setAddress(onlineClient.getAddress());
                                    packet.setPort(onlineClient.getPort());

                                    server.sendPacket(packet);
                                }
                            }
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
