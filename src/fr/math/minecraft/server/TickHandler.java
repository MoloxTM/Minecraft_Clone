package fr.math.minecraft.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.manager.ClientManager;

import java.io.IOException;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class TickHandler extends Thread {

    private final static long TICK_PER_SECONDS = 20;
    private final static long TICK_RATE_MS = 1000 / TICK_PER_SECONDS;

    public TickHandler() {
        this.setName("TickHandler");
    }

    @Override
    public void run() {
        long tickTimer = 0;
        long lastTime = System.currentTimeMillis();
        while (true) {
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - lastTime;

            tickTimer += deltaTime;

            lastTime = currentTime;

            while (tickTimer >= TICK_RATE_MS) {
                tick();
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

                if (client.getName().equalsIgnoreCase("SuperUser")) {
                    System.out.println("SuperUser " + client.getPosition());
                }
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
                if (!client.isActive()) continue;
                client.update();
            }
        }
        this.sendPlayers();
        // this.sendChunks();
    }

    private void sendChunks() {
        MinecraftServer server = MinecraftServer.getInstance();
        ClientManager clientManager = new ClientManager();
        synchronized (server.getClients()) {
            for (Client client : server.getClients().values()) {
                if (!client.isActive()) continue;
                clientManager.sendNearChunks(client);
            }
        }
    }

}
