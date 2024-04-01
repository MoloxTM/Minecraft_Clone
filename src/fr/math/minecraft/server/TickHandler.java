package fr.math.minecraft.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.payload.*;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.entity.Entity;
import fr.math.minecraft.shared.world.BreakedBlock;
import fr.math.minecraft.shared.world.DroppedItem;
import fr.math.minecraft.shared.world.PlacedBlock;
import fr.math.minecraft.shared.world.World;
import org.joml.Vector3i;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

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
        this.sendChat();
        tick++;
    }

    private void sendChat() {
        MinecraftServer server = MinecraftServer.getInstance();
        synchronized (server.getChatMessages()) {
            ChatStatePayload payload = new ChatStatePayload(server.getChatMessages());
            synchronized (server.getClients()) {
                for (Client client : server.getClients().values()) {

                    if (!client.isActive()) {
                        continue;
                    }

                    payload.send(client);
                }
            }
        }
    }

    private void sendWorld() {
        MinecraftServer server = MinecraftServer.getInstance();
        World world = server.getWorld();
        ObjectMapper mapper = new ObjectMapper();
        synchronized (world.getDroppedItems()) {
            ObjectNode node = mapper.createObjectNode();
            ArrayNode droppedItemArray = mapper.createArrayNode();

            for (DroppedItem droppedItem : world.getDroppedItems().values()) {

                droppedItem.update();
                JsonNode droppedItemNode = droppedItem.toJSON();

                droppedItemArray.add(droppedItemNode);
            }


            node.put("type", "DROPPED_ITEM_LIST");
            node.set("droppedItems", droppedItemArray);

            try {
                byte[] buffer = mapper.writeValueAsBytes(node);
                server.broadcast(buffer);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        synchronized (world.getPlacedBlocks()) {
            for (PlacedBlock placedBlock : world.getPlacedBlocks().values()) {
                PlacedBlockPayload payload = new PlacedBlockPayload(placedBlock);
                synchronized (server.getClients()) {
                    for (Client client : server.getClients().values()) {

                        if (!client.isActive()) {
                            continue;
                        }

                        payload.send(client);
                    }
                }
            }
        }

        synchronized (world.getBrokenBlocks()) {
            for (BreakedBlock breakedBlock : world.getBrokenBlocks().values()) {
                BrokenBlockPayload payload = new BrokenBlockPayload(breakedBlock);
                synchronized (server.getClients()) {
                    for (Client client : server.getClients().values()) {

                        if (!client.isActive()) {
                            continue;
                        }

                        payload.send(client);
                    }
                }
            }
        }

        synchronized (world.getEntities()) {
            List<Entity> deadEntities = new ArrayList<>();
            for (Entity entity : world.getEntities().values()) {
                try {
                    entity.update(world);
                    byte[] entityBuffer = mapper.writeValueAsBytes(entity.toJSONObject());
                    server.broadcast(entityBuffer);
                    if (entity.getHealth() <= 0.0f) {
                        deadEntities.add(entity);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (Entity entity : deadEntities) {
                synchronized (world.getEntities()) {
                    world.getEntities().remove(entity.getUuid());
                }
            }
        }
    }
}
