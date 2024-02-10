package fr.math.minecraft.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.manager.ClientManager;
import fr.math.minecraft.server.payload.InputPayload;
import fr.math.minecraft.server.payload.StatePayload;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.lwjgl.glfw.GLFW.*;

public class TickHandler extends Thread {

    public final static long TICK_PER_SECONDS = 20;
    public final static long TICK_RATE_MS = 1000 / TICK_PER_SECONDS;
    private final static int BUFFER_SIZE = 1024;
    private final Queue<InputPayload> inputQueue;
    private final StatePayload[] stateBuffer;

    public TickHandler() {
        this.setName("TickHandler");
        this.inputQueue = new LinkedList<>();
        this.stateBuffer = new StatePayload[BUFFER_SIZE];
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

    public synchronized void enqueue(InputPayload payload) {
        inputQueue.add(payload);
    }

    private void tick() {
        MinecraftServer server = MinecraftServer.getInstance();
        int bufferIndex = -1;
        while (!inputQueue.isEmpty()) {
            InputPayload inputPayload = inputQueue.poll();

            if (inputPayload == null) continue;

            String uuid = inputPayload.getClientUuid();
            Client client = server.getClients().get(uuid);

            if (client == null) {
                continue;
            }

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

        this.sendChunks();
        // this.sendPlayers();
    }

    private void sendChunks() {
        MinecraftServer server = MinecraftServer.getInstance();
        ClientManager clientManager = new ClientManager();
        synchronized (server.getClients()) {
            for (Client client : server.getClients().values()) {
                clientManager.sendNearChunks(client);
            }
        }
    }

}
