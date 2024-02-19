package fr.math.minecraft.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.manager.ChunkManager;
import fr.math.minecraft.server.manager.ClientManager;
import fr.math.minecraft.server.payload.InputPayload;
import fr.math.minecraft.server.payload.StatePayload;
import fr.math.minecraft.server.world.Coordinates;
import fr.math.minecraft.server.world.ServerChunk;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TickHandler extends Thread {

    public final static long TICK_PER_SECONDS = 20;
    public final static long TICK_RATE_MS = 1000 / TICK_PER_SECONDS;
    private final static int BUFFER_SIZE = 1024;
    private final StatePayload[] stateBuffer;

    public TickHandler() {
        this.setName("TickHandler");
        this.stateBuffer = new StatePayload[BUFFER_SIZE];
    }

    @Override
    public void run() {
        long tickTimer = 0;
        long lastTickTime = 0;
        long lastTime = System.currentTimeMillis();
        int tickRate = 0;
        MinecraftServer server = MinecraftServer.getInstance();

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

    private void tick() {
        MinecraftServer server = MinecraftServer.getInstance();
        int bufferIndex = -1;
        synchronized (server.getClients()) {
            for (Client client : server.getClients().values()) {
                synchronized (client.getInputQueue()) {
                    while (!client.getInputQueue().isEmpty()) {
                        InputPayload inputPayload = client.getInputQueue().poll();

                        if (inputPayload == null) continue;

                        if (!client.isActive()) {
                            continue;
                        }

                        bufferIndex = inputPayload.getTick() % BUFFER_SIZE;
                        StatePayload statePayload = new StatePayload(inputPayload);
                        statePayload.predictMovement(client);

                        //statePayload.send();

                        stateBuffer[bufferIndex] = statePayload;
                    }

                    if (bufferIndex != -1) {
                        StatePayload payload = stateBuffer[bufferIndex];
                        payload.send();
                    }
                }
            }
        }

        this.sendChunks();
        this.sendPlayers();
    }

    private void sendChunks() {
        MinecraftServer server = MinecraftServer.getInstance();
        ChunkManager chunkManager = server.getChunkManager();
        synchronized (server.getClients()) {
            for (Client client : server.getClients().values()) {
                synchronized (client.getNearChunks()) {
                    ServerChunk chunk = client.getNearChunks().peek();

                    if (chunk == null) {
                        continue;
                    }

                    chunkManager.sendChunk(client, chunk);
                }
            }
        }
    }

}
