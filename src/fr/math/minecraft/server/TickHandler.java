package fr.math.minecraft.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.payload.InputPayload;
import fr.math.minecraft.server.payload.StatePayload;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.inventory.DroppedItem;
import fr.math.minecraft.shared.world.World;

import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;

public class TickHandler extends Thread {

    public final static long TICK_PER_SECONDS = 20;
    public final static long TICK_RATE_MS = 1000 / TICK_PER_SECONDS;
    private int tick;

    public TickHandler() {
        this.setName("TickHandler");
        this.tick = 0;
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

    private void sendChunks() {

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
            node.put("type", "PLAYERS_LIST");
            node.set("players", playersNode);
            node.put("tick", tick);

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

                if (!client.isActive()) continue;

                synchronized (client.getInputQueue()) {
                    int bufferIndex = -1;
                    while (!client.getInputQueue().isEmpty()) {
                        InputPayload inputPayload = client.getInputQueue().poll();

                        if (inputPayload == null) continue;

                        bufferIndex = inputPayload.getTick() % GameConfiguration.BUFFER_SIZE;
                        StatePayload statePayload = new StatePayload(inputPayload);
                        statePayload.predictMovement(server.getWorld(), client);

                        client.getStateBuffer()[bufferIndex] = statePayload;
                    }

                    if (bufferIndex != -1) {
                        StatePayload payload = client.getStateBuffer()[bufferIndex];
                        payload.send();
                    }
                }
            }
        }
        this.sendPlayers();
        this.sendWorld();
        tick++;
    }

    private void sendWorld() {
        MinecraftServer server = MinecraftServer.getInstance();
        World world = server.getWorld();
        ObjectMapper mapper = new ObjectMapper();
        for (DroppedItem droppedItem : world.getDroppedItems().values()) {

            droppedItem.update();
            JsonNode node = droppedItem.toJSON();
            try {
                byte[] buffer = mapper.writeValueAsBytes(node);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                synchronized (server.getClients()) {
                    for (Client client : server.getClients().values()) {

                        if (!client.isActive()) {
                            continue;
                        }

                        packet.setAddress(client.getAddress());
                        packet.setPort(client.getPort());

                        server.sendPacket(packet);
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
