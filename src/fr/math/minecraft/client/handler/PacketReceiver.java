package fr.math.minecraft.client.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.client.events.*;
import fr.math.minecraft.client.events.listeners.EventListener;
import fr.math.minecraft.client.events.listeners.PacketEventListener;
import fr.math.minecraft.client.events.listeners.PacketListener;
import fr.math.minecraft.client.events.listeners.PlayerListener;
import fr.math.minecraft.client.network.payload.StatePayload;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class PacketReceiver extends Thread {

    private final Game game;
    private final ArrayList<PacketEventListener> packetListeners;
    private final ArrayList<EventListener> eventListeners;
    private final static Logger logger = LoggerUtility.getClientLogger(PacketReceiver.class, LogType.TXT);
    private static PacketReceiver instance = null;
    private boolean receiving;
    private int ping;
    private double lastPingTime;

    private PacketReceiver() {
        this.setName("PacketReceiver");
        this.game = Game.getInstance();
        this.packetListeners = new ArrayList<>();
        this.eventListeners = new ArrayList<>();
        this.receiving = false;
        this.lastPingTime = System.currentTimeMillis();

        this.addEventListener(new PacketListener());
        this.addEventListener(new PlayerListener());
    }

    @Override
    public void run() {
        double lastTime = glfwGetTime();
        while (!glfwWindowShouldClose(game.getWindow())) {

            double currentTime = glfwGetTime();

            if (currentTime - lastTime >= 1.0) {
                game.getPlayer().setPing(ping);
                lastTime = currentTime;
            }

            this.handlePacket();

        }
    }

    private void handlePacket() {
        MinecraftClient client = Game.getInstance().getClient();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String response = client.receive();

            if (response == null) return;
            double currentTime = System.currentTimeMillis();

            ping = (int) (currentTime - lastPingTime);

            lastPingTime = currentTime;

            JsonNode responseData = mapper.readTree(response);
            String packetType = responseData.get("type").asText();

            switch (packetType) {
                case "PLAYER_JOIN":
                    // System.out.println(packetType);
                    this.notifyEvent(new PlayerJoinEvent(responseData));
                    break;
                case "CHUNK_PACKET":
                    this.notifyEvent(new ChunkPacketEvent(responseData));
                    break;
                case "PLAYERS_LIST_PACKET":
                    this.notifyEvent(new PlayerListPacketEvent((ArrayNode) responseData.get("players")));
                    break;
                case "STATE_PAYLOAD":
                    StatePayload statePayload = new StatePayload(responseData);
                    this.notifyEvent(new ServerStateEvent(statePayload));
                    break;
                case "SKIN_PACKET":
                    String base64Skin = responseData.get("skin").asText();
                    String playerUuid = responseData.get("uuid").asText();
                    this.notifyEvent(new SkinPacketEvent(base64Skin, playerUuid));
                    break;
                default:
                    logger.warn("Le packet " + packetType + " est inconnu et a été ignoré.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyEvent(PlayerJoinEvent event) {
        for (EventListener listener : eventListeners) {
            listener.onPlayerJoin(event);
        }
    }

    private void notifyEvent(ServerStateEvent event) {
        for (PacketEventListener listener : packetListeners) {
            listener.onServerState(event);
        }
    }

    private void notifyEvent(ChunkPacketEvent event) {
        for (PacketEventListener listener : packetListeners) {
            listener.onChunkPacket(event);
        }
    }

    private void notifyEvent(PlayerListPacketEvent event) {
        for (PacketEventListener listener : packetListeners) {
            listener.onPlayerListPacket(event);
        }
    }

    private void notifyEvent(SkinPacketEvent event) {
        for (PacketEventListener listener : packetListeners) {
            listener.onSkinPacket(event);
        }
    }

    public void addEventListener(EventListener listener) {
        eventListeners.add(listener);
    }

    public void addEventListener(PacketEventListener listener) {
        packetListeners.add(listener);
    }

    public static PacketReceiver getInstance() {
        if (instance == null) {
            instance = new PacketReceiver();
        }
        return instance;
    }

    public boolean isReceiving() {
        return receiving;
    }

    public void setReceiving(boolean receiving) {
        this.receiving = receiving;
    }
}
